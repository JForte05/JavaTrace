package utilities.json;

import java.util.function.Supplier;

import fileIO.writers.JSONWritable;
import utilities.queue.QueueSL;
import utilities.queue.implementations.SimpleQueue;

public class JSONBuilder{
    private final QueueSL<Supplier<JSONEntry>> entryQueue;
    private final QueueSL<String> nameQueue;

    public JSONBuilder(){
        entryQueue = new SimpleQueue<Supplier<JSONEntry>>();
        nameQueue = new SimpleQueue<String>();
    }

    /**
     * Add an object field
     * @param name The identifier of this field
     * @param o The object to add
     * @return This JSONBuilder to allow for call chaining
     */
    public JSONBuilder addObject(String name, JSONWritable o){
        nameQueue.enqueue(name);
        JSONBuilder b = new JSONBuilder();
        o.writeJSON(b);
        entryQueue.enqueue(b::toEntry);
        return this;
    }
    /**
     * Add a JSONEntry field
     * @param name The identifier of this field
     * @param e The entry to add
     * @return This JSONBuilder to allow for call chaining
     */
    public JSONBuilder addEntry(String name, JSONEntry e){
        nameQueue.enqueue(name);
        entryQueue.enqueue(() -> e);
        return this;
    }
    /**
     * Add a string field
     * @param name The identifier of this field
     * @param s The string to add
     * @return This JSONBuilder to allow for call chaining
     */
    public JSONBuilder addString(String name, String s){
        nameQueue.enqueue(name);
        entryQueue.enqueue(() -> new JSONEntry('"' + s + '"'));
        return this;
    }
    /**
     * Add a boolean field
     * @param name The identifier of this field
     * @param b The boolean to add
     * @return This JSONBuilder to allow for call chaining
     */
    public JSONBuilder addBoolean(String name, boolean b){
        nameQueue.enqueue(name);
        entryQueue.enqueue(() -> new JSONEntry(Boolean.toString(b)));
        return this;
    }
    /**
     * Add a number field 
     * @param name The identifier of this field
     * @param n The nunmber to add
     * @return This JSONBuilder to allow for call chaining
     */
    public JSONBuilder addNumber(String name, Number n){
        nameQueue.enqueue(name);
        entryQueue.enqueue(() -> new JSONEntry(n.toString()));
        return this;
    }
    /**
     * Create a list builder which is bound to this JSONBuilder
     * @param name The name of the list field
     * @return The list builder
     */
    public JSONListBuilder createListBuilder(String name){
        nameQueue.enqueue(name);
        JSONListBuilder b = new JSONListBuilder();
        entryQueue.enqueue(b::toEntry);
        return b;
    }

    /**
     * @return A JSONEntry containing all the fields added to this builder
     */
    public JSONEntry toEntry(){
        StringBuilder b = new StringBuilder();
        b.append("{");

        while (!nameQueue.isEmpty() && !entryQueue.isEmpty()){
            b.append(String.format("\"%s\" : %s", nameQueue.dequeue(), entryQueue.dequeue().get().data()));

            if(!entryQueue.isEmpty() || !nameQueue.isEmpty())
                b.append(", ");
        }
        b.append("}");

        return new JSONEntry(b.toString());
    }
}
