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

    public JSONBuilder addObject(String name, JSONWritable o){
        nameQueue.enqueue(name);
        JSONBuilder b = new JSONBuilder();
        o.writeJSON(b);
        entryQueue.enqueue(b::toEntry);
        return this;
    }
    public JSONBuilder addString(String name, String s){
        nameQueue.enqueue(name);
        entryQueue.enqueue(() -> new JSONEntry('"' + s + '"'));
        return this;
    }
    public JSONBuilder addBoolean(String name, Boolean b){
        nameQueue.enqueue(name);
        entryQueue.enqueue(() -> new JSONEntry(b.toString()));
        return this;
    }
    public JSONBuilder addNumber(String name, Number n){
        nameQueue.enqueue(name);
        entryQueue.enqueue(() -> new JSONEntry(n.toString()));
        return this;
    }
    public JSONListBuilder createListBuilder(String name){
        nameQueue.enqueue(name);
        JSONListBuilder b = new JSONListBuilder();
        entryQueue.enqueue(b::toEntry);
        return b;
    }

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
