/**
 * The Triangle class represents a triangle in 3D space.
 * It extends the Polygon class and inherits its methods for calculating area and checking if a point is inside the triangle.
 * It also provides a method for getting the normal vector at a point on the surface.
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;


public class Triangle extends Polygon {
    /**
     * Constructs a triangle with the specified three points.
     * @param point0 the first vertex of the triangle
     * @param point1 the second vertex of the triangle
     * @param point2 the third vertex of the triangle
     */
    final Point point0;
    final Point point1;
    final Point point2;

    /**
     * Constructs a new Triangle object with the specified points.
     *
     * @param point0 The first Point of the triangle.
     * @param point1 The second Point of the triangle.
     * @param point2 The third Point of the triangle.
     */
    public Triangle(Point point0, Point point1, Point point2) {
        super(point0, point1, point2);
        this.point0 = point0;
        this.point1 = point1;
        this.point2 = point2;
    }


    /**
     * Computes and returns the normal vector at the specified point on the surface of the triangle.
     * @param point a point on the surface of the triangle
     * @return the normal vector at the specified point on the surface of the triangle
     */
    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<Point> planeIntersections = plane.findIntersections(ray);

        if(planeIntersections == null)
            return null;

        Vector v1 = vertices.get(0).subtract(ray.getP0());
        Vector v2 = vertices.get(1).subtract(ray.getP0());
        Vector v3 = vertices.get(2).subtract(ray.getP0());

        Vector n1 = v1.crossProduct(v2);
        Vector n2 = v2.crossProduct(v3);
        Vector n3 = v3.crossProduct(v1);

        // The point is on edge's continuation.
        if (isZero(n1.dotProduct(ray.getDir())) || isZero(n2.dotProduct(ray.getDir())) || isZero(n3.dotProduct(ray.getDir())))
            return null;

        if (n1.dotProduct(ray.getDir()) < 0) {
            if (n2.dotProduct(ray.getDir()) > 0 || n3.dotProduct(ray.getDir()) > 0)
                return null;
        }
        if (n1.dotProduct(ray.getDir()) > 0) {
            if (n2.dotProduct(ray.getDir()) < 0 || n3.dotProduct(ray.getDir()) < 0)
                return null;
        }

        return List.of(new GeoPoint(this,planeIntersections.get(0)));
    }

}
