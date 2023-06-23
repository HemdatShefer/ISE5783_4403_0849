package primitives;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a 3D Vector with Cartesian components.
 * A Vector is defined by three components (x, y, z) and originates from the point (0, 0, 0).
 */
public class Vector extends Point {

    /**
     * Constructs a Vector with the given components.
     *
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @param z The z component of the vector.
     */
    public Vector(double x, double y, double z) throws IllegalArgumentException
    {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
        {
            throw new IllegalArgumentException("Can't create vector zero !");
        }
    }

    /**
     * Constructs a Vector with the given Cartesian coordinates.
     *
     * @param coordinates The Cartesian coordinates of the vector.
     */
    public Vector(Double3 coordinates)throws IllegalArgumentException
    {
        super(coordinates);
        if (coordinates.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("ERROR: The vector is zero");
        }
    }
    /**
     * Calculate the matrix rotation.
     * @param axis the given axis to calculate the matrix
     * @param theta the angle to rotate
     * @return the matrix rotation
     */
    public static List<Vector> getMatrixRotation(Vector axis, double theta) {
        double radian = -theta * Math.PI / 180;
        double x = axis.getX();
        double y = axis.getY();
        double z = axis.getZ();

        double cos = Util.alignZero(Math.cos(radian));
        double sin = Util.alignZero(Math.sin(radian));
        double cosMinus = (1 - cos);

        Vector rotateX = new Vector(x * x * cosMinus + cos,
                x * y * cosMinus - z * sin,
                x * z * cosMinus + y * sin);
        Vector rotateY = new Vector(y * x * cosMinus + z * sin,
                y * y * cosMinus + cos,
                y * z * cosMinus - x * sin);
        Vector rotateZ = new Vector(z * x * cosMinus - y * sin,
                z * y * cosMinus + x * sin,
                z * z * cosMinus + cos);
        return Arrays.asList(rotateX, rotateY, rotateZ);
    }

    /**
     * Returns the dot product of this vector with another vector.
     *
     * @param v3 The other vector.
     * @return The dot product.
     */
    public double dotProduct(Vector v3) {
        Point d = new Point(this.xyz.product(v3.xyz));
        return d.xyz.d1 + d.xyz.d2 + d.xyz.d3;
    }

    /**
     * Returns the cross product of this vector with another vector.
     *
     * @param v2 The other vector.
     * @return The cross product as a new vector.
     */
    public Vector crossProduct(Vector v2) {
        return new Vector(this.xyz.d2 * v2.xyz.d3 - this.xyz.d3 * v2.xyz.d2,
                this.xyz.d3 * v2.xyz.d1 - this.xyz.d1 * v2.xyz.d3,
                this.xyz.d1 * v2.xyz.d2 - this.xyz.d2 * v2.xyz.d1);
    }

    /**
     * Calculate unit vector from the current vector.
     * @return unit vector from the current one
     */
    public Vector normalize() {
        return new Vector(xyz.reduce(length()));
    }

    /**
     * Multiply the current vector by the matrix.
     * @param matrix the matrix to multiply the current vector
     * @return the result vector after multiply by the matrix
     * @throws IllegalArgumentException if the matrix size isn't 3x3
     */
    public Vector matrixProduct(List<Vector> matrix) throws IllegalArgumentException {
        if (matrix.size() != 3) {
            throw new IllegalArgumentException("ERROR: Matrix must be 3x3");
        }
        double[] xyz = new double[3];
        for (int i = 0; i < 3; i++) {
            xyz[i] = dotProduct(matrix.get(i));
        }
        return new Vector(xyz[0], xyz[1], xyz[2]);
    }

    /**
     * Adds another vector to this vector.
     *
     * @param other The vector to be added.
     * @return The resulting vector after addition.
     */
    public Vector add(Vector other) {
        return new Vector(this.xyz.add(other.xyz));
    }

    /**
     * Subtracts another vector from this vector.
     *
     * @param other The vector to be subtracted.
     * @return The resulting vector after subtraction.
     */
    public Vector subtract(Vector other) {
        return new Vector(this.xyz.subtract(other.xyz));
    }

    /**
     * Scales this vector by a scalar value.
     *
     * @param scalar The scalar value.
     * @return The scaled vector.
     */
    public Vector scale(double scalar) {
        return new Vector(this.xyz.scale(scalar));
    }

    /**
     * Calculates the squared length of this vector.
     *
     * @return The squared length.
     */
    public double lengthSquared() {
        return xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3;
    }

    /**
     * Calculates the length of this vector.
     *
     * @return The length.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
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
        return "->" + super.toString();
    }

    /**
     * Check if the vector is the same normal.
     * @param vector the vector to check
     * @return true if the current length and the vector param are parallel and length equals 1
     */
    public boolean isSameNormal(Vector vector) {
        if (!(Util.isZero(length() - 1) && Util.isZero(vector.length() - 1))) {
            return false;
        }
        return equals(vector) || equals(vector.scale(-1));
    }
}
