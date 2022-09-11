from random import randint
from graphs import UndirectedGraph, write_to_file, read_from_file


class Ui:
    def __init__(self):
        self._graphs = []
        self._current = None

    def add_empty_graph(self):
        if self._current is None:
            self._current = 0
        graph = UndirectedGraph(0, 0)
        self._graphs.append(graph)
        self._current = len(self._graphs) - 1

    def create_random(self):
        v = int(input("Enter the number of vertices: "))
        e = int(input("Enter the number of edges: "))

    def generate_random(self, v, e):
        if e > (v * (v - 1)) // 2:
            raise ValueError("too many edges")
        graph = UndirectedGraph(v, 0)
        i = 0
        while i < e:
            x = randint(0, v - 1)
            y = randint(0, v - 1)
            if graph.add_edge(x, y):
                i += 1
        return graph

    def read_graph_from_file(self):
        filename = input("filename: ")
        if self._current is None:
            self._current = 0
        graph = read_from_file(filename)
        self._graphs.append(graph)
        self._current = len(self._graphs) - 1

    def write_to_file(self):
        filename = input("filename: ")
        write_to_file(self._graphs[self._current], filename)

    def switch_graph_ui(self):
        print("You are on the graph number {}".format(self._current))
        print("Available graphs: 0 - {}".format(str(len(self._graphs) - 1)))
        number = int(input("enter graph number: "))
        if number < 0 and number > len(self._graphs) - 1:
            raise ValueError("Non-existing graph.")
        self._current = number

    def get_number_of_vertices_ui(self):
        print("The number of vertices is: {}. ".format(self._graphs[self._current].nrv))

    def get_number_of_edges_ui(self):
        print("The number of edges is: {}. ".format(self._graphs[self._current].nre))

    def list_ad_matrix(self):
        for x in self._graphs[self._current].parse_vertices():
            line = str(x) + ": "
            for y in self._graphs[self._current].parse_ad_matrix(x):
                line = line + " " + str(y)
            print(line)

    def list_neighbours_vertex(self):
        vertex = int(input("vertex: "))
        line = str(vertex) + ": "
        for y in self._graphs[self._current].parse_ad_matrix(vertex):
            line = line + " " + str(y)
        print(line)

    def parse_all_vertices(self):
        for x in self._graphs[self._current].parse_vertices():
            print(x)

    def add_vertex_ui(self):
        vertex = int(input("vertex: "))
        added = self._graphs[self._current].add_vertex(vertex)
        if added:
            print("vertex added")
        else:
            print("failed")

    def remove_vertex_ui(self):
        vertex = int(input("vertex: "))
        removed = self._graphs[self._current].remove_vertex(vertex)
        if removed:
            print("vertex removed")
        else:
            print("remove failed")

    def add_edge_ui(self):
        x = int(input("first vertex: "))
        y = int(input("second vertex: "))
        add = self._graphs[self._current].add_edge(x, y)
        if add:
            print("edge added")
        else:
            print("add failed")

    def remove_edge_ui(self):
        x = int(input("first vertex: "))
        y = int(input("second vertex: "))
        remove = self._graphs[self._current].remove_edge(x, y)
        if remove:
            print("edge removed")
        else:
            print("removed failed")

    def get_degree_ui(self):
        vertex = int(input("vertex: "))
        degree = self._graphs[self._current].get_degree(vertex)
        if degree == -1:
            print("non-existent vertex")
        else:
            print("the degree of the vertex is {}".format(degree))

    def check_if_edge_ui(self):
        x = int(input("first vertex: "))
        y = int(input("second vertex: "))
        check = self._graphs[self._current].check_edge(x, y)
        if check:
            print("{} {} is an edge.".format(x, y))
        else:
            print("{} {} is not an edge.".format(x, y))

    def copy_current_graph_ui(self):
        copy = self._graphs[self._current].copy_graph()
        self._graphs.append(copy)

    def connected_components(self):
        components = self._graphs[self._current].connected_components()
        for comp in components:
            print(comp)

    def print_menu(self):
        print("MENU:\n"
              "0. EXIT.\n"
              "1. Create a random graph with specified number of vertices and edges.\n"
              "2. Read the graph from a text file.\n"
              "3. Write the graph in a text file.\n"
              "4. Switch the graph.\n"
              "5. Get the number of vertices.\n"
              "6. Get the number of edges.\n"
              "7. List the bound edges of a vertex.\n"
              "8. List all the bound vertices.\n"
              "9. Add a vertex.\n"
              "10. Remove a vertex.\n"
              "11. Add an edge.\n"
              "12. Remove an edge.\n"
              "13. Get the degree of a vertex.\n"
              "14. Check if there is an edge between two given vertices.\n"
              "15. Make a copy of the graph.\n"
              "16. Add an empty graph.\n"
              "17. Parse all the vertices.\n"
              "18. Get the connected components using a breadth-first traversal.")

    def start(self):
        run = True
        self.add_empty_graph()
        command_list = {
            "1": self.create_random,
            "2": self.read_graph_from_file,
            "3": self.write_to_file,
            "4": self.switch_graph_ui,
            "5": self.get_number_of_vertices_ui,
            "6": self.get_number_of_edges_ui,
            "7": self.list_neighbours_vertex,
            "8": self.list_ad_matrix,
            "9": self.add_vertex_ui,
            "10": self.remove_vertex_ui,
            "11": self.add_edge_ui,
            "12": self.remove_edge_ui,
            "13": self.get_degree_ui,
            "14": self.check_if_edge_ui,
            "15": self.copy_current_graph_ui,
            "16": self.add_empty_graph,
            "17": self.parse_all_vertices,
            "18": self.connected_components}
        while run:
            try:
                self.print_menu()
                option = input("option: ")
                if option == "0":
                    run = True
                    print("Exit!")
                elif option in command_list:
                    command_list[option]()
                else:
                    print("Non-existent option")
            except ValueError as ve:
                print(str(ve))
            except FileNotFoundError as fe:
                print(str(fe).strip("[Errno 2] "))


Ui().start()
