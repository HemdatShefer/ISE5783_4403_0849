package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;
import primitives.Ray;
/**
 * Class describe tube, the tube is infinity.
 */
public class Tube extends Geometry {

    protected final Ray axisRay;
    protected final double radius;

    /**
     * Create tube.
     * @param axisRay the ray contains the direction and the center point of the center
     * @param radius the radius of the tube
     * @throws IllegalArgumentException if the radius is negative or zero
     */
    public Tube(Ray axisRay, double radius) {
        if (radius < 0 || Util.isZero(radius)) {
            throw new IllegalArgumentException("Radius can't be zero or negative.");
        }
        this.axisRay = axisRay;
        this.radius = radius;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector pMinusHead = point.subtract(axisRay.getP0());
        double t = axisRay.getDir().dotProduct(pMinusHead);
        /* Check if the point is "front" to the p0 the point in the base */
        if (Util.isZero(t)) {
            return pMinusHead.normalize();
        }
        /* The point on the side calculate the normal */
        Point center = axisRay.getP0().add(axisRay.getDir().scale(t));
        return point.subtract(center).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        //The direction of the tube's central ray
        Vector tubeDir = this.axisRay.getDir();
        //The direction of the incoming ray
        Vector rayDir = ray.getDir();

        //The rays are parallel and can't intersect
        if (tubeDir.equals(rayDir) || tubeDir.equals(rayDir.scale(-1))) {
            return null;
        }

        //Projection of rayDir onto tubeDir (dot product)
        double dotP1 = Util.alignZero(rayDir.dotProduct(tubeDir));
        //Component of rayDir orthogonal to tubeDir
        Vector vec1 = dotP1 == 0 ? rayDir : rayDir.subtract(tubeDir.scale(dotP1));
        //Squaring the radius for later calculations
        double radiusSquared = this.radius * this.radius;

        //Coefficient 'a' in the quadratic equation, representing the squared length of vec1
        double a = Util.alignZero(vec1.lengthSquared());

        //Vector from the base of the tube to the base of the incoming ray
        Vector deltaP = ray.getP0().subtract(this.axisRay.getP0());

        //Projection of deltaP onto tubeDir
        double dotP2 = Util.alignZero(deltaP.dotProduct(tubeDir));
        //Component of deltaP orthogonal to tubeDir
        var vec2 = dotP2 == 0 ? deltaP : deltaP.subtract(tubeDir.scale(dotP2));

        //Coefficient 'b' in the quadratic equation, representing 2 times the dot product of vec1 and vec2
        double b = Util.alignZero(2 * (vec1.dotProduct(vec2)));
        //Coefficient 'c' in the quadratic equation, representing the squared length of vec2 minus the squared radius
        double c = Util.alignZero(vec2.lengthSquared() - radiusSquared);

        //Discriminant in the quadratic equation
        double det = Util.alignZero(b * b - 4 * a * c);

        //No solutions to the quadratic equation - the ray doesn't intersect the tube
        if (det <= 0) return null;

        //Solutions exist - calculate them
        det = Math.sqrt(det);
        double t1 = Util.alignZero((-b + det) / (2 * a));
        double t2 = Util.alignZero((-b - det) / (2 * a));

        //No intersections, because they occur behind the ray's base point
        if (t1 <= 0 || t2 <= 0) return null;
        else {
            //Return one or both intersections, if they exist
            return t2 <= 0 ? List.of(new GeoPoint(this, ray.getPoint(t1))) :
                    List.of(new GeoPoint(this, ray.getPoint(t2)), new GeoPoint(this, ray.getPoint(t1)));
        }
    }


}