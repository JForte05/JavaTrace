package rendering;

import geometry.Vector3;

public class RayHit {
    public static RayHit none(){
        return new RayHit(false, Double.POSITIVE_INFINITY, null, null, null, 0.0, null, null);
    }
    public static RayHit of(Double distance, Vector3 hitPoint, Material material, Vector3 hitNormal){
        Vector3 bounce = Vector3.randomUnitVector();
        if (bounce.dot(hitNormal) < 0.0)
            bounce = bounce.unaryMinus();
        bounce.plus(hitNormal).normalized();

        return new RayHit(true, distance, hitPoint, material.getColor().floatVector(), material.getEmissiveColor().floatVector(), 
            material.getEmissiveStrength(), hitNormal, bounce);
    }

    public boolean hit;
    public double distance;
    public Vector3 hitPoint;
    public Vector3 hitColor;
    public Vector3 emissionColor;
    public double emissionStrength;
    public Vector3 hitNormal;
    public Vector3 bounceDirection;

    private RayHit(boolean hit, double distance, Vector3 hitPoint, Vector3 hitColor, Vector3 emissionColor, 
        double emissionStrength, Vector3 hitNormal, Vector3 bounceDirection){
        this.hit = hit;
        this.distance = distance;
        this.hitPoint = hitPoint;
        this.hitColor = hitColor;
        this.emissionColor = emissionColor;
        this.emissionStrength = emissionStrength;
        this.hitNormal = hitNormal;
        this.bounceDirection = bounceDirection;
    }

    public String info(){
        return String.format("Hit - %s\nDistance - %s\nHit Location - %s\nHit Color - %s\nHit Normal - %s\n", 
            hit, hit ? String.format("%.3f", distance) : "N/A", hit ? hitPoint : "N/A", hit ? hitColor.multiply(255) : "N/A", hit ? hitNormal : "N/A");
    }
}
