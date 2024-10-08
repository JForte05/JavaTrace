package utilities.list;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public abstract class ListDL<T> {
    protected ListNode<T> head;
    protected ListNode<T> tail;
    
    protected int size;

    protected ListNode<T> find(int index) throws IndexOutOfBoundsException{
        if (index >= size || index < 0){
            throw new IndexOutOfBoundsException(String.format("Recieved %d, list is %d elements big", index, size));
        }

        ListNode<T> current;
        if (index < (size >> 1)){
            current = head;
            for (int i = 0; i < index; i++){
                current = current.getNext();
            }
        }
        else{
            current = tail;
            for (int i = size - 1; i >= index; i--){
                current = current.getPrevious();
            }
        }
        return current;
    }

    public int size(){
        return size;
    }
    public T get(int index) throws IndexOutOfBoundsException{
        return find(index).getItem();
    }
    public void add(T e, int index) throws IndexOutOfBoundsException{
        ListNode<T> newNode = createNewNode(e);
        
        if (size == 0){
            if (index != 0){
                throw new IndexOutOfBoundsException(String.format("First element must be inserted at 0, attempted inserting at %d", index));
            }

            head = newNode;
            tail = newNode;
        }
        else if (index == 0){
            head.setPrevious(newNode);
            newNode.setNext(head);

            head = newNode;
        }
        else if (index == size){
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
        else{
            ListNode<T> node = find(index);
            newNode.setNext(node);
            newNode.setPrevious(node.getPrevious());
    
            node.getPrevious().setNext(newNode);
            node.setPrevious(newNode);   
        }
        size++;
    }
    public T remove(int index) throws IndexOutOfBoundsException{
        T item;
        if(index == 0){
            item = head.getItem();
            head = head.getNext();
            head.setPrevious(null);
        }
        else if(index == size - 1){
            item = tail.getItem();
            tail = tail.getPrevious();
            tail.setNext(null);
        }
        else{
            ListNode<T> node = find(index);
            item = node.getItem();
            node.getPrevious().setNext(node.getNext());
            node.getNext().setPrevious(node.getPrevious());
        }

        size--;
        return item;
    }
    public void set(int index, T e) throws IndexOutOfBoundsException{
        find(index).setItem(e);
    }

    public void removeAll(){
        head = null;
        tail = null;
        size = 0;
    }

    protected abstract ListNode<T> createNewNode(T data);

    public void forEach(Consumer<T> action){
        if (size == 0){
            return;
        }

        ListNode<T> current = head;
        while(current != null){
            action.accept(current.getItem());
            current = current.getNext();
        }
    }
    public void forEachReverse(Consumer<T> action){
        if (size == 0){
            return;
        }

        ListNode<T> current = tail;
        while (current != null){
            action.accept(current.getItem());
            current = current.getPrevious();
        }
    }

    public ListIterator<T> iterator(int startingAt) throws IndexOutOfBoundsException{
        return new ListItr(startingAt, find(startingAt));
    }

    public ListAccessor<T> accessor(int interPoints) throws IllegalArgumentException{
        return new ListAccessor<T>(this, interPoints + 2);
    }

    private class ListItr implements ListIterator<T>{
        private ListNode<T> lastReturned;
        private ListNode<T> next;
        private int nextIndex;

        private ListItr(int index, ListNode<T> startNode){
            this.nextIndex = index;
            this.next = startNode;
            this.lastReturned = null;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }
        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();

            lastReturned = next;
            next = next.getNext();
            nextIndex++;
            return lastReturned.getItem();
        }
        
        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }
        @Override
        public T previous() {
            if (!hasPrevious()){
                throw new NoSuchElementException();
            }

            lastReturned = next = next.getPrevious() == null ? head : next.getPrevious();
            nextIndex--;
            return lastReturned.getItem();
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }
        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            if (lastReturned == null){
                throw new IllegalStateException();
            }

            ListNode<T> lastNext = lastReturned.getNext();
            ListNode<T> lastPrev = lastReturned.getPrevious();
            if (lastReturned == head){
                head = lastNext;
                lastNext.setPrevious(null);
            }
            else if(lastReturned == tail){
                tail = lastPrev;
                lastPrev.setNext(null);
            }
            else{
                lastPrev.setNext(lastNext);
                lastNext.setPrevious(lastPrev);
            }

            if (next == lastReturned)
                next = lastNext;
            else
                nextIndex--;
            lastReturned = null;
            size--;
        }

        @Override
        public void set(T e) {
            if (lastReturned == null){
                throw new IllegalStateException();
            }
            lastReturned.setItem(e);
        }

        @Override
        public void add(T e) {
            lastReturned = null;
            ListNode<T> newNode = createNewNode(e);
            if (next == null){
                tail.setNext(newNode);
                newNode.setPrevious(tail);
                tail = newNode;
            }
            else if (next == head){
                head.setPrevious(newNode);
                newNode.setNext(head);
                head = newNode;
            }
            else{
                next.getPrevious().setNext(newNode);
                newNode.setPrevious(next.getPrevious());
                next.setPrevious(newNode);
                newNode.setNext(next);
            }
            size++;
            nextIndex++;
        }
    }
}