#pragma once

class Edge {
private:
     int vertex1;
     int vertex2;
     int cost;
public:
    Edge();
    Edge(int vertex1, int vertex2, int cost);
    Edge(const Edge& e);

    int get_cost() const;

    void set_new_cost(int new_cost);

};


