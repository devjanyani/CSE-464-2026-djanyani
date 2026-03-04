package edu.asu.cse464;

import java.util.*;

public class Graph {
    private final Map<String, Set<String>> adj = new LinkedHashMap<>();

    public void addNode(String label) {
        if (label == null) return;
        String v = label.trim();
        if (v.isEmpty()) return;
        adj.putIfAbsent(v, new LinkedHashSet<>());
    }

    public void addNodes(String[] labels) {
        if (labels == null) return;
        for (String s : labels) addNode(s);
    }

    public void addEdge(String srcLabel, String dstLabel) {
        if (srcLabel == null || dstLabel == null) return;
        String src = srcLabel.trim();
        String dst = dstLabel.trim();
        if (src.isEmpty() || dst.isEmpty()) return;

        addNode(src);
        addNode(dst);
        adj.get(src).add(dst);
    }

    public int getNodeCount() {
        return adj.size();
    }

    public List<String> getNodes() {
        return new ArrayList<>(adj.keySet());
    }

    public int getEdgeCount() {
        int c = 0;
        for (Set<String> outs : adj.values()) c += outs.size();
        return c;
    }

    public List<String> getEdges() {
        List<String> out = new ArrayList<>();
        for (Map.Entry<String, Set<String>> e : adj.entrySet()) {
            for (String dst : e.getValue()) out.add(e.getKey() + " -> " + dst);
        }
        return out;
    }

    public Map<String, Set<String>> getAdjacency() {
        Map<String, Set<String>> copy = new LinkedHashMap<>();
        for (Map.Entry<String, Set<String>> e : adj.entrySet()) {
            copy.put(e.getKey(), new LinkedHashSet<>(e.getValue()));
        }
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of nodes: ").append(getNodeCount()).append("\n");
        sb.append("Nodes: ").append(getNodes()).append("\n");
        sb.append("Number of edges: ").append(getEdgeCount()).append("\n");
        sb.append("Edges:\n");
        for (String edge : getEdges()) sb.append(edge).append("\n");
        return sb.toString();
    }

    public String toDotString() {
        StringBuilder sb = new StringBuilder();

        sb.append("digraph {\n");

        for (String node : adj.keySet()) {
            if (adj.get(node).isEmpty()) {
                sb.append("  ").append(node).append(";\n");
            } else {
                for (String dst : adj.get(node)) {
                    sb.append("  ").append(node)
                            .append(" -> ")
                            .append(dst)
                            .append(";\n");
                }
            }
        }

        sb.append("}");

        return sb.toString();
    }
}