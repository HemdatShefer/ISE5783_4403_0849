package test;

import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Axis;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

/**
 * Test program for the 1st stage
 *
 * @author Dan Zilberstein

 */
public final class Main {
    public static final int DEFAULT_BEAM_RAYS = 64;

    /**
     * Main program to test initial functionality of the 1st stage.
     *
     * @param args irrelevant here
     */
    public static void main(String[] args) {

        Material materialDefault = new Material().setKd(0.5).setKs(0.5).setShininess(30);
        Material glassMaterial = new Material().setKs(0).setKr(1).setShininess(30);

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
                                .setMaterial(glassMaterial),

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
                                        .setKr(0.5)),
        //create cube
        new Polygon(new Point(20, -30, 10),
                new Point(40, -30, -30),
                new Point(80, -30, -10),
                new Point(60, -30, 30))
                .setEmission(cubeColor)
                .setMaterial(materialDefault),
                new Polygon(new Point(20, -30, 10),
                        new Point(40, -30, -30),
                        new Point(40, 10, -30),
                        new Point(20, 10, 10))
                        .setEmission(cubeColor)
                        .setMaterial(materialDefault),
                new Polygon(new Point(80, -30, -10),
                        new Point(40, -30, -30),
                        new Point(40, 10, -30),
                        new Point(80, 10, -10))
                        .setEmission(cubeColor)
                        .setMaterial(materialDefault),
                new Polygon(new Point(20, -30, 10),
                        new Point(60, -30, 30),
                        new Point(60, 10, 30),
                        new Point(20, 10, 10))
                        .setEmission(cubeColor)
                        .setMaterial(materialDefault),
                new Polygon(new Point(40, 10, -30),
                        new Point(80, 10, -10),
                        new Point(60, 10, 30),
                        new Point(20, 10, 10))
                        .setEmission(cubeColor)
                        .setMaterial(materialDefault),
                new Polygon(new Point(60, -30, 30),
                        new Point(80, -30, -10),
                        new Point(80, 10, -10),
                        new Point(60, 10, 30))
                        .setEmission(cubeColor)
                        .setMaterial(materialDefault));


        scene.lights.add(new PointLight(new Color(java.awt.Color.YELLOW), new Point(-50, -95, 0))
                .setKc(1)
                .setKl(0.00005)
                .setKq(0.00005));
        LightSource lightSource = new DirectionalLight(new Color(java.awt.Color.WHITE),new Vector(-1, 1, -1));
        scene.lights.add(lightSource);

        scene.setBackground(new Color(java.awt.Color.black));
        scene.setAmbientLight(new AmbientLight(Color.BLACK, new Double3(0)));
        camera.setImageWriter(new ImageWriter("MiniProjectLevel1", 600, 600)).renderImage();
        camera.setImageWriter(new ImageWriter("MiniProjectLevel1", 600, 600));
        camera.setBeamRays(DEFAULT_BEAM_RAYS);
        camera.renderImage();
        camera.writeToImage();
    }

    @Test
     void mainTest() {

        Material materialDefault = new Material().setKd(0.5).setKs(0.5).setShininess(30);
        Material mirrorMaterial = new Material()
                .setKd(0.5)
                .setKs(0.5)
                .setShininess(30)
                .setKt(0)
                .setKr(0.5);
        Color white = new Color(255, 255, 255);

        Material glassMaterial = new Material().setKs(0).setKr(1).setShininess(30);

        LightSource directionalLight = new DirectionalLight(new Color(java.awt.Color.WHITE),
                new Vector(-1, 1, -1));
        LightSource spotLight = new SpotLight(new Color(java.awt.Color.WHITE),
                new Point(-100, -10, -200),
                new Vector(1, -1, 3))
                .setKc(1)
                .setKl(1E-5)
                .setKq(1.5E-7);
        LightSource pointLight = new PointLight(new Color(java.awt.Color.YELLOW),
                new Point(-50, -95, 0))
                .setKc(1)
                .setKl(0.00005)
                .setKq(0.00005);
        LightSource additionalPointLight = new PointLight(new Color(java.awt.Color.WHITE),
                new Point(50, 50, -50))
                .setKc(1)
                .setKl(0.00005)
                .setKq(0.00005);
        Scene scene = new Scene("Image project");
        Camera camera = new Camera(new Point(0, 0, -1000),
                new Vector(0, 0, 1),
                new Vector(0, -1, 0));
        camera.setSize(200, 200).setDistance(1000).setRayTracer(new RayTracerBasic(scene));
        List<Sphere> balls1 = new ArrayList<>();

        int x = 0;
        int y = 80;
        int z = 20;
        int radius = 18;
        Color[] colors = { new Color(0, 255, 255), new Color(128, 0, 128),new Color(255, 255, 0),new Color(255, 105, 180), Color.GREEN};
        final int BALLS_COUNT = 8;

        for (int i = 0; i < BALLS_COUNT; i++) {
            Color color = colors[i % colors.length];
            Sphere sphere = new Sphere(new Point(x, y, z), radius);
            sphere.setEmission(color).setMaterial(materialDefault);
            balls1.add(sphere);
            radius -= 2;
            y -= 2.5 * radius;
        }

        List<Sphere> balls2 = new ArrayList<>();
        x = 70;
        y = 80;
        z = 20;
        radius = 18;

        for (int i = 0; i < BALLS_COUNT; i++) {
            Color color = colors[i % colors.length];
            Sphere sphere = new Sphere(new Point(x, y, z), radius);
            sphere.setEmission(color).setMaterial(glassMaterial);
            balls2.add(sphere);
            radius -= 2;
            y -= 2.5 * radius;
        }

        List<Sphere> balls3 = new ArrayList<>();
        x = -70;
        y = 80;
        z = 20;
        radius = 18;

        for (int i = 0; i < BALLS_COUNT; i++) {
            Color color = colors[i % colors.length];
            Sphere sphere = new Sphere(new Point(x, y, z), radius);
            sphere.setEmission(color).setMaterial(glassMaterial);
            balls2.add(sphere);
            radius -= 2;
            y -= 2.5 * radius;
        }


        Plane leftMirror = new Plane(new Point(-100, 100, -400), new Vector(0, 1, 0));
        leftMirror.setMaterial(mirrorMaterial);
        leftMirror.setEmission(Color.BLACK);


        Ray axisRay = new Ray(new Point(0, -85, 40), new Vector(0,1,0));
        Cylinder cylinder = new Cylinder(0.5, axisRay, 400);
        cylinder.setEmission(white).setMaterial(materialDefault);

        Ray axisRay2 = new Ray(new Point(70, -85, 40), new Vector(0,1,0));
        Cylinder cylinder2 = new Cylinder(0.5, axisRay2, 400);
        cylinder2.setEmission(white).setMaterial(materialDefault);

        Ray axisRay3 = new Ray(new Point(-70, -85, 40), new Vector(0,1,0));
        Cylinder cylinder3 = new Cylinder(0.5, axisRay3, 400);
        cylinder2.setEmission(white).setMaterial(materialDefault);


        Plane backgroundMirror = new Plane(new Point(0, 0, 200), new Vector(0, 0, -1));
        backgroundMirror.setMaterial(mirrorMaterial);
        backgroundMirror.setEmission(Color.BLACK);
        scene.geometries.add(backgroundMirror);


        Sphere[] ballsArray1 = balls1.toArray(new Sphere[0]);
        scene.geometries.add(ballsArray1);
        Sphere[] ballsArray2 = balls2.toArray(new Sphere[0]);
        scene.geometries.add(ballsArray2);
        Sphere[] ballsArray3 = balls3.toArray(new Sphere[0]);
        scene.geometries.add(ballsArray3);
        scene.geometries.add(leftMirror);
        scene.geometries.add(cylinder);
        scene.geometries.add(cylinder2);
        scene.geometries.add(cylinder3);
        scene.lights.add(spotLight);
        scene.lights.add(pointLight);
        scene.lights.add(directionalLight);
        scene.lights.add(additionalPointLight);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, Double3.ZERO));
        camera.setImageWriter(new ImageWriter("MiniProjectLevel2", 1000, 1000));
        camera.setBeamRays(DEFAULT_BEAM_RAYS);
        camera.setRayTracer(new RayTracerBasic(scene).setAdaptiveGrid(true).setMaxLevel(3));
        camera.renderImage();
        camera.writeToImage();


        Camera rotatedCamera1 = new Camera(camera.getP0(), camera.getVectorTo(), camera.getVectorUp())
                .rotation(Math.PI / 4, Axis.Y);
        rotatedCamera1.setSize(200, 200).setDistance(1000).setRayTracer(new RayTracerBasic(scene));
        rotatedCamera1.setImageWriter(new ImageWriter("rotatedImage1", 1000, 1000));
        rotatedCamera1.setBeamRays(DEFAULT_BEAM_RAYS);
        rotatedCamera1.setRayTracer(new RayTracerBasic(scene).setAdaptiveGrid(true).setMaxLevel(3));
        rotatedCamera1.renderImage();
        rotatedCamera1.writeToImage();


        Camera rotatedCamera2 = new Camera(camera.getP0(), camera.getVectorTo(), camera.getVectorUp())
                .rotation(Math.PI / 4, Axis.X);
        rotatedCamera2.setSize(200, 200).setDistance(1000).setRayTracer(new RayTracerBasic(scene));
        rotatedCamera2.setImageWriter(new ImageWriter("rotatedImage2", 1000, 1000));
        rotatedCamera2.setBeamRays(DEFAULT_BEAM_RAYS);
        rotatedCamera2.setRayTracer(new RayTracerBasic(scene).setAdaptiveGrid(true).setMaxLevel(3));
        rotatedCamera2.renderImage();
        rotatedCamera2.writeToImage();

        Camera rotatedCamera3 = new Camera(camera.getP0(), camera.getVectorTo(), camera.getVectorUp())
                .rotation(Math.PI / 4, Axis.Z);
        rotatedCamera3.setSize(200, 200).setDistance(1000).setRayTracer(new RayTracerBasic(scene));
        rotatedCamera3.setImageWriter(new ImageWriter("rotatedImage3", 1000, 1000));
        rotatedCamera3.setBeamRays(DEFAULT_BEAM_RAYS);
        rotatedCamera3.setRayTracer(new RayTracerBasic(scene).setAdaptiveGrid(true).setMaxLevel(3));
        rotatedCamera3.renderImage();
        rotatedCamera3.writeToImage();

    }

}

