package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Plane class represents a two-dimensional plane in 3D Cartesian coordinate system.
 */
public class Plane extends Geometry {
    private final Point p0;
    /**
     * The vertex of the plane /
     * private Point vertex;
     * /* The normal vector to the plane
     */
    private final Vector normal;

    /**
     * Constructs a new Plane object with the specified vertex and normal.
     *
     * @param p0     The Point representing the vertex of the plane.
     * @param normal The Vector representing the normal of the plane.
     */
    public Plane(Point p0, Vector normal) {
        this.p0 = p0;
        this.normal = normal;
    }


    /**
     * Constructs a Plane object by three points in space.
     *
     * @param x a point on the plane
     * @param y another point on the plane
     * @param z a third point on the plane
     */
    public Plane(Point x, Point y, Point z) {
        Vector v1 = y.subtract(x);
        Vector v2 = z.subtract(x);

        this.p0 = x;
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


    /**
     * Finds the intersections of a Ray with the current object.
     * @param ray The ray to intersect
     * @return List of points all the intersections, if there is no intersections return null
     */
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        if (p0.equals(ray.getP0())) {
            return null;
        }
        List<GeoPoint> result = null;
        Vector p0DistanceQ0;
        Vector vector = ray.getDir();
        double numerator, denominator, t;
        boolean isThereNoIntersections;


        p0DistanceQ0 = p0.subtract(ray.getP0());
        numerator = alignZero(normal.dotProduct(p0DistanceQ0));
        denominator = alignZero(normal.dotProduct(vector));
        t = alignZero(numerator / denominator);
        isThereNoIntersections = isZero(numerator) || isZero(denominator) || t <= 0;
        if (!isThereNoIntersections) {
            result = new LinkedList<>();
            result.add(new GeoPoint(this, ray.getP0(t)));
        }

        return result;
    }

}