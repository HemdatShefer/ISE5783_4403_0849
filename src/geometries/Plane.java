package geometries;

import primitives.Point;
import primitives.Vector;

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
     * Constructs a Plane object by three points in space.
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
    }