package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;
import primitives.Double3;

public class Scene
{
    public String name;
    public primitives.Color background= Color.BLACK;;
    public AmbientLight ambientLight= AmbientLight.NONE;
    public Geometries geometries = new Geometries();
    public Scene(String name) {
        this.name = name;
    }
    public Scene setBackground(String backgroundStr) {
        this.background = new Color(backgroundStr);
        return this;
    }
    public Scene setAmbientLight(Color iA, Double3 kA) {
        this.ambientLight = new AmbientLight(iA, kA);
        return this;
    }
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

}
