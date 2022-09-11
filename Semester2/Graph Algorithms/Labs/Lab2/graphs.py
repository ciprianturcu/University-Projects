import copy


class UndirectedGraph:
    def __init__(self, nr_of_vertices, nr_of_edges):
        self._nrv = nr_of_vertices
        self._nre = nr_of_edges
        self._ad_matrix = {}
        self._vertices = []
        for index in range(nr_of_vertices):
            self._ad_matrix[index] = []
            self._vertices.append(index)

    @property
    def nrv(self):
        return self._nrv

    @property
    def nre(self):
        return self._nre

    @property
    def ad_matrix(self):
        return self._ad_matrix

    @property
    def vertices(self):
        return self._vertices

    @nre.setter
    def nre(self, new_nre):
        self._nre = new_nre

    def parse_vertices(self):
        for v in self._vertices:
            yield v

    def parse_ad_matrix(self, x):
        for m in self._ad_matrix[x]:
            yield m

    def add_vertex(self, x):
        """
        Add a vertex to a graph if it doesn't already exist.
        :param x: the vertex to be added
        :return: True if it is added , False otherwise.
        """
        if x in self._vertices:
            return False
        self._vertices.append(x)
        self._ad_matrix[x] = []
        self._nrv += 1
        return True

    def remove_vertex(self, x):
        """
        Remove a vertex from a graph.
        :param x: vertex to be removed
        :return: True if it was removed, False otherwise.
        """
        if x not in self._vertices or x not in self._ad_matrix[x]:
            return False
        self._vertices.remove(x)
        self._ad_matrix.pop(x)
        for key in self._ad_matrix.keys():
            if x in self._ad_matrix[key]:
                self._ad_matrix[key].remove(x)
                self._nre -= 1
        return True

    def add_edge(self, x, y):
        """
        Add an edge if it is valid: the vertices exist and the edge does not exist
        :param x: first vertex
        :param y: second vertex
        :return: False if it can't be placed in the graph, True otherwise.
        """
        if x not in self._vertices or y not in self._vertices:  # vertices do not exist
            return False
        if x == y:  # invalid edge case
            return False
        if x in self._ad_matrix[y] and y in self._ad_matrix[x]:  # already existing edge
            return False

        self._ad_matrix[y].append(x)
        self._ad_matrix[x].append(y)
        self._nre += 1

        return True

    def remove_edge(self, x, y):
        """
        Remove an edge if the vertices exist and the edge exists too
        :param x: first vertex
        :param y: second vertex
        :return: False if the edge couldn't be removed, True if it was removed.
        """
        if x not in self._vertices or y not in self._vertices:
            return False
        if x == y:
            return False
        if x not in self._ad_matrix[y] or y not in self._ad_matrix[x]:
            return False

        self._ad_matrix[x].remove(y)
        self._ad_matrix[y].remove(x)
        self._nre -= 1
        return True

    def degree(self, x):
        """
        Get the degree of a vertex if it exists
        :param x: the vertex for which we get the degree
        :return: the number of bound vertices or -1 if the vertex does not exist
        """
        if x not in self._ad_matrix.keys():
            return False
        return len(self._ad_matrix[x])

    def check_edge(self, x, y):
        """
        Check if a given edge is in the graph
        :param x: first vertex
        :param y: second vertex
        :return: True if the edge exists, False otherwise.
        """
        if x not in self._vertices or y not in self._vertices:
            return False
        if x in self._ad_matrix[y] and y in self._ad_matrix[x]:
            return True
        return False

    def copy_graph(self):
        """
        Function to make a copy of the graph
        :return: the copy of the graph
        """
        return copy.deepcopy(self)

    def bfs(self, vertex, visited):
        """
        Traverse the graph with a breath-first algorithm for a given vertex
        :param vertex: the starting vertex
        :param visited: a map to mark the visited vertices
        :return: the connected component with the given vertex as a starting vertex
        """
        queue_list = []
        connected = []
        queue_list.append(vertex)
        visited[vertex] = True
        while queue_list:
            x = queue_list.pop(0)
            connected.append(x)
            for i in self._ad_matrix[x]:
                if visited[i] is False:
                    queue_list.append(i)
                    visited[i] = True

        return connected

    def connected_components(self):
        """
        Function using the bfs algorithm to find all the connected components in a graph and put them in a list.
        :return: a list of connected components.
        """
        visited = {}
        connected = []
        all_connected = []
        for v in self._vertices:
            visited[v] = False
        for vertex in self._vertices:
            if visited[vertex] is False:
                connected = self.bfs(vertex, visited)
                all_connected.append(connected)
        return all_connected


def write_to_file(graph, file):
    """
    Write the given graph to a file
    :param graph: the graph to be written
    :param file: the name of the file
    :return: -
    """
    file = open(file, "w")
    first_line = str(graph.nrv) + ' ' + str(graph.nre)
    file.write(first_line)
    if len(graph.vertices) == 0 and len(graph.ad_matrix) == 0:
        raise ValueError(" Nothing to be written!")
    edges = []
    for vertex in graph.vertices:
        if len(graph.ad_matrix[vertex]) != 0:
            for second_vertex in graph.ad_matrix[vertex]:
                edge = (vertex, second_vertex)
                if (vertex, second_vertex) not in edges and (second_vertex, vertex) not in edges:
                    edges.append(edge)
        else:
            new_line = '{}\n'.format(vertex)
            file.write(new_line)
    for edge in edges:
        new_line = '{} {}\n'.format(edge[0], edge[1])
        file.write(new_line)
    file.close()


def read_from_file(file):
    """
    Read a graph from a given file
    :param file: The name of the file from which the graph will be read
    :return: the new graph
    """
    file = open(file, "r")
    line = file.readline()
    line.strip()
    vert, edges = line.split(' ')
    graph = UndirectedGraph(int(vert), 0)
    line = file.readline().strip()
    while len(line) > 0:
        line = line.split(' ')
        if len(line) == 3 and int(line[0]) != int(line[1]):
            if int(line[0]) not in graph.ad_matrix[int(line[1])]:
                graph.nre += 1
                graph.ad_matrix[int(line[1])].append(int(line[0]))
            if int(line[1]) not in graph.ad_matrix[int(line[0])]:
                graph.ad_matrix[int(line[0])].append(int(line[1]))
        line = file.readline().strip()
    file.close()
    return graph
