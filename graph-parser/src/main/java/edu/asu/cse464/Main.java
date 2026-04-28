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

        System.out.println("\n=== BFS: Path from 'h' to 'a' ===");
        Path noPath = g.graphSearch("h", "a", Algorithm.BFS);
        System.out.println(noPath != null ? noPath.toString() : "No path found");
    }
}