package primitives;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {
    @Test
    void testFindClosestPoint() {
        // Create a ray
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));

        // Create a list of points
        List<Point> points = new ArrayList<>(Arrays.asList(
                new Point(1, 0, 0),
                new Point(2, 0, 0),
                new Point(3, 0, 0)
        ));

        // ============ Equivalence Partitions Tests ==============

        Point closestPoint = ray.findClosestPoint(points);
        assertEquals(new Point(1, 0, 0), closestPoint);


        // =============== Boundary Values Tests ==================
        // Empty list should return null
        List<Point> emptyList = new ArrayList<>();
        closestPoint = ray.findClosestPoint(emptyList);
        assertNull(closestPoint);

        // First point in the list is closest to the start of the ray
        points.add(0, new Point(-1, 0, 0));
        closestPoint = ray.findClosestPoint(points);
        assertEquals(new Point(-1, 0, 0), closestPoint);

        // Last point in the list is closest to the start of the ray
        points.add(new Point(4, 0, 0));
        closestPoint = ray.findClosestPoint(points);
        assertEquals(new Point(-1.0, 0.0, 0.0), closestPoint);
    }

    @Test
    void testEquals() {
        // Create rays with the same origin point and direction vector
        Ray ray1 = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
        Ray ray2 = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));

        // Rays should be equal
        assertTrue(ray1.equals(ray2));

        // Create rays with different origin points and the same direction vector
        Ray ray3 = new Ray(new Point(1, 0, 0), new Vector(1, 0, 0));

        // Rays should not be equal
        assertFalse(ray1.equals(ray3));

        // Create rays with the same origin point and different direction vectors
        Ray ray4 = new Ray(new Point(0, 0, 0), new Vector(0, 1, 0));

        // Rays should not be equal
        assertFalse(ray1.equals(ray4));
    }

    @Test
    void getPoint() {
        Point point = new Point(1, 2, 3);
        Ray ray = new Ray(point, new Vector(1, 0, 0));
        assertEquals(point, ray.getPoint());
    }

    @Test
    void getDir() {
        Vector dir = new Vector(1, 0, 0);
        Ray ray = new Ray(new Point(0, 0, 0), dir);
        assertEquals(dir, ray.getDir());
    }

    @Test
    void getOrigin() {
        Point origin = new Point(1, 2, 3);
        Ray ray = new Ray(origin, new Vector(1, 0, 0));
        assertEquals(origin, ray.getOrigin());
    }

    @Test
    void getDirection() {
        Vector direction = new Vector(1, 0, 0);
        Ray ray = new Ray(new Point(0, 0, 0), direction);
        assertEquals(direction, ray.getDirection());
    }
}
