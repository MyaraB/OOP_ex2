package tests;

import gameClient.util.Point3D;
import org.junit.jupiter.api.Test;
import api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {

    dw_graph_algorithms algoCreator(){
        directed_weighted_graph graph = new DWGraph_DS();
        node_data n = new NodeData(), k = new NodeData(), l = new NodeData();
        graph.addNode(n);
        graph.addNode(k);
        graph.addNode(l);
        graph.connect(n.getKey(), k.getKey(), 1.5);
        graph.connect(k.getKey(), l.getKey(), 4);
        graph.connect(l.getKey(), n.getKey(), 52);
        dw_graph_algorithms algo = new DWGraph_Algo();
        algo.init(graph);
        return algo;
    }

    @Test
    void copy() {
        dw_graph_algorithms algo = algoCreator();
        directed_weighted_graph graph = algo.copy();
        assertEquals(algo.getGraph().nodeSize() , graph.nodeSize());
        assertEquals(algo.getGraph().edgeSize() , graph.edgeSize());
        assertEquals(algo.getGraph().getMC() , graph.getMC());
        assertEquals(algo.getGraph().getEdge(0 , 1).getWeight(), graph.getEdge(0,1).getWeight());
    }

    @Test
    void isConnected() {
        dw_graph_algorithms algo = algoCreator();
        assertEquals(true, algo.isConnected());
        algo.getGraph().removeEdge(0, 1);
        assertEquals(false, algo.isConnected());
    }

    @Test
    void shortestPathDist() {
        dw_graph_algorithms algo = algoCreator();
        algo.getGraph().connect(0, 2, 6.8);
        assertEquals(5.5, algo.shortestPathDist(0,2));
        assertEquals(-1, algo.shortestPathDist(0,3));
        algo.getGraph().removeEdge(0, 2);
        algo.getGraph().removeNode(1);
        assertEquals( -1 , algo.shortestPathDist(0, 2));

    }

    @Test
    void shortestPath() {
        dw_graph_algorithms algo = algoCreator();
        algo.getGraph().connect(0, 2, 6.8);
        List<node_data> path = new ArrayList<>();
        directed_weighted_graph graph = algo.getGraph();
        path.add(graph.getNode(0));
        path.add(graph.getNode(1));
        path.add(graph.getNode(2));
        assertEquals(path, algo.shortestPath(0,2));
        assertEquals(null, algo.shortestPath(0,3));

        algo.getGraph().removeEdge(0, 2);
        algo.getGraph().removeNode(1);
        assertEquals( null , algo.shortestPath(0, 2));
    }

    @Test
    void saveAndLoad() {
        dw_graph_algorithms algo = algoCreator();
        algo.getGraph().getNode(0).setLocation(new Point3D(999, 999, 999));
        algo.getGraph().getNode(1).setLocation(new Point3D(998, 998, 998));
        algo.getGraph().getNode(2).setLocation(new Point3D(997, 997, 997));
        algo.getGraph().getNode(0).setTag(1);
        algo.getGraph().getEdge(0, 1).setTag(3);
        assertEquals(true, algo.save("Graph1"));
        dw_graph_algorithms loadedAlgo = new DWGraph_Algo();
        assertEquals(true, loadedAlgo.load("Graph1"));
        assertEquals(1, loadedAlgo.getGraph().getNode(0).getTag());
        assertEquals(3, loadedAlgo.getGraph().getEdge(0, 1).getTag());
    }
}