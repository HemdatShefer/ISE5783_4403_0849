package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTests {
    @Test
    public void testConstructorAndGetters() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper constructor
        Ray r = new Ray(new Point(1, 2, 3), new Vector(1, 1, 1));
        assertEquals(new Point(1, 2, 3), r.getOrigin(), "Wrong origin point");
        assertEquals(new Vector(1, 1, 1).normalize(), r.getDirection(), "Wrong direction vector");

        // =============== Boundary Values Tests ==================
        // TC02: Test for a zero vector as direction
        try {
            new Ray(new Point(1, 2, 3), new Vector(0, 0, 0));
            fail("Constructed a ray with a zero vector as direction");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testEquals() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for equality of two rays with the same origin point and direction vector
        Ray r1 = new Ray(new Point(1, 2, 3), new Vector(1, 1, 1));
        Ray r2 = new Ray(new Point(1, 2, 3), new Vector(1, 1, 1));
        assertTrue(r1.equals(r2), "Equal rays returned false");

        // TC02: Test for non-equality of two rays with different origin point and direction vector
        Ray r3 = new Ray(new Point(1, 2, 3), new Vector(1, 1, 1));
        Ray r4 = new Ray(new Point(1, 2, 3), new Vector(1, 1, 0));
        assertFalse(r3.equals(r4), "Non-equal rays returned true");

        // TC03: Test for non-equality of a ray and a different type object
        Ray r5 = new Ray(new Point(1, 2, 3), new Vector(1, 1, 1));
        Object o = new Object();
        assertFalse(r5.equals(o), "Ray equals different type object");
    }
}