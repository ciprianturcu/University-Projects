import src.repository.repository
from src.services.student_service import StudentService, Student
from src.services.assignments_service import AssignmentService, Assignment
from src.services.grade_service import GradeService, Grade
from src.domain.validator_exception import ValidatorException
from src.repository.repository import RepositoryException
from src.services.undo_service import UndoServiceException


class Ui:
    def __init__(self, undo_redo_services, student_services, assignment_services, grade_services):
        self._student_functions = student_services
        self._assignments_functions = assignment_services
        self._grade_functions = grade_services
        self._undo_redo_services = undo_redo_services

    def start(self):
        """
        start method, calls initializing repository service functions, makes the necessary calls for all features based on input.
        """
        self._student_functions.generate_students()
        self._assignments_functions.generate_assignments_data()
        self._grade_functions.generate_ungraded_assignments()
        self._grade_functions.generate_graded_assignments()
        while True:
            self.display_main_menu()
            try:
                input_option = int(input("Option>"))
                if input_option == 1:
                    self.start__manage_students_and_assignments_option()
                elif input_option == 2:
                    self.start__give_assignments_student_group()
                elif input_option == 3:
                    self.start__give_grade_to_student_assignment()
                elif input_option == 4:
                    self.start__create_statistics()
                elif input_option == 5:
                    self._undo_redo_services.undo()
                elif input_option == 6:
                    self._undo_redo_services.redo()
                elif input_option == 7:
                    print("Program finished!")
                    return
                else:
                    raise ValueError("Invalid option!")
            except ValueError as ve:
                print(str(ve))
                print()
            except RepositoryException as re:
                print(str(re))
                print()
            except ValidatorException as vl:
                print(str(vl))
                print()
            except UndoServiceException as us:
                print(str(us))
                print()

    """
    Start functions that make the necessary calls to the service functions    
    """

    def start__manage_students_and_assignments_option(self):
        self.display_menu_for_students_or_assignments()
        input_option = int(input("Option>"))
        if input_option == 1:
            self.start__manage_students()
        elif input_option == 2:
            self.start__manage_assignments()
        else:
            raise ValueError("Invalid option!")

    def start__manage_students(self):
        self.display_menu_manage_students()
        input_option = int(input("Option>"))
        if input_option == 1:  # add student
            student = self.read_student_input()
            self._student_functions.add(student)
        elif input_option == 2:  # remove student
            id_to_remove_input = input("ID>")
            self._student_functions.remove(id_to_remove_input)
        elif input_option == 3:  # update student
            student = self.read_student_input()
            self._student_functions.update(student)
        elif input_option == 4:  # list student
            self.display(self._student_functions.get_values())
        else:
            raise ValueError("Invalid option!")

    def start__manage_assignments(self):
        self.display_menu_manage_assignments()
        input_option = int(input("Option>"))
        if input_option == 1:  # add assignment
            assignment = self.read_assignments_input()
            self._assignments_functions.add(assignment)
        elif input_option == 2:  # remove assignment
            id_to_remove_input = input("ID>")
            self._assignments_functions.remove(id_to_remove_input)
        elif input_option == 3:  # update assignment
            assignment = self.read_assignments_input()
            self._assignments_functions.update(assignment)
        elif input_option == 4:  # list assignment
            self.display(self._assignments_functions.get_values())
        else:
            raise ValueError("Invalid option!")

    def start__give_assignments_student_group(self):
        self.display_menu_give_assignment_student_or_group()
        input_option = int(input("Option>"))
        if input_option == 1:
            self.start__give_assignment_student()
        elif input_option == 2:
            self.start__give_assignment_group()
        elif input_option == 3:
            self.display(self._grade_functions.get_values())
        else:
            raise ValueError("Invalid option!")

    def start__give_assignment_student(self):
        self._grade_functions.add(self.read_assignment_to_student_input())

    def start__give_assignment_group(self):
        assignment_id, student_group = self.read_assignment_to_group_input()
        self._grade_functions.give_group_of_students_assignment(assignment_id, student_group)

    def start__give_grade_to_student_assignment(self):
        students_assignments = self._grade_functions.get_ungraded_assignments_of_students()
        self.display_list_of_students_with_assignments(students_assignments)
        print("Which student assignment would you like to grade?")
        student_id, assignment_id, grade_value = self.read_grading_input()
        grade = Grade((assignment_id, student_id), grade_value)
        self._grade_functions.update(grade)

    def start__create_statistics(self):
        """
        start method to crate statistics, based on the input calls service function that computes a specific statistic.
        :return: if option valid prints out a statistic.
        """
        self.display_statistics_menu()
        input_option = int(input("Option>"))
        if input_option == 1:
            self.start__given_assignments_descending_grade_order()
        elif input_option == 2:
            self.start__late_assignments()
        elif input_option == 3:
            self.start__top_of_students()
        else:
            raise ValueError("Invalid option!")

    def start__given_assignments_descending_grade_order(self):
        order_list = self._grade_functions.get_assignments_descending_order()
        self.display_ordered_assignments(order_list)
        print()

    def start__late_assignments(self):
        result_list = self._grade_functions.get_late_assignments()
        self.display_late_assignments(result_list)

    def start__top_of_students(self):
        """
        start method to get the top of students statistic.
        :return: displays the 
        """
        result_list = self._grade_functions.get_students_top()
        self.display_students_top(result_list)

    """
    Reading input for all functions
    """

    def read_grading_input(self):
        student_id = input("Student ID>")
        assignment_id = input("Assignment ID>")
        grade = input("Grade>")
        print()
        return student_id, assignment_id, grade

    def read_student_input(self):
        id = input("ID>")
        name = input("Name>")
        group = input("Group>")
        print()
        return Student(id, name, group)

    def read_assignments_input(self):
        id = input("ID>")
        description = input("Description>")
        deadline = input("Deadline>")
        print()
        return Assignment(id, description, deadline)

    def read_assignment_to_student_input(self):
        assignment_id = input("Assignment ID>")
        student_id = input("Student ID>")
        print()
        return Grade((assignment_id, student_id), "0")

    def read_assignment_to_group_input(self):
        assignment_id = input("Assignment ID>")
        student_group = input("Students Group>")
        print()
        return assignment_id, student_group

    """
    Display functions for menus and statistics
    """

    def display(self, list):
        result_string = ""
        for element in list:
            result_string += str(element) + "\n"
        print(result_string)

    def display_menu_give_assignment_student_or_group(self):
        print("1.Give assignment to a student")
        print("2.Give assignment to a group")
        print("3.Display assignments given to students")
        print()

    def display_menu_manage_students(self):
        print("1.Add new student")
        print("2.Remove student")
        print("3.Update student")
        print("4.Display list of students")
        print()

    def display_menu_manage_assignments(self):
        print("1.Add new assignment")
        print("2.Remove assignment")
        print("3.Update assignment")
        print("4.Display list of assignments")
        print()

    def display_menu_for_students_or_assignments(self):
        print("1.Manage student")
        print("2.Manage assignment.")
        print()

    def display_main_menu(self):
        print("1.Manage students and assignments.")
        print("2.Give assignments to a student or a group of students.")
        print("3.Grade student for a given assignment.")
        print("4.Create statistics")
        print("5.Undo")
        print("6.Redo")
        print("7.Exit program")
        print()

    def display_statistics_menu(self):
        print("1.All students who received a given assignment, ordered descending by grade.")
        print("2.All students who are late in handing in at least one assignment.")
        print("3.Students with the best school situation.")
        print()

    def display_list_of_students_with_assignments(self, student_assignments_list):
        for i in range(len(student_assignments_list)):
            if len(student_assignments_list[i]) > 0:
                assignments_string = ""
                for elements in student_assignments_list[i]:
                    assignments_string += elements
                    assignments_string += "; "
                print("Student id:" + str(i + 1) + " has ungraded assignments with id: " + assignments_string)
        print()

    def display_late_assignments(self, list):
        if len(list) == 0:
            print("No late assignments!")
        else:
            for elements in list:
                assignment_id = elements.id[0]
                student_id = elements.id[1]
                print("Student id: " + student_id + " is late with Assignment id: " + assignment_id)
        print()
        return

    def display_students_top(self, student_list):
        if len(student_list) == 0:
            print("No graded students!")
        else:
            for elements in student_list:
                print("Student id: " + elements.id + "  Name: " + elements.name + "  GPA: " + str(elements.grade_value))
        print()
        return

    def display_ordered_assignments(self, list):
        for elements in list:
            print(elements)
