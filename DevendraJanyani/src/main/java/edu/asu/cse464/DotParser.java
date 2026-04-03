package edu.asu.cse464;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class DotParser {

    public static Graph parseGraph(String filePath) throws Exception {
        Graph g = new Graph();

        try (BufferedReader br = Files.newBufferedReader(Path.of(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // remove // comments
                int cmt = line.indexOf("//");
                if (cmt >= 0) line = line.substring(0, cmt).trim();
                if (line.isEmpty()) continue;

                // ignore DOT framing
                if (line.startsWith("digraph")) continue;
                if (line.equals("{") || line.equals("}")) continue;

                // strip trailing semicolon
                if (line.endsWith(";")) {
                    line = line.substring(0, line.length() - 1).trim();
                }
                if (line.isEmpty()) continue;

                // EDGE: a -> b (optionally with attributes after dst)
                if (line.contains("->")) {
                    String[] parts = line.split("->", 2);
                    String src = parts[0].trim();
                    String dst = parts[1].trim();

                    // remove attributes from dst: b [label="x"]
                    int attr = dst.indexOf('[');
                    if (attr >= 0) dst = dst.substring(0, attr).trim();

                    g.addEdge(src, dst);
                    continue;
                }

                // NODE: x OR x [color=red]
                int attr = line.indexOf('[');
                String node = (attr >= 0) ? line.substring(0, attr).trim() : line.trim();

                if (!node.isEmpty()) {
                    g.addNode(node);
                }
            }
        }

        return g;
    }
}