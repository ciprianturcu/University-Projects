"""
    Function test module
"""

from functions import add_to_transaction_history, transaction_undo, transaction_filter, transaction_max, \
    transaction_sum, transaction_insert, transaction_add, transaction_replace, transaction_remove, init_data, \
    split_command_parameters,split_command
import datetime


def test_add_to_transaction_history():
    transaction_data_1 = {'key1': [1, 2, 3, 4], 'key2': [1, 2, 3, 4, 5]}
    transaction_data_2 = {'key1': [8, 9, 3, 4], 'key2': [2, 6, 3, 7, 5]}
    transaction_history = [] * 0
    add_to_transaction_history(transaction_data_1, transaction_history)
    assert transaction_history[0] == transaction_data_1
    add_to_transaction_history(transaction_data_2, transaction_history)
    assert transaction_history[1] == transaction_data_2


def test_transaction_undo():
    transaction_data_1 = {'key1': [1, 2, 3, 4], 'key2': [1, 2, 3, 4, 5]}
    transaction_data_2 = {'key1': [8, 9, 3, 4], 'key2': [2, 6, 3, 7, 5]}
    transaction_data_3 = {'key1': [8, 7, 3, 7], 'key2': [2, 2, 3, 5, 5]}
    transaction_history = [] * 0
    add_to_transaction_history(transaction_data_1, transaction_history)
    add_to_transaction_history(transaction_data_2, transaction_history)
    assert transaction_undo(transaction_history) == transaction_data_2
    add_to_transaction_history(transaction_data_3, transaction_history)
    assert transaction_undo(transaction_history) == transaction_data_3
    assert transaction_undo(transaction_history) == transaction_data_1


def test_transaction_filter():
    transaction_data = init_data()
    transaction_filter(transaction_data, "out 500")
    assert transaction_data == {'transaction_day': [5, 6, 7, 8, 10], 'transaction_amount': [111, 11, 5, 6, 8],
                                'transaction_type': ['out', 'out', 'out', 'out', 'out'],
                                'transaction_description': ['food', 'games', 'subscriptions', 'gas', 'drinks']}

    transaction_data = init_data()
    transaction_filter(transaction_data, "in")
    assert transaction_data == {'transaction_day': [1, 3, 9], 'transaction_amount': [300, 452, 10],
                                'transaction_type': ['in', 'in', 'in'],
                                'transaction_description': ['pizza', 'crypto', 'stocks']}


def test_transaction_max():
    transaction_data = {'transaction_day': [1, 2, 3, 4, 7, 6, 7, 8, 9, 10],
                        'transaction_amount': [300, 600, 452, 5234, 111, 11, 5, 6, 10, 8],
                        'transaction_type': ['in', 'out', 'in', 'out', 'out', 'out', 'out', 'out', 'in', 'out'],
                        'transaction_description': ['pizza', 'gym', 'crypto', 'rent', 'food', 'games', 'subscriptions',
                                                    'gas', 'stocks', 'drinks']}
    assert transaction_max(transaction_data, "out 7") == 111
    assert transaction_max(transaction_data, "in 4") == 0
    assert transaction_max(transaction_data, "in 1") == 300


def test_transaction_sum():
    transaction_data = {'transaction_day': [1, 2, 3, 4, 8, 6, 7, 8, 9, 10],
                        'transaction_amount': [300, 600, 452, 5234, 111, 11, 5, 6, 10, 8],
                        'transaction_type': ['in', 'out', 'in', 'out', 'out', 'out', 'out', 'out', 'in', 'out'],
                        'transaction_description': ['pizza', 'gym', 'crypto', 'rent', 'food', 'games', 'subscriptions',
                                                    'gas', 'stocks', 'drinks']}
    assert transaction_sum(transaction_data, "in") == 762
    assert transaction_sum(transaction_data, "out") == 5975


def test_transaction_insert():
    transaction_data = {'transaction_day': [],
                        'transaction_amount': [],
                        'transaction_type': [],
                        'transaction_description': []}
    transaction_insert(transaction_data, "25 100 in salary")
    assert transaction_data == {'transaction_day': [25],
                                'transaction_amount': [100],
                                'transaction_type': ['in'],
                                'transaction_description': ['salary']}
    transaction_insert(transaction_data, "30 1000 out pizza")
    assert transaction_data == {'transaction_day': [25, 30],
                                'transaction_amount': [100, 1000],
                                'transaction_type': ['in', 'out'],
                                'transaction_description': ['salary', 'pizza']}


def test_transaction_add():
    transaction_data = {'transaction_day': [],
                        'transaction_amount': [],
                        'transaction_type': [],
                        'transaction_description': []}
    current_time = datetime.datetime.now()
    current_day = current_time.day
    transaction_add(transaction_data, current_day, "100 in salary")
    assert transaction_data == {'transaction_day': [current_day],
                                'transaction_amount': [100],
                                'transaction_type': ['in'],
                                'transaction_description': ['salary']}


def test_transaction_replace():
    transaction_data = init_data()
    transaction_replace(transaction_data, "3 in crypto with 2000")
    assert transaction_data["transaction_amount"][2] == 2000
    transaction_replace(transaction_data, "4 out rent with 750")
    assert transaction_data["transaction_amount"][3] == 750


def test_transaction_remove():
    transaction_data = {'transaction_day': [1, 2, 4, 4, 7, 6, 7, 8, 9, 10],
                        'transaction_amount': [300, 600, 452, 5234, 111, 11, 5, 6, 10, 8],
                        'transaction_type': ['in', 'out', 'in', 'out', 'out', 'out', 'out', 'out', 'in', 'out'],
                        'transaction_description': ['pizza', 'gym', 'crypto', 'rent', 'food', 'games', 'subscriptions',
                                                    'gas', 'stocks', 'drinks']}
    transaction_remove(transaction_data, "out")
    assert transaction_data == {'transaction_day': [1, 4, 9],
                                'transaction_amount': [300, 452, 10],
                                'transaction_type': ['in', 'in', 'in'],
                                'transaction_description': ['pizza', 'crypto', 'stocks']}


def test_split_command_parameters():
    """
    Function to test the integrity of the split_command_parameters function
    """
    assert split_command_parameters('This is the first test') == ['This', 'is', 'the', 'first', 'test']
    assert split_command_parameters('This     has   uneven  spaces between words') == ['This', 'has', 'uneven',
                                                                                       'spaces', 'between', 'words']


def test_split_command():
    assert split_command('list > 100') == ['list', '> 100']
    assert split_command('insert 10 100 in pizza') == ['insert', '10 100 in pizza']
    assert split_command('add 100 out netflix') == ['add', '100 out netflix']
    assert split_command('remove in') == ['remove', 'in']
    assert split_command('list') == ['list', None]


def run_tests():
    test_add_to_transaction_history()
    test_transaction_replace()
    test_transaction_remove()
    test_transaction_add()
    test_transaction_undo()
    test_transaction_filter()
    test_transaction_sum()
    test_transaction_max()
    test_transaction_insert()
