# JavaTrace

JavaTrace is a Java scripting framework enabling CPU raytracing quickly and easily

## Usage
Using JavaTrace is simple and takes only 4 steps:
    
1. Instantiate the renderer
2. Define the scene
3. Set the camera
4. Render the image and save it in the desired format

```java
public static void main(String[] args) throws IOException{
    Renderer renderer = new RayTracer();
    
    Scene scene = new Scene();
    Sphere s1 = new Sphere(Vector3.forward().multiply(5.0), 1.0, new Material(255, 0, 0));
    Sphere s2 = new Sphere(new Vector3(60.0, 5.0, 60.0), 20, 
        new Material(new Color(0, 0, 0), new Color(255, 255, 255), 100.0));
    
    scene.addObjects(s1, s2);
    
    Camera camera = new Camera(Vector3.zero(), Vector3.forward(), 16.0 / 9.0, 60.0);
    
    Image render = renderer.render(scene, camera, 1080);
    render.save("my_awesome_render", new BMPSaver());
}
```