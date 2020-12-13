package api;

import java.util.*;

/**
 * This class implements given interface dw_graph_algorithms and
 * represents an algorithm class works on "directed_weighted_graph" variables.
 */
public class DWGraph_Algo implements dw_graph_algorithms {
    private directed_weighted_graph myGraph;
    public DWGraph_Algo(){

    }
    /**
     * Initiate the graph on which this set of algorithms operates on.
     * @param g
     */
    @Override
    public void init(directed_weighted_graph g) {
        myGraph = g;
    }

    /**
     * @return the underlying graph of which this class works on.
     */
    @Override
    public directed_weighted_graph getGraph() {
        return myGraph;
    }

    /**
     * @return a deep copy of the graph of which this class works on.
     */
    @Override
    public directed_weighted_graph copy() {
        return new DWGraph_DS(myGraph);
    }

    /**
     * resets all weights of edges in the graph to a given value.
     * @param prm - the tag to reset to.
     */
    private void resetWeightsTo(int prm)
    {
        Iterator<node_data> neighs = myGraph.getV().iterator();
        while (neighs.hasNext())
            neighs.next().setWeight(-1);
    }

    /**
     * checks if all the nodes connected to the directional weighted graph.
     * using an iterator in a loop to get a node and marks it (using the tag).
     * @return true if all nodes connected
     */
    @Override
    public boolean isConnected() {
        return false;
    }

    /**
     * using the Dijkstra algorithm. The function resets all tags to -1 using resetTagsTo(-1).
     * An iterator sets every node's tag to the distance from the source starting from 0 on source node.
     * @param src - start node
     * @param dest - end (target) node
     * @return  the shortest path's distance to a destination node from the source node
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        node_data srcNode = myGraph.getNode(src), destNode = myGraph.getNode(dest);
        if (srcNode == null || destNode == null)
            return -1;
        if(dest == src)
            return 0;
        resetWeightsTo(-1);
        PriorityQueue<node_data> q = new PriorityQueue<>(myGraph.edgeSize() , Comparator.comparingDouble(node_data::getWeight));
        srcNode.setTag(0);
        Iterator<edge_data> firstEdges = myGraph.getE(srcNode.getKey()).iterator();
        while (firstEdges.hasNext()) {
            edge_data e = firstEdges.next();
            node_data firstNeighs = myGraph.getNode(e.getDest());
            firstNeighs.setWeight(e.getWeight());
            q.add(firstNeighs);
        }
        while(!q.isEmpty()){
            node_data nodeSearch = q.poll();
            if (nodeSearch.getKey() == dest)
                return nodeSearch.getWeight();
            Iterator<edge_data> itr = myGraph.getE(nodeSearch.getKey()).iterator();
            edge_data neighs;
            while (itr.hasNext()) {
                neighs = itr.next();
                node_data neighbor = myGraph.getNode(neighs.getDest());
                if (neighbor.getWeight() == -1 || nodeSearch.getWeight() + neighs.getWeight() < neighbor.getWeight()) {
                    neighbor.setWeight(nodeSearch.getWeight() + neighs.getWeight());
                    q.add(neighbor);
                }
            }
        }
        return destNode.getWeight();
    }

    /**
     * using Dijkstra's algorithm.
     * using shortestPathDist(int src, int dest) all the nodes has they're weights set to the distance from the source to them.
     * if there's no such path the function returns null. else, the function
     * adds each time a node to a list from the destination node to the source using the tags to decide the path
     * (the path must go through each minimal neighbor tag from n to 0 once, n being the distance to destination node).
     * @param src - start node
     * @param dest - end (target) node
     * @return  the shortest path as a list to a destination node from the source node.
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        node_data srcNode = myGraph.getNode(src), destNode = myGraph.getNode(dest);
        if (srcNode == null || destNode == null)
            return null;
        double pathLength = shortestPathDist(src, dest);
        if (pathLength == -1)
            return null;
        List<node_data> path = new ArrayList<>();
        path.add(srcNode);
        if (pathLength == 0)
            return path;
        path.remove(srcNode);
        List<node_data> pathMirror = new ArrayList<>();
        double smallestEdge =  destNode.getWeight();
        node_data smallestNei = destNode;
        node_data curr = destNode;
        while (curr.getKey() != src) {
            pathMirror.add(curr);
            Iterator<edge_data> itr = myGraph.getE(curr.getKey()).iterator();
            edge_data neighs;
            while (itr.hasNext()) {
                neighs = itr.next();
                if (neighs.getWeight() < smallestEdge && neighs.getTag() != -1) {
                    smallestEdge = neighs.getTag();
                   // smallestNei = neighs;
                }
            }
            curr = smallestNei;
        }
        pathMirror.add(curr);
        while (!pathMirror.isEmpty())
            path.add(pathMirror.remove(pathMirror.size() - 1));
        return path;
    }

    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        return false;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph remains "as is".
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        return false;
    }
}
