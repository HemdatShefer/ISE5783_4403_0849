package test;

import geometries.*;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
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
     *
     * @param args irrelevant here
     */
    public static void main(String[] args) {

        Material materialDefault = new Material().setKd(0.5).setKs(0.5).setShininess(30);

        Color cubeColor = new Color(255, 105, 180);

        Scene scene = new Scene("Test scene");
        Camera camera = new Camera(new Point(0, 0, -1000),
                new Vector(0, 0, 1),
                new Vector(0, -1, 0))
                .setSize(200, 200)
                .setDistance(1000)
                .setRayTracer(new RayTracerBasic(scene));


        scene.geometries.add
                (new Sphere(new Point(0, 0, 0), 10)
                                .setEmission(new Color(128, 0, 128))
                                .setMaterial(new Material()
                                        .setKd(0.5)
                                        .setKs(0.5)
                                        .setShininess(30)
                                        .setKt(0.7)
                                        .setKr(0)),

                        new Sphere(
                                new Point(-20, -5, 20), 15)
                                .setEmission(new Color(255, 255, 0))
                                .setMaterial(materialDefault),

                        new Sphere(
                                new Point(-30, -10, 60), 20)
                                .setEmission(new Color(java.awt.Color.GREEN))
                                .setMaterial(materialDefault.setKt(0.5)),

                        new Sphere(
                                new Point(15, -20, 70), 30)
                                .setEmission(new Color(0, 255, 255))
                                .setMaterial(new Material()
                                        .setKd(0.5)
                                        .setKs(0.5)
                                        .setShininess(30)
                                        .setKt(0.6)
                                        .setKr(0)),

                        new Plane(
                                new Point(-20, 10, 100),
                                new Point(-30, 10, 140),
                                new Point(15, 10, 150))
                                .setEmission(cubeColor)
                                .setMaterial(new Material()
                                        .setKd(0.5)
                                        .setKs(0.5)
                                        .setShininess(30)
                                        .setKt(0)
                                        .setKr(0.5)));

        scene.lights.add(new SpotLight(new Color(java.awt.Color.WHITE),
                new Point(-100, -10, -200),
                new Vector(1, -1, 3)).setKc(1).setKl(1E-5).setKq(1.5E-7));
        scene.lights.add(new SpotLight(new Color(java.awt.Color.WHITE),
                new Point(+100, +10, +200),
                new Vector(-1, +1, -3)).setKc(1).setKl(1E-5).setKq(1.5E-7));
        scene.lights.add(new PointLight(new Color(java.awt.Color.YELLOW), new Point(-50, -95, 0))
                .setKc(1)
                .setKl(0.00005)
                .setKq(0.00005));

        scene.setBackground(new Color(java.awt.Color.WHITE));
        scene.setAmbientLight(new AmbientLight(Color.BLACK, new Double3(0)));
        camera.setImageWriter(new ImageWriter("MiniProjectLevel5", 600, 600)).renderImage();
        camera.writeToImage();
    }

}

