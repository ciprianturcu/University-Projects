"""
  Program functionalities module
"""
import copy

from ui import display_all_transactions_of_a_given_propriety, display_all_transactions, display_balance_or_sum


def add_to_transaction_history(transaction_database, transaction_history_database):
    """
    Function to add the dictionary used in the last command
    :param transaction_database: in use dictionary containing all the current transactions
    :param transaction_history_database: list of all old dictionaries
    """
    transaction_history_database.append(copy.deepcopy(transaction_database))


def get_last_from_history(transaction_history_database):
    """
    Function to get the most recent used dictionary
    :param transaction_history_database: list of all old dictionaries
    """
    last_element = transaction_history_database[-1]
    return last_element


def transaction_undo(transaction_history_database):
    """
    Function to replace the main dictionary with an old copy
    :param transaction_history_database: list of all old dictionaries
    :return: copy of dictionary ready to be used in a new operation
    """
    if len(transaction_history_database) < 1:
        raise ValueError("Cannot undo operation")
    copy_of_last_element_from_history = get_last_from_history(transaction_history_database)
    transaction_database = copy.deepcopy(copy_of_last_element_from_history)
    transaction_history_database.pop()
    return transaction_database


def transaction_filter(transaction_database, command_parameters):
    """
    Function to keep only a given type of transactions
    :param transaction_database: in use dictionary containing all the current transactions
    :param command_parameters: string containing all the necessary arguments to perform the operation
    :return: modifies the dictionary in use to respect given propriety
    """
    if command_parameters is not None:
        list_of_parameters = split_command_parameters(command_parameters)
        if len(list_of_parameters) == 2:
            operation_parameter = list_of_parameters[0]
            number_parameter = list_of_parameters[1]
            if operation_parameter != 'in' and operation_parameter != 'out':
                raise ValueError("Incorrect type of filter operation. Should be in or out! ")
            if not number_parameter.isdecimal():
                raise ValueError("Incorrect amount value, amount transaction should be a positive integer.")
        elif len(list_of_parameters) == 1:
            operation_parameter = list_of_parameters[0]
            if operation_parameter != 'in' and operation_parameter != 'out':
                raise ValueError("Incorrect type of filter operation. Should be in or out! ")
        else:
            raise ValueError("Command parameters are incorrect.")
    else:
        raise ValueError("There are no command parameters.")
    index_list_of_data_to_be_filtered = [0] * 0
    get_index_list_of_type_to_be_filtered(transaction_database, index_list_of_data_to_be_filtered, operation_parameter)
    if len(list_of_parameters) == 1:
        filter_all_transaction_of_given_index(transaction_database, index_list_of_data_to_be_filtered)
    elif len(list_of_parameters) == 2:
        filter_all_transaction_of_given_index(transaction_database, index_list_of_data_to_be_filtered)
        filter_transaction_with_amount_grater_than_given_amount(transaction_database, number_parameter)


def filter_transaction_with_amount_grater_than_given_amount(transaction_database, given_amount):
    """
    Function to delete all transaction above a given amount.
    :param transaction_database: in use dictionary containing all the current transactions
    :param given_amount: amount introduced by user as a threshold
    :return: modifies the list in use such that all transaction ar below the given amount
    """
    database_length = len(transaction_database["transaction_amount"]) - 1
    while database_length >= 0:
        if transaction_database["transaction_amount"][database_length] >= int(given_amount):
            del transaction_database["transaction_day"][database_length]
            del transaction_database["transaction_amount"][database_length]
            del transaction_database["transaction_type"][database_length]
            del transaction_database["transaction_description"][database_length]
        database_length -= 1


def transaction_max(transaction_database, command_parameters):
    """
    Function to find the transaction in the given day with the specified type and the maximum amount.
    :param transaction_database: in use dictionary containing all the current transactions
    :param command_parameters: string containing all the necessary arguments to perform the operation
    :return: returns the amount found for the given proprieties
    """
    maximum_number = 0  # maximum of <type> transaction in a given day
    if command_parameters is not None:
        list_of_parameters = split_command_parameters(command_parameters)
        if len(list_of_parameters) == 2:
            operation_parameter = list_of_parameters[0]
            number_parameter = list_of_parameters[1]
        else:
            raise TypeError("Command parameters do not correspond.")
    else:
        raise TypeError("Command parameters do not correspond.")
    for i in range(len(transaction_database["transaction_day"])):
        if transaction_database["transaction_type"][i] == operation_parameter and \
                transaction_database["transaction_day"][i] == int(number_parameter):
            if transaction_database["transaction_amount"][i] > maximum_number:
                maximum_number = transaction_database["transaction_amount"][i]
    return maximum_number


