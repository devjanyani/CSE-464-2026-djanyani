package edu.asu.cse464;

public class Main {

    public static void main(String[] args) throws Exception {
        String inputDot = "input/sample.dot";

        Graph g = DotParser.parseGraph(inputDot);

        System.out.print(g.toString());

        g.outputDOTGraph("output.dot");
        g.outputGraph("output.txt");
        g.outputGraphics("output.png", "png");

        System.out.println("Wrote DOT to: output.dot");
        System.out.println("Wrote graph text to: output.txt");
        System.out.println("Wrote PNG to: output.png");
    }
}