package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointTests {
    Point p0 = new Point(0, 1, 2);
    Point p1 = new Point(2, 1, 3);
    Vector vector = new Vector(2, 4, 6);
    double di = 5.0;

    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(new Point(2, 5, 8), (p0.add(vector)), "the method add() is fail");
    }

    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(5.0, (p1.distanceSquared(p0)), "the method DistanceSquared() is fail");
    }

    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(Math.sqrt(di), (p1.distance(p0)), "the method Distance() is fail");
    }

}