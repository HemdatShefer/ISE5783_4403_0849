package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light {
    private final Vector direction;

    /**
     * Constructs a directional light source.
     * @param intensity The intensity of the light source.
     * @param direction The direction of the light source.
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    public Color getIntensity(Point point) {
        return getIntensity();
    }

    public Vector getL(Point point) {
        return direction;
    }

    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;

    }
}
