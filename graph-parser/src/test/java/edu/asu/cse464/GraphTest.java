package edu.asu.cse464;

import org.junit.Test;
import static org.junit.Assert.*;

public class GraphTest {

    @Test
    public void testAddNode() {
        Graph g = new Graph();
        g.addNode("A");
        g.addNode("B");
        assertEquals(2, g.getNodeCount());
    }

    @Test
    public void testAddEdge() {
        Graph g = new Graph();
        g.addEdge("A", "B");
        assertEquals(2, g.getNodeCount());
        assertEquals(1, g.getEdgeCount());
    }

    @Test
    public void testDotOutput() {
        Graph g = new Graph();
        g.addEdge("A", "B");
        String dot = g.toDotString();
        assertTrue(dot.contains("A -> B"));
    }
}