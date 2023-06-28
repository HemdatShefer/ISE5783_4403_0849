package renderer;


import static primitives.Util.alignZero;

import geometries.Intersectable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Pair;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.Wrapper;
import scene.Scene;

/**
 * Implement RayTracerBase to handle all the ray trace.
 */
public class RayTracerBasic extends RayTracerBase {

    private static final double DELTA = 0.1;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private boolean isAdaptiveGrid;
    private int maxLevel;

    /**
     * Constructor.
     *
     * @param scene the scene
     * @throws NullPointerException if scene is null
     */
    public RayTracerBasic(Scene scene) throws NullPointerException {
        super(scene);
        this.isAdaptiveGrid = false;
        this.maxLevel = 1;
    }

    /**
     * Calculate the local effects of the point.
     *
     * @param intersection the geoPoint to calculate the local effects
     * @param ray          the ray to calculate the local effects
     * @param k            max level of color
     * @return the local effects of the point
     */
    private Color calcLocalEffects(Intersectable.GeoPoint intersection, Ray ray, Double3 k) {
        // הגדרת משתנה שיאפשר לנו לדעת אם האור מאחורי הנקודה
        boolean isBehindThePoint;

        // צובע ראשוני שחור שאליו נוסיף את כל ההשפעות
        Color color = Color.BLACK;

        // קוטב הקרן
        Vector dir = ray.getDir();

        // קוטב הנורמלי לגיאומטריה בנקודת ההתנגשות
        Vector normal = intersection.geometry.getNormal(intersection.point);

        // חומר הגיאומטריה שעליה הקרן מתנפקת
        Material material = intersection.geometry.getMaterial();

        // מחשב את המכפלה הסקלרית של הנורמל לקוטב הקרן
        double nv = alignZero(normal.dotProduct(dir));

        // אם המכפלה הסקלרית שווה 0, כלומר הקרן מקבילה למישור, מחזירים את הצבע השחור
        if (nv == 0) {
            return color;
        }

        // עוברים על כל מקורות האור בסצנה
        for (LightSource lightSource : scene.lights) {
            // מחשב את הקוטב של האור
            Vector dirLight = lightSource.getL(intersection.point);

            // מחשב את המכפלה הסקלרית של הנורמל לקוטב האור
            double nl = alignZero(normal.dotProduct(dirLight));

            // בודק אם האור מאחורי הנקודה
            isBehindThePoint = nl * nv > 0;

            // מחשב את השקיפות של האור
            Double3 ktr = transparency(lightSource, dirLight, normal, intersection);

            // אם האור מאחורי הנקודה והשקיפות גדולה ממינימום מסוים
            if (isBehindThePoint) {
                if (!k.product(ktr).lowerThan(MIN_CALC_COLOR_K)) {
                    // מחשב את העצמה של האור
                    Color intensity = lightSource.getIntensity(intersection.point).scale(ktr);

                    // מוסיף את ההשפעות של האור לצבע הכולל
                    color = color.add(calcDiffusive(material, dirLight, normal, intensity),
                            calcSpecular(material, dirLight, normal, dir, intensity));
                }
            }
        }

        // מחזיר את הצבע הסופי שמייצג את ההשפעות המקומיות של האור
        return color;
    }

    /**
     * Calculate the specular color.
     *
     * @param material       the material
     * @param dirLight       the light direction
     * @param normal         the normal
     * @param dir            the ray direction
     * @param lightIntensity the light intensity
     * @return the specular color
     */
    private Color calcSpecular(Material material, Vector dirLight, Vector normal, Vector dir,
                               Color lightIntensity) {
        // מחשב את הקטב שמייצג את הכיוון של האור המושתקף
        Vector reflectedDir = dirLight.add(normal.scale(-2 * dirLight.dotProduct(normal)));

        // מחשב את המכפלה הסקלרית של הקוטב של האור המושתקף עם קוטב הקרן
        double t = alignZero(-reflectedDir.dotProduct(dir));

        // אם t > 0 מחזירים את העצמה של האור מוכפלה בקפיצות השתקפות של החומר
        // ומועלה בחזקת הזוהר של החומר, אחרת מחזירים שחור
        return t > 0 ? lightIntensity.scale(material.kS.scale(Math.pow(t, material.nShininess)))
                : Color.BLACK;
    }

