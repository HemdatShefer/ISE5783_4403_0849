package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * The base class for ray tracing algorithms.
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * Constructor.
     * @param scene the scene
     * @throws NullPointerException if scene is null
     */
    public RayTracerBase(Scene scene) throws NullPointerException {
        if (scene == null) {
            throw new NullPointerException("ERROR: scene can't be null");
        }
        this.scene = scene;
    }

    /**
     * Got a ray and return the color of the intersect geometries.
     * @param ray the ray
     * @return the color of the intersect geometries
     */
    public abstract Color traceRay(Ray ray);
    public abstract Color traceRay(List<Ray> ray);

}
