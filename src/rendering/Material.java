package rendering;

import color.Color;
import fileIO.writers.JSONWritable;
import utilities.json.JSONBuilder;

public class Material implements JSONWritable{
    private Color baseColor;
    private Color emissionColor;
    private double emissionStrength;

    public Material(Color baseColor, Color emissiveColor, double emissiveStrength){
        this.baseColor = baseColor;
        this.emissionColor = emissiveColor;
        this.emissionStrength = emissiveStrength;
    }
    public Material(Color baseColor){
        this.baseColor = baseColor;
        emissionColor = new Color(0, 0, 0);
        emissionStrength = 0.0;
    }

    @Override
    public void writeJSON(JSONBuilder builder) {
        builder.addObject("base-color", baseColor);
        builder.addObject("emssion-color", emissionColor);
        builder.addNumber("emission-strength", emissionStrength);
    }

    public Color getColor(){
        return baseColor;
    }
    public Color getEmissiveColor(){
        return emissionColor;
    }
    public double getEmissiveStrength(){
        return emissionStrength;
    }
}
