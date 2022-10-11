from src.domain.student import Student
from src.services.undo_service import Call, ComplexOperation, Operation
import random


class StudentService:
    def __init__(self, undo_redo_service, student_repository, student_validator, grade_service, grade_repository):
        self.__data = student_repository
        self.__validator = student_validator
        self.__grade_service = grade_service
        self.__undo_redo_service = undo_redo_service
        self.__grade_repository = grade_repository

    def generate_students(self):
        """
        method to initialize the students repository at program runtime, with randomly generated data.
        :return: 20 student objects in the repository
        """
        student_names = ["Mircea", "Alex", "Andrei", "Cristi", "Bianca", "Alexandra", "Maria", "Ioana", "Paul",
                         "Dragos", "Daniel", "Cipri", "Mihai", "Ion", "Ioana", "Delia", "Florina", "Alin", "Radu",
                         "Damian"]
        for i in range(1, 21):
            student = Student(str(i), student_names[random.randint(0, 19)], random.randint(1, 100))
            self.__data.add_entity(student)

    def get_values(self):
        """
        method to return a list of all objects in the student repository
        :return: a list with all students
        """
        return self.__data.get_all()

    def add(self, student):
        """
        method to add a new student object to the repository
        :param student: object of type Student
        :return: adds to the repository the new student
        """
        self.__validator.validate(student)
        self.__data.add_entity(student)
        undo_call = Call(self.__data.delete, student.id)
        redo_call = Call(self.__data.add_entity, student)
        operations_undo_redo = [Operation(undo_call, redo_call)]
        self.__undo_redo_service.record(ComplexOperation(operations_undo_redo))

    def remove(self, student_id):
        """
        method to remove a student from the repository
        :param student_id: the id of the student to be removed
        :return: error if the student is not in the repository or if the id is not a integer, otherwise removes the student with the given id
        """

        if student_id.isdecimal():
            if self.__data.has_element(student_id):
                student = self.__data[student_id]
                self.__data.delete(student_id)
                undo_call = Call(self.__data.add_entity, student)
                redo_call = Call(self.__data.delete, student_id)
                operations_undo_redo = [Operation(undo_call, redo_call)]
                grades = self.__grade_service.get_values()
                for grade in grades:
                    grade__student_id = grade.id[1]
                    if grade__student_id == student_id:
                        self.__grade_service.remove(grade.id)
                        undo_call = Call(self.__grade_repository.add_entity, grade)
                        redo_call = Call(self.__grade_repository.delete, grade.id)
                        operations_undo_redo.append(Operation(undo_call, redo_call))
                self.__undo_redo_service.record(ComplexOperation(operations_undo_redo))
            else:
                raise ValueError("Student not found!")
        else:
            raise ValueError("ID should be an integer!")

    def update(self, updated_student):
        """
        method to update a student's name or group (given id must be from the repository, meaning it cannot be changed.)
        :param updated_student: student object with updated data
        :return: adds the new data to the student with the id.
        """
        self.__validator.validate(updated_student)
        if not self.__data.has_element(updated_student.id):
            raise ValueError("Object not found! Cannot update.")
        student = self.__data[updated_student.id]
        self.__data[updated_student.id] = updated_student
        undo_call = Call(self.__data.update, student.id, student)
        redo_call = Call(self.__data.update, updated_student.id, updated_student)
        operations_undo_redo = [Operation(undo_call, redo_call)]
        self.__undo_redo_service.record(ComplexOperation(operations_undo_redo))
