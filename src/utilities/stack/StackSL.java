package utilities.stack;

/**
 * Abstract parent class for a doubly-linked list
 */
public abstract class StackSL<T> {
    private StackNode<T> head;

    public StackSL(){}

    public boolean isEmpty(){
        return head == null;
    }

    /**
     * Add an element to the top of the stack.
     * @param e The element to add
     */
    public void push(T e){
        StackNode<T> newNode = createNewNode(e);
        newNode.setNext(head);
        head = newNode;
    }
    /**
     * Observe the element at the top of the stack without mutating the collection.
     * @return The element at the top of the stack
     */
    public T peek(){
        return head.getItem();
    }
    /**
     * Remove the element at the top of the stack
     * @return The element at the top of the stack.
     */
    public T pop(){
        T item = head.getItem();
        head = head.getNext();
        return item;
    }

    protected abstract StackNode<T> createNewNode(T e);
}
