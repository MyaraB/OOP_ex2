package api;

import gameClient.util.Point3D;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
            neighs.next().setWeight(prm);
    }

    /**
     * finds the min weight neighbor to a node around it (directionally).
     * @param n
     * @return the min src node that n is neighbor to.
     */
    private  node_data minNeighbor(node_data n){
        Iterator<node_data> itr = myGraph.getV().iterator();
        node_data neigh;
        edge_data edge, smallest = null ;
        double minWeight = n.getWeight(), srcWeight;
        while (itr.hasNext()) {
            neigh = itr.next();
            edge = myGraph.getEdge(neigh.getKey(), n.getKey());
            if (smallest == null)
                smallest = edge;
            if (edge != null) {
                srcWeight = myGraph.getNode(edge.getSrc()).getWeight();
                if (srcWeight < minWeight && srcWeight != -1) {
                    smallest = edge;
                    minWeight = srcWeight;
                }
            }
        }
        return myGraph.getNode(smallest.getSrc());
    }
    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node.
     * using an iterator in a loop to get a node and checking using shortestPathDist to see if there
     * is a path from every node to a certain node and from the same certain node to every node.
     * @return true if all nodes connected
     */
    @Override
    public boolean isConnected() {
        if(myGraph.nodeSize() > 1) {
            Iterator<node_data> itr= myGraph.getV().iterator();
            node_data firstNode = itr.next();
            Iterator<node_data> itr2 = myGraph.getV().iterator();
            node_data neighs;
            while (itr2.hasNext()) {
                neighs = itr2.next();
                if (shortestPathDist(firstNode.getKey(),neighs.getKey()) == -1 || shortestPathDist(neighs.getKey(),firstNode.getKey()) == -1)
                    return false;
            }
        }
        return true;
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
        srcNode.setWeight(0);
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
        node_data curr = destNode;
        while (curr.getKey() != src) {
            pathMirror.add(curr);
            curr = this.minNeighbor(curr);
        }
        pathMirror.add(curr);
        while (!pathMirror.isEmpty())
            path.add(pathMirror.remove(pathMirror.size() - 1));
        return path;
    }

    /**
    JSON format save for nodes.
     */
    private JSONObject toJason(node_data n){
        JSONObject obj = new JSONObject();
        try {
            obj.put("key", n.getKey());
            obj.put("info", n.getInfo());
            obj.put("location", ((Point3D)n.getLocation()).toString());
            obj.put("tag", n.getTag());
            obj.put("weight", n.getWeight());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj;
    }

    /**
     JSON format save for edges.
     */
    private JSONObject toJason(edge_data edge){
        JSONObject obj = new JSONObject();
        try {
            obj.put("source", edge.getSrc());
            obj.put("dest", edge.getDest());
            obj.put("info", edge.getInfo());
            obj.put("tag", edge.getTag());
            obj.put("weight", edge.getWeight());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj;
    }
    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        JSONObject graph = new JSONObject();
        JSONArray nodes = new JSONArray();
        JSONObject node;
        JSONArray edges = new JSONArray();
        JSONObject edge;
        Iterator<node_data> itr = myGraph.getV().iterator();
        node_data nodeItr;
        while (itr.hasNext()) {
            nodeItr = itr.next();
            node = toJason(nodeItr);
            nodes.put(node);
            Iterator<edge_data> itr2 = myGraph.getE(nodeItr.getKey()).iterator();
            edge_data edgeItr;
            while (itr2.hasNext()) {
                edgeItr = itr2.next();
                edge = toJason(edgeItr);
                edges.put(edge);
            }
        }
        try {
            graph.put("nodes", nodes);
            graph.put("edges", edges);
            graph.put("nodeSize", myGraph.nodeSize());
            graph.put("edgeSize", myGraph.edgeSize());
            graph.put("mcCounter", myGraph.getMC());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            FileWriter graphSave = new FileWriter(file);
            graphSave.write(graph.toString());
            graphSave.flush();
            graphSave.close();
            return true;
        } catch (IOException e){
            e.printStackTrace();
         }
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
        try {
            Scanner scanner = new Scanner( new File(file));
            String jsonStr = scanner.useDelimiter("\\A").next();
            scanner.close();
            JSONObject graph = new JSONObject(jsonStr);
            JSONArray nodes = graph.getJSONArray("nodes");
            JSONObject node;
            JSONArray edges = graph.getJSONArray("edges");
            JSONObject edge;
            myGraph = new DWGraph_DS();
            for (int i = 0; i < nodes.length(); i++){
                node = nodes.getJSONObject(i);
                node_data n = new NodeData((int) node.get("key"));
                n.setInfo((String)node.get("info"));
                n.setWeight((double) node.get("weight"));
                n.setTag((int) node.get("tag"));
                String location = (String) node.get("location");
                String[] l = location.split(",");
                n.setLocation(new Point3D(Integer.parseInt(l[0]), Integer.parseInt(l[1]), Integer.parseInt(l[2]) ));
                myGraph.addNode(n);
            }
            for (int j = 0; j < edges.length(); j++){
                edge = edges.getJSONObject(j);
                myGraph.connect((int) edge.get("source"), (int) edge.get("dest"), (double) edge.get("weight"));
                edge_data ed = myGraph.getEdge((int) edge.get("source"), (int) edge.get("dest"));
                ed.setTag((int) edge.get("tag"));
                ed.setInfo((String) edge.get("info"));
            }
            return true;
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
