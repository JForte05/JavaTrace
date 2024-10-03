package utilities.stack;

public abstract class StackSL<T> {
    private StackNode<T> head;

    public boolean isEmpty(){
        return head == null;
    }

    public void push(T e){
        StackNode<T> newNode = createNewNode(e);
        newNode.setNext(head);
        head = newNode;
    }
    public T peek(){
        return head.getItem();
    }
    public T pop(){
        T item = head.getItem();
        head = head.getNext();
        return item;
    }

    protected abstract StackNode<T> createNewNode(T e);
}
