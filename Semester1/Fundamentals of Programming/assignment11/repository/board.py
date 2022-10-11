from domain.cell import Cell


class Board:
    def __init__(self):
        self.__board = dict()
        self.__rows = 6
        self.__columns = 7
        self._initialize_rows()

    def rows(self):
        return self.__rows

    def columns(self):
        return self.__columns

    def _initialize_rows(self):
        for i in range(self.__rows):
            element_list = list()
            for j in range(self.__columns):
                element_list.append(Cell(0))
            self.__board[i] = element_list

    def get_repository(self):
        return list(self.__board.values())

    def set_cell(self, key, index, value):
        self.__board[key][index] = Cell(value)

    def get_cell(self, item, index):
        return self.__board[item][index]

    def convert_repository_to_matrix(self):
        matrix = []
        for i in range(self.__rows):
            element_list = []
            for j in range(self.__columns):
                element_list.append(self.__board[i][j].value)
            matrix.append(element_list)

        return matrix
