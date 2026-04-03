package edu.asu.cse464;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class DotParserTest {

    @Test
    public void testParseGraphSampleDot() throws Exception {
        Graph g = DotParser.parseGraph("input/sample.dot");

        assertTrue(g.getNodeCount() > 0);
        assertTrue(g.getEdgeCount() > 0);
    }

    @Test
    public void testParsesIsolatedNodes() throws Exception {
        String dot =
                "digraph {\n" +
                        "  x;\n" +
                        "  a -> b;\n" +
                        "}\n";

        Path tmp = Files.createTempFile("isolated-", ".dot");
        try {
            Files.writeString(tmp, dot);
            Graph g = DotParser.parseGraph(tmp.toString());

            assertTrue(g.getNodes().contains("x"));
            assertTrue(g.getEdges().contains("a -> b"));
        } finally {
            Files.deleteIfExists(tmp);
        }
    }
}