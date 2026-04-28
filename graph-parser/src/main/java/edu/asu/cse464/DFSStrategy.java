package edu.asu.cse464;

import java.util.*;

public class DFSStrategy extends GraphSearchStrategy {

    private final Stack<String> stack = new Stack<>();
    private final Set<String> visited = new HashSet<>();
    private final Map<String, String> parent = new HashMap<>();

    @Override
    protected void initFrontier(String src) {
        stack.clear();
        visited.clear();
        parent.clear();
        stack.push(src);
        parent.put(src, null);
    }

    @Override
    protected boolean isFrontierEmpty() {
        return stack.isEmpty();
    }

    @Override
    protected String getNextNode() {
        return stack.pop();
    }

    @Override
    protected void addToFrontier(String node) {
        stack.push(node);
    }

    @Override
    public Path search(String src, String dst, Graph graph) {
        if (!graph.getNodes().contains(src) || !graph.getNodes().contains(dst)) {
            return null;
        }

        initFrontier(src);

        while (!isFrontierEmpty()) {
            String current = getNextNode();

            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            // Build and print current path
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
                    parent.put(neighbor, current);
                    addToFrontier(neighbor);
                }
            }
        }
        return null;
    }
}