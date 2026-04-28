package edu.asu.cse464;

import java.util.Stack;

public class DFSStrategy extends GraphSearchStrategy {

    private final Stack<String> stack = new Stack<>();

    @Override
    protected void initFrontier(String src) {
        stack.clear();
        stack.push(src);
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
}