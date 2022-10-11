import datetime

"""
  Write non-UI functions below
"""


def transaction_insert(transaction_database, command_parameters):
    """
    Function to add a new transaction
    :param transaction_database:  a dictionary with all transactions and all of their contents
    :param command_parameters: transaction details to create a new transaction
    """
    list_of_parameters = split_command_parameters(command_parameters)
    if not list_of_parameters[0].isdecimal():
        raise TypeError("Data type error. Day value should be a positive integer between 1-30.")
    if not list_of_parameters[1].isdecimal():
        raise TypeError("Data type error. Amount value should be a positive integer.")
    if int(list_of_parameters[0]) > 30 or int(list_of_parameters[0]) < 0:
        raise ValueError("Number is not a day of the month.")
    if list_of_parameters[2] != 'in' and list_of_parameters[2] != 'out':
        raise ValueError("Transaction type wrong. Should be in or out.")
    create_transaction(transaction_database, int(list_of_parameters[0]), int(list_of_parameters[1]),
                       list_of_parameters[2], list_of_parameters[3])


def transaction_add(transaction_database, current_day, command_parameters):
    """
    Function to add a new transaction in the current day
    :param transaction_database:  a dictionary with all transactions and all of their contents
    :param current_day: the day to add the transaction
    :param command_parameters: transaction details to create a new transaction
    """
    list_of_parameters = split_command_parameters(command_parameters)
    if not list_of_parameters[0].isdecimal():
        raise TypeError("Data type error. Amount value should be a positive integer.")
    if list_of_parameters[1] != 'in' and list_of_parameters[1] != 'out':
        raise ValueError("Transaction type wrong. Should be in or out.")
    create_transaction(transaction_database, current_day, int(list_of_parameters[0]), list_of_parameters[1],
                       list_of_parameters[2])


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
    else:
        list_of_parameters = [''] * 0
    index_list_of_data_to_be_displayed = [0] * 0
    if len(list_of_parameters) == 2:
        index_list_of_data_to_be_displayed.clear()
        if list_of_parameters[0] == '<':
            if not list_of_parameters[1].isdecimal():
                raise ValueError("Transaction amount needs to be a positive integer.")
            get_index_list_of_values_less_than_number_to_be_displayed(transaction_database,
                                                                      index_list_of_data_to_be_displayed,
                                                                      int(list_of_parameters[1]))
            display_all_transactions_of_a_given_propriety(transaction_database, index_list_of_data_to_be_displayed)
        elif list_of_parameters[0] == '=':
            if not list_of_parameters[1].isdecimal():
                raise ValueError("Transaction amount needs to be a positive integer.")
            get_index_list_of_values_equal_to_number_to_be_displayed(transaction_database,
                                                                     index_list_of_data_to_be_displayed,
                                                                     int(list_of_parameters[1]))
            display_all_transactions_of_a_given_propriety(transaction_database, index_list_of_data_to_be_displayed)
        elif list_of_parameters[0] == '>':
            if not list_of_parameters[1].isdecimal():
                raise ValueError("Transaction amount needs to be a positive integer.")
            get_index_list_of_values_grater_than_number_to_be_displayed(transaction_database,
                                                                        index_list_of_data_to_be_displayed,
                                                                        int(list_of_parameters[1]))
            display_all_transactions_of_a_given_propriety(transaction_database, index_list_of_data_to_be_displayed)
        elif list_of_parameters[0] == 'balance':
            if list_of_parameters[1].isdecimal() and (
                    int(list_of_parameters[1]) > 30 or int(list_of_parameters[1]) < 0):
                raise ValueError("Date of month should be between 1 and 30.")
            elif not list_of_parameters[1].isdecimal():
                raise TypeError("The date should be a number!")
            print(calculate_balance_till_given_day(transaction_database, int(list_of_parameters[1])))
    elif len(list_of_parameters) == 1:
        if list_of_parameters[0] == 'in' or list_of_parameters[0] == 'out':
            index_list_of_data_to_be_displayed.clear()
            get_index_list_of_type_to_be_displayed(transaction_database, index_list_of_data_to_be_displayed,
                                                   list_of_parameters[0])
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
    list_of_parameters = split_command_parameters(command_parameters)
    if int(list_of_parameters[0]) > 30 or int(list_of_parameters[0]) < 1:
        raise ValueError("Date of month should be between 1 and 30.")
    if list_of_parameters[1] != 'in' and list_of_parameters[1] != 'out':
        raise ValueError("Type of transaction can be in or out.")
    if int(list_of_parameters[4]) < 0:
        raise ValueError("Transaction amount needs to be a positive integer.")
    found = False
    for i in range(len(transaction_database["transaction_day"])):
        if transaction_database["transaction_day"][i] == int(list_of_parameters[0]) and \
                transaction_database["transaction_type"][i] == list_of_parameters[1] and \
                transaction_database["transaction_description"][i] == list_of_parameters[2]:
            set_transaction_amount(transaction_database, i,
                                   list_of_parameters[4])  # setting the new value on the transaction position
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
    else:
        list_of_parameters = [''] * 0
    index_list_of_data_to_be_removed = [0] * 0
    if len(list_of_parameters) == 3:
        if int(list_of_parameters[0]) > 30 or int(list_of_parameters[0]) < 1 or int(list_of_parameters[2]) > 30 or int(
                list_of_parameters[2]) < 0:
            raise ValueError("Date of month should be between 1 and 30.")
        for days_to_be_deleted in range(int(list_of_parameters[0]) + 1, int(list_of_parameters[2])):
            index_list_of_data_to_be_removed.clear()
            get_index_list_of_day_to_be_removed(transaction_database, index_list_of_data_to_be_removed,
                                                days_to_be_deleted)
            remove_all_transaction_of_given_index(transaction_database, index_list_of_data_to_be_removed)
    elif len(list_of_parameters) == 1:
        if list_of_parameters[0] == 'in' or list_of_parameters[0] == 'out':
            index_list_of_data_to_be_removed.clear()
            get_index_list_of_type_to_be_removed(transaction_database, index_list_of_data_to_be_removed,
                                                 list_of_parameters[0])
            remove_all_transaction_of_given_index(transaction_database, index_list_of_data_to_be_removed)
        elif int(list_of_parameters[0]) < 30 and int(list_of_parameters) > 0:
            index_list_of_data_to_be_removed.clear()
            get_index_list_of_day_to_be_removed(transaction_database, index_list_of_data_to_be_removed,
                                                list_of_parameters[0])
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


