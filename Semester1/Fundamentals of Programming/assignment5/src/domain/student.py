class Student:
    """
    - id (integer, unique), a name (string) and a group (positive integer)
    """

    def __init__(self, _id, name="n/a", group=0):
        """
        Create a new student entity
        """
        self.__id = _id
        self.__name = name
        self.group = group

    @property
    def id(self):
        return self.__id

    @property
    def name(self):
        return self.__name

    @property
    def group(self):
        return self.__group

    @group.setter
    def group(self, new_group):
        if isinstance(new_group,int):
            if new_group < 0:
                raise ValueError("Group should be a positive integer.")
            self.__group = new_group
        else:
            raise ValueError("New group is not integer.")

    def __str__(self):
        return "Id:" + str(self.__id) + " Name:" + self.__name + " Group:" + str(self.__group)
