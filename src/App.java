import java.io.IOException;
import color.Color;
import fileIO.savers.image.BMPSaver;
import geometry.Ray;
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
        Sphere s = new Sphere(Vector3.forward().multiply(5), 1, new Material(new Color(255, 255, 0)));

        Camera c = new Camera(Vector3.zero(), Vector3.forward());

        Image render = renderer.render(s, c, 720);
        render.save("firstRender", new BMPSaver());
    }

    public static void debugRay(Ray r, RayHit rayHit){
        System.out.printf("[DEBUG RAY]:\n\tOrigin - %s\n\tDirection - %s\n\tHit - %s\n\tHit Color - %s\n",
            r.origin.toString(), r.direction.toString(), Boolean.toString(rayHit.hit), rayHit.hitColor.toHexString());
    }
}
