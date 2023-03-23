package primitives;

/**
 * A point in a 3D Euclidean space.
 */
public abstract class Point {
    /**
     * The 3D coordinates of the point.
     */
    private final Double3 coords;
    protected double x;
    protected double y;
    protected double z;


    /**
     * Constructs a new Point object with the specified coordinates.
     *
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @param z The Z coordinate.
     */
    public Point(double x, double y, double z) {
        this.coords = new Double3(x, y, z);
    }

    /**
     * Constructs a new Point object with the specified coordinates.
     *
     * @param coords The coordinates of the point as a Double3 object.
     */
    Point(Double3 coords) {
        this.coords = coords;
    }

    /**
     * Calculates the vector from this point to another point.w
     *
     * @param other The other point.
     * @return The vector from this point to the other point.
     */
    public Vector subtract(Point other) {
        return new Vector(coords.subtract(other.coords));
    }

    /**
     * Adds a vector to this point and returns a new point.
     *
     * @param vector The vector to add.
     * @return A new point which is the result of adding the vector to this point.
     */
    public Point add(Vector vector) {
        return new Point(coords.add(vector.getHead())) {
            @Override
            public Vector getNormal(Point point) {
                return null;
            }
        };
    }

    /**
     * Calculates the squared distance between this point and another point.
     *
     * @param other The other point.
     * @return The squared distance between this point and the other point.
     */
    public double distanceSquared(Point other) {
        return coords.subtract(other.coords).lengthSquared();
    }

    /**
     * Calculates the distance between this point and another point.
     *
     * @param other The other point.
     * @return The distance between this point and the other point.
     */
    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    /**
     * Returns a string representation of this point in the format "(x, y, z)".
     *
     * @return A string representation of this point.
     */
    @Override
    public String toString() {
        return coords.toString();
    }

    public abstract Vector getNormal(Point point);
}
