package model.ADT;

import model.exceptions.ADTException;

import java.util.HashMap;
import java.util.Set;

public interface InterfaceLockTable {
    int getFreeValue();
    void put(int key, int value) throws ADTException;
    HashMap<Integer, Integer> getContent();
    boolean containsKey(int position);
    int get(int position) throws ADTException;
    void update(int position, int value) throws ADTException;
    void setContent(HashMap<Integer, Integer> newMap);
    Set<Integer> keySet();
}