def split_command_parameters(command_parameters):
    """
    Function to split all the parameters of the command
    :param command_parameters: string containing all parameters of any command
    :return: list in which the elements are the parameters of a command
    """
    command_split = command_parameters.split()
    return command_split


def test_split_command_parameters():
    """
    Function to test the integrity of the split_command_parameters function
    """
    assert split_command_parameters('This is the first test') == ['This', 'is', 'the', 'first', 'test']
    assert split_command_parameters('This     has   uneven  spaces between words') == ['This', 'has', 'uneven',
                                                                                       'spaces', 'between', 'words']


def split_command(command):
    """
    Function to split the command word from the command parameters
    :param command: full command with all necessary information
    :return a list with the command word split from the command parameters
    """
    command = command.strip()
    command_split = command.split(sep=' ', maxsplit=1)
    return [command_split[0].strip().lower(), command_split[1].strip().lower()] if len(command_split) == 2 else [
        command_split[0].strip().lower(), None]


def test_split_command():
    assert split_command('list > 100') == ['list', '> 100']
    assert split_command('insert 10 100 in pizza') == ['insert', '10 100 in pizza']
    assert split_command('add 100 out netflix') == ['add', '100 out netflix']
    assert split_command('remove in') == ['remove', 'in']
    assert split_command('list') == ['list', None]


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


"""
  Write the command-driven UI below
"""


def display_all_transactions(transaction_database):
    """
    Function used for the list command, prints all the transaction list
    :param transaction_database: a dictionary with all transactions and all of their contents
    :return: Prints the entire transaction list
    """
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
    for elements in index_list_of_data_to_be_displayed:
        print("transaction: " + "day->" + str(transaction_database["transaction_day"][elements]) + " ; " + "amount->" +
              str(transaction_database["transaction_amount"][elements]) + " ; " + "type->" +
              transaction_database["transaction_type"][elements] + " ; " + "description->" +
              transaction_database["transaction_description"][elements])
    print()


def start_command_ui():
    """
    Function to start the program,reads the command given by the user, makes all the necessary calls to the command
    functions
    :return: displays different content based on the command(s) given
    """
    transaction_database = init_data()
    current_time = datetime.datetime.now()
    current_day = current_time.day
    while True:
        command = input("type command>")
        print()
        command_word, command_parameters = split_command(command)

        try:
            if command_word == 'add':
                transaction_add(transaction_database, current_day, command_parameters)
            elif command_word == 'insert':
                transaction_insert(transaction_database, command_parameters)
            elif command_word == 'remove':
                transaction_remove(transaction_database, command_parameters)
            elif command_word == 'replace':
                transaction_replace(transaction_database, command_parameters)
            elif command_word == 'list':
                transaction_list(transaction_database, command_parameters)
            elif command_word == 'exit':
                return
            else:
                print("Command does not exist.")
                print()
        except ValueError as ve:
            print(str(ve))
            print()
        except TypeError as te:
            print(str(te))
            print()


start_command_ui()