    /**
     * Calculate the diffusive effects of the point.
     *
     * @param material       the material of the point
     * @param dirLight       the light direction
     * @param normal         the normal of the point
     * @param lightIntensity the light intensity
     * @return the diffusive effects of the point
     */
    private Color calcDiffusive(Material material, Vector dirLight, Vector normal,
                                Color lightIntensity) {
        double s = Math.abs(alignZero(dirLight.dotProduct(normal)));
        return lightIntensity.scale(material.kD.scale(s));
    }

    @Override
    public Color traceRay(List<Ray> rays) {
        // מאתחלים את התוצאה
        Color result;
        // בדיקה אם המצלמה היא מצלמה שמתאימה את הרזולוציה באופן דינאמי ואם יש יותר מ-4 קרניים
        if (isAdaptiveGrid && rays.size() > 4) {
            // מאתחלים את העטיפה של הצבע כצבע שחור
            Wrapper<Color> colorWrapper = new Wrapper<>(Color.BLACK);
            // מאתחלים מפה שבה המפתח הוא הקרן והערך הוא הצבע של הפיקסל שאליו היא מצביעה
            Map<Ray, Color> map = new HashMap<>();
            // מפעילים פונקציה רקורסיבית שמחשבת את צבע הפיקסל של כל קרן
            traceRayCube(colorWrapper, rays, map);
            // מחשבים את הצבע הממוצע של הפיקסל
            result = colorWrapper.variable.reduce(rays.size());
        } else {
            // יצירת רשימת צבעים על ידי חישוב הצבע של כל קרן באופן מקביל
            List<Color> colors = rays.stream().map(this::traceRay).collect(Collectors.toList());
            // מחשבים את הצבע הממוצע של הפיקסל
            result = Color.average(colors, colors.size());
        }
        // מחזירים את הצבע שחושב לפיקסל
        return result;
    }

    @Override
    public Color traceRay(Ray ray) {
        // מאתחלים את הצבע
        Color color;
        // מאתחלים רשימה של נקודות חיתוך עם הגאומטריה
        List<Intersectable.GeoPoint> intersectPoints;
        // מאתחלים את הנקודה הקרובה ביותר
        Intersectable.GeoPoint closestPoint;

        // מוצאים את נקודות החיתוך של הקרן עם הגאומטריה
        intersectPoints = scene.geometries.findGeoIntersections(ray);
        // אם אין נקודות חיתוך, הצבע של הפיקסל הוא צבע הרקע
        if (intersectPoints == null) {
            color = scene.background;
        } else {
            // אם יש נקודות חיתוך, מוצאים את הנקודה הקרובה ביותר
            closestPoint = ray.findClosestGeoPoint(intersectPoints);
            // מחשבים את הצבע של הפיקסל בהתאם לנקודת החיתוך הקרובה ביותר
            color = calcColor(closestPoint, ray);
        }

        // מחזירים את הצבע שחושב לפיקסל
        return color;
    }


