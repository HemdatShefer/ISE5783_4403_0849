package geometries;

import primitives.Point;
import primitives.Ray;

import java.awt.*;
import java.util.List;

/**
 * An interface representing an intersectable geometry object.
 * An intersectable object is an object that can be intersected by a ray, meaning that
 * the object can be checked whether it intersects with a given ray and if so, at which point(s).
 */
public interface Intersectable
{
    /**
     * Finds the intersections of a given ray with the geometry object.
     * If no intersections are found, an empty list is returned.
     * @param ray The ray to intersect with the geometry object
     * @return A list of intersection points between the ray and the geometry object
     */
    List<Point> findIntsersections(Ray ray);
}
