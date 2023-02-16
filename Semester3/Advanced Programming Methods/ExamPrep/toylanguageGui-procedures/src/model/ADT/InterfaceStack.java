package model.ADT;

import model.exceptions.ADTException;

import java.util.List;

public interface InterfaceStack <V>{
    V pop();
    void push(V v);
    V top() throws ADTException;

    boolean isEmpty();

    List<V> getAllReversed();

    InterfaceStack<V> clone();
}
