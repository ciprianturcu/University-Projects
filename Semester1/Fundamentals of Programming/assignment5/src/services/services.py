import copy
from src.domain.student import Student


class StudentServices:

    def __init__(self):
        self._student_repository = []
        self._student_repository_history = []

    def generate_initial_student_list(self):
        """
        Function to create 10 default students at runtime
        """
        self.add_student_to_repository(Student(1, "Mircea", 10))
        self.add_student_to_repository(Student(14, "Cristi", 10))
        self.add_student_to_repository(Student(156, "Andrei", 10))
        self.add_student_to_repository(Student(80, "Gabi", 20))
        self.add_student_to_repository(Student(40, "Cristina", 20))
        self.add_student_to_repository(Student(55, "Alexandra", 20))
        self.add_student_to_repository(Student(1000, "Maria", 15))
        self.add_student_to_repository(Student(107, "Rares", 15))
        self.add_student_to_repository(Student(144, "Edi", 60))
        self.add_student_to_repository(Student(1023, "Cipri", 60))

    def add_student_to_repository(self, student):
        """
        Function to add a new student in the current repository
        :param student: object
        :return:
        """
        if isinstance(student.id, int) and isinstance(student.group, int) and student.id > 0 and student.group > 0:
            for i in range(len(self._student_repository)):
                Student = self._student_repository[i]
                if Student.id == student.id:
                    raise ValueError("Cannot add! Student id already in repository.")
            self._student_repository.append(student)
        else:
            raise ValueError("Wrong data type. Student id and student group should be a positive integer.")

    def return_repository(self):
        """
        Function to get the current repository
        :return: the repository in use
        """
        return self._student_repository

    def create_student_entity(self, id, name, group):
        """
        Function to create a student object
        :param id: student id
        :param name: student name
        :param group: the group of student
        :return: Student object containing the id, name and group
        """
        return Student(id, name, group)

    def filter_by_group(self, given_group):
        """
        Function to remove from the repository all students from a given group
        :param given_group: group of students to be removed
        """
        if isinstance(given_group, int):
            found = 0
            for student in self._student_repository:
                if student.group == given_group:
                    found = 1
            if found != 0:
                length = len(self._student_repository) - 1
                while length >= 0:
                    student = self._student_repository[length]
                    if student.group == given_group:
                        del self._student_repository[length]
                    length -= 1
            else:
                raise ValueError("Group to be filtered not found!")
        else:
            raise ValueError("Wrong data type. Group should be a positive integer")

    def add_to_history_repository(self):
        """
        Function to add current repository to a list containing a history of all repositories
        """
        copy_of_current_repository = copy.deepcopy(self._student_repository)
        self._student_repository_history.append(copy_of_current_repository)

    def undo_operation(self):
        """
        Function to undo the last done operation
        """
        if not len(self._student_repository_history) > 0:
            raise ValueError("Cannot undo. No history found.")
        copy_of_last_from_history = copy.deepcopy(self._student_repository_history[-1])
        self._student_repository = copy_of_last_from_history
        self._student_repository_history.pop()

    def check_integrity_of_input(self, id, group, input_option):
        """
        Function to check integrity of input, based on the input option
        :param id: group id
        :param group: group input
        :param input_option: 1 if called function was add, 3 if called function was filter
        """
        if input_option == 1:
            if not id.isdecimal():
                raise ValueError("ID should be a positive integer.")
            if int(id) < 0:
                raise ValueError("Student id cannot be bellow zero!")
        if not group.isdecimal():
            raise ValueError("Student group should be a positive integer.")
        if int(group) < 0:
            raise ValueError("Student group should be a positive integer!")
