package edu.asu.cse464;

import java.util.*;

public abstract class GraphSearchStrategy {

    public abstract Path search(String src, String dst, Graph graph);

    protected abstract void initFrontier(String src);
    protected abstract boolean isFrontierEmpty();
    protected abstract String getNextNode();
    protected abstract void addToFrontier(String node);
}