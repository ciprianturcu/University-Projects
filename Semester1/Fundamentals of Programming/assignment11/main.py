from repository.board import Board
from service.game_service import BoardService
from settings import Settings


if __name__ == "__main__":
    board = Board()
    game_service = BoardService(board)
    settings = Settings(game_service)
    display_mode = settings.get_all_stats()
    console = display_mode
    console.start()
