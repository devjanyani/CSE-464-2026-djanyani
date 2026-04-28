package edu.asu.cse464;

import java.util.*;

public class BFSStrategy extends GraphSearchStrategy {

    private final Queue<String> queue = new LinkedList<>();
    private final Set<String> visited = new HashSet<>();
    private final Map<String, String> parent = new HashMap<>();

    @Override
    protected void initFrontier(String src) {
        queue.clear();
        visited.clear();
        parent.clear();
        queue.add(src);
        visited.add(src);
        parent.put(src, null);
    }

    @Override
    protected boolean isFrontierEmpty() {
        return queue.isEmpty();
    }

    @Override
    protected String getNextNode() {
        return queue.poll();
    }

    @Override
    protected void addToFrontier(String node) {
        queue.add(node);
    }

    @Override
    public Path search(String src, String dst, Graph graph) {
        if (!graph.getNodes().contains(src) || !graph.getNodes().contains(dst)) {
            return null;
        }

        initFrontier(src);

        while (!isFrontierEmpty()) {
            String current = getNextNode();

            List<String> pathNodes = new ArrayList<>();
            String node = current;
            while (node != null) {
                pathNodes.add(node);
                node = parent.get(node);
            }
            Collections.reverse(pathNodes);
            System.out.println("visiting " + new Path(pathNodes));

            if (current.equals(dst)) {
                return new Path(pathNodes);
            }

            for (String neighbor : graph.getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    addToFrontier(neighbor);
                }
            }
        }
        return null;
    }
}