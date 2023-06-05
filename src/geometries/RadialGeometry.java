package geometries;

import primitives.Point;
import primitives.Vector;

public abstract class RadialGeometry extends Geometry
{
    protected double radius;

    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    /**
     * @param point
     * @return
     */
    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
