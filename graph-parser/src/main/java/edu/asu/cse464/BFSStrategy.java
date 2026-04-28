package edu.asu.cse464;

import java.util.LinkedList;
import java.util.Queue;

public class BFSStrategy extends GraphSearchStrategy {

    private final Queue<String> queue = new LinkedList<>();

    @Override
    protected void initFrontier(String src) {
        queue.clear();
        queue.add(src);
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
}