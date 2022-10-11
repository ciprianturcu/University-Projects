
class RepositoryException(Exception):
    pass


class Repository:
    def __init__(self):
        self.__data = dict()

    def add_entity(self, entity):
        if entity.id in self.__data:
            raise RepositoryException("Entity with Id " + str(entity.id) + " already in repo")
        self.__data[entity.id] = entity

    def has_element(self, entity):
        if entity in self.__data:
            return True
        else:
            return False

    def get_all(self):
        return list(self.__data.values())

    def update(self, entity_id, new_entity):
        self.__data[entity_id] = new_entity

    def __getitem__(self, item):
        return self.__data[item]

    def __setitem__(self, key, value):
        self.__data[key] = value

    def delete(self, key):
        if key not in self.__data:
            raise RepositoryException("Entity with Id " + str(key) + " not in repository!")
        del self.__data[key]
