# OOP_ex2
 Class exercize 2 of Object Orinatated Programming class.
 This document explains the project structure and the methodes implemented in it.
 The methodes implementation explained before any methode in the source files.
 Get/set methodes will not be explained.
 
 Ex2.data: contains data examples to test the pokemon game.
 Ex2.docs: contains a guide to the pokemon game and the exercise build instructions.
 Ex2.libs: contains the libraries used to build the project.
 
 Ex2.src - packages:
 
 tests package:
 *DWGraph_DS - tests DWGraph_DS methodes.
 *DWGraph_Algo - tests DWGraph_Algo methodes.
 
 api package:
 classes implemented in the package:
 
 *NodeData (implements given interface node_data), represents a node (vertex) in a directional weighted graph.
 
 *EdgeData (implements given interface edge_data), represents an edge in a directional weighted graph, connecting two nodes.

*DWGraph_DS (implements given interface directed_weighted_graph), implements a directed weighted graph.

*DWGraph_Algo (implements given interface dw_graph_algorithms), an algorithm class works on "directed_weighted_graph" variables.

Note: geo_location interface is implemented as Point3D which can be found at src.gameClient.util

Note 2: edge_location interface is not implemented since it has no use in this project.
 
 
 *NodeData*
uses following parameters - key (an integer uniqe to every node), info (string value stored in node), weight(double value - will to be used in DWGraph_Algo) and tag (an integer 

to mark a node for sorts to be determined).

The methods implemented in the  java class:

public NodeData() - empty builder.

public NodeData(int key) - build a node with a specified key.

public NodeData(node_data n) - coping builder.

public int getKey().

get \ set Weight.

get \ set Info.

get \ set Tag.
 
 *EdgeData*

uses the following parameters - src (a node's key the edge sdarts from), dest (a node's key the edge ends in), tag(an integer to mark a node for sorts to be determined), info 

(string value stored in edge) and weight(double value of the edge's weight).

The methods implemented in the  java class:

public EdgeData(src, dest, weight) - edge builder.

getSrc.

getDest.

getWeight.

get \ set Info.

get \ set Tag.
 
 *DWGraph_DS*
uses the following parameters - nodes (HashMap of the nodes in the graph), nodeSize(the amount of nodes in the graph), edgeSize(the amount of connections between nodes in the 

graph), mcCounter (a counter of the actions that changes the graph), edges (HashMap of edges between nodes in the graph and their weight).

The methods implemented in the  java class:

public DWGraph_DS() - empty builder.

public DWGraph_DS(directed_weighted_graph origin) - deep coping builder.

public node_data getNode(int key).

public double getEdge(int src, int dest).

public void addNode(node_data n).

public void connect(int src, int dest, double w).

public Collection<node_data> getV() - returns a collection of all nodes in the graph.

public Collection<node_data> getE(int node_id) - a collection of nodes that the given node is connected to (directionally, given node is src).

public node_info removeNode(int key).

public void removeEdge(int node1, int node2).

public int nodeSize() \ edgeSize() \ getMC().


 *DWGraph_Algo*
 uses the following parameters - myGraph (the graph which the algorithm class works on).
 The methods implemented in the  java class:
 public 

public directed_weighted_graph copy() - creates a deep copy of myGraph using DWGraph_DS(directed_weighted_graph origin).

private void  resetWeightTo(int prm) - resets all nodes' weights to the parameter required with an iterator going through myGraph's values using getV().

private  node_data minNeighbor(node_data n) - finds the min weight neighbor to a node around it (directionally).

public boolean isConnected() - Returns true if and only if (iff) there is a valid path from each node to each other node. using an iterator in a loop to get a node and checking 

using shortestPathDist to see if there is a path from every node to a certain node and from the same certain node to every node.

public double shortestPathDist(int src, int dest) - using the Dijkstra algorithm. The function resets all tags to -1 using resetTagsTo(-1). An iterator sets every node's weight 

to the distance from the source starting from 0 on source node. Returns the shortest path's distance to a destination node from the source node.

public List<node_info> shortestPath(int src, int dest) - using Dijkstra's algorithm. using shortestPathDist(int src, int dest) all the nodes has they're weights set to the 

distance from the source to them. if there's no such path the function returns null. else, the function adds each time a node to a list from the destination node to the source 

using the weight to decide the path. (the path must go through each minimal neighbor tag from n to 0 once, n being the distance to destination node).

private JSONObject toJason(node_data n) - JSON format save for node.

private JSONObject toJason(edge_data n) - JSON format save for edge.

public boolean save(String file) - saves this weighted (directed) graph to the given with the file name - in JSON format

public boolean load(String file) - loads a graph to this graph algorithm. if the file was successfully loaded - the underlying graph of this class will be changed (to the loaded 

one), in case the graph was not loaded the original graph remains "as is".
