package edu.asu.cse464;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;

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
    public void testAddEdgeAddsNodes() {
        Graph g = new Graph();
        g.addEdge("A", "B");

        assertEquals(2, g.getNodeCount());
        assertEquals(1, g.getEdgeCount());
        assertTrue(g.getEdges().contains("A -> B"));
    }

    @Test
    public void testOutputGraphWritesToFile() throws Exception {
        Graph g = new Graph();
        g.addEdge("A", "B");

        Path tmp = Files.createTempFile("graph-", ".txt");
        try {
            g.outputGraph(tmp.toString());
            String s = Files.readString(tmp);
            assertTrue(s.contains("Number of nodes"));
            assertTrue(s.contains("A -> B"));
        } finally {
            Files.deleteIfExists(tmp);
        }
    }

    @Test
    public void testOutputDOTGraphWritesToFile() throws Exception {
        Graph g = new Graph();
        g.addEdge("A", "B");

        Path tmp = Files.createTempFile("graph-", ".dot");
        try {
            g.outputDOTGraph(tmp.toString());
            String dot = Files.readString(tmp);
            assertTrue(dot.contains("digraph"));
            assertTrue(dot.contains("A -> B"));
        } finally {
            Files.deleteIfExists(tmp);
        }
    }
    @Test
    public void testAddDuplicateEdge() {
        Graph g = new Graph();
        g.addEdge("A", "B");
        g.addEdge("A", "B"); // duplicate
        assertEquals(1, g.getEdgeCount());
    }

    @Test
    public void testAddNodes() {
        Graph g = new Graph();
        g.addNodes(new String[]{"A", "B", "C"});
        assertEquals(3, g.getNodeCount());
        assertTrue(g.getNodes().contains("A"));
        assertTrue(g.getNodes().contains("B"));
        assertTrue(g.getNodes().contains("C"));
    }

    @Test
    public void testToString() {
        Graph g = new Graph();
        g.addEdge("A", "B");
        g.addNode("C");
        String output = g.toString();
        assertTrue(output.contains("Number of nodes: 3"));
        assertTrue(output.contains("Number of edges: 1"));
        assertTrue(output.contains("A -> B"));
        assertTrue(output.contains("Nodes:"));
    }

    @Test
    public void testRemoveNode() {
        Graph g = new Graph();
        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.addEdge("B", "C");
        g.removeNode("A");
        assertEquals(2, g.getNodeCount());
        assertFalse(g.getNodes().contains("A"));
        assertEquals(1, g.getEdgeCount());
    }

    @Test
    public void testRemoveNodes() {
        Graph g = new Graph();
        g.addEdge("A", "B");
        g.addEdge("B", "C");
        g.addEdge("C", "D");
        g.removeNodes(new String[]{"A", "B"});
        assertEquals(2, g.getNodeCount());
        assertTrue(g.getNodes().contains("C"));
        assertTrue(g.getNodes().contains("D"));
        assertEquals(1, g.getEdgeCount());
    }

    @Test
    public void testRemoveEdge() {
        Graph g = new Graph();
        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.removeEdge("A", "B");
        assertEquals(3, g.getNodeCount());
        assertEquals(1, g.getEdgeCount());
        assertFalse(g.getEdges().contains("A -> B"));
        assertTrue(g.getEdges().contains("A -> C"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNodeNotExists() {
        Graph g = new Graph();
        g.addNode("A");
        g.removeNode("Z");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNodesOneNotExists() {
        Graph g = new Graph();
        g.addNode("A");
        g.addNode("B");
        g.removeNodes(new String[]{"A", "Z"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveEdgeNotExists() {
        Graph g = new Graph();
        g.addEdge("A", "B");
        g.removeEdge("A", "C");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveEdgeSrcNotExists() {
        Graph g = new Graph();
        g.addEdge("A", "B");
        g.removeEdge("Z", "B");
    }

}