def transaction_sum(transaction_database, command_parameters):
    """
    Function to calculate the sum of all transaction of a given type.
    :param transaction_database: in use dictionary containing all the current transactions
    :param command_parameters: string containing all the necessary arguments to perform the operation
    :return: the total sum of all transaction with the given type
    """
    if command_parameters is not None:
        list_of_parameters = split_command_parameters(command_parameters)
        if len(list_of_parameters) == 1:
            type_parameter = list_of_parameters[0]
        else:
            raise TypeError("Command parameters do not correspond.")
    else:
        raise TypeError("Command parameters do not correspond.")

    if type_parameter != 'in' and type_parameter != 'out':
        raise ValueError("Transaction type wrong. Should be in or out.")
    total_sum = 0
    for i in range(len(transaction_database["transaction_type"])):
        if transaction_database["transaction_type"][i] == type_parameter:
            total_sum += transaction_database["transaction_amount"][i]
    return total_sum


def transaction_insert(transaction_database, command_parameters):
    """
    Function to add a new transaction
    :param transaction_database:  a dictionary with all transactions and all of their contents
    :param command_parameters: transaction details to create a new transaction
    """
    if command_parameters is not None:
        list_of_parameters = split_command_parameters(command_parameters)
        if len(list_of_parameters) == 4:
            day_parameter = list_of_parameters[0]
            amount_parameter = list_of_parameters[1]
            type_parameter = list_of_parameters[2]
            description_parameter = list_of_parameters[3]
        else:
            raise TypeError("Command parameters do not correspond.")
    else:
        raise TypeError("Command parameters do not correspond.")

    if not day_parameter.isdecimal():
        raise TypeError("Data type error. Day value should be a positive integer between 1-30.")
    if not amount_parameter.isdecimal():
        raise TypeError("Data type error. Amount value should be a positive integer.")
    if int(day_parameter) > 30 or int(day_parameter) < 0:
        raise ValueError("Number is not a day of the month.")
    if type_parameter != 'in' and type_parameter != 'out':
        raise ValueError("Transaction type wrong. Should be in or out.")
    create_transaction(transaction_database, int(day_parameter), int(amount_parameter),
                       type_parameter, description_parameter)


def transaction_add(transaction_database, current_day, command_parameters):
    """
    Function to add a new transaction in the current day
    :param transaction_database:  a dictionary with all transactions and all of their contents
    :param current_day: the day to add the transaction
    :param command_parameters: transaction details to create a new transaction
    """

    if command_parameters is not None:
        list_of_parameters = split_command_parameters(command_parameters)
        if len(list_of_parameters) == 3:
            amount_parameter = list_of_parameters[0]
            type_parameter = list_of_parameters[1]
            description_parameter = list_of_parameters[2]
        else:
            raise ValueError("Wrong command parameters.")
    else:
        raise ValueError("Error. There are no parameters!")
    if not amount_parameter.isdecimal():
        raise TypeError("Data type error. Amount value should be a positive integer.")
    if type_parameter != 'in' and type_parameter != 'out':
        raise ValueError("Transaction type wrong. Should be in or out.")
    create_transaction(transaction_database, current_day, int(amount_parameter), type_parameter,
                       description_parameter)


