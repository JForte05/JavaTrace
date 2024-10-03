package utilities.queue.implementations;

import utilities.queue.QueueNode;

public class SimpleQueueNode<T> extends QueueNode<T>{
    private final T item;

    public SimpleQueueNode(T item){
        this.item = item;
    }

    @Override
    public T getItem() {
        return this.item;
    }
    
}
