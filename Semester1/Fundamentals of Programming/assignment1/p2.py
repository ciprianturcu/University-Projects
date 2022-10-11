# Solve the problem from the second set here

"""
UI-functions go under here
"""

def input_function():
    """
    Function gives a welcoming message to the user
    and reads the input that needs to be solved
    :return: a integer number
    """
    n = input("Please enter a number n:")
    return n

def main():
    """
    Function to initialize the first set of numbers based on the given value n,
    then make the necessary function calls for solving the problem
    :return: prints the result
    """
    n = input_function()
    n = int(n)
    p1 = n + 1
    p2 = n + 3
    print("The twin prime numbers p1 and p2 immediately larger than the given n are: ",search(p1,p2))

"""
Non-UI functions go under here
"""

def prime_check(x):
    """
    Function verifies if a given value "x" is a prime number (checking for any divisors)
    :param x: number to be checked
    :return: 1 if the number x is prime, 0 otherwise
    """
    ok = True
    for i in range(2, int(x / 2) + 1):
        if ok == True:
            if x % i == 0:
                ok = False
    if ok == True:
        return 1
    elif ok == False:
        return 0


def search(p1, p2):
    """
    function uses 2 given values and finds if they are twin prime numbers,
    by verifying if both of the numbers are prime numbers and the difference between them is equal to 2
    :param p1: first number
    :param p2: second number
    :return: the two twin prime numbers
    """
    found = False
    while found == False:
        if prime_check(p1) == 1 and prime_check(p2) == 1 and p2 - p1 == 2:
            found = True
        else:
            p1 += 1
            p2 += 1
    return p1, p2

main()
