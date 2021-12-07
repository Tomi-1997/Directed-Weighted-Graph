package api;

import static org.junit.jupiter.api.Assertions.*;

class VertexTest {

    @org.junit.jupiter.api.Test
    void t1()
    {
        Vertex v = new Vertex(new Point3D(1, 1, 0), 0);
        assertTrue(v.getInfo().compareTo("1.0,1.0,0.0,0") == 0);
        assertEquals(v.weight, 0);
        assertEquals(v.tag , 0);
    }

}