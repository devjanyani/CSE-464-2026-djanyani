package edu.asu.cse464;

public enum Algorithm {
    BFS,
    DFS,
    RANDOM;

    public GraphSearchStrategy getStrategy() {
        return switch (this) {
            case BFS -> new BFSStrategy();
            case DFS -> new DFSStrategy();
            case RANDOM -> new RandomWalkStrategy();
        };
    }
}