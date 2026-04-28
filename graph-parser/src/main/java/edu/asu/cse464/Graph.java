package edu.asu.cse464;
import java.io.IOException;
import java.nio.file.Files;
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
        String s = src.trim();
        String d = dst.trim();
        if (!adjacency.get(s).contains(d)) {
            adjacency.get(s).add(d);
        }
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
        for (String[] pair : getSortedEdgePairs()) {
            out.add(pair[0] + " -> " + pair[1]);
        }
        return out;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        List<String> sortedNodes = getSortedNodes();

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

        List<String> sortedNodes = getSortedNodes();
        for (String n : sortedNodes) {
            sb.append("  ").append(n).append(";\n");
        }

        for (String[] pair : getSortedEdgePairs()) {
            sb.append("  ").append(pair[0]).append(" -> ").append(pair[1]).append(";\n");
        }

        sb.append("}\n");
        return sb.toString();
    }

    public void outputDOTGraph(String path) throws IOException {
        Files.writeString(java.nio.file.Path.of(path), buildDotString());
    }

    public void outputGraph(String filepath) throws IOException {
        Files.writeString(java.nio.file.Path.of(filepath), this.toString());
    }

    public void outputGraphics(String path, String format) throws Exception {
        if (format == null) throw new IllegalArgumentException("format cannot be null");
        String fmt = format.trim().toLowerCase(Locale.ROOT);

        if (!fmt.equals("png")) {
            throw new IllegalArgumentException("Only png is supported right now");
        }

        java.nio.file.Path dotTmp = Files.createTempFile("graph-", ".dot");
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

    public void removeNode(String label) {
        if (label == null || !nodes.contains(label.trim())) {
            throw new IllegalArgumentException("Node '" + label + "' does not exist in the graph");
        }
        String n = label.trim();
        nodes.remove(n);
        adjacency.remove(n);
        for (List<String> neighbors : adjacency.values()) {
            neighbors.remove(n);
        }
    }

    public void removeNodes(String[] labels) {
        if (labels == null) throw new IllegalArgumentException("Labels array cannot be null");
        for (String label : labels) {
            removeNode(label);
        }
    }

    public void removeEdge(String srcLabel, String dstLabel) {
        if (srcLabel == null || !nodes.contains(srcLabel.trim())) {
            throw new IllegalArgumentException("Source node '" + srcLabel + "' does not exist in the graph");
        }
        if (dstLabel == null || !nodes.contains(dstLabel.trim())) {
            throw new IllegalArgumentException("Destination node '" + dstLabel + "' does not exist in the graph");
        }
        String s = srcLabel.trim();
        String d = dstLabel.trim();
        List<String> neighbors = adjacency.get(s);
        if (!neighbors.contains(d)) {
            throw new IllegalArgumentException("Edge '" + s + " -> " + d + "' does not exist in the graph");
        }
        neighbors.remove(d);
    }

    public Path graphSearch(String src, String dst, Algorithm algo) {
        if (!nodes.contains(src) || !nodes.contains(dst)) {
            return null;
        }

        if (algo == Algorithm.BFS) {
            return bfsSearch(src, dst);
        } else {
            return dfsSearch(src, dst);
        }
    }

    private Path bfsSearch(String src, String dst) {
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> parent = new HashMap<>();

        queue.add(src);
        visited.add(src);
        parent.put(src, null);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(dst)) {
                return buildPath(parent, dst);
            }

            List<String> neighbors = getNeighbors(current);
            for (String neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return null;
    }

    private Path dfsSearch(String src, String dst) {
        Stack<String> stack = new Stack<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> parent = new HashMap<>();

        stack.push(src);
        parent.put(src, null);

        while (!stack.isEmpty()) {
            String current = stack.pop();

            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            if (current.equals(dst)) {
                return buildPath(parent, dst);
            }

            List<String> neighbors = getNeighbors(current);
            for (String neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    parent.put(neighbor, current);
                    stack.push(neighbor);
                }
            }
        }
        return null;
    }

    private Path buildPath(Map<String, String> parent, String dst) {
        List<String> pathNodes = new ArrayList<>();
        String node = dst;
        while (node != null) {
            pathNodes.add(node);
            node = parent.get(node);
        }
        Collections.reverse(pathNodes);
        return new Path(pathNodes);
    }

    private List<String> getSortedNodes() {
        List<String> sorted = new ArrayList<>(nodes);
        Collections.sort(sorted);
        return sorted;
    }

    private List<String[]> getSortedEdgePairs() {
        List<String[]> pairs = new ArrayList<>();
        List<String> srcs = new ArrayList<>(adjacency.keySet());
        Collections.sort(srcs);
        for (String src : srcs) {
            List<String> dsts = new ArrayList<>(adjacency.getOrDefault(src, List.of()));
            Collections.sort(dsts);
            for (String dst : dsts) {
                pairs.add(new String[]{src, dst});
            }
        }
        return pairs;
    }

    public List<String> getNeighbors(String node) {
        return adjacency.getOrDefault(node, new ArrayList<>());
    }

}