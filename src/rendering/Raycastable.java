package rendering;

import geometry.Ray;

public interface Raycastable {
    public RayHit testRay(Ray r);
}
