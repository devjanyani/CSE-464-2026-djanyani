package edu.asu.cse464;

public class Main {

    public static void main(String[] args) throws Exception {

        Graph graph = DotParser.parseGraph("input/sample.dot");

        System.out.println(graph);

        String outFile = "output.png";
        graph.writePng(outFile);

        System.out.println("Wrote PNG to: " + outFile);
    }
}