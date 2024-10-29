package utilities.json;

import fileIO.writers.JSONWritable;
import utilities.queue.implementations.SimpleQueue;

public class JSONListBuilder {
    private final SimpleQueue<JSONEntry> queue;
    
    protected JSONListBuilder(){
        queue = new SimpleQueue<>();
    }

    /**
     * Add numbers to the list
     * @param numbers The numbers to add
     */
    public void addNumbers(Number... numbers){
        for (Number n : numbers) {
            queue.enqueue(new JSONEntry(n.toString()));
        }
    }
    /**
     * Add booleans to the list
     * @param booleans The booleans to add
     */
    public void addBooleans(boolean... booleans){
        for (boolean bool : booleans) {
            queue.enqueue(new JSONEntry(Boolean.toString(bool)));
        }
    }
    /**
     * Add strings to the list
     * @param strings The strings to add
     */
    public void addStrings(String... strings){
        for (String s : strings){
            queue.enqueue(new JSONEntry('"' + s + '"'));
        }
    }
    /**
     * Add objects to the list
     * @param objects The objects to add
     */
    public void addObjects(JSONWritable... objects){
        for (JSONWritable o : objects){
            JSONBuilder b = new JSONBuilder();
            o.writeJSON(b);
            queue.enqueue(b.toEntry());
        }
    }

    protected JSONEntry toEntry(){
        StringBuilder b = new StringBuilder();
        b.append("[");
        while (!queue.isEmpty()){
            b.append(queue.dequeue().data());
            if(!queue.isEmpty())
                b.append(", ");
        }
        b.append("]");

        return new JSONEntry(b.toString());
    }
}
