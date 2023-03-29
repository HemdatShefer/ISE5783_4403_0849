/**

 The Tube class represents an infinitely long tube in 3D space.
 It implements the Geometry interface and provides a method for getting the normal vector at a point on the surface.
 */
package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube implements Geometry {
    protected Ray axisRay; // the axis ray of the tube
    protected double radius; // the radius of the tube

    /**
     * Computes and returns the normal vector at the specified point on the surface of the tube.
     * @param point a point on the surface of the tube
     * @return the normal vector at the specified point on the surface of the tube
     */
    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
