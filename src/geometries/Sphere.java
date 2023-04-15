package geometries;

import primitives.Point;
import primitives.Vector;
public class Sphere implements Geometry{
    private Point center;
    private double radius;

    public Sphere(double r, Point c) {
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
    }