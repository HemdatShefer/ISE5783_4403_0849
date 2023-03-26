/**

 The Triangle class represents a triangle in 3D space.
 It extends the Polygon class and inherits its methods for calculating area and checking if a point is inside the triangle.
 It also provides a method for getting the normal vector at a point on the surface.
 */
package geometries;
import primitives.*;


public class Triangle extends Polygon{
    /**
     * Constructs a triangle with the specified three points.
     * @param p0 the first vertex of the triangle
     * @param p1 the second vertex of the triangle
     * @param p2 the third vertex of the triangle
     */
    final Point p0;
    final  Point p1;
    final Point p2;

    public Triangle(Point p0, Point p1, Point p2) {
        super(p0,p1,p2);
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * Computes and returns the normal vector at the specified point on the surface of the triangle.
     * @param point a point on the surface of the triangle
     * @return the normal vector at the specified point on the surface of the triangle
     */
    @Override
    public Vector getNormal(Point point) {
        // TODO: Implement the logic for computing the normal vector at the specified point
        return null;
    }
    }