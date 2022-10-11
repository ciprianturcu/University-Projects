import datetime
from src.domain.validator_exception import ValidatorException


class Assignment:
    def __init__(self, _id, description, deadline):
        self.__id = _id
        self.__description = description
        self.__deadline = deadline

    @staticmethod
    def get_string_form(object):
        string = f"{object.id},{object.description},{str(object.deadline)}\n"
        return string

    @staticmethod
    def get_from_string(string):
        list = string.strip().split(",")
        return Assignment(list[0], list[1], list[2])

    @property
    def id(self):
        return self.__id

    @property
    def description(self):
        return self.__description

    @description.setter
    def description(self, new_description):
        self.__description = new_description

    @property
    def deadline(self):
        return self.__deadline

    @deadline.setter
    def deadline(self, new_deadline):
        self.__deadline = new_deadline

    def __str__(self):
        return "Id:" + str(self.__id) + " Description:" + self.__description + " Deadline:" + str(self.__deadline)


class AssignmentValidator:
    """
        Class to validate the data of an assignment before creating the object.
        """
    def _is_id_valid(self, assignment_id):
        """
        method to check if the id is a positive integer.
        :param assignment_id: the id of the assignment
        :return: True if the assignment id is a positive integer, False otherwise
        """
        if not assignment_id.isdecimal():
            return False
        return True

    def _is_deadline_valid(self, assignment_deadline):
        """
        method to check if the deadline of the assignment is a valid date.
        :param assignment_deadline: string containing the assignment deadline
        :return: True if the deadline is a valid date, False otherwise
        """
        try:
            datetime.datetime.strptime(assignment_deadline, "%d/%m/%Y")
        except ValueError:
            return False
        return True

    def validate(self, assignment):
        """
        method to validate an assignment type object
        :param assignment: object to be validated
        :return: True if all data matches the Assignment object criteria
        """
        if not isinstance(assignment, Assignment):
            raise TypeError("Can only validate Assignment objects!")
        _errors = []
        if not self._is_id_valid(assignment.id):
            _errors.append("Assignment must have an integer id!")
        if not self._is_deadline_valid(assignment.deadline):
            _errors.append("Assignment must have a valid deadline!")

        if len(_errors) > 0:
            raise ValidatorException(_errors)
        return True