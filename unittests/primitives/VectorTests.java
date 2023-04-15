package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class VectorTests {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    @Test
    void testZero(){
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        try { // test zero vector
            new Vector(0, 0, 0);
            fail("ERROR: zero vector does not throw an exception");
        } catch (IllegalArgumentException e) {
            out.println("Good: vector 0 not created");
        }
    }


    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        if (!isZero(v1.lengthSquared() - 14))
            fail("ERROR: lengthSquared() wrong value");
        if (!isZero(new Vector(0, 3, 4).length() - 5))
            fail("ERROR: length() wrong value");
    }

    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(new Vector(1, 1, 1), new Vector(2, 3, 4).add(new Vector(-1, -2, -3)), "the method add() is fail");

    }

    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(new Vector(2, 4, 6), new Vector(1, 2, 3).scale(2), "the method scale() is fail");

        // =============== Boundary Values Tests ==================
        // TC11: test scaling to 0
        assertThrows(IllegalArgumentException.class, () -> new Vector(1, 2, 3).scale(0d), "the method scale(0) must thrown Exception");
    }

    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(5d, new Vector(0, 3, 4).length(), "the method length() is fail");
    }

    @Test
    void testDotProduct() {
        // test Dot-Product
        if (!isZero(v1.dotProduct(v3)))
            fail("ERROR: dotProduct() for orthogonal vectors is not zero");
        if (!isZero(v1.dotProduct(v2) + 28))
            fail("ERROR: dotProduct() wrong value");
    }

    @Test
    public void testCrossProduct() {
        // test Cross-Product
        try { // test zero vector
            v1.crossProduct(v2);
            fail("ERROR: crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {
        }
        Vector vr = v1.crossProduct(v3);
        if (!isZero(vr.length() - v1.length() * v3.length()))
            fail("ERROR: crossProduct() wrong result length");
        if (!isZero(vr.dotProduct(v1)) || !isZero(vr.dotProduct(v3)))
            fail("ERROR: crossProduct() result is not orthogonal to its operands");
    }


        @Test
        void testNormalize() {
            Vector v = new Vector(0, 3, 4);
            Vector n = v.normalize();
            // ============ Equivalence Partitions Tests ==============
            // TC01: Simple test
            assertEquals(1d, n.lengthSquared(), 0.00001, "wrong normalized vector length");
            assertThrows(IllegalArgumentException.class, ()-> v.crossProduct(n), "normalized vector is not in the same direction");
            assertEquals(new Vector(0, 0.6, 0.8), n, "wrong normalized vector");
        }
}