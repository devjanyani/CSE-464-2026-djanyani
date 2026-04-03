package edu.asu.cse464;

public class Main {

    public static void main(String[] args) throws Exception {
        String inputDot = "input/sample.dot";

        Graph g = DotParser.parseGraph(inputDot);

        System.out.println("=== Parsed Graph ===");
        System.out.print(g.toString());

        System.out.println("\n=== After removing node 'a' ===");
        g.removeNode("a");
        System.out.print(g.toString());

        System.out.println("\n=== After removing edge 'e' -> 'f' ===");
        g.removeEdge("e", "f");
        System.out.print(g.toString());

        g.outputDOTGraph("output.dot");
        g.outputGraphics("output.png", "png");

        System.out.println("\nWrote DOT to: output.dot");
        System.out.println("Wrote PNG to: output.png");
    }
}