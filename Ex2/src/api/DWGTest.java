package api;

import org.junit.jupiter.api.Test;

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

        // Invalid
        g.connect(0,2, 1);
        // Make sure does not exist
        assertNull(g.getEdge(0,2));

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

    }

//    @Test
//    void getEdge() {
//    }
//
//    @Test
//    void addNode() {
//    }
//
//    @Test
//    void connect() {
//    }
//
//    @Test
//    void nodeIter() {
//    }
//
//    @Test
//    void edgeIter() {
//    }
//
//    @Test
//    void testEdgeIter() {
//    }
//
//    @Test
//    void removeNode() {
//    }
//
//    @Test
//    void removeEdge() {
//    }
//
//    @Test
//    void nodeSize() {
//    }
//
//    @Test
//    void edgeSize() {
//    }
//
//    @Test
//    void getMC() {
//    }
}