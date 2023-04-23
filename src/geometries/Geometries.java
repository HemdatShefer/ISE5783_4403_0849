package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

public class Geometries implements Intersectable
{
    private List<Intersectable> intersectableList;

    public Geometries()
    {
        intersectableList =new ArrayList<Intersectable>();
    }
    public Geometries( Intersectable ... Geometries)
    {

    }
    public void add(Intersectable ... Geometries)
    {

    }
/**
     * Finds the intersections of a given ray with the geometry object.
     * If no intersections are found, an empty list is returned.
     *
     * @param ray The ray to intersect with the geometry object
     * @return A list of intersection points between the ray and the geometry object
     */
    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}