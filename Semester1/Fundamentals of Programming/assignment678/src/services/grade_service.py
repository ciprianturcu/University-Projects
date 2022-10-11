from src.repository.repository import RepositoryException
from src.domain.grade import Grade
from src.domain.student import StudentWithAverageGrade
import random
import datetime

from src.services.undo_service import Operation, Call, ComplexOperation


class GradeService:
    def __init__(self, undo_redo_service, grade_repository, grade_validator, _student_repository,
                 _assignment_repository):
        self.__validator = grade_validator
        self.__data = grade_repository
        self.__student_repository = _student_repository
        self.__assignment_repository = _assignment_repository
        self.__undo_redo_service = undo_redo_service

    def generate_ungraded_assignments(self):
        """
        method to generate ungraded assignments at program start (ungraded -> grade value = 0 ; graded -> grade value = [1,10])
        :return: adds to the grade repository 40 ungraded assignments
        """
        student_list = self.__student_repository.get_all()
        assignment_list = self.__assignment_repository.get_all()
        for i in range(1, 41):
            done_unique_iteration = False
            while done_unique_iteration is False:
                try:
                    student = random.choice(student_list)
                    assignment = random.choice(assignment_list)
                    grade = Grade((assignment.id, student.id), "0")
                    self.__data.add_entity(grade)
                    done_unique_iteration = True
                except:
                    pass

    def generate_graded_assignments(self):
        """
        method to grade 20 assignments at program run.
        :return:
        """
        ungraded_list = self.__data.get_all()
        for iteration in range(21):
            ungraded_assignment = ungraded_list[iteration]
            ungraded_assignment.grade = str(random.randint(1, 10))
            self.__data[ungraded_assignment.id] = ungraded_assignment

    def give_group_of_students_assignment(self, assignment_id, student_group):
        """
        method to give a group of students a specific assignment
        :param assignment_id: the assignment that needs to be given
        :param student_group: group of students whom are given the assignment
        :return: adds to the grade repository new assignments if the students in the group did not have the assignments previously.
        """
        student_list = self.__student_repository.get_all()
        operations_undo_redo = []
        for students in student_list:
            if int(students.group) == int(student_group):
                grade = Grade((assignment_id, students.id), "0")
                self.__validator.validate(grade)
                try:
                    self.__data.add_entity(grade)
                    undo_call = Call(self.__data.delete, grade.id)
                    redo_call = Call(self.__data.add_entity, grade)
                    operations_undo_redo.append(Operation(undo_call, redo_call))
                except RepositoryException:
                    pass
        self.__undo_redo_service.record(ComplexOperation(operations_undo_redo))

    def get_ungraded_assignments_of_students(self):
        """
        method to get the list of students assignments which are not graded.
        :return:
        """
        assignment_list = self.__data.get_all()
        student_list = self.__student_repository.get_all()
        student_assignments = list()
        for student in student_list:
            individual_student_assignment_list = list()
            for assignment in assignment_list:
                if assignment.id[1] == student.id and int(assignment.grade) == 0:
                    individual_student_assignment_list.append(assignment.id[0])
            student_assignments.append(individual_student_assignment_list)
        return student_assignments

    def add(self, grade):
        """
        method to add a new assignment with a grade.
        :param grade: ungraded assignment
        :return: adds to the grade repository a new object of type Grade
        """
        assignment_id = grade.id[0]
        student_id = grade.id[1]
        if self._check_id_existence_in_list(assignment_id, self.__assignment_repository.get_all()) is False:
            raise ValueError("Assignment id does not exist.")
        if self._check_id_existence_in_list(student_id, self.__student_repository.get_all()) is False:
            raise ValueError("Student id does not exist.")
        self.__validator.validate(grade)
        self.__data.add_entity(grade)
        undo_call = Call(self.__data.delete, grade.id)
        redo_call = Call(self.__data.add_entity, grade)
        operations_undo_redo = [Operation(undo_call, redo_call)]
        self.__undo_redo_service.record(ComplexOperation(operations_undo_redo))

    def _check_id_existence_in_list(self, id, object_list):
        """
        method to check the existence of an object in a object list
        :param id: id of the searched object
        :param object_list: list of objects of specific type
        :return: True if the searched object is in the list, False otherwise
        """
        found = False
        for elements in object_list:
            if elements.id == id:
                found = True
        return found

    def get_assignments_descending_order(self):
        """
        method to get a list with all assignments ordered in descending order by grade. Ungraded assignments included
        :return: descending ordered list of Grade objects
        """
        unordered_list = self.__data.get_all()
        order_list = sorted(unordered_list, key=lambda element: int(element.grade), reverse=True)
        return order_list

    def get_late_assignments(self):
        """
        method to get a list of ungraded assignments that have passed their deadline
        :return: list of late and not graded assignments
        """
        assignment_list = self.__data.get_all()
        assignment_data = self.__assignment_repository.get_all()
        result_list = list()
        for assignments in assignment_list:
            assignment_id = assignments.id[0]
            assignment_grade_value = assignments.grade
            for i in range(len(assignment_data)):
                if assignment_id == assignment_data[i].id and assignment_grade_value == "0":
                    assignment_deadline = datetime.datetime.strptime(assignment_data[i].deadline, "%d/%m/%Y")
                    current_time = datetime.datetime.now()
                    if current_time > assignment_deadline:
                        result_list.append(assignments)
        return result_list

    def get_students_top(self):
        """
        method to get a list containing a top of all students with graded assignments. descending order by grade average.
        :return: list containing the top of the students
        """
        student_list = self.__student_repository.get_all()
        assignment_list = self.__data.get_all()
        unordered_list = list()
        for i in range(len(student_list)):
            student_id = student_list[i].id
            indices = [index for index, element in enumerate(assignment_list) if
                       element.id[1] == student_id and element.grade != "0"]
            if len(indices) > 0:
                grade_sum = 0
                for j in range(len(indices)):
                    index = indices[j]
                    grade_sum += int(assignment_list[index].grade)
                student_with_grade = StudentWithAverageGrade(student_id, student_list[i].name, grade_sum / len(indices))
                unordered_list.append(student_with_grade)
        result_list = sorted(unordered_list, key=lambda element: element.grade_value, reverse=True)
        return result_list

    def update(self, updated_grade):
        """
        method to update a Grade object (assignment)
        :param updated_grade: updated Grade object
        :return: updates the element in the repository corresponding to the id of the new element
        """
        if not self.__data.has_element(updated_grade.id):
            raise ValueError("Object not found! Cannot update.")
        self.__validator.validate(updated_grade)
        self.__data[updated_grade.id] = updated_grade
        undo_call = Call(self.__data.update, updated_grade.id, self.__data[updated_grade.id])
        redo_call = Call(self.__data.update, updated_grade.id, updated_grade)
        operations_undo_redo = [Operation(undo_call, redo_call)]
        self.__undo_redo_service.record(ComplexOperation(operations_undo_redo))

    def remove(self, grade_id):
        """
        method to remove an assignment by a given id
        :param grade_id: assignment id to be removed
        :return: removes an assignment Grade object
        """
        if self.__data.has_element(grade_id):
            self.__data.delete(grade_id)
        else:
            raise ValueError("Grade not found!")

    def get_values(self):
        """
        method returning a list of objects from the repository
        :return: list containing all objects of the repository
        """
        return self.__data.get_all()
