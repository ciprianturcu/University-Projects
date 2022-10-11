import math
import sys
import random

import pygame


class Gui:
    def __init__(self, game_service):
        self.__game_functions = game_service
        pygame.init()

    def display_board(self, board, columns, rows, screen):
        """
        method to display a window containing the game
        :param board: board state
        :param columns: number of columns
        :param rows: number of rows
        :param screen: space where the board game is drawn
        """

        blue = (0, 0, 255)
        black = (0, 0, 0)
        red = (255, 0, 0)
        yellow = (255, 255, 0)
        square_size = 100
        circle_radius = int(square_size / 2 - 5)
        for column in range(columns):
            for row in range(rows):
                pygame.draw.rect(screen, blue,
                                 (column * square_size, row * square_size + square_size, square_size, square_size))
                pygame.draw.circle(screen, black, (
                    int(column * square_size + square_size / 2),
                    int(row * square_size + square_size + square_size / 2)), circle_radius)

        for column in range(columns):
            for row in range(rows):
                element = board[row][column]
                if element.value == 1:
                    pygame.draw.circle(screen, red, (
                        int(column * square_size + square_size / 2),
                        square_size + int(row * square_size + square_size / 2)),
                                       circle_radius)
                if element.value == 2:
                    pygame.draw.circle(screen, yellow, (
                        int(column * square_size + square_size / 2),
                        square_size + int(row * square_size + square_size / 2)),
                                       circle_radius)
        pygame.display.update()

    def start(self):
        """
        Start function containing the game loop and all necessary calls to play the game
        """
        black = (0, 0, 0)
        red = (255, 0, 0)
        yellow = (255, 255, 0)
        player1 = 1
        player2 = 2
        player_order = [player1, player2]
        random.shuffle(player_order)
        turn = player_order[0]
        square_size = 100
        columns = 7
        rows = 6
        width = square_size * columns
        height = square_size * (rows + 1)
        size = (width, height)
        circle_radius = int(square_size / 2 - 5)
        screen = pygame.display.set_mode(size)
        self.display_board(self.__game_functions.get_board(), columns, rows, screen)
        pygame.display.update()
        font = pygame.font.SysFont("monospace", 75)
        run = True
        while run:
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    sys.exit()
                if event.type == pygame.MOUSEMOTION:
                    pygame.draw.rect(screen, black, (0, 0, width, square_size))
                    posx = event.pos[0]
                    if turn == player1:
                        pygame.draw.circle(screen, red, (posx, square_size // 2), circle_radius)
                pygame.display.update()
                if event.type == pygame.MOUSEBUTTONDOWN:
                    pygame.draw.rect(screen, black, (0, 0, width, square_size))
                    if turn == player1:
                        posx = event.pos[0]
                        column = posx // square_size
                        if self.__game_functions.find_empty_row_of_column(column) != -1:
                            self.__game_functions.change_value_of_cell(column, player1)
                            if self.__game_functions.check_board_for_wining_move(player1):
                                label = font.render("Player wins!!", 1, red)
                                screen.blit(label, (40, 10))
                                run = False
                            turn = player2
                    self.display_board(self.__game_functions.get_board(), columns, rows, screen)

            if turn == player2 and run == True:
                board = self.__game_functions.get_matrix()
                column, minimax_score = self.__game_functions.minimax_algorithm(board, 4, -math.inf, math.inf, True)
                if self.__game_functions.find_empty_row_of_column(column) != -1:
                    self.__game_functions.change_value_of_cell(column, player2)
                    if self.__game_functions.check_board_for_wining_move(player2):
                        label = font.render("AI wins!!", 1, yellow)
                        screen.blit(label, (40, 10))
                        run = False
                    turn = player1
                self.display_board(self.__game_functions.get_board(), columns, rows, screen)

        if not run:
            pygame.time.wait(3000)
