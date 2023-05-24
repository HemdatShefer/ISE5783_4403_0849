package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight
{
    protected primitives.Color intensity;
    public static final AmbientLight NONE = new AmbientLight(primitives.Color.BLACK);
    public AmbientLight(primitives.Color intensity)
    {
        this.intensity = intensity;
    }
    public AmbientLight(Color iA, Double3 kA) {
    }
    public AmbientLight() {
    }

}




