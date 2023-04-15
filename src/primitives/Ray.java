package primitives;

import java.util.Objects;

/**

 This class represents a ray in 3D space, which consists of an origin point and a direction vector.

 The origin point is the starting point of the ray, and the direction vector is the direction in which

 the ray extends infinitely.
 /
 public class Ray {
 /*

 The origin point of the ray.
 */
public class Ray {
private Point p0;
/**

 The direction vector of the ray.
 */
private Vector dir;
/**

 Constructs a new ray with the given origin point and direction vector.
 The direction vector is automatically normalized.
 @param point the origin point of the ray
 @param vector the direction vector of the ray
 */
public Ray(Point point, Vector vector) {
        p0 = point;
        dir = vector.normalize();
        }
/**

 Compares this ray to the given object for equality.
 Two rays are considered equal if they have the same origin point and direction vector.
 @param obj the object to compare to this ray
 @return true if the object is a ray with the same origin point and direction vector as this ray, false otherwise
 */
@Override
public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
        && this.p0.equals(other.p0)
        && this.dir.equals(other.dir);
        }
/**

 Returns a string representation of this ray.
 @return a string representation of this ray
 */
@Override
public String toString() {
        return "Ray{" +
        "p0=" + p0 +
        ", dir=" + dir +
        '}';
        }

        public Point getOrigin() {
                return p0;
        }
        public Vector getDirection() {
                return dir;
        }
}