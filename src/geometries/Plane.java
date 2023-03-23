package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Plane class represents two-dimensional plane in 3D Cartesian coordinate
 * system
 *
 */
public class Plane implements Geometry {
    /** The plane's vertex */
    private Point vertex;
    /** The plane's normal vector */
    private Vector normal;

    /**
     * Constructs a plane object by three points in space.
     *
     * @param vertex  a point on the plane
     * @param vertex1 another point on the plane
     * @param vertex2 a third point on the plane
     */
    public Plane(Point vertex, Point vertex1, Point vertex2) {
        Vector v1 = vertex1.subtract(vertex);
        Vector v2 = vertex2.subtract(vertex);

        this.vertex = vertex;
        normal = v1.crossProduct(v2).normalize();
    }

    /**
     * Returns the normal vector of the plane.
     *
     * @return the normal vector
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }
}
