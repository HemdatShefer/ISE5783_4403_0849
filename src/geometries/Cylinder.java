package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * The height of the cylinder
 */
public class Cylinder extends Tube{
private double height;
        public Cylinder(double v, Ray ray, double h) {
                super(v, ray);
                height=h;
        }
/**
 * Returns the normal vector to the cylinder at the given point on its surface
 *
 * @param point a point on the surface of the cylinder
 * @return the normal vector to the cylinder at the given point
 */
@Override
public Vector getNormal(Point point) {
        return super.getNormal(point);
        }}
