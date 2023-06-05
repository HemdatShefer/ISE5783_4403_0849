package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    @Test
    public void testGetNormalPoint() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane pl = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point(0, 0, 1)), "Bad normal to plane");
        Point p3=new Point(0,0,0);
        Point p4=new Point(0,0,1);
        Point p5=new Point(0,4,2);

        // =============== Boundary Values Tests ==================
        try {
            new Plane(p3,p5,p3);
        }
        catch (IllegalArgumentException e){
            fail("the constructor of plane get same points");
        }

        // =============== Boundary Values Tests ==================
        try {
            new Plane(p3,p4,p5);
        }
        catch (IllegalArgumentException e){
            fail("there is two points are on the same line");
        }

    }
    @Test
    public void testFindIntersectionPoints() {
        Plane pl = new Plane(new Point(0, 0, 1), new Point(1, 0, 1), new Point(0, 1, 1));
        // ============ Equivalence Partitions Tests ==============

        //**** Group: The Ray must be neither orthogonal nor parallel to the plane
        //TC01:The Ray intersects the plane
        assertEquals( 1, (pl.findIntersections(new Ray(new Point(0,0,0.5),new Vector(0,0,5)))).size(),"Wrong number of points");

        //TC02:Ray does not intersect the plane
        assertNull((pl.findIntersections(new Ray(new Point(1,0,0),new Vector(5,0,0)))),"Wrong number of points");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane
        //TC03:the ray included in the plane
        assertNull((pl.findIntersections(new Ray(new Point(0,0,1),new Vector(0,4,0)))),"Wrong number of points");

        //TC04:the ray not included in the plane
        assertNull((pl.findIntersections(new Ray(new Point(1,0,0),new Vector(0,4,0)))),"Wrong number of points");

        //****Group: Ray is orthogonal to the plane
        //Tc05:The Ray before the plane
        assertEquals( 1, (pl.findIntersections(new Ray(new Point(0,0,0.5),new Vector(0,0,1)))).size(),"Wrong number of points");

        //Tc06:The Ray in the plane
        assertNull((pl.findIntersections(new Ray(new Point(0,0,1),new Vector(0,0,4)))),"Wrong number of points");

        //Tc07:The Ray after the plane
        assertNull((pl.findIntersections(new Ray(new Point(0,0,1.5),new Vector(0,0,10)))),"Wrong number of points");

        //TC08: Ray is neither orthogonal nor parallel to and begins at the same point which appears as reference point in the plane
        assertNull((pl.findIntersections(new Ray(new Point(0,0,1),new Vector(2,0,-4)))),"Wrong number of points");

    }
}