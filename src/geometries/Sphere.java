package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Sphere implements Geometry{
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


    @Override
    public List<Point> findIntsersections(Ray ray)
    {
        return null;
    }
}