package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

/**
 * Basic implementation of a ray tracer that traces rays in a scene and computes colors.
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * Constructs a new RayTracerBasic object with the given scene.
     *
     * @param scene the scene to be rendered
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray in the scene and computes the color.
     *
     * @param ray the ray to be traced
     * @return the computed color of the traced ray
     */
    @Override
    public Color traceRay(Ray ray)
    {
        return null; // Placeholder implementation
    }
    private Color calcColor(Point point)
    {
        Color ambientColor = scene.getAmbientLight().getIntensity();
        Color objectColor = getColorAtPoint(point);
        return ambientColor.add(objectColor);

    }

    private Color getColorAtPoint(Point point)
    {
        return null;
    }
}
