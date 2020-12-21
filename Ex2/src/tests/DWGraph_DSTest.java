package tests;

import api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {

    directed_weighted_graph graphCreator(){
        directed_weighted_graph graph = new DWGraph_DS();
        node_data n = new NodeData(), k = new NodeData(), l = new NodeData();
        graph.addNode(n);
        graph.addNode(k);
        graph.addNode(l);
        return graph;
    }

    @Test
    void getEdge() {
        directed_weighted_graph graph = new DWGraph_DS();
        node_data n = new NodeData(), k = new NodeData();
        graph.addNode(n);
        graph.addNode(k);
        assertEquals(null , graph.getEdge(k.getKey(), n.getKey()));
    }

    @Test
    void addNode() {
        directed_weighted_graph graph = new DWGraph_DS();
        node_data n = new NodeData();
        graph.addNode(n);
        assertEquals(graph.getNode(0), n);
    }

    @Test
    void connect() {
        directed_weighted_graph graph = new DWGraph_DS();
        node_data n = new NodeData(), k = new NodeData();
        graph.addNode(n);
        graph.addNode(k);
        graph.connect(0 , 1, 5.3);
        assertEquals(5.3 , graph.getEdge(n.getKey(), k.getKey()).getWeight());
        assertEquals(null , graph.getEdge(k.getKey() , n.getKey()));
        graph.connect(1, 1, 1.3);
        assertEquals( null, graph.getEdge(1 , 1));
    }

    @Test
    void connect2(){
        directed_weighted_graph graph = graphCreator();
        graph.connect(0, 1, 1.3);
        graph.connect(1, 2, 2.4);
        assertEquals(null, graph.getEdge(0 , 2));
    }

    @Test
    void removeNode() {
        directed_weighted_graph graph = graphCreator();
        graph.connect(0, 1, 1.3);
        graph.connect(1, 2, 2.4);
        graph.removeNode(0);
        assertEquals(null , graph.getNode(0));
        assertEquals( null, graph.getEdge(0 , 1));
    }

    @Test
    void removeEdge() {
        directed_weighted_graph graph = graphCreator();
        graph.connect(0, 1, 1.3);
        graph.connect(1, 2, 2.4);
        graph.removeEdge(0 , 1);
        assertEquals( null, graph.getEdge(0 , 1));
    }
}