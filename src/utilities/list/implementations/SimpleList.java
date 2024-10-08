package utilities.list.implementations;

import utilities.list.ListDL;
import utilities.list.ListNode;

public class SimpleList<T> extends ListDL<T>{
    public SimpleList(){
        super();
    }

    @Override
    protected ListNode<T> createNewNode(T data) {
        return new Node(data);
    }
    
    private class Node extends ListNode<T>{
        private T item;

        public Node(T e){
            item = e;
        }

        @Override
        public T getItem() {
            return item;
        }

        @Override
        public void setItem(T item) {
            this.item = item;
        }
        
    }
}
