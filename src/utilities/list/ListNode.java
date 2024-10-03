package utilities.list;

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
