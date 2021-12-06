package api;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class DWGTest {

    DWG g = new DWG();

    @Test
    void t1() {
        assertNull(g.getNode(0));
        g.addNode( new Vertex( new Point3D(1,1,0) , 0));
        g.addNode( new Vertex( new Point3D(1 , 2 , 0), 1));

        assertNotNull(g.getNode(0));
        assertNotNull(g.getNode(1));

        g.connect(0,1, 1);

        // Try to connect edge with non-existent node
        g.connect(0,2, 1);
        // Make sure it does not exist
        assertNull(g.getEdge(0,2));

        // Disconnect vertex and make sure it is null afterwards.
        Vertex disconnected = (Vertex) g.removeNode(0);
        assertNull(g.getNode(0));


        // Make sure runtime exception is raised for insertion after iterator is born.
        boolean flag = false;
        Iterator<EdgeData> t = g.edgeIter();
        try
        {
            g.addNode(disconnected);
        }
        catch (RuntimeException e)
        {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    void t2() {
        int N = 10;
        for (int i = 0; i < N; i++)
        {
            g.addNode( new Vertex(new Point3D(i,0,0) , i));
        }

        Iterator<NodeData> iter = g.nodeIter();
        int counter = 0;

        // Make sure iterator iterates over all nodes
        while(iter.hasNext())
        {
             counter++;
            iter.next().getLocation();
        }
        assertEquals(g.nodeSize(), counter);

        // Make sure iterator deletion deletes all nodes
        iter = g.nodeIter();
        while (iter.hasNext())
        {
            iter.next();
            iter.remove();
        }
        assertEquals(g.nodeSize(), 0);
    }

    @Test
    void t3() {

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

        // Check that iterator over single node returns only relevant edges.
        Iterator<EdgeData> iter = g.edgeIter(0);
        int c = 0;
        while (iter.hasNext())
        {
            c++;
        }
        assertEquals(c , 2);
    }

}