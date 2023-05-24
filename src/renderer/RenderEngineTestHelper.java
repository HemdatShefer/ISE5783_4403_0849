package renderer;

import geometries.Geometry;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import primitives.Color;
import primitives.Point;
import scene.Scene;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Helper class for rendering engine tests that builds the scene and imageWriter objects from an XML file.
 */
public class RenderEngineTestHelper {

    /**
     * Builds the scene and imageWriter objects from an XML file.
     *
     * @param fileName the name of the XML file
     * @return the scene object
     */
    public static Scene buildSceneFromXML(String fileName) {
        try {
            // Create a DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML file
            Document document = builder.parse(new File(fileName));

            // Get the root element
            Element root = document.getDocumentElement();

            // Create the scene object
            Scene scene = new Scene("newScene");

            // Set the background color
            String bgColorString = root.getAttribute("background-color");
            Color bgColor = parseColor(bgColorString);
            scene.setBackground(bgColor);

            // Create the ambient light
            Element ambientLightElement = (Element) root.getElementsByTagName("ambient-light").item(0);
            String ambientLightColorString = ambientLightElement.getAttribute("color");
            Color ambientLightColor = parseColor(ambientLightColorString);
            AmbientLight ambientLight = new AmbientLight(ambientLightColor);
            scene.setAmbientLight(ambientLight);

            // Get the geometries element
            Element geometriesElement = (Element) root.getElementsByTagName("geometries").item(0);
            NodeList geometryNodes = geometriesElement.getChildNodes();

            // Create the geometries
            for (int i = 0; i < geometryNodes.getLength(); i++) {
                Node geometryNode = geometryNodes.item(i);
                if (geometryNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element geometryElement = (Element) geometryNode;
                    String geometryType = geometryElement.getTagName();

                    // Create the geometry object
                    Geometry geometry = createGeometry(geometryType, geometryElement);
                    scene.addGeometry(geometry);
                }
            }

            return scene;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a Color object from a string representation.
     *
     * @param colorString the string representation of the color (format: "R G B")
     * @return the Color object
     */
    private static Color parseColor(String colorString) {
        String[] components = colorString.split(" ");
        int r = Integer.parseInt(components[0]);
        int g = Integer.parseInt(components[1]);
        int b = Integer.parseInt(components[2]);
        return new Color(r, g, b);
    }

    /**
     * Creates a Geometry object based on the given type and element.
     *
     * @param type    the type of the geometry
     * @param element the XML element representing the geometry
     * @return the Geometry object
     */
    private static Geometry createGeometry(String type, Element element) {
        switch (type) {
            case "sphere":
                return createSphere(element);
            case "triangle":
                return createTriangle(element);
            // Add support for other geometry types here
            default:
                return null;
        }
    }

    /**
     * Creates a Sphere object from the given XML element.
     *
     * @param element the XML element representing the sphere
     * @return the Sphere object
     */
    private static Sphere createSphere(Element element) {
        String centerString = element.getAttribute("center");
        String radiusString = element.getAttribute("radius");
        Point center = parsePoint(centerString);
        double radius = Double.parseDouble(radiusString);
        return new Sphere(center, radius);
    }

    /**
     * Creates a Triangle object from the given XML element.
     *
     * @param element the XML element representing the triangle
     * @return the Triangle object
     */
    private static Triangle createTriangle(Element element) {
        String p0String = element.getAttribute("p0");
        String p1String = element.getAttribute("p1");
        String p2String = element.getAttribute("p2");
        Point p0 = parsePoint(p0String);
        Point p1 = parsePoint(p1String);
        Point p2 = parsePoint(p2String);
        return new Triangle(p0, p1, p2);
    }

    /**
     * Creates a Point object from a string representation.
     *
     * @param pointString the string representation of the point (format: "X Y Z")
     * @return the Point object
     */
    private static Point parsePoint(String pointString) {
        String[] components = pointString.split(" ");
        double x = Double.parseDouble(components[0]);
        double y = Double.parseDouble(components[1]);
        double z = Double.parseDouble(components[2]);
        return new Point(x, y, z);
    }
}
