package geometry;

public class Ray {
    public Vector3 direction;
    public Vector3 origin;

    public Ray(Vector3 origin, Vector3 direction){
        this.direction = direction;
        this.origin = origin;
    }
}
