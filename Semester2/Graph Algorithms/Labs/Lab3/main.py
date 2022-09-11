from random import randint

import graph
from graph import Graph, read_from_file, write_to_file, minimum_cost_path


class Ui:
    def __init__(self):
        self._graph_list = []
        self._current = None

    def get_nr_of_vertices(self):
        print("The number of vertices is : {}".format(self._graph_list[self._current].number_vertices))

    def parse_vertices(self):
        for x in self._graph_list[self._current].parse_vertices():
            print("{}".format(x))

    def check_if_edge_ui(self):
        x_vertex = int(input("input the first vertex: "))
        y_vertex = int(input("input the second vertex: "))
        edge = self._graph_list[self._current].check_edge(x_vertex, y_vertex)
        if edge is not False:
            print("({},{}) is an edge.\n".format(x_vertex, y_vertex))
        else:
            print("({},{}) is not an edge.\n".format(x_vertex, y_vertex))

    def get_in_degree_of_vertex(self):
        vertex = int(input("vertex : "))
        degree = self._graph_list[self._current].get_in_degree(vertex)
        if degree is not False:
            print("{} vertex has an in degree of {}.\n".format(vertex, degree))
        else:
            print("{} vertex has an in degree of 0.\n".format(vertex))

    def get_out_degree_of_vertex(self):
        vertex = int(input("vertex : "))
        degree = self._graph_list[self._current].get_out_degree(vertex)
        if degree is not False:
            print("{} vertex has an out degree of {}.\n".format(vertex, degree))
        else:
            print("{} vertex has an out degree of 0.\n".format(vertex))

    def parse_outbound_edges_of_vertex(self):
        vertex = int(input("vertex : "))
        edges = self._graph_list[self._current].parse_out(vertex)
        result_string = str(vertex) + " :"

        for x in edges:
            result_string = result_string + " " + "({},{})".format(vertex, x)

        print(result_string)

    def parse_inbound_edges_of_vertex(self):
        vertex = int(input("vertex : "))
        edges = self._graph_list[self._current].parse_in(vertex)
        result_string = str(vertex) + " :"

        for x in edges:
            result_string = result_string + " " + "({},{})".format(str(x), str(vertex))
        print(result_string)

    def modify_cost_edge(self):
        print("\nModify the cost of an edge.")
        x_vertex = int(input("input the first vertex: "))
        y_vertex = int(input("input the second vertex: "))
        new_cost = int(input("input the new cost: "))

        result = self._graph_list[self._current].change_cost_edge(x_vertex, y_vertex, new_cost)
        if result:
            print("Modification was successful.")
        else:
            print("Error, the edge does not exist!")

    def add_vertex_ui(self):
        vertex = int(input("enter new vertex: "))
        result = self._graph_list[self._current].add_vertex(vertex)
        if result is not False:
            print("Added successfully.")
        else:
            print("Error, vertex already exists!")

    def delete_vertex(self):
        vertex = int(input("enter vertex to delete: "))
        result = self._graph_list[self._current].remove_vertex(vertex)
        if result is not False:
            print("Removed successfully.")
        else:
            print("Error, vertex non-existent!")

    def add_edge_ui(self):
        x_vertex = int(input("input the first vertex: "))
        y_vertex = int(input("input the second vertex: "))
        cost = int(input("input the cost: "))
        result = self._graph_list[self._current].add_edge(x_vertex, y_vertex, cost)
        if result is not False:
            print("Added successfully.")
        else:
            print("Error, edge already exists!")

    def delete_edge(self):
        x_vertex = int(input("input the first vertex: "))
        y_vertex = int(input("input the second vertex: "))

        result = self._graph_list[self._current].remove_edge(x_vertex, y_vertex)
        if result is not False:
            print("Removed successfully.")
        else:
            print("Error, edge non-existent!")

    def copy_graph(self):
        copy = self._graph_list[self._current].copy_data()
        self._graph_list.append(copy)

    def read_graph_from_file(self):
        filename = input("Please provide the filename:")
        if self._current is None:
            self._current = 0
        graph = read_from_file(filename)
        self._graph_list.append(graph)
        self._current = len(self._graph_list) - 1

    def write_graph_to_file(self):
        filename = input("filename: ")
        write_to_file(filename, self._graph_list[self._current])

    def initialize_new_graph(self):
        if self._current is None:
            self._current = 0
        graph = Graph(0, 0)
        self._graph_list.append(graph)
        self._current = len(self._graph_list) - 1

    def ui_generate_graph_random(self):
        nr_of_vert = int(input("enter nr of vertices: "))
        nr_of_edges = int(input("enter the number of edges: "))
        graph = self.generate_random_graph(nr_of_vert, nr_of_edges)

        if self._current is None:
            self._current = 0

        self._graph_list.append(graph)
        self._current = len(self._graph_list) - 1

    def generate_random_graph(self, v, e):
        if (e > v * v):
            graph = Graph(v, 0)
            raise ValueError("to many edges")
        graph = Graph(v, 0)
        i = 0
        while (i < e):
            x = randint(0, v - 1)
            y = randint(0, v - 1)
            c = randint(0, 1000)
            if graph.add_edge(x, y, c):
                i += 1
        return graph

    def switch_to_other_graph(self):
        print("Current graph is {}\n".format(self._current))
        print("Available graphs 0-{}\n".format(self._current))
        number = int(input("option:"))
        if number > 0 and number <= len(self._graph_list):
            raise ValueError("Can't switch, this graph does not exist!")
        self._current = number

    def initialize_command_dict(self):
        command_dict = {"1": self.get_nr_of_vertices, "2": self.parse_vertices,
                        "3": self.check_if_edge_ui, "4": self.get_in_degree_of_vertex,
                        "5": self.get_out_degree_of_vertex, "6": self.parse_outbound_edges_of_vertex,
                        "7": self.parse_inbound_edges_of_vertex, "8": self.modify_cost_edge,
                        "9": self.add_edge_ui, "10": self.add_vertex_ui, "11": self.delete_edge,
                        "12": self.delete_vertex, "13": self.copy_graph,
                        "14": self.read_graph_from_file, "15": self.write_graph_to_file,
                        "16": self.ui_generate_graph_random, "17": self.switch_to_other_graph,
                        "18": self.get_min_cost_path
                        }
        return command_dict

    @staticmethod
    def print_menu():
        print("\n0.Exit\n"
              "1. Get number of vertices current graph.\n"
              "2. Parse vertices.\n"
              "3. Check existence of edge.\n"
              "4. Get in degree of a vertex.\n"
              "5. Get out degree of a vertex.\n"
              "6. Parse outbound edges of a vertex.\n"
              "7. Parse inbound edges of a vertex.\n"
              "8. Modify cost of an edge.\n"
              "9. Add edge to current graph.\n"
              "10. Add vertex to current graph.\n"
              "11. Remove edge of current graph.\n"
              "12. Remove vertex of current graph.\n"
              "13. Copy current graph.\n"
              "14. Read graph from file.\n"
              "15. Write graph to file.\n"
              "16. Generate random graph.\n"
              "17. Switch to other graph.\n"
              "18. Get min cost path.\n"
              )

    def get_min_cost_path(self):
        start_vertex = int(input("\nStart vertex: "))
        end_vertex = int(input("\nEnd vertex: "))
        min_cost, path = minimum_cost_path(self._graph_list[self._current],start_vertex,end_vertex)
        print (str(min_cost))
        print()
        print(path[::-1])



    def start(self):
        run = True
        self.initialize_new_graph()
        commands = self.initialize_command_dict()
        while run:
            try:
                self.print_menu()
                option = input("Option:")
                if option == '0':
                    run = False
                    print("program closed!")
                elif option in commands:
                    commands[option]()
                else:
                    print("Invalid command!\n")
            except ValueError as ve:
                print(str(ve))
            except FileNotFoundError as fe:
                print(str(fe))


Ui().start()