    /**
     * Calculate the color of the point.
     *
     * @param colorWrapper the color wrapper for hold the color
     * @param allRays      the rays to calculate the color
     * @param map          the map to hold the color and avoid calculate for the same ray
     */
    public void traceRayCube(Wrapper<Color> colorWrapper, List<Ray> allRays, Map<Ray, Color> map)
    {
        // "color" will hold the color computed for a given ray
        Color color = null;
        // "rays" will be used to hold the current list of rays being processed
        List<Ray> rays;
        // "n" will be the square root of the number of rays (since rays are arranged in a grid)
        // "level" will hold the current level of subdivision in the adaptive super-sampling process
        int n, level;
        // "pair" will hold the popped value from the stack, containing a list of rays and the associated subdivision level
        Pair<List<Ray>, Integer> pair;
        // A stack to keep track of all subdivisions, each entry contains a list of rays and their subdivision level
        Stack<Pair<List<Ray>, Integer>> stack = new Stack<>();
        // The initial list of rays and their subdivision level are added to the stack
        stack.add(new Pair<>(allRays, maxLevel));

        // Until the stack is empty, there are still rays that need to be processed
        while (!stack.isEmpty()) {
            // Pop the top value from the stack, which contains the rays to be processed and their subdivision level
            pair = stack.pop();
            rays = pair.first;
            level = pair.second;

            // If subdivision level is <= 1, it means that rays can't be subdivided further
            if (level <= 1) {
                // Loop through the rays and calculate their color by calling the `traceRay` function
                for (Ray ray : rays) {
                    colorWrapper.variable = colorWrapper.variable.add(traceRay(ray));
                }
                // Skip the rest of the loop and continue with the next iteration
                continue;
            }

            // Calculate the square root of the number of rays (assuming that rays are arranged in a square grid)
            n = (int)Math.sqrt(rays.size());
        /* Indexes of: topLeft, topRight, bottomLeft, bottomRight
           these are the rays at the corners of the ray grid for the current level of subdivision */
            int[] indexes = {0, (n - 1), (n * (n - 1)), (n * n - 1)};

            // Create a list to store the colors of the corner rays
            List<Color> cubeColors = new ArrayList<>(4);

            // For each corner ray
            for (int index : indexes) {
                // Get the ray at the current index
                Ray ray = rays.get(index);
                // Check if the color of the ray is already calculated and stored in the map
                if (map.containsKey(ray)) {
                    // If yes, get the color from the map
                    color = map.get(ray);
                } else {
                    // If no, calculate the color of the ray using `traceRay` function and store it in the map for future reference
                    color = traceRay(ray);
                    map.put(ray, color);
                }
                // Add the color of the ray to the list of corner colors
                cubeColors.add(color);
            }

            // If there are only 4 rays left or all corner colors are equal
            if (rays.size() <= 4 || Color.allEquals(cubeColors)) {
                // Add the color scaled by the number of rays to the color wrapper
                colorWrapper.variable = colorWrapper.variable.add(color.scale(n * n));


            } else {
                // If not, the grid of rays needs to be subdivided further
                for (int row = 0; row < n; row += n / 2) {
                    for (int column = 0; column < n; column += n / 2) {
                        // Create a list to store the rays in the current sub-grid
                        List<Ray> rayList = new LinkedList<>();
                        for (int i = row; i < row + n / 2; i++) {
                            for (int j = column; j < column + n / 2; j++) {
                                // Add the rays in the current sub-grid to the list
                                rayList.add(rays.get(i * n + j));
                            }
                        }
                        // Push the list of rays in the current sub-grid and the next level of subdivision to the stack
                        stack.push(new Pair<>(rayList, level - 1));
                    }
                }
            }
        }
    }

    /**
     * Finding Light Beam Cutting - Finding Shadow.
     * @param light The source of light
     * @param dirLight Direction of light
     * @param normal The normal vector
     * @param geoPoint GeoPoint
     * @return Whether intersections were found or not (whether there is a shadow or not)
     */
    private Double3 transparency(LightSource light, Vector dirLight, Vector normal,
                                 Intersectable.GeoPoint geoPoint) {
        // Reverse the light direction as we want to travel along the ray from the light source to the point
        Vector lightDirection = dirLight.scale(-1);
        // Delta is a small vector used to move the origin of the shadow ray slightly off the intersection point
        // to avoid self-shadowing due to numerical inaccuracies. If the normal and light direction are in the same
        // direction, move slightly along the normal. Otherwise, move slightly in the opposite direction.
        Vector delta = normal.scale(normal.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        // A new point is created by moving slightly off the intersection point
        Point point = geoPoint.point.add(delta);
        // The light ray is a new ray object that is cast from the intersection point (offset by delta), along the direction towards the light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, normal);
        // Intersections are computed between the shadow ray and all the geometries in the scene
        List<Intersectable.GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        // The initial transparency is set to 1 (fully transparent)
        Double3 result = Double3.ONE;
        // If there are no intersections, then the light source isn't blocked and the method returns full transparency
        if (intersections == null) {
            return result;
        }
        // The distance from the light source to the intersection point is computed
        double lightDistance = light.getDistance(point);
        // For each intersection along the shadow ray
        for (Intersectable.GeoPoint gp : intersections) {
            // If the intersection is closer to the light source than the original point,
            // it means the light is blocked, so the transparency is decreased.
            // The transparency is decreased proportionally to the transparency coefficient of the intersected object material
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
                result = gp.geometry.getMaterial().kT.product(result);
                // If transparency drops below a certain threshold, it is set to zero and returned,
                // as the light is effectively fully blocked
                if (result.lowerThan(MIN_CALC_COLOR_K)) {
                    return Double3.ZERO;
                }
            }
        }
        // After all intersections are processed, the resulting transparency is returned
        return result;
    }

