from src.domain.assignments import Assignment
import random

from src.services.undo_service import Operation, ComplexOperation, Call


class AssignmentService:
    def __init__(self, undo_redo_service, assignment_repository, assignment_validator, grade_service, grade_repository):
        self.__validator = assignment_validator
        self.__assignments_data = assignment_repository
        self.__grade_service = grade_service
        self.__undo_redo_service = undo_redo_service
        self.__grade_repository = grade_repository

    def generate_assignments_data(self):
        """
        method to initialize the assignments repository at program runtime, with randomly generated data.
        :return: 20 assignment objects in the repository
        """
        assignments_description = ["lab1", "lab2", "lab3", "lab4", "lab5", "lab6", "lab7", "lab8", "lab9", "lab10",
                                   "lab11", "lab12", "lab13", "lab14", "lab15", "lab16", "lab17", "lab18", "lab19",
                                   "lab20"]
        assignments_deadline = ['16/12/2021', '10/3/2021', '4/1/2022', '11/6/2022', '17/6/2022', '21/12/2021',
                                '29/12/2021', '20/11/2021', '11/4/2022', '10/12/2021']
        for i in range(1, 21):
            assignment = Assignment(str(i), assignments_description[random.randint(0, 19)],
                                    random.choice(assignments_deadline))
            self.__assignments_data.add_entity(assignment)

    def get_values(self):
        """
        method to return a list of all objects in the assignment repository
        :return: a list with all assignments
        """
        return self.__assignments_data.get_all()

    def add(self, assignment):
        """
        method to add a new assignment object to the repository
        :param assignment: object of type Assignment
        :return: adds to the repository the new assignment
        """
        self.__validator.validate(assignment)
        undo_call = Call(self.__assignments_data.delete, assignment.id)
        redo_call = Call(self.__assignments_data.add_entity, assignment)
        operations_undo_redo = [Operation(undo_call, redo_call)]
        self.__undo_redo_service.record(ComplexOperation(operations_undo_redo))
        self.__assignments_data.add_entity(assignment)

    def remove(self, assignment_id):
        """
        method to remove an assignment from the repository
        :param assignment_id: the id of the assignment to be removed
        :return: error if the assignment is not in the repository or if the id is not a integer, otherwise removes the assignment with the given id
        """
        if assignment_id.isdecimal():
            if self.__assignments_data.has_element(assignment_id):
                assignment = self.__assignments_data[assignment_id]
                self.__assignments_data.delete(assignment_id)
                undo_call = Call(self.__assignments_data.add_entity, assignment)
                redo_call = Call(self.__assignments_data.delete, assignment_id)
                operations_undo_redo = [Operation(undo_call, redo_call)]
                grades = self.__grade_service.get_values()
                for grade in grades:
                    grade__assignment_id = grade.id[0]
                    if grade__assignment_id == assignment_id:
                        self.__grade_service.remove(grade.id)
                        undo_call = Call(self.__grade_repository.add_entity, grade)
                        redo_call = Call(self.__grade_repository.delete, grade.id)
                        operations_undo_redo.append(Operation(undo_call, redo_call))
                self.__undo_redo_service.record(ComplexOperation(operations_undo_redo))
            else:
                raise ValueError("Assignment not found!")
        else:
            raise ValueError("ID should be an integer!")

    def update(self, updated_assignment):
        """
        method to update an assignment's description or deadline (given id must be from the repository, meaning it cannot be changed.)
        :param updated_assignment: assignment object with updated data
        :return: adds the new data to the assignment with the id.
        """
        if not self.__assignments_data.has_element(updated_assignment.id):
            raise ValueError("Object not found! Cannot update.")
        assignment = self.__assignments_data[updated_assignment.id]
        self.__assignments_data[updated_assignment.id] = updated_assignment
        undo_call = Call(self.__assignments_data.update, assignment.id, assignment)
        redo_call = Call(self.__assignments_data.update, updated_assignment.id, updated_assignment)
        operations_undo_redo = [Operation(undo_call, redo_call)]
        self.__undo_redo_service.record(ComplexOperation(operations_undo_redo))
