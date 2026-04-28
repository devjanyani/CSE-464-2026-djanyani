package edu.asu.cse464;

public class Main {

    public static void main(String[] args) throws Exception {
        String inputDot = "input/sample.dot";

        Graph g = DotParser.parseGraph(inputDot);
        System.out.println("=== Parsed Graph ===");
        System.out.print(g.toString());

        System.out.println("\n=== BFS: Path from 'a' to 'h' ===");
        Path bfsPath = g.graphSearch("a", "h", Algorithm.BFS);
        System.out.println(bfsPath != null ? bfsPath.toString() : "No path found");

        System.out.println("\n=== DFS: Path from 'a' to 'h' ===");
        Path dfsPath = g.graphSearch("a", "h", Algorithm.DFS);
        System.out.println(dfsPath != null ? dfsPath.toString() : "No path found");

        System.out.println("\n=== Random Walk Searches from 'a' to 'c' (5 runs) ===");
        for (int i = 1; i <= 5; i++) {
            System.out.println("\nrandom testing " + i);
            Path rPath = g.graphSearch("a", "c", Algorithm.RANDOM);
            System.out.println(rPath != null ? rPath.toString() : "No path found");
        }
    }
}