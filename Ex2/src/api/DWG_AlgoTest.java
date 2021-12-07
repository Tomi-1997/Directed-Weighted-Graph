package api;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DWG_AlgoTest {

    // Template:
    // DirectedWeightedGraph g = GraphBuilder.getConnected(v);
    // DirectedWeightedGraphAlgorithms ga = new DWG_Algo();
    // ga.init(g);

    DWG g = new DWG();
    DWG_Algo ga = new DWG_Algo();

    @Test
    void init() {

        ga.init(g);
        assertNotNull(ga.getGraph());

    }

    @Test
    void getGraph() {

        Vertex v0 = new Vertex(new Point3D(0, 0, 0), 0);
        Vertex v1 = new Vertex(new Point3D(1, 0, 0), 1);
        Edge e0 = new Edge(v1, v0, 1);

        g.addNode(v0);
        g.addNode(v1);
        g.connect(e0);

        ga.init(g);

        assertEquals(ga.getGraph().edgeSize(), 1);
        assertEquals(ga.getGraph().nodeSize(), 2);

    }

    @Test
    void copy() {

        // Make sure copy is deep, first define graph and copy it
        // then clear first graph and make sure copy is unaffected.

        Vertex v0 = new Vertex(new Point3D(0, 0, 0), 0);
        Vertex v1 = new Vertex(new Point3D(1, 0, 0), 1);

        Edge e0 = new Edge(v1, v0, 1);


        g.addNode(v0);
        g.addNode(v1);
        g.connect(e0);
        ga.init(g);

        DirectedWeightedGraph copy = ga.copy();

        g.removeNode(0);
        g.removeNode(1);
        g.removeEdge(1,0);

        assertEquals(g.nodeSize(), 0);
        assertEquals(g.edgeSize(), 0);

        assertEquals(copy.nodeSize(), 2);
        assertEquals(copy.edgeSize(), 1);
    }

    @Test
    void isConnected(){
        int v = 20;
        for (int i = 0; i < v; i++) {
            DirectedWeightedGraph g = GraphBuilder.getConnected(v);
            DirectedWeightedGraphAlgorithms ga = new DWG_Algo();
            ga.init(g);
            assertTrue(ga.isConnected());
        }
    }

    @Test
    void shortestPathDist() {
    }

    @Test
    void shortestPath() {

        Vertex v0 = new Vertex(new Point3D(0, 0, 0), 0);
        Vertex v1 = new Vertex(new Point3D(1, 1, 0), 1);
        Vertex v2 = new Vertex(new Point3D(2, 2, 0), 2);
        Vertex v3 = new Vertex(new Point3D(3, 3, 0), 3);
        Vertex v4 = new Vertex(new Point3D(4, 4, 0), 4);


        Edge e0 = new Edge(v0, v1, 1);
        Edge e1 = new Edge(v0, v2, 4);
        Edge e2 = new Edge(v1, v2, 1);
        Edge e3 = new Edge(v2, v3, 1);
        Edge e4 = new Edge(v2, v4, 2);
        Edge e5 = new Edge(v3, v4, 2);

        g.addNode(v0);
        g.addNode(v1);
        g.addNode(v2);
        g.addNode(v3);
        g.addNode(v4);

        g.connect(e0);
        g.connect(e1);
        g.connect(e2);
        g.connect(e3);
        g.connect(e4);
        g.connect(e5);

        ga.init(g);
        List<NodeData> sp = ga.shortestPath(0, 4);
        assertEquals(sp.size() , 4);
    }

    @Test
    void GTranpose()
    {
        Vertex v0 = new Vertex(new Point3D(0 , 0 , 0 ) , 0);
        Vertex v1 = new Vertex(new Point3D(1 , 0 , 0 ) , 1);
        Vertex v2 = new Vertex(new Point3D(2 , 0 , 0 ) , 2);


        Edge e0 = new Edge(v0, v1 , 1);
        Edge e1 = new Edge(v1, v2 , 1);

        g.addNode(v0);
        g.addNode(v1);
        g.addNode(v2);

        g.connect(e0);
        g.connect(e1);
        ga.init(g);
        g = ga.getTranspose();

        assertEquals(g.edgeSize() , ga.getTranspose().edgeSize());

    }
    @Test
    void center()
    {
        Vertex v0 = new Vertex(new Point3D(0 , 0 , 0 ) , 0);
        Vertex v1 = new Vertex(new Point3D(1 , 0 , 0 ) , 1);
        Vertex v2 = new Vertex(new Point3D(2 , 0 , 0 ) , 2);
        Vertex v3 = new Vertex(new Point3D(3 , 0 , 0 ) , 3);
        Vertex v4 = new Vertex(new Point3D(4 , 0 , 0 ) , 4);


        Edge e0 = new Edge(v0, v4 , 2);
        Edge e1 = new Edge(v1, v0 , 5);
        Edge e2 = new Edge(v2, v1 , 2);
        Edge e3 = new Edge(v3, v0 , 1);
        Edge e4 = new Edge(v4, v1 , 1);
        Edge e5 = new Edge(v4, v2 , 2);
        Edge e6 = new Edge(v4, v3 , 1);

        g.addNode(v0);
        g.addNode(v1);
        g.addNode(v2);
        g.addNode(v3);
        g.addNode(v4);

        g.connect(e0);
        g.connect(e1);
        g.connect(e2);
        g.connect(e3);
        g.connect(e4);
        g.connect(e5);
        g.connect(e6);

        ga.init(g);
        assertEquals(ga.center().getKey() , 4);
    }

    @Test
    void center2()
    {
        // It takes around 15 seconds to get the center of
        // a connected graph with around 20 edges for each vertex.

//        DirectedWeightedGraph g = GraphBuilder.getConnected(100);
//        ga.init(g);
//
//        System.out.println(ga.center().getKey());

    }
    @Test
    void tsp() {
        Vertex v0 = new Vertex(new Point3D(0 , 0 , 0 ) , 0);
        Vertex v1 = new Vertex(new Point3D(1 , 0 , 0 ) , 1);
        Vertex v2 = new Vertex(new Point3D(2 , 0 , 0 ) , 2);
        Vertex v3 = new Vertex(new Point3D(3 , 0 , 0 ) , 3);


        Edge e0 = new Edge(v0, v1 , 1);
        Edge e1 = new Edge(v0, v2 , 4);
        Edge e2 = new Edge(v0, v3 , 2);
        Edge e3 = new Edge(v1, v2 , 1);
        Edge e4 = new Edge(v2, v3 , 5);

        g.addNode(v0);
        g.addNode(v1);
        g.addNode(v2);
        g.addNode(v3);

        g.connect(e0);
        g.connect(e1);
        g.connect(e2);
        g.connect(e3);
        g.connect(e4);

        ga.init(g);
        ArrayList<NodeData> tsp = new ArrayList<>();
        tsp.add(g.getNode(0));
        tsp.add(g.getNode(2));
        tsp.add(g.getNode(3));

        List<NodeData> l = ga.tsp(tsp);
        assertEquals(l.size() , 4);
    }

    @Test
    void save()
    {
        DirectedWeightedGraph g = GraphBuilder.getConnected(15);
        DirectedWeightedGraphAlgorithms ga = new DWG_Algo();
        ga.init(g);

        // Should be true but don't want to create unnecessary files.
        //assertTrue(ga.save("deleteMe.json"));
    }

    @Test
    void load() {
    }
}