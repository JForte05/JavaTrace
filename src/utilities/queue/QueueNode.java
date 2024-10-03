package utilities.queue;

public abstract class QueueNode<T> {
    private QueueNode<T> next;


    public QueueNode<T> getNext(){
        return next;
    }
    public void setNext(QueueNode<T> next){
        this.next = next;
    }

    public abstract T getItem();
}
