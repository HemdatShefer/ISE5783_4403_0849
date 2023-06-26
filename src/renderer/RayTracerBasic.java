package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Basic implementation of a ray tracer that traces rays in a scene and computes colors.
 */
public class RayTracerBasic extends RayTracerBase {

    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * Constructor.
     *
     * @param scene the scene
     * @throws NullPointerException if scene is null
     */
    public RayTracerBasic(Scene scene) throws NullPointerException {
        super(scene);
    }

    /**
     * Calculate the color of some point.
     *
     * @param intersection the geoPoint to calculate the color
     * @param ray          the ray to calculate the color
     * @return the color of the point
     */
    private Color calcColor(Intersectable.GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity().add(intersection.geometry.getEmission())
                .add(calcLocalEffects(intersection, ray));
    }

    /**
     * Calculate the local effects of the point.
     *
     * @param intersection the geoPoint to calculate the local effects
     * @param ray          the ray to calculate the local effects
     * @return the local effects of the point
     */
    private Color calcLocalEffects(Intersectable.GeoPoint intersection, Ray ray) {
        // The direction of the ray.
        Vector dir = ray.getDir();

        // Calculate the normal vector at the intersection point.
        Vector normal = intersection.geometry.getNormal(intersection.point);

        // Dot product of the normal and the ray's direction.
        double nv = alignZero(normal.dotProduct(dir));

        Material material = intersection.geometry.getMaterial();

        // If the ray is parallel to the surface, return black color.
        if (nv == 0) {
            return Color.BLACK;
        }

        // Extract the properties of the intersected material.
        int nShininess = intersection.geometry.getMaterial().nShininess;
        Double3 kd = intersection.geometry.getMaterial().kD;
        Double3 ks = intersection.geometry.getMaterial().kS;

        // Initialize the color as black.
        Color color = Color.BLACK;

        // Loop over all light sources in the scene.
        for (LightSource lightSource : scene.lights) {
            // Calculate the direction of the light from the light source to the intersection point.
            Vector dirLight = lightSource.getL(intersection.point);

            // Calculate dot product of the normal and the light's direction.
            double nl = alignZero(normal.dotProduct(dirLight));

            // If the light is coming from the same side as the ray (the outside of the surface)
            if (nl * nv > 0) {
                // Check if the light source is not blocked by another object.
                if (unshaded(lightSource, dirLight, normal, intersection)) {
                    // Get the light intensity of the light source.
                    Color lightIntensity = lightSource.getIntensity(intersection.point);

                    // Add the diffuse and specular components of the light source.
                    color = color.add(calcDiffusive(kd, dirLight, normal, lightIntensity),
                            calcSpecular(ks, dirLight, normal, dir, nShininess,
                                    lightIntensity));
                }
            }
        }

        // Return the calculated color.
        return color;
    }


    private Color calcSpecular(Double3 ks, Vector dirLight, Vector normal, Vector dir,
                               int nShininess, Color lightIntensity) {
        // Calculate the direction that a perfectly reflected ray of light would take given a surface normal.
        Vector reflectedDir = dirLight.add(normal.scale(-2 * dirLight.dotProduct(normal)));

        // Calculate dot product of the reflected direction and the negative direction of the original ray.
        double t = alignZero(-reflectedDir.dotProduct(dir));

        // If the dot product is greater than 0, calculate the specular contribution to the color,
        // otherwise return black color.
        return t > 0 ? lightIntensity.scale(ks.scale(Math.pow(t, nShininess))) : Color.BLACK;
    }


    private Color calcDiffusive(Double3 kd, Vector dirLight, Vector normal, Color lightIntensity) {
        // Calculate dot product of the light direction and the surface normal.
        double s = alignZero(dirLight.dotProduct(normal));

        // Make sure that the dot product is positive.
        if (s < 0) {
            s *= -1;
        }

        // Scale the light intensity by the product of the diffuse reflection coefficient and the dot product.
        return lightIntensity.scale(kd.scale(s));
    }


    @Override
    public Color traceRay(Ray ray) {
        // Find all intersections of the ray with the scene.
        List<Intersectable.GeoPoint> intersectPoints = scene.geometries.findGeoIntersections(ray);

        // If there are no intersections, return the background color of the scene.
        if (intersectPoints == null) {
            return scene.background;
        }

        // Find the closest intersection point to the origin of the ray.
        Intersectable.GeoPoint closestPoint = ray.findClosestGeoPoint(intersectPoints);

        // Calculate the color of the closest intersection point.
        return calcColor(closestPoint, ray);
    }

    /**
     * @param ray
     * @return
     */
    @Override
    public Color traceRay(List<Ray> ray) {
        return null;
    }


    /**
     * Finding Light Beam Cutting - Finding Shadow.
     *
     * @param light    The source of light
     * @param dirLight Direction of light
     * @param normal   The normal vector
     * @param geopoint GeoPoint
     * @return Whether intersections were found or not (whether there is a shadow or not)
     */
    private boolean unshaded(LightSource light, Vector dirLight, Vector normal, Intersectable.GeoPoint geopoint) {
        // Reverse the direction of the light.
        Vector lightDirection = dirLight.scale(-1);

        // Calculate a small offset to avoid self-shadowing.
        Vector delta = normal.scale(normal.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);

        // Apply the offset to the point.
        Point point = geopoint.point.add(delta);

        // Create a ray from the point towards the light source.
        Ray lightRay = new Ray(point, lightDirection);

        // Find all intersections of the ray with the scene.
        List<Intersectable.GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        // If there are no intersections, the light is not blocked.
        if (intersections == null) {
            return true;
        }

        double lightDistance = light.getDistance(geopoint.point);

        // If all intersection points are beyond the light source, the light is not blocked.
        return !(intersections.stream().allMatch(
                geoPoint -> alignZero(geoPoint.point.distance(geoPoint.point) - lightDistance) <= 0));
    }

    /**
     * Calculate how much light from a given light source transparently reaches a point.
     *
     * @param light    the light source
     * @param dirLight the direction of the light
     * @param normal   the normal vector at the point
     * @param geoPoint the point to calculate the transparency
     * @return a vector representing how much each color component (red, green, blue) of the light reaches the point
     */
    private Double3 transparency(LightSource light, Vector dirLight, Vector normal, Intersectable.GeoPoint geoPoint) {
        // Reverse the direction of the light to get the direction from the point to the light source.
        Vector lightDirection = dirLight.scale(-1);

        // Calculate a small offset to avoid self-shadowing.
        Vector delta = normal.scale(normal.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);

        // Apply the offset to the point.
        Point point = geoPoint.point.add(delta);

        // Create a ray from the point towards the light source.
        Ray lightRay = new Ray(geoPoint.point, lightDirection, normal);

        // Find all intersections of the ray with the scene.
        List<Intersectable.GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        // If there are no intersections, the light is not blocked at all, return full transparency.
        Double3 result = Double3.ONE;
        if (intersections == null) {
            return result;
        }

        double lightDistance = light.getDistance(point);

        // For each intersection
        for (Intersectable.GeoPoint gp : intersections) {
            // If the intersection point is closer to the point than the light source
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
                // Multiply the current transparency by the transparency of the intersected object.
                result = gp.geometry.getMaterial().kT.product(result);

                // If the transparency is very low, return zero transparency to save calculations.
                if (result.lowerThan(MIN_CALC_COLOR_K)) {
                    return Double3.ZERO;
                }
            }
        }

        // Return the calculated transparency.
        return result;
    }

}

