package renderer;

import primitives.Ray;
import scene.Scene;

/**
 * The base class for ray tracing algorithms.
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * Constructs a new RayTracerBase object with the given scene.
     *
     * @param scene the scene to be rendered
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces a ray in the scene and computes the color.
     *
     * @param ray the ray to be traced
     * @return the computed color of the traced ray
     */
    public abstract primitives.Color traceRay(Ray ray);
}
