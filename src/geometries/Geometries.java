package geometries;

import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Geometries extends Intersectable {

    private final List<Intersectable> geometries;

    public Geometries() {
        geometries = new LinkedList<>();
    }

    public Geometries(Intersectable... geometries) {
        this.geometries = new LinkedList<>(Arrays.asList(geometries));
    }

    /**
     * Add more geometries to the list.
     * @param geometries Geometries to add to current list
     */
    public void add(Intersectable... geometries) {
        this.geometries.addAll(Arrays.asList(geometries));
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> result = new LinkedList<>();

        for (Intersectable item : geometries) {
            List<GeoPoint> itemIntersectionPoints = item.findGeoIntersections(ray);
            if (itemIntersectionPoints != null) {
                result.addAll(itemIntersectionPoints);
            }
        }
        result = result.isEmpty() ? null : result;

        return result;
    }
}
