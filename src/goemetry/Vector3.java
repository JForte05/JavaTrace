package goemetry;

import fileIO.writers.JSONWritable;
import utilities.json.JSONBuilder;

public class Vector3 implements JSONWritable{
    public double x;
    public double y;
    public double z;

    public Vector3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double sqrMagnitude(){
        return (x * x) + (y * y) + (z * z);
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

    public Vector3 plus(Vector3 other){
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }
    public Vector3 scaledBy(double scalar){
        return new Vector3(x * scalar, y * scalar, z * scalar);
    }

    @Override
    public void writeJSON(JSONBuilder b){
        b.addNumber("x", x);
        b.addNumber("y", y);
        b.addNumber("z", z);
    }
}
