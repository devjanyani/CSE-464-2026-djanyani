package edu.asu.cse464;

import java.util.*;

public abstract class GraphSearchStrategy {

    public Path search(String src, String dst, Graph graph) {
        if (!graph.getNodes().contains(src) || !graph.getNodes().contains(dst)) {
            return null;
        }

        Map<String, String> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();

        initFrontier(src);
        parent.put(src, null);

        while (!isFrontierEmpty()) {
            String current = getNextNode();

            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            System.out.println("visiting " + buildCurrentPath(parent, src, current));

            if (current.equals(dst)) {
                return buildPath(parent, dst);
            }

            List<String> neighbors = graph.getNeighbors(current);
            for (String neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    parent.put(neighbor, current);
                    addToFrontier(neighbor);
                }
            }
        }
        return null;
    }

    protected abstract void initFrontier(String src);
    protected abstract boolean isFrontierEmpty();
    protected abstract String getNextNode();
    protected abstract void addToFrontier(String node);

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

    private String buildCurrentPath(Map<String, String> parent, String src, String current) {
        List<String> pathNodes = new ArrayList<>();
        String node = current;
        while (node != null) {
            pathNodes.add(node);
            node = parent.get(node);
        }
        Collections.reverse(pathNodes);
        return new Path(pathNodes).toString();
    }
}