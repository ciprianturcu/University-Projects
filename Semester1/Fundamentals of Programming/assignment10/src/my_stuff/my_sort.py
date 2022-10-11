def my_sort(list_to_sort, comparison_function):
    """
    This sorting method is gnome sort. It sorts two adjacent elements that are in the wrong order.
    The algorithm takes advantage of the new swapped elements whom can introduce a new out-of-order pair on one lower position.
    From this method the algorithm only checks the previous position to the swapped elements and doesn't look for order higher than the swaped position.
    Therefore, if 2 elements are in the correct order they are not swapped, otherwise the elements get swapped and the search goes downwards to check if previous elements are out of order
    For the list a the gnome sort algorithm executes like this:
    a = [-9, 10, 2, 15, 1] - initial list, ascending order

    first iteration:
    position = 0 -> position = 1

    2nd iteration:
    position = 1
    -9 <= 10 true -> position 2, no swap

    3rd iteration:
    position = 2; 10 <= 2 false -> swap 2 and 10, position 1  a = [-9, 2, 10, 15, 1]

    4th iteration:
    position = 1; -9 <= 2 true -> no swap, position 2

    5th iteration:
    position = 2; 2<= 10 true -> no swap, position 3

    6 th iteration:
    position = 3; 10<=15 true -> no swap, position 4

    7th iteration:
    position = 4; 15<=1 false -> swap 15 and 1, position 3 a = [-9, 2, 10, 1, 15]

    8th iteration:
    position = 3; 10<=1 false -> swap 10 and 1, position 2 a = [-9, 2, 1, 10, 15]

    9th iteration:
    position = 2; 2<=1 false -> swap 2 and 1, position 1 a = [-9, 1, 2, 10, 15]

    10th iteration:
    position = 1; -9<=1 true -> no swap

    after the 10th iteration no swaps are required in this case position goes up until the list is finished after that the list is sorted.
    :param list_to_sort: list to be sorted
    :param comparison_function: function by which the elements get swapped
    :return: the ordered list
    """
    position = 0
    while position < len(list_to_sort):
        if position == 0 or comparison_function(list_to_sort[position], list_to_sort[position - 1]):
            position += 1
        else:
            list_to_sort[position], list_to_sort[position - 1] = list_to_sort[position - 1], list_to_sort[position]
            position -= 1

    return list_to_sort
