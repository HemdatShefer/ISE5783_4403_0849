package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight
{
    private Vector direction;
    public SpotLight(Color intensity, Point sphereLightPosition, Vector vector)
    {
        super(intensity, vector);////////
        direction=vector;
    }

    public PointLight setNarrowBeam(int i)
    {
        return this;
    }
}
