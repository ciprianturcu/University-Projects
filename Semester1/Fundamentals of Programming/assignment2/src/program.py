#
# Write the implementation for A2 in this file


# UI section
# (write all functions that have input or print statements here). 
# Ideally, this section should not contain any calculations relevant to program functionalities

def print_menu():
    """
    Function to create a menu for the user
    :return: prints a menu with 5 options
    """
    print("1.Read a list of complex numbers (in z = a + bi form)")
    print("2.Display all the list")
    print("3.Display the longest sequence of numbers with a strictly increasing real part")
    print("4.Display the longest sequence where both real and imaginary parts can be written using the same base "
          "10 digits")
    print("5.Exit")
    print()


def start_program():
    """
    Main function in which, based on the user input, a task is performed and outputted for the user
    :return: a list based on the given task or the exit of the program
    """
    complex_numbers, operation_sign_list = init_complex_numbers()
    while True:
        print_menu()
        option = input("Option:")
        print()
        if option == '5':
            return
        elif option == '4':
            result_list, result_operation_sign_list = parts_can_be_written_using_the_same_base(complex_numbers,
                                                                                               operation_sign_list)
            print(
                "Longest sequence where both real and imaginary parts can be written using the same base 10 digits is:")
            display_the_entire_list(result_list, result_operation_sign_list)
        elif option == '3':
            result_list, result_operation_sign_list = longest_sequence_real_part_strictly_increasing(complex_numbers,
                                                                                                     operation_sign_list)
            print("Longest sequence with a strictly increasing real part is:")
            display_the_entire_list(result_list, result_operation_sign_list)
        elif option == '2':
            display_the_entire_list(complex_numbers, operation_sign_list)
        elif option == '1':
            user_input, result_operation_sign_list = read_complex_number_list()
            operation_sign_list.extend(result_operation_sign_list)
            add_to_main_list(complex_numbers, user_input)
        else:
            raise TypeError("Invalid input")


def display_the_entire_list(given_list, operation_sign_list):
    """
    Display all the numbers in the list
    :param operation_sign_list: list containing the signs of the complex numbers
    :param given_list: list containing all numbers to be displayed
    """
    for i in range(len(given_list)):
        temporary_list = given_list[i]
        print(str(i + 1) + ". z=" + str(temporary_list[0]) + operation_sign_list[i] + str(temporary_list[1]) + "i")
    print()


def read_complex_number_list():
    """
    Converting the input from the string to a list of elements consisting of small lists each having a real and an
    imaginary part
    :return: list of numbers from last input
    """
    user_input = input("Please type your complex numbers:")  # string that holds all input from user
    print()
    temporary_list = user_input.split(',')  # list of every number in the form z=a+bi
    operation_sign_list = [''] * 0
    for i in range(len(temporary_list)):
        temporary_list[i] = temporary_list[i].strip("z =i")  # removing unnecessary information to get numbers
        if find_operation_sign(temporary_list[i]) == 0:
            operation_sign_list.append('-')
        elif find_operation_sign(temporary_list[i]) == 1:
            operation_sign_list.append('+')
        # splitting every op into 2 strings holding the real part and the imaginary part
        if (find_operation_sign(temporary_list[i]) == 1):
            temporary_list[i] = temporary_list[i].split(
                '+')
        elif (find_operation_sign(temporary_list[i]) == 0):
            temporary_list[i] = temporary_list[i].rsplit('-', 1)
        else:
            raise TypeError("Invalid input, form z=a+bi or z=a-bi not respected")
    return temporary_list, operation_sign_list


# Function section
# (write all non-UI functions in this section)
# There should be no print or input statements below this comment
# Each function should do one thing only
# Functions communicate using input parameters and their return values

# print('Hello A2'!) -> prints aren't allowed here!

def find_operation_sign(numbers_with_sign):
    """
    Function to determine the sign of a complex number
    :param numbers_with_sign: part of a complex number in form of a+b or a-b
    :return: 1 for the sign "+", 0 for the sign "-"
    """
    numbers_with_sign = str(numbers_with_sign)
    result = 0
    if numbers_with_sign.find('+') != -1:
        result = 1
    elif numbers_with_sign.find('-') != -1:
        result = 0
    return result


