package geometries;

import primitives.Point;
import primitives.Ray;

import java.awt.*;

public interface Intersectable
{
    List findIntersections(Ray r);//list point
}
