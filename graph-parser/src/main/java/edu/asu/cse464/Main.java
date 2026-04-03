package edu.asu.cse464;

public class Main {

    public static void main(String[] args) throws Exception {
        String inputDot = "input/sample.dot";

        Graph g = DotParser.parseGraph(inputDot);
        System.out.println("=== Parsed Graph ===");
        System.out.print(g.toString());

        System.out.println("\n=== DFS: Path from 'a' to 'h' ===");
        Path path = g.GraphSearch("a", "h");
        System.out.println(path != null ? path.toString() : "No path found");

        System.out.println("\n=== DFS: Path from 'h' to 'a' ===");
        Path path2 = g.GraphSearch("h", "a");
        System.out.println(path2 != null ? path2.toString() : "No path found");
    }
}