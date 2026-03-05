package edu.asu.cse464;

import org.junit.Test;
import static org.junit.Assert.*;

public class DotParserTest {

    @Test
    public void testParseGraph() throws Exception {

        Graph g = DotParser.parseGraph("input/sample.dot");

        assertEquals(8, g.getNodeCount());
        assertEquals(9, g.getEdgeCount());

        assertTrue(g.getNodes().contains("a"));
        assertTrue(g.getNodes().contains("h"));
    }
}