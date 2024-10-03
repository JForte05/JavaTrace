package utilities.stack;

import java.util.NoSuchElementException;

public class ReadOnlyStack<T> {
    private StackNode<T> head;

    protected ReadOnlyStack(StackNode<T> head){
        this.head = head;
    }

    public T peek() throws NoSuchElementException{
        if (head.getNext() == null){
            throw new NoSuchElementException();
        }

        return head.getItem();
    }

    public T pop() throws NoSuchElementException{
        T item = head.getItem();
        head = head.getNext();
        return item;
    }
}
