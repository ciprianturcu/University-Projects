#include "Graph.h"
#include <cstdlib>
#include <ctime>
#include <algorithm>

Graph::Graph() {
}

Graph::Graph(int nr_vertices) {
    for (int i=0;i<nr_vertices;i++)
        add_vertex(i);
}

Graph::Graph(int nr_vertices, int nr_edges) {
    for (int i=0;i<nr_vertices;i++)
        add_vertex(i);
    srand (time(NULL));
    for (int i=0;i<nr_edges;i++)
    {
        int vertex1 = rand()%nr_vertices;
        int vertex2 = rand()%nr_vertices;
        while (is_edge(vertex1, vertex2))
        {
            vertex1 = rand()%nr_vertices;
            vertex2 = rand()%nr_vertices;
        }
        int cost = rand()%101;
        add_edge(vertex1, vertex2, cost);

    }
}

bool Graph::is_edge(int vertex1, int vertex2) const {
    if (*this->outbound.find(vertex1)->second.find(vertex2)!=*this->outbound.find(vertex1)->second.end())
        return true;
    else
        return false;
}

bool Graph::is_vertex(int vertex) const {
    if (this->vertices.find(vertex)!=this->vertices.end())
        return true;
    else
        return false;
}

bool Graph::add_vertex(int vertex) {
    if (is_vertex(vertex))
        return false;
    this->vertices.insert(vertex);
    std::set<int> inbound_;
    this->inbound.insert({vertex, inbound_});
    std::set<int> outbound_;
    this->outbound.insert({vertex, outbound_});
    return true;
}

bool Graph::add_edge(int vertex1, int vertex2, int cost) {
    if (is_edge(vertex1, vertex2))
        return false;
    if (!is_vertex(vertex1) || !is_vertex(vertex2))
        return false;
    this->outbound.at(vertex1).insert(vertex2);
    this->inbound.at(vertex2).insert(vertex1);
    //this->edges.insert(Edge(vertex1, vertex2, cost));
    std::pair<int, int> key(vertex1, vertex2);
    this->cost.insert({key, cost});
    return true;
}

int Graph::get_number_of_vertices() const {
    return this->vertices.size();
}

int Graph::get_number_of_edges() const {
    return this->cost.size();
}

int Graph::get_out_degree(int vertex) const {
    if (!is_vertex(vertex))
        return -1;
    return this->outbound.at(vertex).size();
}

int Graph::get_in_degree(int vertex) const {
    if(!is_vertex(vertex))
        return -1;
    return this->inbound.at(vertex).size();
}

int Graph::get_edge_cost(int vertex1, int vertex2) const {
    if(!is_vertex(vertex1) || !is_vertex(vertex2))
        return INT_MIN;
    if(!is_edge(vertex1, vertex2))
        return INT_MIN;
    std::pair<int, int> key(vertex1, vertex2);
    return this->cost.at(key);
}

int Graph::set_edge_cost(int vertex1, int vertex2, int new_cost) {
    if(!is_vertex(vertex1) || !is_vertex(vertex2))
        return INT_MIN;
    if(!is_edge(vertex1, vertex2))
        return INT_MIN;
    std::pair<int, int> key(vertex1, vertex2);
    if (this->cost.find(key)!=this->cost.end()) {
        this->cost.find(key)->second = new_cost;
        return 1;
    }
    return 0;
}

int Graph::remove_edge(int vertex1, int vertex2) {
    if (!is_vertex(vertex1) || !is_vertex(vertex2))
        return -1;
    if (!is_edge(vertex1, vertex2))
        return -1;
    std::pair<int, int>key(vertex1, vertex2);
    if (this->cost.find(key)!=this->cost.end())
    {
        this->cost.erase(key);
        this->inbound.at(vertex2).erase(vertex1);
        this->outbound.at(vertex1).erase(vertex2);
        return 1;
    }
    return -1;
}

int Graph::remove_vertex(int vertex) {
    if (!is_vertex(vertex))
        return -1;
    std::vector<int> temporary;
    auto it = this->outbound.at(vertex).begin();
    while (it != this->outbound.at(vertex).end())
    {
        temporary.push_back(*it);
        it++;
    }

    for(auto it = std::begin(temporary);it!=std::end(temporary); it++)
        remove_edge(vertex, *it);

    it = this->inbound.at(vertex).begin();
    temporary.clear();
    while (it!= this->inbound.at(vertex).end())
    {
        temporary.push_back(*it);
        it++;
    }

    for(auto it = std::begin(temporary);it!=std::end(temporary); it++)
        remove_edge(*it, vertex);

    this->inbound.erase(vertex);
    this->outbound.erase(vertex);
    this->vertices.erase(vertex);
    return 1;
}

Graph::Graph(const Graph &gr) {
    //std::copy(gr.vertices.begin(), gr.vertices.end(), this->vertices.begin());
    for (auto it = gr.vertices.begin();it!=gr.vertices.end();it++)
        this->vertices.insert(*it);
    //std::copy(gr.cost.begin(), gr.cost.end(), this->cost.begin());
    for (auto it = gr.cost.begin(); it!=gr.cost.end();it++)
        this->cost.insert(*it);
    //std::copy(gr.outbound.begin(), gr.outbound.end(), this->outbound.begin());
    for (auto it = gr.inbound.begin();it!=gr.inbound.end();it++)
        this->inbound.insert(*it);
    //std::copy(gr.inbound.begin(), gr.inbound.end(), this->inbound.begin());
    for (auto it = gr.outbound.begin();it!=gr.outbound.end();it++)
        this->outbound.insert(*it);
}

std::set<int>::const_iterator Graph::vertices_begin() {
    return this->vertices.begin();
}

std::set<int>::const_iterator Graph::vertices_end() {
    return this->vertices.end();
}

std::set<int>::const_iterator Graph::outbound_begin(int vertex) {
    return this->outbound.at(vertex).begin();
}

std::set<int>::const_iterator Graph::outbound_end(int vertex) {
    return this->outbound.at(vertex).end();
}

std::set<int>::const_iterator Graph::inbound_begin(int vertex) {
    return this->inbound.at(vertex).begin();
}

std::set<int>::const_iterator Graph::inbound_end(int vertex) {
    return this->inbound.at(vertex).end();
}

std::map<std::pair<int, int>, int>::const_iterator Graph::cost_begin() {
    return this->cost.begin();
}

std::map<std::pair<int, int>, int>::const_iterator Graph::cost_end() {
    return this->cost.end();
}


