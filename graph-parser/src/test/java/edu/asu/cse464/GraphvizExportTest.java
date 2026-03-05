package edu.asu.cse464;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;

public class GraphvizExportTest {

    @Test
    public void testGraphPngExport() throws Exception {
        Graph g = DotParser.parseGraph("input/sample.dot");
        String output = "test_graph.png";
        g.writePng(output);
        File f = new File(output);

        assertTrue(f.exists());
        assertTrue(f.length() > 0);

        f.delete();
    }
}