from src.domain.validator_exception import ValidatorException


class Student:
    def __init__(self, _id, name, group):
        self.__id = _id
        self.__name = name
        self.__group = group

    @staticmethod
    def get_string_form(object):
        string = f"{object.id},{object.name},{str(object.group)}\n"
        return string

    @staticmethod
    def get_from_string(string):
        list = string.strip().split(",")
        return Student(list[0], list[1], int(list[2]))

    @property
    def id(self):
        return self.__id

    @property
    def name(self):
        return self.__name

    @name.setter
    def name(self, new_name):
        self.__name = new_name

    @property
    def group(self):
        return self.__group

    @group.setter
    def group(self, new_group):
        self.__group = new_group

    def __str__(self):
        return "Id:" + str(self.__id) + " Name:" + self.__name + " Group:" + str(self.__group)


class StudentValidator:
    """
    Class to validate the data of a student before creating the object.
    """

    def _is_id_valid(self, student_id):
        """
        method to check if the id is a positive integer.
        :param student_id: the id of the student
        :return: True if the student id is a positive integer, False otherwise
        """
        if not student_id.isdecimal():
            return False
        return True

    def _is_group_valid(self, student_group):
        """
        method to check if the group is a positive integer.
        :param student_group: the group of the student
        :return: True if the student group is a positive integer, False otherwise
        """
        if int(student_group) < 0:
            return False
        return True

    def validate(self, student):
        """
        method to validate a student type object
        :param student: object to be validated
        :return: True if all data matches the Student object criteria
        """
        if not isinstance(student, Student):
            raise TypeError("Can only validate Student objects!")
        _errors = []
        if not self._is_id_valid(student.id):
            _errors.append("Student must have an integer id. ")
        if not self._is_group_valid(student.group):
            _errors.append("Student must have an integer group. ")
        if len(_errors) > 0:
            raise ValidatorException(_errors)

        return True


class StudentWithAverageGrade:
    """
    DTO- used in creating objects for the statistic in which students appear in a top in descending order by grade average.
    """

    def __init__(self, _id, name, grade_value):
        self.__id = _id
        self.__name = name
        self.__grade_value = grade_value

    @property
    def id(self):
        return self.__id

    @property
    def name(self):
        return self.__name

    @name.setter
    def name(self, new_name):
        self.__name = new_name

    @property
    def grade_value(self):
        return self.__grade_value

    @grade_value.setter
    def grade_value(self, new_grade_value):
        self.__grade_value = new_grade_value

    def __str__(self):
        return "Student id: " + self.__id + " Name: " + self.__name + " has the average grade :" + self.__grade_value
