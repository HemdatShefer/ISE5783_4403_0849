package lighting;

import primitives.Color;
import primitives.Vector;

public class DirectionalLight extends Light
{
    private Vector direction;
    public DirectionalLight(Color intensity, Vector vector)
    {
        super(intensity);
        direction=vector;
    }
}
