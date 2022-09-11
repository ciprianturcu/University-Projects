#include "Edge.h"

Edge::Edge() {
    vertex1 = -1;
    vertex2 = -1;
    cost = -1;
}

Edge::Edge(int vertex1, int vertex2, int cost) {
    this->vertex1 = vertex1;
    this->vertex2 = vertex2;
    this->cost = cost;
}

int Edge::get_cost() const {
    return this->cost;
}

void Edge::set_new_cost(int new_cost) {
    this->cost = new_cost;
}

Edge::Edge(const Edge& e) {
    this->vertex1 = e.vertex1;
    this->vertex2 = e.vertex2;
    this->cost = e.cost;
}
