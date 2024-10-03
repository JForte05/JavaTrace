package goemetry;

import java.util.HashMap;
import java.util.Map;

import fileIO.writers.JSONWritable;
import utilities.json.JSONBuilder;
import utilities.json.JSONEntry;

public class Vector3 implements JSONWritable{
    public double x;
    public double y;
    public double z;

    public Vector3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double magnitude(){
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public void normalize(){
        double mag = magnitude();
        this.x /= mag;
        this.y /= mag;
        this.z /= mag;
    }

    public Vector3 normalized(){
        double mag = magnitude();
        return new Vector3(x / mag, y / mag, z / mag);
    }

    @Override
    public void writeJSON(JSONBuilder b){
        
    }
}
