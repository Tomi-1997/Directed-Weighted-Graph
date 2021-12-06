package api;

import java.util.HashMap;

public class test {
    /** test class just for testing hashmap **/
    public static void main(String[]args)
    {

        DWG g = new DWG();
        initGraph(g);

        Graph_GUI gui = new Graph_GUI(g , 500 , 500 );

    }

    public static void initGraph(DWG g)
    {
        Vertex v0 = new Vertex(new Point3D(0 , 0 , 0 ) , 0);
        Vertex v1 = new Vertex(new Point3D(1 , 0 , 0 ) , 1);
        Vertex v2 = new Vertex(new Point3D(2 , 0 , 0 ) , 2);
        Vertex v3 = new Vertex(new Point3D(3 , 0 , 0 ) , 3);

        Edge e0 = new Edge(v1, v0 , 1);
        Edge e1 = new Edge(v0, v2 , 1);
        Edge e2 = new Edge(v0, v3 , 2);
        Edge e3 = new Edge(v3, v1 , 0.5);


        g.addNode(v0);
        g.addNode(v1);
        g.addNode(v2);
        g.addNode(v3);

        g.connect(e0);
        g.connect(e1);
        g.connect(e2);
        g.connect(e3);
    }
}
