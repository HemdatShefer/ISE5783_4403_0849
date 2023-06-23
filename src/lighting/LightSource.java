package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public interface LightSource {
    /**
     * Returns the color of the light source by the given point.
     * @param point The point to calculate the color by.
     * @return The color of the light source by the given point.
     */
    Color getIntensity(Point point);

    /**
     * Returns the direction of the light source.
     * @param point The point to calculate the direction by.
     * @return The direction of the light source.
     */
    Vector getL(Point point);


    double getDistance(Point point);
}