def transaction_list(transaction_database, command_parameters):
    """
    Function find and display an amount or a list of transactions based on the given command
    list - display all transactions
    list <type> - display all in/out transactions
    list [ < | = | > ] <value> - display all transactions having an amount of money < / = / > than "value"
    list balance <day> - compute the account’s balance at the end of given day. This is the sum of all in transactions,
                         from which we subtract out transactions occurring before or on given day
    :param transaction_database: a dictionary with all transactions and all of their contents
    :param command_parameters: string containing the parameters on which
    :return: prints the amount till a given day or the transaction list of a given propriety
    """
    if command_parameters is not None:
        list_of_parameters = split_command_parameters(command_parameters)
        if len(list_of_parameters) == 2:
            operation_parameter = list_of_parameters[0]
            number_parameter = list_of_parameters[1]
        elif len(list_of_parameters) == 1:
            operation_parameter = list_of_parameters[0]
            number_parameter = ''
        else:
            raise ValueError("Command parameters are incorrect.")
    else:
        list_of_parameters = [''] * 0
        operation_parameter = ''
        number_parameter = ''
    index_list_of_data_to_be_displayed = [0] * 0
    if len(list_of_parameters) == 2:
        index_list_of_data_to_be_displayed.clear()
        if operation_parameter == '<':
            if not number_parameter.isdecimal():
                raise ValueError("Transaction amount needs to be a positive integer.")
            get_index_list_of_values_less_than_number_to_be_displayed(transaction_database,
                                                                      index_list_of_data_to_be_displayed,
                                                                      int(number_parameter))
            display_all_transactions_of_a_given_propriety(transaction_database, index_list_of_data_to_be_displayed)
        elif operation_parameter == '=':
            if not number_parameter.isdecimal():
                raise ValueError("Transaction amount needs to be a positive integer.")
            get_index_list_of_values_equal_to_number_to_be_displayed(transaction_database,
                                                                     index_list_of_data_to_be_displayed,
                                                                     int(number_parameter))
            display_all_transactions_of_a_given_propriety(transaction_database, index_list_of_data_to_be_displayed)
        elif operation_parameter == '>':
            if not number_parameter.isdecimal():
                raise ValueError("Transaction amount needs to be a positive integer.")
            get_index_list_of_values_grater_than_number_to_be_displayed(transaction_database,
                                                                        index_list_of_data_to_be_displayed,
                                                                        int(number_parameter))
            display_all_transactions_of_a_given_propriety(transaction_database, index_list_of_data_to_be_displayed)
        elif operation_parameter == 'balance':
            if number_parameter.isdecimal() and (
                    int(number_parameter) > 30 or int(number_parameter) < 0):
                raise ValueError("Date of month should be between 1 and 30.")
            elif not number_parameter.isdecimal():
                raise TypeError("The date should be a number!")
            display_balance_or_sum(calculate_balance_till_given_day(transaction_database, int(number_parameter)))
    elif len(list_of_parameters) == 1:
        if operation_parameter == 'in' or operation_parameter == 'out':
            index_list_of_data_to_be_displayed.clear()
            get_index_list_of_type_to_be_displayed(transaction_database, index_list_of_data_to_be_displayed,
                                                   operation_parameter)
            display_all_transactions_of_a_given_propriety(transaction_database, index_list_of_data_to_be_displayed)
        else:
            raise ValueError("Wrong type input. Should be in or out.")
    elif len(list_of_parameters) == 0:
        display_all_transactions(transaction_database)
    else:
        raise ValueError("Invalid command parameters.")


def transaction_replace(transaction_database, command_parameters):
    """
    Function to search for one or more transaction, given a specific set of attributes, and replace the transaction
    amount with a given positive integer number
    :param transaction_database: a dictionary with all transactions and all of their contents
    :param command_parameters: string containing details about the transactions that need their amounts changed
    :return: modified list if the specified transaction was found; the list without any change otherwise
    """
    if command_parameters is not None:
        list_of_parameters = split_command_parameters(command_parameters)
        if len(list_of_parameters) == 5:
            day_parameter = list_of_parameters[0]
            type_parameter = list_of_parameters[1]
            description_parameter = list_of_parameters[2]
            amount_parameter = int(list_of_parameters[4])
        else:
            raise ValueError("Wrong command parameters.")
    else:
        raise ValueError("Error. There are no parameters!")

    if int(day_parameter) > 30 or int(day_parameter) < 1:
        raise ValueError("Date of month should be between 1 and 30.")
    if type_parameter != 'in' and type_parameter != 'out':
        raise ValueError("Type of transaction can be in or out.")
    if amount_parameter < 0:
        raise ValueError("Transaction amount needs to be a positive integer.")
    found = False
    for i in range(len(transaction_database["transaction_day"])):
        if transaction_database["transaction_day"][i] == int(day_parameter) and \
                transaction_database["transaction_type"][i] == type_parameter and \
                transaction_database["transaction_description"][i] == description_parameter:
            set_transaction_amount(transaction_database, i,
                                   amount_parameter)  # setting the new value on the transaction position
            found = True
    if not found:
        raise ValueError("transaction not found!")


