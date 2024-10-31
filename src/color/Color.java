package color;

import fileIO.writers.JSONWritable;
import geometry.Vector3;
import utilities.json.JSONBuilder;

public class Color implements JSONWritable{
    public byte r;
    public byte g;
    public byte b;

    public Color(byte r, byte g, byte b){
        this.r = r;
        this.g = g;
        this.b = b;
    }
    public Color(int r, int g, int b){
        this((byte)r, (byte)g, (byte)b);
    }
    public Color(Vector3 floatVector){
        this.r = (byte)(floatVector.x * 255.0);
        this.g = (byte)(floatVector.y * 255.0);
        this.b = (byte)(floatVector.z * 255.0);
    }

    public Vector3 floatVector(){
        return new Vector3(((int)r & 0xff) / 255.0, ((int)g & 0xff) / 255.0, ((int)b & 0xff) / 255.0);
    }

    public String toHexString(){
        return String.format("#%02x%02x%02x", r, g, b);
    }

    @Override
    public String toString(){
        return String.format("(%d, %d, %d)", r, g, b);
    }
    
    @Override
    public void writeJSON(JSONBuilder builder) {
        builder.createListBuilder("components").addNumbers(r, g, b);
        builder.addString("hex-string", toHexString());
    }
}
