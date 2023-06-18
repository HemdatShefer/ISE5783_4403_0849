package scene;

import geometries.Geometries;
import geometries.Geometry;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;

import java.util.List;

/**
 * Represents a scene containing geometries and lighting information.
 */
public class Scene {
    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = AmbientLight.NONE;
    public Geometries geometries = new Geometries();
    public List<LightSource> lights;

    /**
     * Constructs a new Scene object with the specified name.
     *
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    /**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight the ambient light to be set
     * @return the current scene object
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the background color of the scene using a string representation.
     *
     * @param backgroundStr the string representation of the background color
     * @return the current scene object
     */
    public Scene setBackground(String backgroundStr) {
        this.background = new Color(backgroundStr);
        return this;
    }

    /**
     * Sets the background color of the scene using a Color object.
     *
     * @param backgroundColor the background color
     * @return the current scene object
     */
    public Scene setBackground(Color backgroundColor) {
        this.background = backgroundColor;
        return this;
    }

    /**
     * Sets the ambient light of the scene using the ambient color and coefficient.
     *
     * @param iA the ambient color of the light
     * @param kA the ambient coefficient of the light
     * @return the current scene object
     */
    public Scene setAmbientLight(Color iA, Double3 kA) {
        this.ambientLight = new AmbientLight(iA, kA);
        return this;
    }

    /**
     * Sets the geometries of the scene.
     *
     * @param geometries the geometries to be added to the scene
     * @return the current scene object
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Adds a geometry to the scene.
     *
     * @param geometry the geometry to add
     */
    public void addGeometry(Geometry geometry) {
        geometries.add(geometry);
    }
}