def transaction_remove(transaction_database, command_parameters):
    """
    Function to remove one or more transactions based on the given parameters
    :param transaction_database: a dictionary with all transactions and all of their contents
    :param command_parameters: string containing details about the transactions that need to be removed
    :return: modifies the dictionary
    """
    if command_parameters is not None:
        list_of_parameters = split_command_parameters(command_parameters)
        if len(list_of_parameters) == 3:
            first_removing_criteria = list_of_parameters[0]
            second_removing_criteria = list_of_parameters[2]
        elif len(list_of_parameters) == 1:
            first_removing_criteria = list_of_parameters[0]
        else:
            raise ValueError("Wrong command parameters.")
    else:
        list_of_parameters = [''] * 0
        first_removing_criteria = ''
        second_removing_criteria = ''
    index_list_of_data_to_be_removed = [0] * 0
    if len(list_of_parameters) == 3:
        if int(first_removing_criteria) > 30 or int(first_removing_criteria) < 1 or int(
                second_removing_criteria) > 30 or int(second_removing_criteria) < 0:
            raise ValueError("Date of month should be between 1 and 30.")
        for days_to_be_deleted in range(int(first_removing_criteria) + 1, int(second_removing_criteria)):
            index_list_of_data_to_be_removed.clear()
            get_index_list_of_day_to_be_removed(transaction_database, index_list_of_data_to_be_removed,
                                                days_to_be_deleted)
            remove_all_transaction_of_given_index(transaction_database, index_list_of_data_to_be_removed)
    elif len(list_of_parameters) == 1:
        if first_removing_criteria == 'in' or first_removing_criteria == 'out':
            index_list_of_data_to_be_removed.clear()
            get_index_list_of_type_to_be_removed(transaction_database, index_list_of_data_to_be_removed,
                                                 first_removing_criteria)
            remove_all_transaction_of_given_index(transaction_database, index_list_of_data_to_be_removed)
        elif int(first_removing_criteria) < 30 and int(first_removing_criteria) > 0:
            index_list_of_data_to_be_removed.clear()
            get_index_list_of_day_to_be_removed(transaction_database, index_list_of_data_to_be_removed,
                                                first_removing_criteria)
            remove_all_transaction_of_given_index(transaction_database, index_list_of_data_to_be_removed)
        else:
            raise ValueError("Day or Type criteria not met. Try a day from 1-30 or one type in/out.")
    else:
        raise ValueError("Missing removing criteria.")


def remove_all_transaction_of_given_index(transaction_database, index_list_of_data_to_be_removed):
    """
    Function to delete all transactions based of an index list
    :param transaction_database: a dictionary with all transactions and all of their contents
    :param index_list_of_data_to_be_removed: list with the index numbers of the transactions that need to be removed
    :return: modifies the main dictionary
    """
    while len(index_list_of_data_to_be_removed) > 0:
        index_to_delete = index_list_of_data_to_be_removed[-1]
        del transaction_database["transaction_day"][index_to_delete]
        del transaction_database["transaction_amount"][index_to_delete]
        del transaction_database["transaction_type"][index_to_delete]
        del transaction_database["transaction_description"][index_to_delete]
        del index_list_of_data_to_be_removed[-1]


def filter_all_transaction_of_given_index(transaction_database, index_list_of_data_to_be_filtered):
    """

    :param transaction_database:
    :param index_list_of_data_to_be_filtered:
    :return:
    """
    while len(index_list_of_data_to_be_filtered) > 0:
        index_to_delete = index_list_of_data_to_be_filtered[-1]
        del transaction_database["transaction_day"][index_to_delete]
        del transaction_database["transaction_amount"][index_to_delete]
        del transaction_database["transaction_type"][index_to_delete]
        del transaction_database["transaction_description"][index_to_delete]
        del index_list_of_data_to_be_filtered[-1]


def split_command_parameters(command_parameters):
    """
    Function to split all the parameters of the command
    :param command_parameters: string containing all parameters of any command
    :return: list in which the elements are the parameters of a command
    """
    command_split = command_parameters.split()
    return command_split


def split_command(command):
    """
    Function to split the command word from the command parameters
    :param command: full command with all necessary information
    :return a list with the command word split from the command parameters
    """
    command = command.strip()
    command_split = command.split(sep=' ', maxsplit=1)
    if len(command_split) == 2:
        command_word = command_split[0].strip().lower()
        command_parameters = command_split[1].strip().lower()
    else:
        command_word = command_split[0].strip().lower()
        command_parameters = None

    return [command_word, command_parameters]


