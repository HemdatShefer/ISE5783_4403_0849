package primitives;

/**
 * Represents the material properties of a geometry.
 */
public class Material {
    /**
     * Diffusion coefficient.
     */
    public Double3 kD;
    /**
     * Specular coefficient.
     */
    public Double3 kS;
    /**
     * Transparency coefficient.
     */
    public Double3 kT;
    /**
     * Reflection coefficient.
     */
    public Double3 kR;
    /**
     * Specular exponent.
     */
    public int nShininess;

    /**
     * Default constructor.
     */
    public Material() {
        this.kD = Double3.ZERO;
        this.kS = Double3.ZERO;
        this.kT = Double3.ZERO;
        this.kR = Double3.ZERO;
        this.nShininess = 0;
    }

    /**
     * Sets the diffusion coefficient.
     * @param kD the diffusion coefficient as Double3
     * @return the Material object
     */
    public Material setKD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets the diffusion coefficient.
     * @param kD the diffusion coefficient as double
     * @return the Material object
     */
    public Material setKD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Sets the specular coefficient.
     * @param kS the specular coefficient as Double3
     * @return the Material object
     */
    public Material setKS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets the specular coefficient.
     * @param kS the specular coefficient as double
     * @return the Material object
     */
    public Material setKS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Sets the transparency coefficient.
     * @param kT the transparency coefficient as Double3
     * @return the Material object
     */
    public Material setKT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * Sets the transparency coefficient.
     * @param kT the transparency coefficient as double
     * @return the Material object
     */
    public Material setKT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * Sets the reflection coefficient.
     * @param kR the reflection coefficient as Double3
     * @return the Material object
     */
    public Material setKR(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * Sets the reflection coefficient.
     * @param kR the reflection coefficient as double
     * @return the Material object
     */
    public Material setKR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * Sets the specular exponent.
     * @param nShininess the specular exponent
     * @return the Material object
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
