package geometry;

import fileIO.writers.JSONWritable;
import utilities.json.JSONBuilder;
import utilities.math.MathUtils;

public class Vector3 implements JSONWritable{
    public static Vector3 zero(){
        return new Vector3(0.0, 0.0, 0.0);
    }
    public static Vector3 one(){
        return new Vector3(1.0, 1.0, 1.0);
    }
    public static Vector3 right(){
        return new Vector3(1.0, 0.0, 0.0);
    }
    public static Vector3 left(){
        return new Vector3(-1.0, 0.0, 0.0);
    }
    public static Vector3 up(){
        return new Vector3(0.0, 1.0, 0.0);
    }
    public static Vector3 down(){
        return new Vector3(0.0, -1.0, 0.0);
    }
    public static Vector3 forward(){
        return new Vector3(0.0, 0.0, 1.0);
    }
    public static Vector3 backward(){
        return new Vector3(0.0, 0.0, -1.0);
    }

    public static Vector3 randomUnitVector(){
        // Algorithm sourced from:
        // https://math.stackexchange.com/questions/44689/how-to-find-a-random-axis-or-unit-vector-in-3d
        
        double phi = MathUtils.randDouble(0.0, 6.28318530718); // 0 to 2pi
        double cosTheta = MathUtils.randDouble(-1.0, 1.0);
        double theta = MathUtils.facos(cosTheta);

        return new Vector3(Math.sin(theta) * Math.cos(phi), Math.sin(theta) * Math.sin(phi), cosTheta);
    }

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
    public Vector3 minus(Vector3 other){
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }
    public Vector3 multiply(double scalar){
        return new Vector3(x * scalar, y * scalar, z * scalar);
    }
    public Vector3 divide(double scalar){
        return new Vector3(x / scalar, y / scalar, z / scalar);
    }

    public Vector3 unaryMinus(){
        return new Vector3(-x, -y, -z);
    }

    public double dot(Vector3 other){
        return (x * other.x + y * other.y + z * other.z) / (magnitude() * other.magnitude());
    }
    public double dotNonNormal(Vector3 other){
        return x * other.x + y * other.y + z * other.z;
    }
    public Vector3 cross(Vector3 other){
        return new Vector3(y*other.z - z*other.y, z*other.x - x*other.z, x*other.y - y*other.x);
    }

    public Vector3 componentMultiplication(Vector3 other){
        return new Vector3(x * other.x, y * other.y, z * other.z);
    }

    @Override
    public void writeJSON(JSONBuilder b){
        b.addNumber("x", x);
        b.addNumber("y", y);
        b.addNumber("z", z);
    }

    @Override
    public String toString(){
        return String.format(("(%.3f, %.3f, %.3f)"), x, y, z);
    }
}
