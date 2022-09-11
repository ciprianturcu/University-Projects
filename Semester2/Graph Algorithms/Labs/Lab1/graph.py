import copy

class Graph:
    """Class representing a directed graph.

    It uses 3 dictionaries 2 representing the adjacency matrix and one for edge costs.
    """
    def __init__(self, number_vertices, number_edges):
        """The constructor."""
        self._number_vertices = number_vertices
        self._number_edges = number_edges
        self._vertices_in = {}
        self._vertices_out = {}
        self._dict_cost = {}
        for i in range(number_vertices):
            self._vertices_in[i] = []
            self._vertices_out[i] = []

    @property
    def dict_cost(self):
        """Method to get the dictionary containing the cost of each edge."""
        return self._dict_cost

    @property
    def vertices_in(self):
        """Method to get the dictionary containing the adjacency matrix of the inbound vertices"""
        return self._vertices_in

    @property
    def vertices_out(self):
        return self._vertices_out

    @property
    def number_vertices(self):
        return self._number_vertices

    @property
    def number_edges(self):
        return self._number_edges

    def parse_vertices(self):
        vertices = list(self._vertices_in.keys())
        for vert in vertices:
            yield vert

    def parse_cost(self):
        keys = list(self._dict_cost.keys())
        for k in keys:
            yield k

    def parse_in(self, x):
        for y in self._vertices_in[x]:
            yield y

    def parse_out(self, x):
        for y in self._vertices_out[x]:
            yield y

    def add_vertex(self, x):
        if x in self._vertices_out.keys() and x in self._vertices_in.keys():
            return False
        self._vertices_out[x] = []
        self._vertices_in[x] = []
        self._number_vertices += 1
        return True

    def remove_vertex(self, x):
        if x not in self._vertices_in.keys() and x not in self._vertices_out.keys():
            return False
        self._vertices_out.pop(x)
        self._vertices_in.pop(x)
        for keys in self.vertices_in.keys():
            if x in self._vertices_in[keys]:
                self._vertices_in[keys].remove(x)
            elif x in self._vertices_out[keys]:
                self._vertices_out[keys].remove(x)

        # we now delete all the costs related to the vertex
        edge_list = list(self.dict_cost.keys())
        for index in edge_list:
            if index[0] == x or index[1] == x:
                self._dict_cost.pop(index)
                self._number_edges -= 1
        self._number_vertices -= 1
        return True

    def get_in_degree(self, x):
        if x not in self._vertices_in:
            return False
        return len(self._vertices_in[x])

    def get_out_degree(self, x):
        if x not in self._vertices_out:
            return False
        return len(self._vertices_out[x])

    def add_edge(self, x, y, cost):
        if x in self._vertices_in[y]:
            return False
        elif y in self._vertices_out[x]:
            return False
        elif (x, y) in self.dict_cost.keys():
            return False
        self._vertices_in[y].append(x)
        self._vertices_out[x].append(y)
        self._dict_cost[(x, y)] = cost
        self._number_edges += 1
        return True

    def remove_edge(self, x, y):
        if x not in self._vertices_in.keys() or y not in self._vertices_in.keys() or x not in self._vertices_out.keys() or y not in self._vertices_out.keys():
            return False
        if x not in self._vertices_in[y]:
            return False
        if y not in self._vertices_out[x]:
            return False
        elif (x, y) not in self._dict_cost.keys():
            return False
        self._vertices_in[y].remove(x)
        self._vertices_out[x].remove(y)
        self.dict_cost.pop((x, y))
        self._number_edges -= 1
        return True

    def check_edge(self, x, y):
        if x in self._vertices_in[y]:
            return self._dict_cost[(x, y)]
        elif y in self._vertices_out[x]:
            return self._dict_cost[(x, y)]
        return False

    def change_cost_edge(self, x, y, new_cost):
        if (x, y) not in self._dict_cost.keys():
            return False

        self._dict_cost[(x, y)] = new_cost
        return True

    def copy_data(self):
        return copy.deepcopy(self)


def read_from_file(filename):
    file = open(filename, "r")
    line = file.readline()
    line = line.strip()
    nr_of_vert, nr_of_edges = line.split(' ')
    graph = Graph(int(nr_of_vert), int(nr_of_edges))
    line = file.readline().strip()
    while len(line) > 0:
        line = line.split(' ')
        if len(line) == 1:
            graph.vertices_in[int(line[0])] = []
            graph.vertices_out[int(line[0])] = []
        else:
            x_vertex = int(line[0])
            y_vertex = int(line[1])
            cost = int(line[2])
            graph.vertices_in[y_vertex].append(x_vertex)
            graph.vertices_out[x_vertex].append(y_vertex)
            graph.dict_cost[(x_vertex, y_vertex)] = cost
        line = file.readline().strip()
    file.close()
    return graph


def write_to_file(filename, graph):
    file = open(filename, "w")
    graph_size_line = str(graph.number_vertices) + " " + str(graph.number_edges) + "\n"
    file.write(graph_size_line)
    for edge in graph.dict_cost.keys():
        newline = "{} {} {}\n".format(edge[0], edge[1], graph.dict_cost[edge])
        file.write(newline)
    for vertex in graph.vertices_in.keys():
        if len(graph.vertices_in[vertex]) == 0 and len(graph.vertices_out[vertex]) == 0:
            newline = "{}\n".format(vertex)
            file.write(newline)
    file.close()