def calculate_balance_till_given_day(transaction_database, given_day):
    """
    Function to calculate the account balance from day one to a given day
    :param transaction_database: a dictionary with all transactions and all of their contents
    :param given_day: the end day of the period in which we need to find the balance account
    :return: total balance in range day 1-given_day
    """
    total_balance = 0
    for i in range(len(transaction_database["transaction_day"])):
        if transaction_database["transaction_day"][i] <= given_day:
            if transaction_database["transaction_type"][i] == 'in':
                total_balance += transaction_database["transaction_amount"][i]
            elif transaction_database["transaction_type"][i] == 'out':
                total_balance -= transaction_database["transaction_amount"][i]
    return total_balance


def get_index_list_of_day_to_be_removed(transaction_database, index_list_of_data_to_be_removed, remove_day):
    """
    Function to get the indexes of the transactions with a day that need to be removed
    :param transaction_database: a dictionary with all transactions and all of their contents
    :param index_list_of_data_to_be_removed: empty list in which we put the needed transactions
    :param remove_day: transaction day to be removed
    :return: list with needed indexes
    """
    for i in range(len(transaction_database["transaction_day"])):
        if transaction_database["transaction_day"][i] == int(remove_day):
            index_list_of_data_to_be_removed.append(i)


def get_index_list_of_type_to_be_removed(transaction_database, index_list_of_data_to_be_removed, remove_type):
    """
    Function to get the indexes of the transactions with a type(in/out) that need to be removed
    :param transaction_database: a dictionary with all transactions and all of their contents
    :param index_list_of_data_to_be_removed: empty list in which we put the needed transactions
    :param remove_type: transaction type (in/out) to be removed
    :return: list with needed indexes
    """
    for i in range(len(transaction_database["transaction_type"])):
        if transaction_database["transaction_type"][i] == remove_type:
            index_list_of_data_to_be_removed.append(i)


def get_index_list_of_type_to_be_filtered(transaction_database, index_list_of_data_to_be_filtered, searching_type):
    """
    Function to get the indexes of the transaction
    :param transaction_database:
    :param index_list_of_data_to_be_filtered:
    :param searching_type:
    :return:
    """
    for i in range(len(transaction_database["transaction_type"])):
        if transaction_database["transaction_type"][i] != searching_type:
            index_list_of_data_to_be_filtered.append(i)


def get_index_list_of_values_less_than_number_to_be_displayed(transaction_database, index_list_of_data_to_be_displayed,
                                                              given_number):
    """
    Function to get the indexes of the transactions with an amount that is lower than a given number from the main dictionary "transaction_database"
    :param transaction_database: a dictionary with all transactions and all of their contents
    :param index_list_of_data_to_be_displayed: empty list in which we put the needed transactions
    :param given_number: the number that we compare to
    :return: list with needed indexes
    """
    for i in range(len(transaction_database["transaction_amount"])):
        if transaction_database["transaction_amount"][i] < given_number:
            index_list_of_data_to_be_displayed.append(i)


def get_index_list_of_values_equal_to_number_to_be_displayed(transaction_database, index_list_of_data_to_be_displayed,
                                                             given_number):
    """
    Function to get the indexes of the transactions with an equal amount than a given number from the main dictionary "transaction_database"
    :param transaction_database: a dictionary with all transactions and all of their contents
    :param index_list_of_data_to_be_displayed: empty list in which we put the needed transactions
    :param given_number: the number that we compare to
    :return: list with needed indexes
    """
    for i in range(len(transaction_database["transaction_amount"])):
        if transaction_database["transaction_amount"][i] == given_number:
            index_list_of_data_to_be_displayed.append(i)


def get_index_list_of_values_grater_than_number_to_be_displayed(transaction_database,
                                                                index_list_of_data_to_be_displayed, given_number):
    """
    Function to get the indexes of the transactions with a greater amount than a given number from the main dictionary "transaction_database"
    :param transaction_database: a dictionary with all transactions and all of their contents
    :param index_list_of_data_to_be_displayed: empty list in which we put the needed transactions
    :param given_number: the number that we compare to
    :return: list with needed indexes
    """
    for i in range(len(transaction_database["transaction_amount"])):
        if transaction_database["transaction_amount"][i] > given_number:
            index_list_of_data_to_be_displayed.append(i)


def get_index_list_of_balance_to_given_day_to_be_displayed(transaction_database, index_list_of_data_to_be_displayed,
                                                           given_day):
    """
    Function to get the indexes of the transactions of a given day from the main dictionary "transaction_database"
    :param transaction_database: a dictionary with all transactions and all of their contents
    :param index_list_of_data_to_be_displayed: empty list in which we put the needed transactions
    :param given_day: the searching day
    :return: list with needed indexes
    """
    for i in range(transaction_database["transaction_day"]):
        if transaction_database["transaction_day"][i] <= given_day:
            index_list_of_data_to_be_displayed.append(i)


