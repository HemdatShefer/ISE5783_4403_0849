package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTests {
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3),
                pl.getNormal(new Point(0, 0, 1)), "Bad normal to trinagle");
    }
    public void testFindIntersectionPoints()
    {
        Triangle triangle=new Triangle(new Point(-3,0,0), new Point(0,0,3), new Point(2,0,0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: inside
        Point p1=new Point(0,0,1);

        assertTrue(triangle.findIntersections(new Ray(new Point(0,-4,0),new Vector(0,4,1))).get(0).equals(p1),"fail in intersection case" );
        //TC02: out: againts the corner
        Ray r0=new Ray(new Point(0,-6,0),new Vector(0,6,4));
        assertNull(triangle.findIntersections(r0),"fail againts the corner");
        //TC03: out: againts the side
        Ray r1=new Ray(new Point(-2,0,0),new Vector(0,2,-2));
        assertNull(triangle.findIntersections(r1),"fail againts the side");

        // =============== Boundary Values Tests ==================
        // TC04- on corner
        Ray r2=new Ray(new Point(0,4,0),new Vector(-3,-4,0));
        assertNull(triangle.findIntersections(r2),"fail on corner");

        //TC05- on side
        Ray r3=new Ray(new Point(0,4,0),new Vector(0,-3,0));
        assertNull(triangle.findIntersections(r3),"fail on side");

        //TC06- on the continue of a side
        Ray r4=new Ray(new Point(0,6,0),new Vector(-6,-6,0));
        assertNull(triangle.findIntersections(r4),"fail on the continue of a side");

    }
}