def get_real_part(number):
    """
    Function to get real part
    :param number: list with 2 elements [x,y],x-real part,y-imaginary part representing a complex number
    :return: the real part of a complex number
    """
    return number[0]


def get_imaginary_part(number):
    """
    Function to get imaginary part
    :param number: list with 2 elements [x,y],x-real part,y-imaginary part representing a complex number
    :return: the imaginary part of a complex number
    """
    return number[1]


def existence_of_all_digits_of_2_numbers(digits_of_given_list, real_part_of_searched_element,
                                         imaginary_part_of_searched_element):
    """
    Function that verifies if a number is represented with the same digits as previous number(s)
    :param digits_of_given_list: list of which digits from base 10 were found in the last numbers representation
    :param real_part_of_searched_element: the real part of the number that needs to be verified
    :param imaginary_part_of_searched_element: the imaginary part of the number that needs to be verified
    :return: 1 if the number that needs to be verified is represented with the same digits, 0 otherwise
    """
    found = 1
    digits_of_searched_element = [0] * 10
    while real_part_of_searched_element != 0:
        digits_of_searched_element[real_part_of_searched_element % 10] = 1
        real_part_of_searched_element = real_part_of_searched_element // 10
    while imaginary_part_of_searched_element != 0:
        digits_of_searched_element[imaginary_part_of_searched_element % 10] = 1
        imaginary_part_of_searched_element = imaginary_part_of_searched_element // 10
    for i in range(10):
        if digits_of_searched_element[i] != digits_of_given_list[i]:
            found = 0
    return found


def put_given_list_on_0(given_list):
    """
    function to put all elements of a list to 0
    :param given_list: the list that needs to be modified
    """
    for i in range(len(given_list)):
        given_list[i] = 0


def create_digits_of_base_10_from_number(digits_of_base_10, given_parts_list):
    """
    Function that creates a "bool" list with the digits from a given complex number
    :param digits_of_base_10: list with all 10 elements on 0
    :param given_parts_list: complex number needed to be represented as the "bool" list of digits in base 10
    :return: the "bool" list of digits that appear in the representation of the given compplex number
    """
    put_given_list_on_0(digits_of_base_10)
    real_part = get_real_part(given_parts_list)
    imaginary_part = get_imaginary_part(given_parts_list)
    if real_part < 0:
        real_part *= -1
    while real_part != 0:
        digits_of_base_10[real_part % 10] = 1
        real_part = real_part // 10
    while imaginary_part != 0:
        digits_of_base_10[imaginary_part % 10] = 1
        imaginary_part = imaginary_part // 10


def reset_sequence(digits_of_base_10, longest_sequence, signs_of_longest_sequence, main_list, operation_sign_list, i):
    """
    Function called when a new sequence needs to start. It initializes all the necessary space for the upcoming operations
    :param digits_of_base_10: the list
    :param longest_sequence: empty list in which we put the first element after a new sequence starts
    :param signs_of_longest_sequence: empty list in which we put the first sign of the element after a new sequence starts
    :param main_list: list with all the complex numbers
    :param operation_sign_list: list with the operation signs of all complex numbers
    :param i: the current position in the main list of complex numbers
    :return:modifying the corresponding lists
    """
    create_digits_of_base_10_from_number(digits_of_base_10, main_list[i])
    longest_sequence.append(main_list[i])
    signs_of_longest_sequence.append(operation_sign_list[i])


