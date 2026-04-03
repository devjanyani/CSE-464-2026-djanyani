package edu.asu.cse464;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private final List<String> nodes;

    public Path() {
        this.nodes = new ArrayList<>();
    }

    public Path(List<String> nodes) {
        this.nodes = new ArrayList<>(nodes);
    }

    public void addNode(String node) {
        nodes.add(node);
    }

    public List<String> getNodes() {
        return new ArrayList<>(nodes);
    }

    public int size() {
        return nodes.size();
    }

    @Override
    public String toString() {
        return String.join(" -> ", nodes);
    }
}