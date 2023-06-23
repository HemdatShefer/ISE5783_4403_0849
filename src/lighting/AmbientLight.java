package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Represents the ambient light in a scene.
 */
public class AmbientLight extends Light {
    public AmbientLight() {
        super(Color.BLACK);
    }

    public AmbientLight(Color intensity, Double3 kAmbient) {
        super(intensity.scale(kAmbient));
    }
}
