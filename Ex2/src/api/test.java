package api;

import java.util.HashMap;

public class test {
    /** test class just for testing hashmap **/
    // For testing - will be deleted.
    public static void main (String[]args)
    {
        DirectedWeightedGraph g = GraphBuilder.getGraph(30);
        DirectedWeightedGraphAlgorithms ga = new DWG_Algo();

        ga.init(g);
        Graph_GUI gui = new Graph_GUI(ga, 500, 500);
    }

}
