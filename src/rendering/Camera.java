package rendering;

import goemetry.Vector3;

public class Camera {
    private Vector3 position;
    private Vector3 forwardDirection;

    // width:height
    private double aspect;
    // vertical ; degrees
    private double fov;

    public Camera(Vector3 position, Vector3 forward, double aspect, double fov){
        this.position = position;
        this.forwardDirection = forward;
        this.aspect = aspect;
        this.fov = fov;
    }
    public Camera(Vector3 position, Vector3 forward){
        this(position, forward, 16.0/9.0, 60.0);
    }
}
