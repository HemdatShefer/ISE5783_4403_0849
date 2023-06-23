package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

public class SpotLight extends PointLight {
    private final Vector direction;

    /**
     * Constructor for SpotLight class.
     * @param intensity The intensity of the light.
     * @param position The position of the light.
     * @param direction The direction of the light.
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point point) {
        double projection = direction.dotProduct(getL(point));
        if (alignZero(projection) < 0) {
            return Color.BLACK;
        }
        return super.getIntensity(point).scale(projection);
    }}
