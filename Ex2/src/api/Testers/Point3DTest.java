package api;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Point3DTest {

    @Test
    public static void main(String[]args)
    {
        Point3D p1 = new Point3D(3,4,0);
        Point3D p2 = new Point3D();
    }

    @Test
     void x(Point3D p) {
        assertEquals(p.x, 0);
    }

    @Test
    void y(Point3D p) {
        assertEquals(p.y, 0);
    }

    @Test
    void z(Point3D p) {
        assertEquals(p.z, 0);
    }

    @Test
    void distance(Point3D p, Point3D p2) {
        assertEquals(p.distance(p2),5);
    }
}