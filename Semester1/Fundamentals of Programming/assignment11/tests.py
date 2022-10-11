import math
import unittest

from repository.board import Board
from service.game_service import BoardService


class GameServiceTest(unittest.TestCase):
    def setUp(self) -> None:
        self.__board = Board()
        self.__board_functions = BoardService(self.__board)

    def test__get_board__no_input__list_of_length_6(self):
        test_list = self.__board_functions.get_board()
        self.assertEqual(6, len(test_list))

    def test__get_matrix__no_input_list_of_length_6(self):
        test_matrix = self.__board_functions.get_matrix()
        self.assertEqual(6, len(test_matrix))
        self.assertEqual(test_matrix[1][1], 0)

    def test__get_valid_locations__no_input__list_of_available_row_positions(self):
        valid_locations = self.__board_functions.get_valid_locations()
        self.assertEqual(len(valid_locations), 7)
        self.assertEqual(valid_locations[1], 5)

    def test__check_board_for_wining_move__user_player_input__return_true_from_horizontal(self):
        self.__board_functions.change_value_of_cell(0, 1)
        self.__board_functions.change_value_of_cell(1, 1)
        self.__board_functions.change_value_of_cell(2, 1)
        self.__board_functions.change_value_of_cell(3, 1)
        assert self.__board_functions.check_board_for_wining_move(1)

    def test__check_board_for_wining_move__user_player_input__return_true_from_vertical(self):
        self.__board_functions.change_value_of_cell(0, 1)
        self.__board_functions.change_value_of_cell(0, 1)
        self.__board_functions.change_value_of_cell(0, 1)
        self.__board_functions.change_value_of_cell(0, 1)
        assert self.__board_functions.check_board_for_wining_move(1)

    def test__check_board_for_wining_move__user_player_input__return_true_from_diagonal_positive_slope(self):
        self.__board_functions.change_value_of_cell(0, 1)
        self.__board_functions.change_value_of_cell(1, 2)
        self.__board_functions.change_value_of_cell(1, 1)
        self.__board_functions.change_value_of_cell(2, 2)
        self.__board_functions.change_value_of_cell(2, 2)
        self.__board_functions.change_value_of_cell(2, 1)
        self.__board_functions.change_value_of_cell(3, 2)
        self.__board_functions.change_value_of_cell(3, 2)
        self.__board_functions.change_value_of_cell(3, 2)
        self.__board_functions.change_value_of_cell(3, 1)
        assert self.__board_functions.check_board_for_wining_move(1)

    def test__check_board_for_wining_move__user_player_input__return_true_from_diagonal_negative_slope(self):
        self.__board_functions.change_value_of_cell(3, 1)
        self.__board_functions.change_value_of_cell(2, 2)
        self.__board_functions.change_value_of_cell(2, 1)
        self.__board_functions.change_value_of_cell(1, 2)
        self.__board_functions.change_value_of_cell(1, 2)
        self.__board_functions.change_value_of_cell(1, 1)
        self.__board_functions.change_value_of_cell(0, 2)
        self.__board_functions.change_value_of_cell(0, 2)
        self.__board_functions.change_value_of_cell(0, 2)
        self.__board_functions.change_value_of_cell(0, 1)
        assert self.__board_functions.check_board_for_wining_move(1)

    def test__evaluate_window__input_list_of_4_same_cell__return_10000(self):
        window = [1, 1, 1, 1]
        score = self.__board_functions.evaluate_window(window, 1, 2)
        self.assertEqual(score, 10000)

    def test__minimax_algorithm__input_empty_board__return_best_column_2(self):
        board = self.__board_functions.get_matrix()
        column = self.__board_functions.minimax_algorithm(board, 5, -math.inf, math.inf, True)[0]
        self.assertEqual(column, 3)

    def test__minimax_algorithm__input_empty_board_and_depth0__return_best_column_2(self):
        board = self.__board_functions.get_matrix()
        column = self.__board_functions.minimax_algorithm(board, 0, -math.inf, math.inf, True)[0]
        self.assertEqual(column, None)

    def tearDown(self) -> None:
        pass
