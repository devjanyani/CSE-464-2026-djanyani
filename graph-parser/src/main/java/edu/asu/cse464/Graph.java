package edu.asu.cse464;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Graph {

    private final Set<String> nodes;
    private final Map<String, List<String>> adjacency;

    public Graph() {
        nodes = new HashSet<>();
        adjacency = new HashMap<>();
    }

    public void addNode(String label) {
        if (label == null) return;
        String n = label.trim();
        if (n.isEmpty()) return;

        nodes.add(n);
        adjacency.putIfAbsent(n, new ArrayList<>());
    }

    public void addNodes(String[] labels) {
        if (labels == null) return;

        for (String label : labels) {
            addNode(label);
        }
    }

    public void addEdge(String src, String dst) {
        addNode(src);
        addNode(dst);
        adjacency.get(src.trim()).add(dst.trim());
    }

    public int getNodeCount() {
        return nodes.size();
    }

    public int getEdgeCount() {
        int count = 0;
        for (String src : adjacency.keySet()) {
            count += adjacency.get(src).size();
        }
        return count;
    }

    public Set<String> getNodes() {
        return new HashSet<>(nodes);
    }

    public List<String> getEdges() {
        List<String> out = new ArrayList<>();
        List<String> srcs = new ArrayList<>(adjacency.keySet());
        Collections.sort(srcs);

        for (String src : srcs) {
            List<String> dsts = new ArrayList<>(adjacency.getOrDefault(src, List.of()));
            Collections.sort(dsts);
            for (String dst : dsts) {
                out.add(src + " -> " + dst);
            }
        }
        return out;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        List<String> sortedNodes = new ArrayList<>(nodes);
        Collections.sort(sortedNodes);

        sb.append("Number of nodes: ").append(getNodeCount()).append("\n");
        sb.append("Nodes: ").append(sortedNodes).append("\n");
        sb.append("Number of edges: ").append(getEdgeCount()).append("\n");
        sb.append("Edges:\n");

        for (String e : getEdges()) {
            sb.append(e).append("\n");
        }

        return sb.toString();
    }

    private String buildDotString() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n");

        List<String> sortedNodes = new ArrayList<>(nodes);
        Collections.sort(sortedNodes);
        for (String n : sortedNodes) {
            sb.append("  ").append(n).append(";\n");
        }

        List<String> srcs = new ArrayList<>(adjacency.keySet());
        Collections.sort(srcs);

        for (String src : srcs) {
            List<String> dsts = new ArrayList<>(adjacency.getOrDefault(src, List.of()));
            Collections.sort(dsts);
            for (String dst : dsts) {
                sb.append("  ").append(src).append(" -> ").append(dst).append(";\n");
            }
        }

        sb.append("}\n");
        return sb.toString();
    }

    public void outputDOTGraph(String path) throws IOException {
        Files.writeString(Path.of(path), buildDotString());
    }

    public void outputGraph(String filepath) throws IOException {
        Files.writeString(Path.of(filepath), this.toString());
    }

    public void outputGraphics(String path, String format) throws Exception {
        if (format == null) throw new IllegalArgumentException("format cannot be null");
        String fmt = format.trim().toLowerCase(Locale.ROOT);

        if (!fmt.equals("png")) {
            throw new IllegalArgumentException("Only png is supported right now");
        }

        Path dotTmp = Files.createTempFile("graph-", ".dot");
        try {
            outputDOTGraph(dotTmp.toString());

            ProcessBuilder pb = new ProcessBuilder(
                    "dot",
                    "-T" + fmt,
                    dotTmp.toString(),
                    "-o",
                    path
            );
            pb.redirectErrorStream(true);

            Process p = pb.start();
            int code = p.waitFor();

            if (code != 0) {
                throw new RuntimeException("Graphviz dot failed with exit code " + code);
            }
        } finally {
            Files.deleteIfExists(dotTmp);
        }
    }
}