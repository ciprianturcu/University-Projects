package model.ADT;

import java.util.List;

public interface InterfaceStack <V>{
    V pop();
    void push(V v);
    V top();

    boolean isEmpty();

    List<V> getAllReversed();
}
