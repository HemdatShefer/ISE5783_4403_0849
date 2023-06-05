package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Sphere extends Geometry{
    private Point center;
    private double radius;

    public Sphere( Point c,double r) {
        radius=r;
        center=c;
    }



    /**
 * Computes and returns the normal vector at the specified point on the surface of the sphere.
 * @param point a point on the surface of the sphere
 * @return the normal vector at the specified point on the surface of the sphere
 */
@Override
public Vector getNormal(Point point) {
    return null;
        }


    /**
     * Finds the intersections of a given ray with the geometry object.
     * If no intersections are found, an empty list is returned.
     *
     * @param ray The ray to intersect with the geometry object
     * @return A list of intersection points between the ray and the geometry object
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }

    /**
     * @param ray
     * @return
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return null;
    }
}