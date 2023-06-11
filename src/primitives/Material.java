package primitives;

/**
 * Material class represents the material properties of a geometry.
 */
public class Material
{
    public Double3 kD = new Double3(0.0, 0.0, 0.0);
    public Double3 kS = new Double3(0.0, 0.0, 0.0);
    public int nShininess = 0;

    /**
     * Sets the diffuse color of the material.
     * @param kD the diffuse color to set
     * @return this material
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets the diffuse color of the material.
     * @param kD the diffuse color to set
     * @return this material
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD, kD, kD);
        return this;
    }

    /**
     * Sets the specular color of the material.
     * @param kS the specular color to set
     * @return this material
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets the specular color of the material.
     * @param kS the specular color to set
     * @return this material
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS, kS, kS);
        return this;
    }

    /**
     * Sets the shininess of the material.
     * @param nShininess the shininess to set
     * @return this material
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
