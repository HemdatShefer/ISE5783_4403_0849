package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * An interface representing an intersectable geometry object.
 * An intersectable object is an object that can be intersected by a ray, meaning that
 * the object can be checked whether it intersects with a given ray and if so, at which point(s).
 */
public abstract class Intersectable {
    /**
     * Finds the intersections of a given ray with the geometry object.
     * If no intersections are found, an empty list is returned.
     *
     * @param ray The ray to intersect with the geometry object
     * @return A list of intersection points between the ray and the geometry object
     */
    public List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null :
                geoList.stream()
                        .map(gp -> gp.point)
                        .toList();
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    /**
     * Represents a point on a geometry object.
     */
    public static class GeoPoint {
        /**
         * The geometry object.
         */
        public Geometry geometry;

        /**
         * The point on the geometry object.
         */
        public Point point;

        /**
         * Constructs a new GeoPoint with the specified geometry and point.
         *
         * @param geometry the geometry object
         * @param point    the point on the geometry object
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint geoPoint)) return false;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }

    }
}
