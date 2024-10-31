package rendering;

import geometry.Ray;
import geometry.Vector3;
import rendering.targets.Image;

public class Camera {
    private Vector3 position;
    private Vector3 forwardDirection;

    // width:height
    private double aspect;
    // vertical ; degrees
    private double fov;

    private double planeWidth;
    private double planeHeight;

    public Camera(Vector3 position, Vector3 forward, double aspect, double fov){
        this.position = position;
        this.forwardDirection = forward;
        this.aspect = aspect;
        this.fov = fov;

        double fovRads = Math.toRadians(fov);
        planeHeight = 2.0 * Math.tan(fovRads / 2.0);
        planeWidth = planeHeight * aspect;
    }
    public Camera(Vector3 position, Vector3 forward){
        this(position, forward, 16.0/9.0, 60.0);
    }

    public double getAspectRatio(){
        return aspect;
    }
    public double getVerticalFOV(){
        return fov;
    }
    public Vector3 getPosition(){
        return position;
    }
    public Vector3 getForwardDirection(){
        return forwardDirection;
    }

    public Ray getRay(double u, double v){
        Vector3 right = forwardDirection.cross(Vector3.up());
        Vector3 trueUp = forwardDirection.cross(right);

        Vector3 dir = forwardDirection.plus(right.multiply((u - 0.5) * planeWidth)).plus(trueUp.multiply((0.5 - v) * planeHeight));
        dir.normalize();
        
        return new Ray(position, dir);
    }

    public Image renderTexture(int desiredVerticalSize){
        return new Image((int)(aspect * desiredVerticalSize), desiredVerticalSize);
    }
}