def get_index_list_of_type_to_be_displayed(transaction_database, index_list_of_data_to_be_displayed, given_type):
    """
    Function to get the indexes of the transactions of a given type(in/out) from the main dictionary "transaction_database"
    :param transaction_database: a dictionary with all transactions and all of their contents
    :param index_list_of_data_to_be_displayed: empty list in which we put the needed transactions
    :param given_type: the searching criteria
    """
    for i in range(len(transaction_database["transaction_type"])):
        if transaction_database["transaction_type"][i] == given_type:
            index_list_of_data_to_be_displayed.append(i)


def get_transaction_day(transaction_database, position):
    """
    Function to get a given day from a transaction
    :param transaction_database: a dictionary with all transactions and all of their contents
    :param position: the transaction number that needs to be returned
    """
    return transaction_database["transaction_day"][position]


def get_transaction_amount(transaction_database, position):
    """
    Function to get a given amount from a transaction
    :param transaction_database: a dictionary with all transactions and all of their contents
    :param position: the transaction number that needs to be returned
    """
    return transaction_database["transaction_amount"][position]


def get_transaction_type(transaction_database, position):
    """
    Function to get a given type from a transaction
    :param transaction_database: a dictionary with all transactions and all of their contents
    :param position: the transaction number that needs to be returned
    """
    return transaction_database["transaction_type"][position]


def get_transaction_description(transaction_database, position):
    """
    Function to get a given day from a transaction
    :param transaction_database: a dictionary with all transactions and all of their contents
    :param position: the transaction number that needs to be returned
    """
    return transaction_database["transaction_description"][position]


def set_transaction_day(transaction_database, position, value):
    """
    Function to set a given day in a transaction
    :param transaction_database: a dictionary with all transactions and all of their contents
    :param position: the transaction number that needs to be set
    :param value: the day transaction to be set
    """
    transaction_database["transaction_day"][position] = value


def set_transaction_amount(transaction_database, position, value):
    """
    Function to set a given amount in a transaction
    :param transaction_database: a dictionary with all transactions and all of their contents
    :param position: the transaction number that needs to be set
    :param value: the amount transaction to be set
    """
    transaction_database["transaction_amount"][position] = value


def set_transaction_type(transaction_database, position, value):
    """
    Function to set a given type in a transaction
    :param transaction_database: a dictionary with all transactions and all of their contents
    :param position: the transaction number that needs to be set
    :param value: the type transaction to be set
    """
    transaction_database["transaction_type"][position] = value


def set_transaction_description(transaction_database, position, value):
    """
    Function to set a given description in a transaction
    :param transaction_database:  a dictionary with all transactions and all of their contents
    :param position: the transaction number that needs to be set
    :param value: the description word to be set
    """
    transaction_database["transaction_description"][position] = value


def create_transaction(transaction_database, day, amount, type, description):
    """
    Function to create an new transaction, and put it in the transaction dictionary
    :param transaction_database:  a dictionary with all transactions and all of their contents
    :param day: transaction day
    :param amount: transaction amount
    :param type: transaction type -> in or out
    :param description: short one-word description
    :return:appends a new transaction
    """
    transaction_database["transaction_day"].append(day)
    transaction_database["transaction_amount"].append(amount)
    transaction_database["transaction_type"].append(type)
    transaction_database["transaction_description"].append(description)


def init_data():
    """
    Function to create the initial 10 transaction at program start
    :return: a dictionary with 4 keys and 10 values (transactions)
    """
    initial_transactions = {"transaction_day": [],
                            "transaction_amount": [],
                            "transaction_type": [],
                            "transaction_description": []}
    create_transaction(initial_transactions, 1, 300, "in", "pizza")
    create_transaction(initial_transactions, 2, 600, "out", "gym")
    create_transaction(initial_transactions, 3, 452, "in", "crypto")
    create_transaction(initial_transactions, 4, 5234, "out", "rent")
    create_transaction(initial_transactions, 5, 111, "out", "food")
    create_transaction(initial_transactions, 6, 11, "out", "games")
    create_transaction(initial_transactions, 7, 5, "out", "subscriptions")
    create_transaction(initial_transactions, 8, 6, "out", "gas")
    create_transaction(initial_transactions, 9, 10, "in", "stocks")
    create_transaction(initial_transactions, 10, 8, "out", "drinks")
    return initial_transactions
