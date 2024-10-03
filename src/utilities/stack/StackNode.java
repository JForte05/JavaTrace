package utilities.stack;

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
