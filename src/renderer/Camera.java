package renderer;

import primitives.*;

import static primitives.Util.isZero;

/**
 * This class represents a camera in 3D space, with relevant fields including
 * its location (as a Point), and three direction vectors for the camera's forward,
 * up, and right directions. The class also includes fields for the height, width,
 * and distance of the plane view, relative to the camera.
 *
 * The camera can construct rays through a pixel on the plane view and provides getter methods
 * for its properties. The camera's view plane size and distance can be modified with chaining
 * methods that return the camera object itself.
 *
 * Also, it includes the method constructRayThroughPixel which constructs a ray passing through
 * a certain pixel on the view plane considering the screen distance and the screen size.
 *
 * Note: This class assumes that the up and forward vectors are orthogonal.
 */
public class Camera {
    private Point location;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double width;
    private double height;
    private double distance;

    /**
     * Constructs a new camera object with the specified location and direction vectors.
     * The direction vectors must be orthogonal and normalized, otherwise an IllegalArgumentException is thrown.
     * The orthogonal vectors are then used to calculate the right direction vector.
     *
     * @param location the location of the camera in 3D space
     * @param vTo the forward direction vector of the camera
     * @param vUp the up direction vector of the camera
     * @throws IllegalArgumentException if the direction vectors are not orthogonal and normalized
     */

    public Camera(Point location, Vector vTo, Vector vUp) throws IllegalArgumentException {
        // Make sure the direction vectors are orthogonal and normalized
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("Up vector is not orthogonal to the forward vector");
        }
        this.location = location;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        this.vRight = this.vTo.crossProduct(this.vUp).normalize();
    }

    /**
     * Sets the width and height of the plane view of the camera, and returns the camera itself.
     * @param width the width of the plane view
     * @param height the height of the plane view
     * @return the camera object itself
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Sets the distance between the camera and the plane view, and returns the camera itself.
     * @param distance the distance between the camera and the plane view
     * @return the camera object itself
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Constructs a ray through a pixel on the plane view considering the screen distance, width, and height.
     * It calculates the center point of the pixel and then constructs the ray passing from the camera location
     * through that point.
     *
     * @param nX The total number of pixels in the x-direction (width)
     * @param nY The total number of pixels in the y-direction (height)
     * @param i The pixel's index in the x-direction
     * @param j The pixel's index in the y-direction
     * @return A ray passing through the given pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        // Implementation
        return null;
    }

    /**
     * Returns a string representation of the camera object.
     * @return a string representation of the camera object
     */
    @Override
    public String toString() {
        return "Camera{" +
                "location=" + location +
                ", vTo=" + vTo +
                ", vUp=" + vUp +
                ", vRight=" + vRight +
                ", width=" + width +
                ", height=" + height +
                ", distance=" + distance +
                '}';
    }
    public Ray constructRayThroughPixel(int Nx, int Ny, int i, int j, double screenDistance, double screenWidth, double screenHeight) {
        Point Pc = location.add(vTo.scale(screenDistance));

        double Ry = screenHeight/Ny;
        double Rx = screenWidth/Nx;

        double yi = ((i - Ny/2.0)*Ry + Ry/2.0);
        double xj = ((j - Nx/2.0)*Rx + Rx/2.0);

        Point Pij = Pc;

        if (!isZero(xj)) {
            Pij = Pij.add(vRight.scale(xj));
        }

        if (!isZero(yi)) {
            Pij = Pij.subtract(vUp.scale(yi)); // Pij.add(vUp.scale(-yi))
        }

        Vector Vij = Pij.subtract(location);

        return new Ray(location, Vij.normalize());
    }


    /**
     * Returns the forward direction vector of the camera.
     * @return the forward direction vector of the camera
     */
    public Vector getVTo() {
        return vTo;
    }

    /**
     * Returns the up direction vector of the camera.
     * @return the up direction vector of the camera
     */
    public Vector getVUp() {
        return vUp;
    }

    /**
     * Returns the right direction vector of the camera.
     * @return the right direction vector of the camera
     */
    public Vector getVRight() {
        return vRight;
    }

    /**
     * Returns the width of the plane view of the camera.
     * @return the width of the plane view of the camera
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns the height of the plane view of the camera.
     * @return the height of the plane view of the camera
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the distance between the camera and the plane view.
     * @return the distance between the camera and the plane view
     */
    public double getDistance() {
        return distance;
    }
    }