package utilities.stack;

/**
 * Abstract interally used base for stack nodes.
 * <p>This class does not have implentations for getting and setting an element
 * contained within the node. This is left to subclasses as they can specialize in 
 * storing that object in a more efficient manner.
 */
public abstract class StackNode<T> {
    private StackNode<T> next;

    public abstract T getItem();

    public StackNode<T> getNext(){
        return next;
    }
    public void setNext(StackNode<T> next){
        this.next = next;
    }
}
