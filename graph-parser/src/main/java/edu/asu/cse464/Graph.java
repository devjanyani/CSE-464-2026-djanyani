package edu.asu.cse464;

import java.util.*;

public class Graph {
    private final Set<String> nodes = new LinkedHashSet<>();
    private final List<String> edges = new ArrayList<>();

    public void addNode(String label) {
        if (label == null || label.isBlank()) return;
        nodes.add(label.trim());
    }

    public void addEdge(String srcLabel, String dstLabel) {
        if (srcLabel == null || dstLabel == null) return;

        String src = srcLabel.trim();
        String dst = dstLabel.trim();

        if (src.isEmpty() || dst.isEmpty()) return;

        addNode(src);
        addNode(dst);

        String edge = src + " -> " + dst;
        if (!edges.contains(edge)) {
            edges.add(edge);
        }
    }

    public int getNodeCount() {
        return nodes.size();
    }

    public List<String> getNodes() {
        return new ArrayList<>(nodes);
    }

    public int getEdgeCount() {
        return edges.size();
    }

    public List<String> getEdges() {
        return new ArrayList<>(edges);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Number of nodes: ").append(getNodeCount()).append("\n");
        sb.append("Nodes: ").append(getNodes()).append("\n");
        sb.append("Number of edges: ").append(getEdgeCount()).append("\n");
        sb.append("Edges:\n");
        for (String e : edges) {
            sb.append(e).append("\n");
        }

        return sb.toString();
    }
}