package utilities.queue;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

public abstract class QueueSL<T> {
    private QueueNode<T> head;
    private QueueNode<T> tail;

    public QueueSL() {}

    public boolean isEmpty(){
        return head == null;
    }

    public void enqueue(T e){
        QueueNode<T> newNode = createNewNode(e);

        if (head == null){
            head = newNode;
            tail = newNode;
            return;
        }

        tail.setNext(newNode);
        tail = newNode;
    }
    public T peek(){
        if (head == null)
            return null;

        return head.getItem();
    }
    public T dequeue() throws NoSuchElementException{
        if (head == null)
            throw new NoSuchElementException();

        T item = head.getItem();
        head = head.getNext();

        if (head == null)
            tail = null;
        return item;
    }
    public void clear(){
        head = null;
        tail = null;
    }

    public void forEach(Consumer<T> action){
        while(!isEmpty()){
            action.accept(dequeue());
        }
    }

    protected abstract QueueNode<T> createNewNode(T e);
}
