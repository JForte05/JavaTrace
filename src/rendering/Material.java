package rendering;

import color.Color;
import fileIO.writers.JSONWritable;
import utilities.json.JSONBuilder;

public class Material implements JSONWritable{
    private Color baseColor;
    private Color emissionColor;
    private double emissionStrength;

    @Override
    public void writeJSON(JSONBuilder builder) {
        builder.addObject("base-color", baseColor);
        builder.addObject("emssion-color", emissionColor);
        builder.addNumber("emission-strength", emissionStrength);
    }
}
