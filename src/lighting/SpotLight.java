package lighting;

import primitives.Color;
import primitives.Vector;

public class SpotLight extends PointLight
{
    private Vector direction;
    protected SpotLight(Color intensity, Vector vector)
    {
        super(intensity, vector);////////
        direction=vector;
    }
}
