class Collection:
    def __init__(self, dictionary=None):
        if dictionary is None:
            dictionary = dict()
        self._data = dictionary
        self._position = None

    def add(self, key, element):
        self._data[key] = element

    def clear(self):
        self._data.clear()

    @property
    def values(self):
        return list(self._data.values())

    def has_element(self, object):
        if object in self._data:
            return True
        else:
            return False

    def __iter__(self):
        self._position = 0
        return self._position

    def __next__(self):
        if self._position == len(list(self._data.keys())):
            raise StopIteration()
        self._position += 1
        return self._data[list(self._data.keys())[self._position - 1]]

    def __getitem__(self, key):
        return self._data[key]

    def __setitem__(self, key, value):
        self._data[key] = value

    def __delitem__(self, key):
        del self._data[key]
