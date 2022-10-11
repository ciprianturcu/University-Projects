import math
import time

from texttable import Texttable
from service.game_service import BoardService
import random


class Ui:
    def __init__(self, game_service):
        self.__game_functions = game_service

    def read_player_input(self):
        """
        Function to read player column input
        :return: A letter from [A,G]
        """
        command = input(f"\nPlayer enter the column:\n")
        return command

    def start(self):
        """
        Start function containing the game loop and all necessary calls to play the game
        :return:
        """
        player1 = 1
        player2 = 2
        columns = 7
        rows = 6
        A = 65
        player_order = [player1, player2]
        random.shuffle(player_order)
        turn = player_order[0]
        run = True
        while run:
            if turn == player1:
                try:
                    print(self.__game_functions.display_board())
                    option = self.read_player_input()
                    if option <'A' or option>'G':
                        raise ValueError("Entered column doesn't exist!")
                    self.__game_functions.change_value_of_cell(ord(option) - A, player1)
                    if self.__game_functions.check_board_for_wining_move(player1):
                        print(self.__game_functions.display_board())
                        print("Player wins!")
                        run = False
                    else:
                        turn = player2
                except ValueError as ve:
                    print(str(ve))
                    print()
            elif turn == player2:
                time.sleep(2)
                try:
                    print(self.__game_functions.display_board())
                    board = self.__game_functions.get_matrix()
                    column, minimax_score = self.__game_functions.minimax_algorithm(board, 4, -math.inf, math.inf, True)
                    print("\nAI's turn.\n")
                    self.__game_functions.change_value_of_cell(column, player2)
                    if self.__game_functions.check_board_for_wining_move(player2):
                        print(self.__game_functions.display_board())
                        print("AI wins!")
                        run = False
                    else:
                        turn = player1
                except ValueError as ve:
                    print(str(ve))
                    print()
