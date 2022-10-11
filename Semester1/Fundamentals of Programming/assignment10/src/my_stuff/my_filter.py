def my_filter(list_to_be_filtered, filter_function):
    """
    :param list_to_be_filtered: the list that is going to be filtered
    :param filter_function: this function is used to determine which elements pass the filter
    :return: the filtered list
    """
    filtered_list = []
    for element in list_to_be_filtered:
        if filter_function(element):
            filtered_list.append(element)
    return filtered_list