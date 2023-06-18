package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * This class represents a ray in 3D space, which consists of an origin point and a direction vector.
 * The origin point is the starting point of the ray, and the direction vector is the direction in which
 * the ray extends infinitely.
 */
public class Ray {
    /**
     * The origin point of the ray.
     */
    private Point p0;


    /**
     * The direction vector of the ray.
     */
    private Vector dir;

    /**
     * Constructs a new ray with the given origin point and direction vector.
     * The direction vector is automatically normalized.
     *
     * @param point  the origin point of the ray
     * @param vector the direction vector of the ray
     */
    public Ray(Point point, Vector vector) {
        p0 = point;
        dir = vector.normalize();
    }

    /**
     * Compares this ray to the given object for equality.
     * Two rays are considered equal if they have the same origin point and direction vector.
     *
     * @param obj the object to compare to this ray
     * @return true if the object is a ray with the same origin point and direction vector as this ray, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.p0.equals(other.p0)
                && this.dir.equals(other.dir);
    }

    /**
     * Returns the origin point of the ray.
     *
     * @return the origin point of the ray
     */
    public Point getPoint(double t) {
        return p0.add(dir.scale(t));
    }

    /**
     * Returns the direction vector of the ray.
     *
     * @return the direction vector of the ray
     */
    public Vector getDir() {
        return dir;
    }

    public Ray setDir(Vector dir) {
        this.dir = dir;
        return this;
    }

    /**
     * Returns a string representation of this ray.
     *
     * @return a string representation of this ray
     */
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    /**
     * Returns the origin point of the ray.
     *
     * @return the origin point of the ray
     */
    public Point getOrigin() {
        return p0;
    }

    /**
     * Returns the direction vector of the ray.
     *
     * @return the direction vector of the ray
     */
    public Vector getDirection() {
        return dir;
    }

    /**
     * Finds the closest point to the start of the ray among the given list of points.
     *
     * @param points a list of points
     * @return the point closest to the start of the ray, or null if the list is empty
     */
    public Point findClosestPoint(List<Point> points) {
        if (points == null || points.isEmpty())
            return null;
        Point closest = null;
        double minDistance = Double.MAX_VALUE;
        for (Point p : points) {
            double distance = p.distance(p0);
            if (distance < minDistance) {
                closest = p;
                minDistance = distance;
            }
        }
        return closest;
    }

    /**
     * Find closest GeoPoint
     *
     * @param points list of GeoPoints
     * @return closest GeoPoint
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
        if (points == null || points.isEmpty())
            return null;
        GeoPoint closest = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (GeoPoint p : points) {
            double distance = p.point.distance(p0);
            if (distance < minDistance) {
                closest = p;
                minDistance = distance;
            }
        }
        return closest;
    }

    public Point getP0() {
        return p0;
    }

    public Ray setP0(Point p0) {
        this.p0 = p0;
        return this;
    }

    public Point getPoint() {
        return p0;
    }
}
