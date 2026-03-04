package edu.asu.cse464;

import java.io.*;
import java.util.*;

public class DotParser {

    public static Graph parseGraph(String filePath) throws IOException {
        Graph graph = new Graph();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {

            line = line.trim();

            if (line.isEmpty()) continue;
            if (line.startsWith("digraph")) continue;
            if (line.startsWith("{")) continue;
            if (line.startsWith("}")) continue;

            if (line.contains("->")) {

                line = line.replace(";", "");

                String[] parts = line.split("->");

                if (parts.length == 2) {
                    String src = parts[0].trim();
                    String dst = parts[1].trim();

                    graph.addEdge(src, dst);
                }
            }
        }

        reader.close();

        return graph;
    }
}