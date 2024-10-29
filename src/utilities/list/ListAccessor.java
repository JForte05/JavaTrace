package utilities.list;

/**
 * An object designed to speed up accessing values in a doubly-linked list by creating
 * multiple entry points along the length of the list and beginning traversal from these entry points.
 */
public class ListAccessor<T> {
    private final ListNode<T>[] accessPoints;
    private final int interPointDistance;

    private final int listSize;

    @SuppressWarnings("unchecked")
    protected ListAccessor(ListDL<T> list, int accessPoints){
        if (accessPoints <= 2){
            throw new IllegalArgumentException("ListAccessor must have at least 3 access points");
        }

        this.listSize = list.size;

        this.accessPoints = new ListNode[accessPoints];
        this.accessPoints[0] = list.head;
        this.accessPoints[this.accessPoints.length - 1] = list.tail;

        interPointDistance = listSize / (accessPoints - 1);
        
        for (int i = 1; i < accessPoints - 2; i++){
            this.accessPoints[i] = list.find(i * interPointDistance);
        }
    } 

    protected ListNode<T> find(int index) throws IndexOutOfBoundsException{
        if (index >= listSize || index < 0)
            throw new IndexOutOfBoundsException();

        if (index % interPointDistance == 0){
            return accessPoints[index / interPointDistance];
        }
        
        int startPoint = index / interPointDistance;
        int offset = index % interPointDistance;

        if (offset <= (interPointDistance >> 1)){
            ListNode<T> current = accessPoints[startPoint];
            for (int i = 0; i < offset; i++){
                current = current.getNext();
            }
            return current;
        }
        else{
            startPoint++;
            offset = interPointDistance - offset;
            ListNode<T> current = accessPoints[startPoint];
            for (int i = interPointDistance; i < offset; i--){
                current = current.getNext();
            }
            return current;
        }
    }
    
    /**
     * Gets the size of the parent list.
     * @return The size of the parent list
     */
    public int size(){
        return listSize;
    }

    /**
     * Retrieves the element at the given index in the parent list.
     * @param index The index to retrieve at
     * @return The element at the given index
     * @throws IndexOutOfBoundsException If the index given is larger than the size of the parent list
     */
    public T get(int index) throws IndexOutOfBoundsException{
        return find(index).getItem();
    }
    /**
     * Sets the element at the given index in the parent list.
     * @param index The index to retrieve at
     * @param e The element at the given index
     * @throws IndexOutOfBoundsException
     */
    public void set(int index, T e) throws IndexOutOfBoundsException{
        find(index).setItem(e);
    }
}
