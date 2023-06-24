package test;

import geometries.*;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

/**
 * Test program for the 1st stage
 *
 * @author Dan Zilberstein

 */
public final class Main {
    /**
     * Main program to test initial functionality of the 1st stage.
     * @param args irrelevant here
     */
    public static void main(String[] args) {
        // Create the material for the ring and the pearl
        Material ringMaterial = new Material()
                .setKd(0.5)  // Diffusion coefficient
                .setKs(0.5)  // Specular coefficient
                .setKr(0.5)  // Reflection coefficient
                .setKt(0.5)  // Transparency coefficient
                .setShininess(20); // Specular exponent

        Material pearlMaterial = new Material()
                .setKd(0.8)  // Diffusion coefficient
                .setKs(0.2)  // Specular coefficient
                .setKr(0)    // Reflection coefficient
                .setKt(0)    // Transparency coefficient
                .setShininess(100); // Specular exponent

        Material planeMaterial = new Material()
                .setKd(0.5)  // Diffusion coefficient
                .setKs(0.5)  // Specular coefficient
                .setKr(0.5)  // Reflection coefficient
                .setKt(0.5)  // Transparency coefficient
                .setShininess(20); // Specular exponent


        // Create the pearl
        Sphere pearl = new Sphere(new Point(0, 0, 0), 1);  // Pearl centered at origin with radius 1
        pearl.setMaterial(pearlMaterial);

        // Create the ring base (a tube)
        Tube ringBase = new Tube(new Ray(new Point(0, 0, -2), new Vector(0, 0, 1)), 2);
        ringBase.setMaterial(ringMaterial);

        // Create the triangles that hold the pearl
        Triangle triangle1 = new Triangle(new Point(-1, -1, -1), new Point(-1, 1, -1), new Point(1, 0, -1));
        triangle1.setMaterial(ringMaterial);
        Triangle triangle2 = new Triangle(new Point(-1, -1, -1), new Point(1, -1, -1), new Point(1, 0, -1));
        triangle2.setMaterial(ringMaterial);
        Triangle triangle3 = new Triangle(new Point(-1, 1, -1), new Point(1, 1, -1), new Point(1, 0, -1));
        triangle3.setMaterial(ringMaterial);


        Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(2500, 2500).setVPDistance(10000); //

        // Create a bright pink plane
        Plane plane = new Plane(new Point(0, -5, 0), new Vector(0, 1, 0));
        plane.setMaterial(planeMaterial);

        // Assuming you have some kind of scene or world object to add these geometries to...
        Scene scene = new Scene("pearl ring" );
        scene.addGeometry(pearl);
        scene.addGeometry(ringBase);
        scene.addGeometry(triangle1);
        scene.addGeometry(triangle2);
        scene.addGeometry(triangle3);



        plane.setEmission(new Color(java.awt.Color.PINK));

        scene.addGeometry(plane);

        scene.setAmbientLight(
                new AmbientLight(
                        new Color(255, 255, 255),
                        new Double3(0.1)));

        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setKl(0.00001).setKq(0.000005));



        ImageWriter imageWriter = new ImageWriter("pearlRing", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

    }
}

