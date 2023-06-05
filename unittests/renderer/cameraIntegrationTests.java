package renderer;

import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class cameraIntegrationTests {
    // ============ Equivalence Partitions Tests ==============
    Camera cm = new Camera(new Point(0,0,0.5), new Vector(0,1,0), new Vector(0,0,-1));
    Camera cm1 = new Camera(new Point(0,0,0), new Vector(0,1,0), new Vector(0,0,-1));
    //TC01 : small Sphere
    public void testSmallSphere()
    {
        //TC01: Small Sphere 2 points
        Sphere sphere = new Sphere(new Point(0,0,-3), 1);
        List<Point> results = sphere.findIntersections(new Ray(new Point(0,0,0), cm1.getVTo()));
        assertEquals(2, results.size(), "Wrong number of points");
    }

    //TC02: Test for central ray
    @Test
    public void testCentralRay()
    {
        Ray expectedRay = new Ray(new Point(0, 0, 0.5), new Vector(0, 0, -1));
        Ray actualRay = cm.constructRayThroughPixel(3, 3, 1, 1, 1, 6, 6);
        assertEquals(expectedRay, actualRay, "Central ray not as expected");
    }

    //TC03: Test for corner rays
    public void testCornerRays()
    {
        Ray expectedRay = new Ray(new Point(0, 0, 0.5), new Vector(-2, -2, -1));
        Ray actualRay = cm.constructRayThroughPixel(3, 3, 0, 0, 1, 6, 6);
        assertEquals(expectedRay, actualRay, "Corner ray not as expected");
    }

    //TC04: Test for edge rays
    public void testEdgeRays()
    {
        Ray expectedRay = new Ray(new Point(0, 0, 0.5), new Vector(0, -2, -1));
        Ray actualRay = cm.constructRayThroughPixel(3, 3, 1, 0, 1, 6, 6);
        assertEquals(expectedRay, actualRay, "Edge ray not as expected");
    }
    /**
     * Counts the number of intersections between the specified camera and geometry.
     *
     * @param camera    the camera used to generate rays
     * @param geometry  the geometry to test for intersections
     * @return          the total number of intersection points found
     * @throws IllegalAccessException if the intersection calculation fails
     */
    int findNumberOfIntersecions(Camera camera, Geometry geometry) throws IllegalAccessException {
        int points = 0;
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                Ray ray = camera.constructRay(3, 3, j, i);
                if (geometry.findIntersections(ray) != null) {
                    points += geometry.findIntersections(ray).size();
                }
            }
        }
        return points;
    }


    /**
     * Test method for {@link renderer.Camera#constructRay(int, int, int, int)}.
     */
    @Test
    void CameraSphereIntersections() throws IllegalAccessException {
        Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, -1));
        camera.setVPSize(3, 3);
        camera.setVPDistance(1);

        // TC01: small sphere in front of view plane(2 points)
        Sphere sphere = new Sphere(new Point(0, 0, -3), 1);
        assertEquals(2, findNumberOfIntersecions(camera, sphere), "wrong number of intersection points ");

        camera = new Camera(new Point(0, 0, 0.5), new Vector(0, -1, 0), new Vector(0, 0, -1));
        camera.setVPSize(3, 3);
        camera.setVPDistance(1);

        // TC02: big sphere in front of view plane(18 points)
        sphere = new Sphere(new Point(0, 0, -2.5), 2.5);
        assertEquals(18, findNumberOfIntersecions(camera, sphere), "wrong number of intersection points ");

        // TC03: the corners rays of the view plane doesn't cross the sphere  (10 points)
        sphere = new Sphere(new Point(0, 0, -2), 2);
        assertEquals(10, findNumberOfIntersecions(camera, sphere), "wrong number of intersection points ");

        // TC04: sphere behind the view plane (0 points)
        sphere = new Sphere(new Point(0, 0, 1), 0.5);
        assertEquals(0, findNumberOfIntersecions(camera, sphere), "wrong number of intersection points ");


    }
    /**
     * Test method for {@link renderer.Camera#constructRay(int, int, int, int)}.
     */
    @Test
    public void testConstructRayOfPlane() throws IllegalAccessException {
        Camera camera1 = new Camera(new Point(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, -1));
        camera1.setVPSize(3, 3);
        camera1.setVPDistance(1);

        //TC01: the plane are parallel to the view plane
        Plane plane1 = new Plane(new Point(0, 0, -5), new Vector(0, 0, 1));
        assertEquals(findNumberOfIntersecions(camera1, plane1), 9, "good");

        //TC02: the plane approaching to the vie plan
        Plane plane2 = new Plane(new Point(0, 0, -5), new Vector(0, 1, 2));
        assertEquals(findNumberOfIntersecions(camera1, plane2), 9, "good");

        //TC03: the plane slowly approaching to the vie plan and don't touch the ray of the third pixel
        Plane plane3 = new Plane(new Point(0, 0, -5), new Vector(0, 1, 1));
        assertEquals(findNumberOfIntersecions(camera1, plane3), 6, "good");
    }

    /**
     * Test method for {@link renderer.Camera#constructRay(int, int, int, int)}.
     */
    @Test
    public void testConstructRayOfTriangle() throws IllegalAccessException {
        Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, -1));
        camera.setVPSize(3, 3);
        camera.setVPDistance(1);

        //TC01: the triangle small and just the ray from the middle pixel integrate
        Triangle triangle1 = new Triangle(new Point(1, 1, -2), new Point(-1, 1, -2), new Point(0, -1, -2));
        assertEquals(findNumberOfIntersecions(camera, triangle1), 1, "bad");

        //TC02: the triangle start across the middle pixel and continue to the top pixel
        Triangle triangle2 = new Triangle(new Point(1, 1, -2), new Point(-1, 1, -2), new Point(0, -20, -2));
        assertEquals(findNumberOfIntersecions(camera, triangle2), 2, "bad");
    }
}
