package edu.asu.cse464;

import java.util.*;

public class RandomWalkStrategy extends GraphSearchStrategy {

    private String currentNode;
    private boolean hasNext;
    private final Random random = new Random();
    private static final int MAX_STEPS = 100;

    @Override
    protected void initFrontier(String src) {
        currentNode = src;
        hasNext = true;
    }

    @Override
    protected boolean isFrontierEmpty() {
        return !hasNext;
    }

    @Override
    protected String getNextNode() {
        return currentNode;
    }

    @Override
    protected void addToFrontier(String node) {
    }

    @Override
    public Path search(String src, String dst, Graph graph) {
        if (!graph.getNodes().contains(src) || !graph.getNodes().contains(dst)) {
            return null;
        }

        List<String> pathNodes = new ArrayList<>();
        String current = src;

        for (int step = 0; step < MAX_STEPS; step++) {
            pathNodes.add(current);
            System.out.println("visiting " + new Path(pathNodes));

            if (current.equals(dst)) {
                return new Path(pathNodes);
            }

            List<String> neighbors = graph.getNeighbors(current);
            if (neighbors.isEmpty()) {
                pathNodes.clear();
                current = src;
                continue;
            }

            current = neighbors.get(random.nextInt(neighbors.size()));
        }
        return null;
    }
}