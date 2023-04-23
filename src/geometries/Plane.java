package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**

 Plane class represents a two-dimensional plane in 3D Cartesian coordinate system.
 */
public class Plane implements Geometry {
    private final Point vertex;
    /** The vertex of the plane /
     private Point vertex;
     /* The normal vector to the plane */
    private final Vector normal;

    /**
     * Constructs a new Plane object with the specified vertex and normal.
     *
     * @param vertex The Point representing the vertex of the plane.
     * @param normal The Vector representing the normal of the plane.
     */
    public Plane(Point vertex, Vector normal) {
        this.vertex = vertex;
        this.normal = normal;
    }


    /**
     * Constructs a Plane object by three points in space.
     *
     * @param x  a point on the plane
     * @param y another point on the plane
     * @param z a third point on the plane
     */
    public Plane(Point x, Point y, Point z) {
        Vector v1 = y.subtract(x);
        Vector v2 = z.subtract(x);

        this.vertex = x;
        normal = v1.crossProduct(v2).normalize();
    }

    /**
     * Returns the normal vector of the plane.
     *
     * @return the normal vector of the plane
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * Returns the normal vector to the plane at the given point (which is on the plane).
     *
     * @param point a point on the plane
     * @return the normal vector to the plane
     */
    @Override
    public Vector getNormal(Point point) {
        return normal;
    }


    @Override
    public List<Point> findIntsersections(Ray ray)
    {
        return null;
    }
}