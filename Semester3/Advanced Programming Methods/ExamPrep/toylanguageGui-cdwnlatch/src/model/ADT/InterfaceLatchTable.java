package model.ADT;

import model.exceptions.ADTException;

import java.util.HashMap;

public interface InterfaceLatchTable {
    void put(int key, int value) throws ADTException;
    int get(int key) throws ADTException;
    boolean containsKey(int key);
    int getFreeAddress();
    void update(int key,int value) throws ADTException;
    void setFreeAddress(int freeAddress);
    HashMap<Integer,Integer> getLatchTable();
    void setLatchTable(HashMap<Integer,Integer> newLatchTable);
}
