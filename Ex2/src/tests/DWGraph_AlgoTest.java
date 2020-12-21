package tests;

import org.junit.jupiter.api.Test;
import api.*;
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
    }

    @Test
    void shortestPath() {
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }
}