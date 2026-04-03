package edu.asu.cse464;

import org.junit.Assume;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class GraphvizExportTest {

    private static boolean hasDot() {
        try {
            Process p = new ProcessBuilder("dot", "-V")
                    .redirectErrorStream(true)
                    .start();
            int code = p.waitFor();
            return code == 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Test
    public void testGraphPngExport() throws Exception {
        Assume.assumeTrue("Graphviz 'dot' not installed; skipping PNG export test", hasDot());

        Graph g = DotParser.parseGraph("input/sample.dot");

        Path out = Files.createTempFile("graph-", ".png");
        try {
            g.outputGraphics(out.toString(), "png");

            File f = out.toFile();
            assertTrue(f.exists());
            assertTrue(f.length() > 0);
        } finally {
            Files.deleteIfExists(out);
        }
    }
}