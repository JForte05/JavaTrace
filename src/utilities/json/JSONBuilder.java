package utilities.json;

import java.util.function.Supplier;

import fileIO.writers.JSONWritable;
import utilities.queue.QueueNode;
import utilities.queue.QueueSL;
import utilities.queue.implementations.SimpleQueueNode;

public class JSONBuilder{
    private final QueueSL<Supplier<JSONEntry>> entryQueue;
    private final QueueSL<String> nameQueue;

    public JSONBuilder(){
        entryQueue = new QueueSL<Supplier<JSONEntry>>() {
            @Override
            protected QueueNode<Supplier<JSONEntry>> createNewNode(Supplier<JSONEntry> e) {
                return new SimpleQueueNode<Supplier<JSONEntry>>(e);
            }
        };
        nameQueue = new QueueSL<String>() {
            @Override
            protected QueueNode<String> createNewNode(String e){
                return new SimpleQueueNode<String>(e);
            }
        };
    }

    public JSONBuilder addEntry(String name, JSONEntry entry){
        nameQueue.enqueue(name);
        entryQueue.enqueue(() -> entry);
        return this;
    }
    public JSONBuilder addString(String name, String s){
        nameQueue.enqueue(name);
        entryQueue.enqueue(() -> new JSONEntry(s));
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
    public JSONListBuilder createList(String name){
        nameQueue.enqueue(name);
        JSONListBuilder b = new JSONListBuilder();
        entryQueue.enqueue(b::toEntry);
        return b;
    }

    public JSONEntry toEntry(){
        StringBuilder b = new StringBuilder();

        while (!nameQueue.isEmpty() && !entryQueue.isEmpty()){
            
        }

        return null;
    }
}
