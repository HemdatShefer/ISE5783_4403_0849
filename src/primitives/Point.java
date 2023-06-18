package primitives;

import java.util.Objects;

/**
 * This class represents a point in 3D space using Cartesian coordinates.
 * A point is defined by its x, y, and z coordinates.
 * The class provides basic operations for working with points, such as adding a vector to a point,
 * subtracting one point from another to get a vector, computing the distance between two points,
 * and checking for equality between points.
 * /
 * public class Point {
 * /*
 * The Cartesian coordinates of the point.
 */
public class Point {
    public static final Point ZERO = new Point(0, 0, 0);
    protected Double3 xyz;

    /**
     * Constructs a new point with the given x, y, and z coordinates.
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @param z the z coordinate of the point
     */
    public Point(double x, double y, double z) {
        this(new Double3(x, y, z));
    }

    /**
     * Constructs a new point with the given Cartesian coordinates.
     *
     * @param xyz the Cartesian coordinates of the point
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Adds the given vector to this point and returns the resulting point.
     *
     * @param vector the vector to add to this point
     * @return the resulting point after adding the vector
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * Subtracts the given point from this point and returns the resulting vector.
     *
     * @param other the point to subtract from this point
     * @return the resulting vector after subtracting the other point
     */
    public Vector subtract(Point other) {
        return new Vector(xyz.subtract(other.xyz));
    }

    /**
     * Computes the squared distance between this point and the given point.
     *
     * @param other the other point to compute the distance to
     * @return the squared distance between this point and the other point
     */
    public double distanceSquared(Point other) {
        return (this.xyz.d1 - other.xyz.d1) * (this.xyz.d1 - other.xyz.d1)
                + (this.xyz.d2 - other.xyz.d2) * (this.xyz.d2 - other.xyz.d2)
                + (this.xyz.d3 - other.xyz.d3) * (this.xyz.d3 - other.xyz.d3);
    }

    /**
     * Computes the distance between this point and the given point.
     *
     * @param other the other point to compute the distance to
     * @return the distance between this point and the other point
     */
    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    /**
     * Compares this point to the given object for equality.
     * Two points are considered equal if they have the same Cartesian coordinates.
     *
     * @param o the object to compare to this point
     * @return true if the object is a point with the same coordinates as this point, false otherwise
     */
        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Point point = (Point) o;
                return xyz.equals(point.xyz);
        }

        @Override
        public int hashCode() {
                return Objects.hash(xyz);
        }

        /**
     * Returns a string representation of this point.
     *
     * @return a string representation of this point
     */
    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }


    public double getX() {
        return 0;
    }

}