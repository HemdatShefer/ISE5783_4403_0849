package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;

/**
 * An interface representing an intersectable geometry object.
 * An intersectable object is an object that can be intersected by a ray, meaning that
 * the object can be checked whether it intersects with a given ray and if so, at which point(s).
 */
public abstract class Intersectable {
    /**
     * Find all Intersections points from the ray.
     * @param ray MUST be not null, The ray tested at the intersection of the object
     * @return List of points that intersection with the object
     */
    public List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        List<Point> result = null;
        if (geoList != null) {
            result = geoList.stream().map(GeoPoint::getPoint)
                    .collect(java.util.stream.Collectors.toList());
        }
        return result;
    }

    /**
     * Find all GeoPoints from the ray.
     * @param ray MUST be not null, The ray tested at the intersection of the object
     * @return List of GeoPoints that intersection with the object
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    /**
     * Find the nearest intersection point from the ray.
     * @param ray MUST be not null, The ray tested at the intersection of the object
     * @return The nearest point that intersection with the object
     */
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }


    /**
     * GeoPoint contains the geometry and the point on the geometry.
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public GeoPoint setGeometry(Geometry geometry) {
            this.geometry = geometry;
            return this;
        }

        public Point getPoint() {
            return point;
        }

        public GeoPoint setPoint(Point point) {
            this.point = point;
            return this;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof GeoPoint other)) {
                return false;
            }
            boolean sameGeometryType = this.geometry.getClass().equals(other.geometry.getClass());
            return sameGeometryType && this.point.equals(other.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" + "geometry=" + geometry + ", point=" + point + '}';
        }
    }
}