    /**
     * Calculate the color of the scene.
     * @param intersection geo point intersections with the ray
     * @param level of the recursion
     * @param ray ray
     * @param k factor of the max value of the level
     * @return color of the geometry
     */
    private Color calcColor(Intersectable.GeoPoint intersection, Ray ray, int level, Double3 k) {
        if (intersection == null) {
            return scene.background;
        }
        Color color = intersection.geometry.getEmission();
        color = color.add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * Calculate the color of some point.
     * @param intersection the geoPoint to calculate the color
     * @param ray the ray to calculate the color
     * @return the color of the point
     */
    private Color calcColor(Intersectable.GeoPoint intersection, Ray ray) {
        return calcColor(intersection,
                         ray,
                         MAX_CALC_COLOR_LEVEL,
                         new Double3(DELTA)).add(scene.ambientLight.getIntensity());
    }

    /**
     * Construct a reflected ray from the geometry.
     * @param normal normal vector of the point on the geometry
     * @param point on the geometry
     * @param ray from the geometry
     * @return new reflected ray
     */
    private Ray constructReflectedRay(Vector normal, Point point, Ray ray) {
        Vector dir = ray.getDir();
        Vector normalDir = normal.scale(-2 * dir.dotProduct(normal));
        Vector result = dir.add(normalDir);
        /* use the constructor with 3 arguments to move the head */
        return new Ray(point, result, normal);
    }

    /**
     * Calculate the global effects of the scene.
     * @param geoPoint point on the geometry
     * @param ray from the geometry
     * @param level of recursion
     * @param k max value level
     * @return color
     */
    private Color calcGlobalEffects(Intersectable.GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        // צובע ראשוני שחור שאליו נוסיף את כל ההשפעות
        Color color = Color.BLACK;

        // חומר הגיאומטריה שעליה הקרן מתנפקת
        Material material = geoPoint.geometry.getMaterial();

        // יוצר מקדם של השתקפות החומר שמתאים לרמת הקינון של הקרן
        Double3 kR = material.kR.product(k);

        // מציאת וקטור הנורמלי לגיאומטריה בנקודת המסיחה
        Vector normal = geoPoint.geometry.getNormal(geoPoint.point);

        // אם ההשפעה של השתקפות החומר היא לא קטנה מהמינימום המוגדר
        if (!kR.lowerThan(MIN_CALC_COLOR_K)) {
            // בניית הקרן המשתקפת
            Ray reflectedRay = constructReflectedRay(normal, geoPoint.point, ray);

            // מציאת הנקודה הקרובה ביותר שעליה הקרן המשתקפת מתנפקת
            Intersectable.GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);

            // הוספה של צבע ההשפעה של הקרן המשתקפת לצבע הכולל
            color = color.add(calcColor(reflectedPoint,
                    reflectedRay,
                    level - 1,
                    kR).scale(material.kR));
        }

        // יוצר מקדם של שקיפות החומר שמתאים לרמת הקינון של הקרן
        Double3 kT = material.kT.product(k);

        // אם ההשפעה של שקיפות החומר היא לא קטנה מהמינימום המוגדר
        if (!kT.lowerThan(MIN_CALC_COLOR_K)) {
            // בניית הקרן השקופה
            Ray refractedRay = constructRefractedRay(normal, geoPoint.point, ray);

            // מציאת הנקודה הקרובה ביותר שעליה הקרן השקופה מתנפקת
            Intersectable.GeoPoint refractedPoint = findClosestIntersection(refractedRay);

            // הוספה של צבע ההשפעה של הקרן השקופה לצבע הכולל
            color = color.add(calcColor(refractedPoint,
                    refractedRay,
                    level - 1,
                    kT).scale(material.kT));
        }

        // החזרת הצבע הסופי שמייצג את כל ההשפעות
        return color;
    }


    /**
     * Construct the refracted ray of the point on the geometry.
     * @param normal normal vector
     * @param point on the geometry
     * @param ray from the geometry
     * @return new ray
     */
    private Ray constructRefractedRay(Vector normal, Point point, Ray ray) {
        return new Ray(point, ray.getDir(), normal);
    }

    /**
     * Find the closest intersection point of the ray with the geometry.
     * @param ray on the geometry
     * @return the closest geo point
     */
    private Intersectable.GeoPoint findClosestIntersection(Ray ray) {
        List<Intersectable.GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        boolean isNullOrEmpty = intersections == null || intersections.isEmpty();
        return isNullOrEmpty ? null : ray.findClosestGeoPoint(intersections);
    }

    public RayTracerBasic setAdaptiveGrid(boolean isAdaptiveGrid) {
        this.isAdaptiveGrid = isAdaptiveGrid;
        return this;
    }

    public RayTracerBasic setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
        return this;
    }
}