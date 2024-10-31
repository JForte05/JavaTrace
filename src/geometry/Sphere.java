package geometry;

import rendering.Material;
import rendering.RayHit;
import rendering.Renderable;
import utilities.json.JSONBuilder;

public class Sphere implements Renderable{
    private Vector3 center;
    private double radius;
    private Material material;

    public Sphere(Vector3 center, double radius, Material material){
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    @Override
    public void writeJSON(JSONBuilder builder) {
        builder.addObject("position", center);
        builder.addNumber("radius", radius);
        builder.addObject("material", material);
    }

    @Override
    public RayHit testRay(Ray r) {
        Vector3 d = r.direction;
        Vector3 o = r.origin;
        Vector3 diff = center.minus(o);
        if (diff.dot(r.direction) < 0.0){
            System.out.println("Early Exit");
            return RayHit.none();
        }

        double b = 2.0 * diff.dotNonNormal(d);
        double c = diff.sqrMagnitude() - (radius * radius);
        double discrim = (b * b) - (4.0 * c);
        
        if (discrim <= 0)
            return RayHit.none();

        double t = Math.min((-b + Math.sqrt(discrim)) / 2.0, (-b - Math.sqrt(discrim)) / 2.0);
        Vector3 point = r.direction.multiply(t).plus(o);
        return RayHit.of(t, point, material.getColor(), point.minus(center).normalized());
    }
}
