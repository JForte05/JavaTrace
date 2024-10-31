import java.io.IOException;

import color.Color;
import fileIO.savers.image.BMPSaver;
import geometry.Scene;
import geometry.Sphere;
import geometry.Vector3;
import rendering.Camera;
import rendering.Material;
import rendering.engines.RayTracer;
import rendering.engines.Renderer;
import rendering.targets.Image;

public class App {
    public static void main(String[] args) throws IOException{
        Renderer renderer = new RayTracer(5, 40);
        Scene scene = new Scene();

        Sphere s = new Sphere(Vector3.forward().multiply(5.0), 1, new Material(new Color(255, 255, 0)));
        Sphere s2 = new Sphere(new Vector3(30.0, 5.0, 30.0), 10, 
            new Material(new Color(128, 128, 128), new Color(255, 255, 255), 10000.0));
        Sphere s3 = new Sphere(new Vector3(0.0, -51.0, 5.0), 50.0, new Material(new Color(100, 100, 100)));

        scene.addObjects(s, s2, s3);

        Camera c = new Camera(Vector3.zero(), Vector3.forward(), 16.0 / 9.0, 60.0);

        long start;
        long renderEnd;
        long saveEnd;

        start = System.nanoTime();
        Image render = renderer.render(scene, c, 720);
        renderEnd = System.nanoTime();
        render.save("litRender", new BMPSaver());
        saveEnd = System.nanoTime();

        System.out.printf("\nRender time: %.3f ms\nSave time: %.3f ms\nTotal time: %.3f ms\n", 
            (renderEnd - start) / 1000000.0, (saveEnd - renderEnd) / 1000000.0, (saveEnd - start) / 1000000.0);
    }
}
