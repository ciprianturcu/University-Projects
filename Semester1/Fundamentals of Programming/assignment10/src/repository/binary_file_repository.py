import pickle

from src.repository.repository import Repository


class BinaryFileRepository(Repository):
    def __init__(self, file_name):
        super().__init__()
        self.__file_name = file_name
        self._read_from_file()

    @property
    def data(self):
        return super().data

    @property
    def values(self):
        return super().values

    def _read_from_file(self):
        with open(self.__file_name, "rb") as file:
            try:
                data = pickle.load(file)
            except EOFError:
                data = {}
                print("empty!")
            for elements in data:
                super().add_entity(data[elements])

    def _write_to_file(self):
        with open(self.__file_name, "wb") as file:
            pickle.dump(super().get_all(), file)

    def add_entity(self, entity):
        super().add_entity(entity)
        self._write_to_file()

    def delete(self, key):
        super().delete(key)
        self._write_to_file()

    def update(self, entity_id, new_entity):
        super().update(entity_id, new_entity)
        self._write_to_file()