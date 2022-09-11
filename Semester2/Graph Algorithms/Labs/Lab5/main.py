from graphs import read_from_file

def Bron_Kerbosch(graph, clique, candidates, excluded, cliques):
    if not candidates and not excluded:
        if len(clique) >= 2:
            cliques.append(clique)
        return

    for v in list(candidates):
        new_candidates = candidates.intersection(graph.get_neighbors(v))
        new_excluded = excluded.intersection(graph.get_neighbors(v))
        Bron_Kerbosch(graph, clique + [v], new_candidates, new_excluded, cliques)
        candidates.remove(v)
        excluded.add(v)


def start():
    graph = read_from_file("graph.txt")
    clique_list = list()
    Bron_Kerbosch(graph, [], set(graph.get_vertices()), set(), clique_list)
    max_elem = []
    for elem in clique_list:
        if len(elem) > len(max_elem):
            max_elem = elem
    print(max_elem)


start()
