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
}