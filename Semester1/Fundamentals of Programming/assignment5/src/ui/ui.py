from src.services.services import StudentServices
from src.services.tests import tests_for_add_student_to_repository,tests_for_filter_by_group

class Ui:

    def __init__(self):
        self._functions = StudentServices()

    def show_all_students(self):
        """
        Function to display all students
        """
        student_list = self._functions.return_repository()
        for student in student_list:
            print(student)
        print()

    def print_menu(self):
        """
        Function to print application menu
        """
        print("1.Add a student.")
        print("2.Display the list of students.")
        print("3.Filter students out from a given group.")
        print("4.Undo.")
        print("5.Exit")
        print()

    def read_student(self):
        """
        Function to read student details and create a new student
        :return: Student object
        """
        id = input("Id:")
        name = input("Name:")
        group = input("Group:")
        print()
        self._functions.check_integrity_of_input(id, group, 1)
        student = self._functions.create_student_entity(int(id), name, int(group))
        return student

    def filter_students(self):
        """
        Function to read input and remove students based on input
        """
        group_to_be_filtered = input("Group to be filtered:")
        print()
        self._functions.check_integrity_of_input(0, group_to_be_filtered, 3)
        self._functions.filter_by_group(int(group_to_be_filtered))

    def start(self):
        """
        Function to start the program
        """
        tests_for_add_student_to_repository()
        tests_for_filter_by_group()
        self._functions.generate_initial_student_list()
        while True:
            self.print_menu()
            input_option = int(input("Option:"))
            print()
            try:
                if input_option == 1:
                    self._functions.add_to_history_repository()
                    self._functions.add_student_to_repository(self.read_student())
                elif input_option == 2:
                    self.show_all_students()
                elif input_option == 3:
                    self._functions.add_to_history_repository()
                    self.filter_students()
                elif input_option == 4:
                    self._functions.undo_operation()
                elif input_option == 5:
                    return
                else:
                    raise ValueError("Invalid option!")
            except ValueError as ve:
                print(str(ve))
                print()


console = Ui()
console.start()
