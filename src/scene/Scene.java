package scene;

import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a scene containing geometries and lighting information.
 */
public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;
    public List<LightSource> lights;

    public Scene(String name) {
        this.name = name;
        background = Color.BLACK;
        ambientLight = new AmbientLight();
        geometries = new Geometries();
        lights = new LinkedList<>();
    }


    public Scene setBackground(Color color) {
        background = color;
        return this;
    }


    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene addGeometry(Intersectable geometries) {
        if (geometries != null) {
            this.geometries.add(geometries);
        }
        return this;
    }

    public Scene addLights(LightSource... lightSource) {
        if (lightSource != null) {
            lights.addAll(List.of(lightSource));
        }
        return this;
    }
}