def parts_can_be_written_using_the_same_base(main_list, operation_sign_list):
    """
    Function to find the longest sequence of complex numbers represented with the same digits
    :param main_list: list of all complex numbers
    :param operation_sign_list: list of all signs of the complex numbers
    :return: two lists with the complex numbers in the longest sequence,and their operation signs
    """
    digits_of_base_10 = [0] * 10
    longest_sequence = [0] * 0
    signs_of_longest_sequence = [0] * 0
    result_sequence = [0] * 0
    signs_of_result_sequence = [0] * 0
    longest_sequence.append(main_list[0])
    signs_of_longest_sequence.append(operation_sign_list[0])
    create_digits_of_base_10_from_number(digits_of_base_10, main_list[0])
    count_elements_in_sequence = 1
    for i in range(1, len(main_list)):
        current_number = [0] * 0
        current_number.extend(main_list[i])
        if existence_of_all_digits_of_2_numbers(digits_of_base_10, get_real_part(current_number),
                                                get_imaginary_part(current_number)) == 1:
            longest_sequence.append(main_list[i])
            signs_of_longest_sequence.append(operation_sign_list[i])
            count_elements_in_sequence += 1
        elif count_elements_in_sequence > len(result_sequence):
            result_sequence = longest_sequence
            signs_of_result_sequence = signs_of_longest_sequence
            count_elements_in_sequence = 1
            longest_sequence = [0] * 0
            signs_of_longest_sequence = [0] * 0
            reset_sequence(digits_of_base_10, longest_sequence, signs_of_longest_sequence, main_list,
                           operation_sign_list, i)
        else:
            count_elements_in_sequence = 1
            longest_sequence = [0] * 0
            signs_of_longest_sequence = [0] * 0
            reset_sequence(digits_of_base_10, longest_sequence, signs_of_longest_sequence, main_list,
                           operation_sign_list, i)

        if count_elements_in_sequence > len(result_sequence):
            result_sequence = longest_sequence
            signs_of_result_sequence = signs_of_longest_sequence

    return result_sequence, signs_of_result_sequence


def longest_sequence_real_part_strictly_increasing(main_list, operation_sign_list):
    """
    Finding the longest sequence with a strictly increasing real part
    :param operation_sign_list: list containing all signs from the complex numbers
    :param main_list: list containing all complex numbers
    :return: the longest sequence with a strictly increasing real part
    """
    longest_sequence = [0] * 0
    signs_of_longest_sequence = [0] * 0
    result_sequence = [0] * 0
    signs_of_result_sequence = [0] * 0
    longest_sequence.append(main_list[0])
    signs_of_longest_sequence.append(operation_sign_list[0])
    count_elements_in_sequence = 1
    for i in range(len(main_list) - 1):
        last_number = [0] * 0
        current_number = [0] * 0
        last_number.extend(longest_sequence[count_elements_in_sequence - 1])
        current_number.extend(main_list[i + 1])
        if get_real_part(last_number) < get_real_part(current_number):
            longest_sequence.append(main_list[i + 1])
            signs_of_longest_sequence.append(operation_sign_list[i + 1])
            count_elements_in_sequence += 1
        elif count_elements_in_sequence > len(result_sequence):
            result_sequence = longest_sequence
            signs_of_result_sequence = signs_of_longest_sequence
            count_elements_in_sequence = 1
            longest_sequence = [0] * 0
            signs_of_longest_sequence = [0] * 0
            longest_sequence.append(main_list[i + 1])
            signs_of_longest_sequence.append(operation_sign_list[i + 1])
        else:
            count_elements_in_sequence = 1
            longest_sequence = [0] * 0
            signs_of_longest_sequence = [0] * 0
            longest_sequence.append(main_list[i + 1])
            signs_of_longest_sequence.append(operation_sign_list[i + 1])
    if count_elements_in_sequence > len(result_sequence):
        result_sequence = longest_sequence
        signs_of_result_sequence = signs_of_longest_sequence
    return result_sequence, signs_of_result_sequence


def add_to_main_list(main_list, new_list):
    """
    Adding the newly inputted list of complex number to the main list in which all numbers are stored
    :param main_list: the list with all the complex numbers
    :param new_list: the last inputted list of numbers
    :return: the newly modified list with all the numbers
    """
    for i in range(len(new_list)):
        temporary_list = [0] * 2
        j = 0
        for element in new_list[i]:
            temporary_list[j] = int(element)
            j += 1
        main_list.append(temporary_list)
    return main_list


def init_complex_numbers():
    """
    Create a few test complex numbers
    :return: a list of created complex numbers
    """
    return [create_complex_number(2, 8), create_complex_number(22, 8), create_complex_number(2, 88),
            create_complex_number(2, 8), create_complex_number(22, 8), create_complex_number(28, 2),
            create_complex_number(228, 0), create_complex_number(9, 3), create_complex_number(23, 11),
            create_complex_number(33, 6)], ['+', '-', '+', '+', '+', '+', '+', '-', '+', '+']


def create_complex_number(real_part, imag_part):
    """
    Create a new complex number
    :param real_part: the real part of the number
    :param imag_part: the imaginary part of the number
    :return: a newly created complex number
    """
    return [real_part, imag_part]


start_program()
