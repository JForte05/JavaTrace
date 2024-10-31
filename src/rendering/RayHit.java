package rendering;

import color.Color;
import geometry.Vector3;

public class RayHit {
    public static RayHit none(){
        return new RayHit(false, Double.POSITIVE_INFINITY, null, null, null, null);
    }
    public static RayHit of(Double distance, Vector3 hitPoint,  Color hitColor, Vector3 hitNormal){
        //To-Do: Random bounce direction
        return new RayHit(true, distance, hitPoint, hitColor, hitNormal, Vector3.zero());
    }

    public boolean hit;
    public double distance;
    public Vector3 hitPoint;
    public Color hitColor;
    public Vector3 hitNormal;
    public Vector3 bounceDirection;

    private RayHit(boolean hit, double distance, Vector3 hitPoint, Color hitColor, Vector3 hitNormal, Vector3 bounceDirection){
        this.hit = hit;
        this.distance = distance;
        this.hitPoint = hitPoint;
        this.hitColor = hitColor;
        this.hitNormal = hitNormal;
        this.bounceDirection = bounceDirection;
    }
}
