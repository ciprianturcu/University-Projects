#pragma once
#include <map>
#include <set>
#include <vector>
#include "Edge.h"

class Graph {
private:
    std::set<int> vertices;
    std::map<int, std::set<int>> inbound;
    std::map<int, std::set<int>> outbound;
    std::map<std::pair<int,int>, int> cost;
public:
    Graph();
    explicit Graph(int nr_vertices);
    Graph(int nr_vertices, int nr_edges);
    Graph(const Graph& gr);


    bool is_edge(int vertex1, int vertex2) const;
    bool is_vertex(int vertex) const;

    bool add_vertex(int vertex);
    bool add_edge(int vertex1, int vertex2, int cost);

    int get_number_of_vertices() const;
    int get_number_of_edges() const;
    int get_out_degree(int vertex) const;
    int get_in_degree(int vertex) const;

    int get_edge_cost(int vertex1, int vertex2) const;
    int set_edge_cost(int vertex1, int vertex2, int new_cost);

    int remove_edge(int vertex1, int vertex2);
    int remove_vertex(int vertex);


    ///iterators:
    std::set<int>::const_iterator vertices_begin();
    std::set<int>::const_iterator vertices_end();
    std::set<int>::const_iterator outbound_begin(int vertex);
    std::set<int>::const_iterator outbound_end(int vertex);
    std::set<int>::const_iterator inbound_begin(int vertex);
    std::set<int>::const_iterator inbound_end(int vertex);
    std::map<std::pair<int,int>, int>::const_iterator cost_begin();
    std::map<std::pair<int,int>, int>::const_iterator cost_end();
};


