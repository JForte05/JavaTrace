import java.io.IOException;
import color.Color;
import fileIO.savers.image.BMPSaver;
import geometry.Ray;
import geometry.Scene;
import geometry.Sphere;
import geometry.Vector3;
import rendering.Camera;
import rendering.Material;
import rendering.RayHit;
import rendering.engines.RayTracer;
import rendering.engines.Renderer;
import rendering.targets.Image;

public class App {
    public static void main(String[] args) throws IOException{
        Renderer renderer = new RayTracer();
        Scene scene = new Scene();

        Sphere s = new Sphere(Vector3.forward().multiply(5), 1, new Material(new Color(255, 255, 0)));
        Sphere s2 = new Sphere(new Vector3(5, 5, 5), 2, new Material(new Color(128, 128, 128)));

        scene.addObjects(s, s2);

        Camera c = new Camera(Vector3.zero(), Vector3.forward(), 16.0 / 9.0, 60.0);

        Image render = renderer.render(scene, c, 720);
        render.save("firstRender", new BMPSaver());
    }
}
