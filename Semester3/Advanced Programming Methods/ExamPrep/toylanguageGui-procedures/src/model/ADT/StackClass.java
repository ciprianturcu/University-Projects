package model.ADT;
import model.exceptions.ADTException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class StackClass <T> implements InterfaceStack<T> {

    private Stack<T> stack;

    public StackClass()
    {
        this.stack = new Stack<>();
    }

    public StackClass(Stack<T> stack) {
        this.stack = stack;
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public T top() throws ADTException {
        if(stack.isEmpty())
            throw new ADTException("Stack is empty!");
        return stack.peek();
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public List<T> getAllReversed() {
        List<T> list = Arrays.asList((T[]) stack.toArray());
        Collections.reverse(list);
        return list;
    }

    @Override
    public InterfaceStack<T> clone() {
        return new StackClass<T>((Stack<T>) stack.clone());
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
