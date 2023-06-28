package primitives;

import geometries.Intersectable.GeoPoint;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * This class represents a ray in 3D space, which consists of an origin point and a direction vector.
 * The origin point is the starting point of the ray, and the direction vector is the direction in which
 * the ray extends infinitely.
 */
public class Ray {
    /**
     * The origin point of the ray.
     */
    private final Point p0;

    /**
     * The direction vector of the ray.
     */
    private Vector dir;
    private static final double DELTA = 0.1;

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
     * Creates a new ray by point,vector direction and normal.
     * @param p0 head point of the ray
     * @param dir direction of the ray
     * @param normal normal of the ray
     */
    public Ray(Point p0, Vector dir, Vector normal) {
        this.dir = dir;
        /* make sure the normal and the direction are not orthogonal */
        double nv = alignZero(normal.dotProduct(dir));
        /* if not orthogonal */
        if (!isZero(nv)) {
            Vector moveVector = normal.scale(nv > 0 ? DELTA : -DELTA);
            /* move the head of the vector in the right direction */
            this.p0 = p0.add(moveVector);
        } else {
            this.p0 = p0;
        }
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
        return p0 + " -> " + dir;
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
     * Find the closest point to current ray.
     * @param points list of geo points
     * @return the closest geo point to ray, if there are no points, return null
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
        if (points == null || points.isEmpty()) {
            return null; // Return null if the list is empty
        }

        GeoPoint closestPoint = null;
        double minDistanceSquared = Double.MAX_VALUE;

        for (GeoPoint geoPoint : points) {
            double distanceSquared = geoPoint.point.distanceSquared(p0);
            if (distanceSquared < minDistanceSquared) {
                minDistanceSquared = distanceSquared;
                closestPoint = geoPoint;
            }
        }

        return closestPoint;
    }

    @Override
    public int hashCode()
    {
        return p0.hashCode() + dir.hashCode();
    }
    public Point getP0()
    {
        return p0;
    }

    public Point getP0(double t)
    {
        return p0.add(dir.scale(t));
    }

    public Vector getDir()
    {
        return dir;
    }


}
