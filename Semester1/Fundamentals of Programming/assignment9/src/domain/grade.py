from src.domain.validator_exception import ValidatorException


class Grade:
    def __init__(self, _assignment_to_student_id, grade):
        self.__id = _assignment_to_student_id
        self.__grade = grade

    @staticmethod
    def get_string_form(object):
        string = f"{object.id[0]},{object.id[1]},{object.grade}\n"
        return string

    @staticmethod
    def get_from_string(string):
        list = string.strip().split(",")
        return Grade((list[0], list[1]), list[2])

    @property
    def id(self):
        return self.__id

    @property
    def grade(self):
        return self.__grade

    @grade.setter
    def grade(self, new_grade):
        self.__grade = new_grade

    def __str__(self):
        _assignment_id = self.__id[0]
        _student_id = self.__id[1]
        return "Assignment ID:" + str(_assignment_id) + " Student ID:" + str(_student_id) + " Grade:" + str(
            self.__grade)


class GradeValidator:
    """
    Class to validate the data of a grade before creating the object.
    """

    def _is_id_valid(self, grade_id):
        """
        method to check if the id is a positive integer.
        :param grade_id: the id of the grade
        :return: True if the grade id is a positive integer, False otherwise
        """
        assignment_id = grade_id[0]
        student_id = grade_id[1]
        if not assignment_id.isdecimal():
            return False
        if not student_id.isdecimal():
            return False
        return True

    def _is_grade_number_valid(self, grade_number):
        """
        method to check if the grade value is a positive integer between 0 and 10, 0 means ungraded assignment
        :param grade_number:
        :return:
        """
        if not grade_number.isdecimal():
            return False
        if int(grade_number) < 0 or int(grade_number) > 10:
            return False
        return True

    def validate(self, grade):
        """
        method to validate a grade type object
        :param grade: object to be validated
        :return: True if all data matches the Grade object criteria
        """
        if not isinstance(grade, Grade):
            raise TypeError("Can only validate Grade objects!")
        _error = []
        if not self._is_grade_number_valid(grade.grade):
            _error.append("Grade number should pe a positive integer between 0 and 10.")
        if not self._is_id_valid(grade.id):
            _error.append("Grade must have a positive integer tuple id formed from the Assignment id and Student id.")

        if len(_error) > 0:
            raise ValidatorException(_error)

        return True
