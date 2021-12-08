package api;
/**
 * This is a factory class to quickly construct a directed, weighted graph. **/
public class GraphBuilder {
    /**
     *
     * @param v - number of nodes
     * @return a connected graph with V vertexes
     */
    public static DirectedWeightedGraph getConnected(int v)
    {
        if (v <= 0) return null;
        DirectedWeightedGraph g = new DWG();
        v++;

        // Edge number is the minimal between v/2 and 20
        int e = v / 2;
        if ( e > 20 )
            e = 20;

        for (int i = 0; i < v; i++)
        {
            Vertex ver = new Vertex(new Point3D( i , Math.random() , 0) , i);
            g.addNode(ver);
        }

        // Connect first to second, second to third and so on
        for (int i = 0; i < v - 1; i++) {
            g.connect( i , i+1 , (Math.random() + 0.5));
        }
        g.connect(v-1, 0 , (Math.random() + 0.5));


        // Connect random edges
        e--;
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < e; j++) {
                g.connect(i , randInt_exclude(i , v), (Math.random() + 0.5));
            }
        }

        return g;
    }

    /***
     *
     * @param v - number of nodes
     * @return a graph with V vertexes, could be connected, could be not.
     */
    public static DirectedWeightedGraph getGraph(int v)
    {
        if (v <= 0) return null;
        DirectedWeightedGraph g = new DWG();
        v++;

        // Edge number is the minimal between v/2 and 20
        int e = v / 2;
        if ( e > 20 )
            e = 20;

        for (int i = 0; i < v; i++)
        {
            Vertex ver = new Vertex(new Point3D( i + Math.random() , Math.random(), 0) , i);
            g.addNode(ver);
        }

        // Connect random edges
        e--;
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < e; j++) {
                g.connect(i , randInt_exclude(i , v), (Math.random() + 0.5));
            }
        }

        return g;
    }

    /***
     *
     * @param i, range
     * @return number from 0 to range which is not i
     */
    public static int randInt_exclude(int i, int range)
    {
        int ans = (int) (Math.random() * range);
        // Get random number, if by chance it is i, should roll again.
        // Could be better ways to implement
        while (ans == i)
            ans = (int) (Math.random() * range);
        return ans;
    }

}
