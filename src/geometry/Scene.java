package geometry;

import java.util.Iterator;
import java.util.ListIterator;

import rendering.RayHit;
import rendering.Renderable;
import utilities.json.JSONBuilder;
import utilities.json.JSONListBuilder;
import utilities.list.implementations.SimpleList;

public class Scene implements Renderable{
    private SimpleList<Renderable> sceneObjects;

    public Scene(){
        sceneObjects = new SimpleList<>();
    }
    
    public void addObjects(Renderable... objects){
        for (Renderable o : objects) {
            sceneObjects.add(o, 0);
        }
    }

    @Override
    public void writeJSON(JSONBuilder builder) {
        Iterator<Renderable> itr = sceneObjects.iterator(0);
        JSONListBuilder lb = builder.createListBuilder("objects");
        while(itr.hasNext()){
            lb.addObjects(itr.next());
        }
    }

    @Override
    public RayHit testRay(Ray r) {
        ListIterator<Renderable> itr = sceneObjects.iterator(0);

        RayHit closestHit = RayHit.none();
        while(itr.hasNext()){
            RayHit newHit = itr.next().testRay(r);
            if (newHit.hit && (newHit.distance < closestHit.distance || Double.isInfinite(closestHit.distance)))
                closestHit = newHit;
        }

        return closestHit;
    }
    
}
