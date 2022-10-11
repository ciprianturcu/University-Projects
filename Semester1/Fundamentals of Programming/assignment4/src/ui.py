"""
  User interface module
"""


def display_balance_or_sum(amount):
    print(amount)


def display_all_transactions(transaction_database):
    """
    Function used for the list command, prints all the transaction list
    :param transaction_database: a dictionary with all transactions and all of their contents
    :return: Prints the entire transaction list
    """
    if len(transaction_database["transaction_day"])==0:
        raise ValueError("The list is empty")
    for i in range(len(transaction_database["transaction_day"])):
        print("transaction: " + "day->" + str(transaction_database["transaction_day"][i]) + " ; " + "amount->" +
              str(transaction_database["transaction_amount"][i]) + " ; " + "type->" +
              transaction_database["transaction_type"][i] + " ; " + "description->" +
              transaction_database["transaction_description"][i])
    print()


def display_all_transactions_of_a_given_propriety(transaction_database, index_list_of_data_to_be_displayed):
    """
    Function to display all transactions respecting a given propriety
    :param transaction_database: a dictionary with all transactions and all of their contents
    :param index_list_of_data_to_be_displayed: list of the positions of all transactions respecting a propriety
    :return: Prints for the user on each line a transaction with all of its contents
    """
    if len(index_list_of_data_to_be_displayed)==0:
        raise ValueError("The list is empty")
    for elements in index_list_of_data_to_be_displayed:
        print("transaction: " + "day->" + str(transaction_database["transaction_day"][elements]) + " ; " + "amount->" +
              str(transaction_database["transaction_amount"][elements]) + " ; " + "type->" +
              transaction_database["transaction_type"][elements] + " ; " + "description->" +
              transaction_database["transaction_description"][elements])
    print()


def display_error_message(error_message):
    print(str(error_message))
    print()


def command_input():
    command = input("type command>")
    return command
