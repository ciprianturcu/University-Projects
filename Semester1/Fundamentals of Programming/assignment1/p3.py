# Solve the problem from the third set here

"""
UI functions go under here
"""


def input_date():
    """
    Function to input date of birth
    :return: the date of birth
    """
    n = int(input("please enter your birthday in this format(ddmmyyyy):"))
    return n


def input_current_date():
    """
    Function to input current date
    :return: the current date
    """
    n = int(input("please enter the current date in this format(ddmmyyyy):"))
    return n


def output_result():
    """
    Function that calls the solving function and prints its output.
    :return: prints the expected result, or an error
    """
    x1 = input_date()
    x2 = input_current_date()
    if check_input_errors(x1, x2) == 1:
        print("The age expressed in days is: ", calc_all_days(x1, x2), "days")
    else:
        print("Opss! One of the dates does not exist! Please re-enter the dates")


"""
Non-UI functions go under here
"""


# using getters to obtain year,month and day
def get_year_from_date(x):
    """
    Function to extract year from date
    :param x: date
    :return: year from date
    """
    n = x % 10000
    return n


def get_month_from_date(x):
    """
    Function to extract month from date
    :param x: date
    :return: month from date
    """
    n = x // 10000
    n = n % 100
    return n


def get_day_from_date(x):
    """
    Function to extract day from date
    :param x: date
    :return: day from date
    """
    n = x // 1000000
    n = n % 100
    return n


# defining a set of conditions to help determine if a year is a leap year or not

""" the year is divisible by 4, then it’s a leap year (condition 1), UNLESS 

    it’s also divisible by 100, then it’s not a leap year (condition 2), UNLESS FURTHER

    the year is divisible by 400, then it is a leap year.(condition 3)"""


def con1(x):
    if x % 4 == 0:
        return 1
    else:
        return 0


def con2(x):
    if x % 100 == 0:
        return 1
    else:
        return 0


def con3(x):
    if x % 400 == 0:
        return 1
    else:
        return 0


#

def check_input_errors(x1, x2):
    """
    Function that ensures the user didn't send a wrong input to be processed.
    :param x1: birth date
    :param x2: current date
    :return: 1 if the both dates are correct, 0 if one or both dates are entered incorrectly
    """
    if get_year_from_date(x1) > get_year_from_date(x2): return 0
    if get_month_from_date(x1) < 1 or get_month_from_date(x1) > 12: return 0
    if get_month_from_date(x2) < 1 or get_month_from_date(x2) > 12: return 0
    if get_day_from_date(x1) < 1 or get_day_from_date(x1) > 32: return 0
    if get_day_from_date(x1) < 1 or get_day_from_date(x1) > 32: return 0
    if get_year_from_date(x1) < 0 or get_year_from_date(x2) < 0: return 0
    return 1


def check_same_year(x1, x2):
    """
    Function to check if the dates are in the same year
    :param x1: birth date
    :param x2: current date
    :return: 1 if the dates are in the same year, 0 otherwise
    """
    if get_year_from_date(x1) == get_year_from_date(x2):
        return 1
    else:
        return 0


def check_same_month(x1, x2):
    """
    Function that determines if two dates have the same month, usually used for dates in the same year and month
    :param x1: birth date
    :param x2: current date
    :return: 1 if the dates have the same month, 0 otherwise
    """
    if get_month_from_date(x1) == get_month_from_date(x2):
        return 1
    else:
        return 0


def check_leap_year(x):
    """
    Function to check if a given year is a leap year
    :param x: year
    :return: 1 if the year is a leap year, 0 otherwise
    """
    if con3(x) == 1:
        return 1
    elif con1(x) == 1 and con2(x) == 1:
        return 0
    elif con1(x) == 1 and con2(x) == 0:
        return 1


def days_nr_first_month(x):
    """
    Function to get days from birth month
    :param x: date
    :return: the number of days
    """
    days_of_months = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]  # list of days in each month for normal years
    if (check_leap_year(get_year_from_date(x)) == 1): days_of_months[1] += 1
    cnt_days = days_of_months[get_month_from_date(x) - 1] - (get_day_from_date(x) - 1)
    return cnt_days


def get_days_sameyear(x1, x2):
    """
    Function to get all days if both dates are in the same year
    :param x1: birth date
    :param x2: current date
    :return: the number of days
    """
    days_of_months = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]  # list of days in each month for normal years
    if check_leap_year(get_year_from_date(x1)) == 1: days_of_months[1] += 1
    days = days_of_months[get_month_from_date(x1) - 1] - (get_day_from_date(x1) - 1)
    for i in range(get_month_from_date(x1), get_month_from_date(x2)):
        days += days_of_months[i]
    if check_same_month(x1, x2) == 1: days = days - days_of_months[get_month_from_date(x1) - 1]
    days += get_day_from_date(x2)
    return days


""" breaking the calculation of days in 3 parts
    1. number of days in the first year, most probably not a full year
    2. number of days from the whole years 
    3. number of days till current day introduced
"""


def get_nr_days_birthyear(x):
    """
    Function to calculate the remaining days in the year of birth
    :param x: date
    :return: number of days left in first year
    """
    days_of_months = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]  # list of days in each month for normal years
    if (check_leap_year(x) == 1): days_of_months[1] += 1
    days = days_nr_first_month(x)  # initializing with days in the birth month
    for i in range(get_month_from_date(x), len(days_of_months)):
        days += days_of_months[i]
    return days


def get_period_inbetween(x1, x2):
    """
    Function that gets the days from a period of whole years
    :param x1: birth date
    :param x2: current date
    :return: part of the number of days
    """
    days = 0
    byear = get_year_from_date(x1) + 1
    cyear = get_year_from_date(x2)
    if (byear == cyear + 1):
        return days
    else:
        for i in range(byear, cyear):
            if check_leap_year(i) == 1:
                days += 366
            else:
                days += 365
        return days


def get_nr_days_currentyear(x):
    """
    Function to calculate the remaining days in the current year
    :param x: date
    :return: number of days passed in the last year
    """
    days_of_months = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]  # list of days in each month for normal years
    days = get_day_from_date(x)
    for i in range(get_month_from_date(x) - 1):
        days += days_of_months[i]
    return days


def calc_all_days(x1, x2):
    """
    Function calls all the subfunctions that calculate the number of days in different periods and gets the final result
    :param x1: birth day
    :param x2: current date
    :return: total nr of days
    """
    if (check_same_year(x1, x2) == 1):
        return get_days_sameyear(x1, x2)
    else:
        return get_nr_days_birthyear(x1) + get_period_inbetween(x1, x2) + get_nr_days_currentyear(x2)


output_result()
