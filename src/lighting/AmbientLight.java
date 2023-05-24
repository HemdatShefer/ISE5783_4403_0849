package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Represents the ambient light in a scene.
 */
public class AmbientLight {
    protected Color intensity;
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK);

    /**
     * Constructs a new AmbientLight object with the specified intensity.
     * @param intensity the intensity of the ambient light
     */
    public AmbientLight(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Constructs a new AmbientLight object with the specified ambient color and coefficient.
     * @param iA the ambient color of the light
     * @param kA the ambient coefficient of the light
     */
    public AmbientLight(Color iA, Double3 kA) {
        // Implementation goes here
    }

    /**
     * Constructs a new AmbientLight object with default values.
     */
    public AmbientLight() {
        // Implementation goes here
    }

    public Color getIntensity()
    {
        return intensity;
    }
}
