import os.path

from src.repository.repository import Repository


class FileRepository(Repository):
    def __init__(self, file_name, entity_type):
        super().__init__()
        self.__file_name = file_name
        self.__entity_type = entity_type
        self._read_from_file()

    def _read_from_file(self):
        if os.path.getsize(self.__file_name) > 0:
            with open(self.__file_name, "rt") as file:
                lines = file.readlines()
                for line in lines:
                    super().add_entity(self.__entity_type.get_from_string(line))
        else:
            pass

    def _write_to_file(self):
        with open(self.__file_name, "wt") as file:
            for entity in super().get_all():
                file.write(self.__entity_type.get_string_form(entity))

    def _append_to_file(self, entity):
        with open(self.__file_name, "a+") as file:
            file.write(self.__entity_type.get_string_form(entity))

    def add_entity(self, entity):
        super().add_entity(entity)
        self._write_to_file()

    def delete(self, entity_id):
        super().delete(entity_id)
        self._clear_file()
        self._write_to_file()

    def update(self, entity_id, new_entity):
        super().update(entity_id, new_entity)
        self._write_to_file()

    def _clear_file(self):
        file = open(self.__file_name, "wt")
        file.write("")
        file.close()
