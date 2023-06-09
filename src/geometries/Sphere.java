package geometries;

import static primitives.Util.alignZero;

import geometries.Geometry;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.LinkedList;
import java.util.List;

/**
 * Class describe sphere.
 */
public class Sphere extends Geometry {

    private final Point center;
    private final double radius;

    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        /* In case that p0 is same as center */
        if (ray.getP0().equals(center)) {
            return List.of(new GeoPoint(this, center.add(ray.getDir().scale(radius))));
        }
        List<GeoPoint> result = new LinkedList<>();
        double tm, d, th, t1, t2;
        Point p0 = ray.getP0();
        Vector vector = ray.getDir();
        Vector u;

        u = center.subtract(ray.getP0());
        tm = alignZero(vector.dotProduct(u));
        d = alignZero(Math.sqrt(alignZero(u.lengthSquared() - tm * tm)));
        th = alignZero(Math.sqrt(alignZero(radius * radius - d * d)));
        t1 = alignZero(tm - th);
        t2 = alignZero(tm + th);

        /* Check if the ray direction is above the sphere */
        if (d < radius) {
            if (t1 > 0) {
                Point p1 = p0.add(vector.scale(t1));
                result.add(new GeoPoint(this, p1));
            }
            if (t2 > 0) {
                Point p2 = p0.add(vector.scale(t2));
                result.add(new GeoPoint(this, p2));
            }
        }

        result = result.isEmpty() ? null : result;

        return result;
    }
}
