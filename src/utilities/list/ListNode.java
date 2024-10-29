package utilities.list;

/**
 * Abstract interally used base for doubly-linked list nodes.
 * <p>This class does not have implentations for getting and setting an element
 * contained within the node. This is left to subclasses as they can specialize in 
 * storing that object in a more efficient manner.
 */
public abstract class ListNode<T> {
    private ListNode<T> next;
    private ListNode<T> prev;
    
    public abstract T getItem();
    public abstract void setItem(T item);

    public final ListNode<T> getNext(){
        return next;
    }
    public final void setNext(ListNode<T> next){
        this.next = next;
    }
    public final ListNode<T> getPrevious(){
        return prev;
    }
    public final void setPrevious(ListNode<T> prev){
        this.prev = prev;
    }

}
