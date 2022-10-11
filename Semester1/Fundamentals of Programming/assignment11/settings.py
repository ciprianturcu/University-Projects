from configparser import ConfigParser

from ui.gui import Gui
from ui.ui import Ui


class Settings:
    def __init__(self, game_service):

        config = ConfigParser()
        config.read(r'C:\Users\cipri\Documents\GitHub\a11-917-Turcu-Ciprian\settings.properties')
        display_type = config.get("options", "display")

        if display_type == "gui":
            self._display = Gui(game_service)
        elif display_type == "ui":
            self._display = Ui(game_service)

    def get_all_stats(self):
        return self._display
