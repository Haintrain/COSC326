import networkx as nx
import matplotlib.pyplot as plt
import sys


def main():
    G = nx.Graph()

    if sys.stdin.isatty():
        print("Invalid: No input")
        exit()

    file = sys.stdin
    firstLine = True
    findRoute = ""

    f1 = file.readlines()
    for x in f1:
        x = x.replace(" ", "")
        x = x.strip()
        l = x.split(',')

        for i in range(len(l)):
            l[i] = l[i].capitalize()

        if firstLine:
            firstLine = False

            if len(l) != 2:
                print("Invalid: route")
                exit()

            findRoute = l

        else:
            if len(l) != 3:
                print("Invalid: route set")
                exit()

            if G.has_edge(l[0], l[1]):
                print("Invalid: Non-unique routes")
                exit()
            else:
                G.add_edge(l[0], l[1], weight=float(l[2]))

    nodelist = nx.shortest_path(G, source=findRoute[0], target=findRoute[1], weight='weight')

    edgelist = zip(nodelist, nodelist[1:])
    edgelist = set(edgelist)

    otherlist = G.nodes
    otheredges = G.edges

    drawedges = [];

    drawlist = [x for x in otherlist if x not in nodelist]

    for x in otheredges:
        a = x[0], x[1]
        b = x[1], x[0]

        if a not in edgelist and b not in edgelist:
            drawedges.append(x)

    pos = nx.spring_layout(G)
    plt.figure(figsize=(10,10))

    nx.draw_networkx_nodes(G, pos, nodelist=nodelist, node_color='orange', node_size=500, alpha=0.8)
    nx.draw_networkx_nodes(G, pos, nodelist=drawlist, node_color='blue', node_size=500, alpha=0.5)
    nx.draw_networkx_labels(G, pos, fontsize=14)

    nx.draw_networkx_edges(G, pos, edgelist=edgelist, edge_color='green', width=2)
    nx.draw_networkx_edges(G, pos, edgelist=drawedges, edge_color='blue', width=2, style='dotted', alpha=0.5)

    labels = nx.get_edge_attributes(G, 'weight')
    nx.draw_networkx_edge_labels(G, pos, edge_labels=labels)

    plt.title('COSC 326 - Cheapest Bus Route')
    plt.savefig("Graph.png", format="PNG")

if __name__ == '__main__':
    main();
