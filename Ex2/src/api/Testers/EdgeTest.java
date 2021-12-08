package api;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {

    Vertex v = new Vertex(new Point3D(1,1,0) , 0);
    Vertex u = new Vertex(new Point3D(2,2,0) , 1);

    double phi = (1 + Math.sqrt(5)) / 2;

    Edge e1 = new Edge( v , u , phi);
    Edge e2 = new Edge( u , v , 1/phi);

    @Test
    void getSrc() {
        assertNotEquals(e2.src.id, e1.src.id);
    }
    @Test
    void getInfo()
    {
        String expected = v.id + "," + phi + "," + u.id;
        assertEquals(0, e1.getInfo().compareTo(expected));
        String temp = "i am just a temporary string";
        e1.setInfo(temp);
        assertEquals(0, e1.getInfo().compareTo(temp));
    }
    @Test
    void getWeight()
    {
        assertNotEquals(e1.getWeight(), e2.getWeight(), 0.0);
    }
}