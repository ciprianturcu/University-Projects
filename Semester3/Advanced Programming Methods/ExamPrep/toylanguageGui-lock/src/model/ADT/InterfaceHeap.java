package model.ADT;

import model.exceptions.ADTException;
import model.values.Value;

import java.util.HashMap;
import java.util.Set;

public interface InterfaceHeap {
    HashMap<Integer, Value> getContent();
    void setContent(HashMap<Integer,Value> newMap);
    int add(Value value);
    void update(Integer position, Value value) throws ADTException;
    void remove(Integer position) throws ADTException;
    Value get(Integer position) throws ADTException;
    boolean containsKey(Integer position);
    Set<Integer> keySet();
    int getFreeAddress();

}
