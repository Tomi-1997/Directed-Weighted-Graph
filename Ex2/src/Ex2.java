import api.*;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph g = new DWG();
        DirectedWeightedGraphAlgorithms ga = new DWG_Algo();
        ga.init(g);
        ga.load(json_file);

        return ga.getGraph();
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraph g = new DWG();
        DirectedWeightedGraphAlgorithms ga = new DWG_Algo();
        ga.init(g);
        ga.load(json_file);

        return ga;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file)
    {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        Graph_GUI gui = new Graph_GUI(alg , 500 , 500);
    }

    public static void main(String[]args)
    {
        if (args.length > 0)
        {
            String filename = args[0];
            runGUI(filename);
        }
        else
        {
            DirectedWeightedGraphAlgorithms algo = new DWG_Algo();
            algo.init(GraphBuilder.getGraph(20));
            Graph_GUI gui = new Graph_GUI(algo,400 , 400);
        }
    }
}