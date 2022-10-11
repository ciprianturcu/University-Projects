import copy
import math
import random

from texttable import Texttable


class BoardService:
    def __init__(self, board):
        self.__board = board
        self.__rows = self.__board.rows()
        self.__columns = self.__board.columns()

    def get_board(self):
        """
        method to get all cell objects in the repository
        :return:list of row lists of the cell elements
        """
        return self.__board.get_repository()

    def get_matrix(self):
        """
        method to get all data from the repository, used for the minimax algorithm
        :return: a nested  list of row lists containing the values of the game board, mush like a matrix
        """
        return self.__board.convert_repository_to_matrix()

    def get_valid_locations(self):
        """
        method to get all the row positions available at a certain game state, if column full row position is -1
        :return:list of row positions
        """
        valid_locations = []
        for column in range(self.__columns):
            valid_locations.append(self.find_empty_row_of_column(column))
        return valid_locations

    def find_valid_row_in_board(self, board, column):
        """
        method to find in a board, on a specific column which row is empty. (used in minimax algorithm)
        :param board: board game containing the values occupied by players or free space
        :param column: search criteria to find what row is available on this column.
        :return: 0-5 if free space found, -1 if column full
        """
        row_list = board
        index = len(row_list) - 1
        for i in range(len(row_list)):
            element = board[index][column]
            if element == 0:
                return index
            index -= 1
        return -1

    def get_board_valid_locations(self, board):
        """
        method to get all the row positions available at a certain game state, if column full row position is -1 (used in minimax algorithm)
        :param board:  board game containing the values occupied by players or free space
        :return: list of row positions
        """
        valid_locations = []
        for column in range(self.__columns):
            valid_locations.append(self.find_valid_row_in_board(board, column))
        return valid_locations

    def find_empty_row_of_column(self, column):
        """
        method to find in the repository board, on a specific column which row is empty.
        :param column: search criteria to find what row is available on this column.
        :return: 0-5 if free space found, -1 if column full
        """
        row_list = self.__board.get_repository()
        index = len(row_list) - 1
        for i in range(len(row_list)):
            element = self.__board.get_cell(index, column)
            if element.value == 0:
                return index
            index -= 1
        return -1

    def is_board_full(self, valid_locations):
        """
        method to see if a board game is full.
        :param valid_locations: list of available row locations
        :return: false if board is not full, true otherwise
        """
        count = 0
        for i in range(len(valid_locations)):
            if valid_locations[i] != -1:
                count += 1

        if count != 0:
            return False
        else:
            return True

    def check_board_for_wining_move(self, player):
        """
        method to check all the repository game board for a wining move.
        there are 4 ways to create a wining move, horizontally, vertically, positive sloped diagonals and negative sloped diagonals.
        :param player: player number of whom we check the wining move for.
        :return: True if a wining move is found, False otherwise.
        """
        board = self.__board.convert_repository_to_matrix()
        # horizontal
        for row in range(self.__rows):
            for column in range(self.__columns - 3):
                if board[row][column] == player and board[row][column + 1] == player and board[row][
                    column + 2] == player and board[row][column + 3] == player:
                    return True

        # vertical
        for row in range(self.__rows - 3):
            for column in range(self.__columns):
                if board[row][column] == player and board[row + 1][column] == player and board[row + 2][
                    column] == player and board[row + 3][column] == player:
                    return True

        # +slope
        for row in range(3, self.__rows):
            for column in range(self.__columns - 3):
                if board[row][column] == player and board[row - 1][column + 1] == player and board[row - 2][
                    column + 2] == player and board[row - 3][column + 3] == player:
                    return True

        # -slope
        for row in range(self.__rows - 3):
            for column in range(self.__columns - 3):
                if board[row][column] == player and board[row + 1][column + 1] == player and board[row + 2][
                    column + 2] == player and board[row + 3][column + 3] == player:
                    return True

    def evaluate_window(self, window, player, opponent_player):
        """
        function to evaluate a 4 piece sequence from the board. the window can contain pieces placed horizontally, vertically, on a positive sloped diagonal or on a negative sloped diagonal.
        :param window: list of 4 elements
        :param player: player number
        :param opponent_player: opponent player number
        :return: score based on how many player pieces ar in the window
        """
        score = 0
        empty = 0
        if window.count(player) == 4:
            score += 10000
        elif window.count(player) == 3 and window.count(empty) == 1:
            score += 5
        elif window.count(player) == 2 and window.count(empty) == 2:
            score += 2

        if window.count(opponent_player) == 3 and window.count(empty) == 1:
            score -= 8

        return score

    def score_position(self, board, player):
        """
        method to calculate the score of a board game state for a specific player. Function gets score form all possible directions of wining.
        :param board: board game
        :param player: player number
        :return: the score of the board game state.
        """
        if player == 1:
            opponent_player = 2
        elif player == 2:
            opponent_player = 1
        window_length = 4
        score = 0

        # center
        center_array = [int(element[self.__columns // 2]) for element in board]
        center_count = center_array.count(player)
        score += center_count * 3

        # horizontal
        for row in range(self.__rows):
            row_array = [element for element in board[row]]
            for column in range(self.__columns - 3):
                window = row_array[column: column + window_length]
                score += self.evaluate_window(window, player, opponent_player)

        # vertical
        for column in range(self.__columns):
            column_array = [element[column] for element in board]
            for row in range(self.__rows - 3):
                window = column_array[row: row + window_length]
                score += self.evaluate_window(window, player, opponent_player)

        # +slope
        for row in range(3, self.__rows):
            for column in range(self.__columns - 3):
                window = [board[row - i][column + i] for i in range(window_length)]
                score += self.evaluate_window(window, player, opponent_player)

        # -slope
        for row in range(self.__rows - 3):
            for column in range(self.__columns - 3):
                window = [board[row + i][column + i] for i in range(window_length)]
                score += self.evaluate_window(window, player, opponent_player)

        return score

    def is_terminal_node(self, valid_locations, player1, player2):
        """
        function to see if a board game state is a terminal node in the minimax algorithm.
        this looks if the game is won by either of the players or if the board is full. if any of this criterion are ture then the board state is a terminal node.
        :param valid_locations: list of all valid locations(to see if board is full)
        :param player1:player 1 number
        :param player2:player 2 number
        :return: True if is a terminal node, False otherwise
        """
        return self.check_board_for_wining_move(player1) or self.check_board_for_wining_move(
            player2) or self.is_board_full(valid_locations)

    def minimax_algorithm(self, board, depth, alpha, beta, maximizing_player):
        """
        Algorithm to determine the best column to drop in a specific board state used by the AI.
        This algorithm branches out all possible plays on the depth given and then scores each play.
        By choosing the highest score for the AI, it is capable of blocking certain moves and uses a preferential method in order to win.
        :param board: board game state
        :param depth: depth to which the algorithm plays. the bigger the more situations covered. the bigger the slower it gets.
        alpha and beta improve the efficiency of the algorithm by cutting off branches that are not worth calculating forward.
        :param alpha: upper branching bound
        :param beta: lower branching bound
        :param maximizing_player: the player turns, True, False.
        :return: best column to be picked in the specific game state
        """
        player = 1
        ai = 2
        absolute_value = float('inf')
        valid_locations = self.get_board_valid_locations(board)
        is_terminal = self.is_terminal_node(valid_locations, player, ai)
        if depth == 0 or is_terminal:
            if is_terminal:
                if self.check_board_for_wining_move(ai):
                    return None, 100000000000000
                elif self.check_board_for_wining_move(player):
                    return None, -100000000000000
                else:  # board game full
                    return None, 0
            else:  # depth is 0
                return None, self.score_position(board, ai)
        if maximizing_player:
            value = absolute_value * -1
            valid_locations = self.get_board_valid_locations(board)
            best_column = random.randint(0, self.__columns - 1)
            for column in range(self.__columns):
                if valid_locations[column] != -1:
                    row = valid_locations[column]
                    temporary_board = copy.deepcopy(board)
                    temporary_board = self.set_value_in_board(temporary_board, row, column, ai)
                    score = self.minimax_algorithm(temporary_board, depth - 1, alpha, beta, False)[1]
                    if score > value:
                        value = score
                        best_column = column
                    alpha = max(alpha, value)
                    if alpha >= beta:
                        break
            return best_column, value
        else:  # minimizing_player
            value = absolute_value
            valid_locations = self.get_board_valid_locations(board)
            best_column = random.randint(0, self.__columns - 1)
            for column in range(len(valid_locations)):
                if valid_locations[column] != -1:
                    row = valid_locations[column]
                    temporary_board = copy.deepcopy(board)
                    temporary_board = self.set_value_in_board(temporary_board, row, column, player)
                    score = self.minimax_algorithm(temporary_board, depth - 1, alpha, beta, True)[1]
                    if score < value:
                        value = score
                        best_column = column
                    beta = min(beta, value)
                    if alpha >= beta:
                        break
            return (best_column, value)

    def pick_best_move(self, player):
        """
        method to make a slightly less advanced AI
        :param player: player number to find the best column for
        :return: best column to be picked by the player
        """
        board = self.__board.convert_repository_to_matrix()
        valid_locations = self.get_valid_locations()
        best_score = 0
        best_column = random.randint(0, self.__columns - 1)
        for column in range(self.__columns):
            if valid_locations[column] != -1:
                row = valid_locations[column]
                temporary_board = copy.deepcopy(board)
                temporary_board = self.set_value_in_board(temporary_board, row, column, player)
                score = self.score_position(temporary_board, player)
                if score > best_score:
                    best_score = score
                    best_column = column
        return best_column

    def set_value_in_board(self, board, row, column, player):
        board[row][column] = player
        return board

    def change_value_of_cell(self, column, value):
        """
        method to change a cell value in the repository board
        :param column: column position where value changes
        :param value: new value
        """
        empty_rows_false = -1
        if self.find_empty_row_of_column(column) != empty_rows_false:
            row_index = self.find_empty_row_of_column(column)
            self.__board.set_cell(row_index, column, value)
        else:
            raise ValueError("Column already full!")

    def display_board(self):
        """
        function to return a timetable drawn game board.
        :return:
        """
        cell_list = self.get_board()
        game_screen = Texttable()
        header = []
        for letter in range(self.__columns):
            header.append(chr(65 + letter))
        game_screen.header(header)
        for i in range(self.__rows):
            game_screen.add_row(cell_list[i])

        return game_screen.draw()
