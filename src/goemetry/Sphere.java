package goemetry;

import fileIO.writers.JSONWritable;
import rendering.Material;
import rendering.Renderable;
import utilities.json.JSONBuilder;

public class Sphere implements Renderable, JSONWritable{
    private Vector3 center;
    private float radius;
    private Material material;

    @Override
    public void writeJSON(JSONBuilder builder) {
        builder.addObject("position", center);
        builder.addNumber("radius", radius);
        builder.addObject("material", material);
    }
}
