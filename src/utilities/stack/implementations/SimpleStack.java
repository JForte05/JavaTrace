package utilities.stack.implementations;

import utilities.stack.StackNode;
import utilities.stack.StackSL;

/**
 * Standard implementation of a stack collection.
 */
public class SimpleStack<T> extends StackSL<T>{

    @Override
    protected StackNode<T> createNewNode(T e) {
        return new SimpleNode(e);
    }

    private class SimpleNode extends StackNode<T>{
        private final T item;

        public SimpleNode(T e){
            this.item = e;
        }

        @Override
        public T getItem() {
            return item;
        }

    }
}
