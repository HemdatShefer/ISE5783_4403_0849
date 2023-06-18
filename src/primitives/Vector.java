package primitives;

public class Vector extends Point {
    /**
     * This class represents a vector in 3D space, which consists of a direction and magnitude.
     * <p>
     * The vector is defined by its x, y, and z components, which can be thought of as the differences
     * <p>
     * between two points in 3D space.
     * /
     * public class Vector extends Point {
     * /*
     * <p>
     * Constructs a new vector with the given x, y, and z components.
     * The vector cannot be the zero vector.
     *
     * @param x the x component of the vector
     * @param y the y component of the vector
     * @param z the z component of the vector
     * @throws IllegalArgumentException if the vector is the zero vector
     */

    public Vector(double x, double y, double z) {
        this(new Double3(x, y, z));
    }

    /**
     * Constructs a new vector with the given Double3 object.
     * The vector cannot be the zero vector.
     *
     * @param xyz the Double3 object containing the x, y, and z components of the vector
     * @throws IllegalArgumentException if the vector is the zero vector
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("ERROR: The vector is zero");
    }

    /**
     * Returns the square of the length of this vector.
     *
     * @return the square of the length of this vector
     */
    public double lengthSquared() {
        return xyz.d1 * xyz.d1
                + xyz.d2 * xyz.d2
                + xyz.d3 * xyz.d3;
    }

    /**
     * Returns the sum of this vector and the given vector.
     *
     * @param other the vector to add to this vector
     * @return the sum of this vector and the given vector
     */
    public Vector add(Vector other) {
        return new Vector(this.xyz.add(other.xyz));
    }
    public Vector subtract(Vector other) {
        return new Vector(this.xyz.subtract(other.xyz));
    }

    /**
     * Returns a new vector that is the result of scaling this vector by the given factor.
     *
     * @param a the factor to scale this vector by
     * @return a new vector that is the result of scaling this vector by the given factor
     */
    public Vector scale(double a) {
        return new Vector(this.xyz.scale(a));
    }

    /**
     * Returns the length of this vector.
     *
     * @return the length of this vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Returns the dot product of this vector and the given vector.
     *
     * @param v3 the vector to dot product with this vector
     * @return the dot product of this vector and the given vector
     */
    public double dotProduct(Vector v3) {
        Point d = new Point(this.xyz.product(v3.xyz));
        return d.xyz.d1 + d.xyz.d2 + d.xyz.d3;
    }

    /**
     * Returns the cross product of this vector and the given vector.
     *
     * @param v2 the vector to cross product with this vector
     * @return the cross product of this vector and the given vector
     * <p>
     * v1 x v2 = (y1 * z2 - z1 * y2, z1 * x2 - x1 * z2, x1 * y2 - y1 * x2)
     */
    public Vector crossProduct(Vector v2) {
        return new Vector(this.xyz.d2 * v2.xyz.d3 - this.xyz.d3 * v2.xyz.d2,
                this.xyz.d3 * v2.xyz.d1 - this.xyz.d1 * v2.xyz.d3,
                this.xyz.d1 * v2.xyz.d2 - this.xyz.d2 * v2.xyz.d1);
    }

    /**
     * Returns a new vector in the direction of this vector with length 1.
     *
     * @return a new vector in the direction of this vector with length 1
     */
    public Vector normalize() {
        double len = this.length();
        return new Vector(this.xyz.reduce(len));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return xyz.equals(vector.xyz);
    }

    @Override
    public String toString() {
        return "Vector{}";
    }
}
