from src.my_stuff.collection import Collection


class RepositoryException(Exception):
    pass


class Repository(object):
    def __init__(self):
        self.__data = Collection()

    @property
    def data(self):
        return self.__data

    @property
    def values(self):
        return self.__data.values

    def add_entity(self, entity):
        if self.__data.has_element(entity):
            raise RepositoryException("Entity with Id " + str(entity.id) + " already in repo")
        self.__data[entity.id] = entity

    def has_element(self, entity):
        if self.__data.has_element(entity):
            return True
        else:
            return False

    def get_all(self):
        return self.__data.values

    def update(self, entity_id, new_entity):
        self.__data[entity_id] = new_entity

    def __getitem__(self, item):
        return self.__data[item]

    def __setitem__(self, key, value):
        self.__data[key] = value

    def delete(self, key):
        if not self.__data.has_element(key):
            raise RepositoryException("Entity with Id " + str(key) + " not in repository!")
        del self.__data[key]
