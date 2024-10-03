package utilities.queue.implementations;

import utilities.queue.QueueNode;
import utilities.queue.QueueSL;

public class SimpleQueue<T> extends QueueSL<T> {

    @Override
    protected QueueNode<T> createNewNode(T e) {
        return new SimpleQueueNode<T>(e);
    }
    
}
