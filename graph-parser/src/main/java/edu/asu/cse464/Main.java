package edu.asu.cse464;

public class Main {

    public static void main(String[] args) throws Exception {

        Graph graph = DotParser.parseGraph("input/sample.dot");

        System.out.println(graph);

        System.out.println("\nDOT OUTPUT:");
        System.out.println(graph.toDotString());
    }
}