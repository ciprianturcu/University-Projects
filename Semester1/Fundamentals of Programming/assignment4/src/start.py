"""
  Start the program by running this module
"""
import datetime
from functions import transaction_add, transaction_insert, transaction_remove, transaction_replace, init_data, \
    split_command, transaction_list, transaction_sum, transaction_max, transaction_filter, transaction_undo, \
    add_to_transaction_history
from ui import command_input, display_error_message, display_balance_or_sum
from test import run_tests


def start_command_ui():
    """
    Function to start the program,reads the command given by the user, makes all the necessary calls to the command
    functions
    :return: displays different content based on the command(s) given
    """
    run_tests()
    transaction_history_database = [] * 0
    transaction_database = init_data()
    current_time = datetime.datetime.now()
    current_day = current_time.day
    while True:
        command = command_input()
        command_word, command_parameters = split_command(command)
        try:
            if command_word == 'add':
                add_to_transaction_history(transaction_database, transaction_history_database)
                transaction_add(transaction_database, current_day, command_parameters)
            elif command_word == 'insert':
                add_to_transaction_history(transaction_database, transaction_history_database)
                transaction_insert(transaction_database, command_parameters)
            elif command_word == 'remove':
                add_to_transaction_history(transaction_database, transaction_history_database)
                transaction_remove(transaction_database, command_parameters)
            elif command_word == 'replace':
                add_to_transaction_history(transaction_database, transaction_history_database)
                transaction_replace(transaction_database, command_parameters)
            elif command_word == 'list':
                transaction_list(transaction_database, command_parameters)
            elif command_word == 'sum':
                display_balance_or_sum(transaction_sum(transaction_database, command_parameters))
            elif command_word == 'max':
                display_balance_or_sum(transaction_max(transaction_database, command_parameters))
            elif command_word == 'filter':
                add_to_transaction_history(transaction_database, transaction_history_database)
                transaction_filter(transaction_database, command_parameters)
            elif command_word == 'undo':
                transaction_database = transaction_undo(transaction_history_database)
            elif command_word == 'exit':
                return
            else:
                raise TypeError("Command doesn't exist")
        except ValueError as ve:
            display_error_message(ve)
        except TypeError as te:
            display_error_message(te)


start_command_ui()
