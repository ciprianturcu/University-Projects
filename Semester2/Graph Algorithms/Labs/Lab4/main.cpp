#include "Graph.h"
#include <bits/stdc++.h>

using namespace std;

vector<int> toposort(Graph &g) {
    vector<int> in_degree(g.get_number_of_vertices(), 0);
    for (auto edge = g.cost_begin();edge != g.cost_end();++edge)
        ++in_degree[edge->first.second];
    queue<int> q;
    for(int i = 0; i<g.get_number_of_vertices();++i)
    {
        if(in_degree[i] == 0)
            q.push(i);
    }
    vector<int> topo;
    while(!q.empty())
    {
        int nod = q.front();
        q.pop();
        topo.push_back(nod);
        for(auto x = g.outbound_begin(nod); x!=g.outbound_end(nod);++x)
        {
            --in_degree[*x];
            if(in_degree[*x]==0)
                q.push(*x);
        }

    }
    return topo;
}

void scheduling_problem()
{
    std::ifstream f("test_graph.txt");
    int n;
    f>>n;
    Graph g(n);
    vector<int> duration;
    for(int i = 0; i < n; ++i)
    {
        int pre, time;
        f>>pre>>time;
        duration.push_back(time);
        for(int j=0;j<pre;j++)
        {
            int x;
            f>>x;
            g.add_edge(x,i,0);
        }
    }
    auto order = toposort(g);
    for(auto o: order)
    {
        cout <<o <<" ";
    }
    cout<<endl;
    if(order.size() != g.get_number_of_vertices()){
        cout<<"not a dag!\n";
        return;
    }
    vector<int> earliest(g.get_number_of_vertices()), latest(g.get_number_of_vertices());
    int total_time =0;

    for (auto x : order)
    {
        earliest[x] = 0;
        for(auto it = g.inbound_begin(x); it!=g.inbound_end(x);++it)
        {
            earliest[x] = max(earliest[x], earliest[*it]+duration[*it]);
        }
    }
    for(auto x: order)
    {
        total_time = max(total_time, earliest[x]+duration[x]);
    }
    for(auto it = order.rbegin();it!=order.rend();++it)
    {
        latest[*it] = total_time - duration[*it];
        for (auto x = g.outbound_begin(*it); x != g.outbound_end(*it);++x)
        {
            latest[*it] = min(latest[*it],latest[*x]-duration[*it]);
        }
    }
    for(int i=0;i<g.get_number_of_vertices();++i)
        cout << "For activity " << i << " the earliest starting time is " << earliest[i] << " and the latest is " << latest[i] << ".\n";
    cout << "The total time to finish the project is " << total_time << ".\n";
    cout << "The critical activities are ";
    for (int i = 0; i < g.get_number_of_vertices(); ++i)
        if (earliest[i] == latest[i])
            cout << i << " ";
    cout << "\n";
    f.close();
}

int main() {
    scheduling_problem();
    return 0;
}
