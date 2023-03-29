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
     * @param point0 the first vertex of the triangle
     * @param point1 the second vertex of the triangle
     * @param point2 the third vertex of the triangle
     */
    final Point point0;
    final  Point point1;
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
        return null;
    }
    }