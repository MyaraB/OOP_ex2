package api;

import gameClient.util.Point3D;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class DWGraph_DS implements directed_weighted_graph {
    private HashMap<Integer, node_data> nodes;
    private int nodeSize, edgeSize;
    private int mcCounter = 0;
    private HashMap<Integer, HashMap<Integer, edge_data>> edges;

    public DWGraph_DS() {
        nodes = new HashMap<>();
        edges = new HashMap<>();
        nodeSize = 0;
        edgeSize = 0;
    }

    /**
     * deep copying builder, works in O(n^2) to deep copy the nodes and the edges in between.
     *
     * @param origin - the copied graph
     */
    public DWGraph_DS(directed_weighted_graph origin) {
        nodes = new HashMap<>();
        edges = new HashMap<>();
        Iterator<node_data> neigh = origin.getV().iterator();
        node_data curr;
        while (neigh.hasNext()) {
            curr = neigh.next();
            addNode(curr);
        }
        for (int i : nodes.keySet())
            for (int j : nodes.keySet())
                if (origin.getEdge(i, j) != null)
                    connect(i, j, origin.getEdge(i, j).getWeight());

        nodeSize = origin.nodeSize();
        edgeSize = origin.edgeSize();
        mcCounter = origin.getMC();
    }

    /**
     * returns the node asked through the given key.
     *
     * @param key - the node_id
     * @return
     */
    @Override
    public node_data getNode(int key) {
        return nodes.get(key);
    }

    /**
     * gets the weight of the edge between the 2 given nodes
     *
     * @param src
     * @param src
     * @return the edge (if does not exist - null)
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        if(edges.get(src) != null)
            return edges.get(src).get(dest);
        return null;
    }

    /**
     * adds a given node to the graph
     * Note: if there is a node with specified key the new node will not be added.
     *
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        if (nodes.get(n) == null) {
            nodes.put(n.getKey(), n);
            nodeSize++;
            mcCounter++;
        }
    }

    /**
     * connects the given nodes with an edge weighted as requested.
     *
     * @param src
     * @param dest
     * @param w    - weight of the edge connecting
     */
    @Override
    public void connect(int src, int dest, double w) {
        node_data n1 = getNode(src), n2 = getNode(dest);
        if (n1 != null && n2 != null && src != dest) {
            if (!edges.containsKey(src)) {
                HashMap<Integer, edge_data> inner = new HashMap<>();
                inner.put(dest, new EdgeData(src, dest, w));
                edges.put(src, inner);
                edgeSize++;
                mcCounter++;
            } else if (getEdge(src, dest) == null) {
                edges.get(src).put(dest, new EdgeData(src, dest, w));
                edgeSize++;
                mcCounter++;
            }
        }
    }

    /**
     * @return a collection of nodes in the graph
     */
    @Override
    public Collection<node_data> getV() {
        return nodes.values();
    }

    /**
     * @param node_id
     * @return a collection of nodes that the given node is connected to (directionally, given node is src).
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        return edges.get(node_id).values();
    }

    /**
     * delete the given node
     * Note: deleting the node deletes all edges the node has.
     *
     * @param key
     * @return the deleted node
     */
    @Override
    public node_data removeNode(int key) {
        if (getNode(key) != null && this.getE(key) != null) {
            Iterator<Integer> neigh = edges.get(key).keySet().iterator();
            int n;
            while (neigh.hasNext()) {
                n = neigh.next();
                removeEdge(key, n);
            }
            nodeSize--;
            mcCounter++;
            return nodes.remove(key);
        }
        return null;
    }

    /**
     * Delete the edge from the graph.
     *
     * @param src
     * @param dest
     * @return the edge between src and dest
     * Note: if does not exist return null.
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        if (getEdge(src, dest) != null) {
            edgeSize--;
            mcCounter++;
            return edges.get(src).remove(dest);
        }
        return null;
    }

    /**
     * @return the number of vertices (nodes) in the graph.
     */
    @Override
    public int nodeSize() {
        return nodeSize;
    }

    /**
     * @return the number of edges in the graph.
     */
    @Override
    public int edgeSize() {
        return edgeSize;
    }

    /**
     * @return the Mode Count - for testing changes in the graph.
     */
    @Override
    public int getMC() {
        return mcCounter;
    }

}
