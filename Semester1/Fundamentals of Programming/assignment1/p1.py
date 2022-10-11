# Solve the problem from the first set here
"""
UI functions go under here
"""

def input_function():
    """
    Function to read input from user
    :return: the number read
    """
    n = input("Please enter a number n:")
    return n

def result():
    """
    Function puts all the operating functions in motion and creates the output message,
    giving the user a proper result
    :return: prints the output message
    """
    n = input_function()
    list = digit_list(n)
    list = sort_digit_list(list)
    n = reconstruc(list)
    print("For the number n that was inputted, the largest natural number written with the same digits is:", n)

"""
Non-UI functions go under here
"""

def digit_list(n):
    """
    Function creates a list in witch the digits of a given number is saved
    :param n: number to be saved in list
    :return: saved list
    """
    list = [0] * 0
    n = int(n)
    while (int(n)):
        list.append(int(n % 10))
        n = n / 10
    return list


def sort_digit_list(list):
    """
    Function takes the given list and sorts the elements in decreasing order
    :param list: unordered list
    :return: the list ordered in decreasing order
    """
    for i in range(len(list)):
        for j in range(len(list)):
            if list[i] > list[j]:
                aux = list[i]
                list[i] = list[j]
                list[j] = aux
    return list


def reconstruc(list):
    """
    Function takes the list given and remakes the whole number
    :param list: ordered list
    :return: number formed with the elements of the list
    """
    n = 0
    for i in range(len(list)):
        n = n * 10 + list[i]
    return n

result()
