package model.ADT;

import javafx.util.Pair;
import model.exceptions.ADTException;

import java.util.HashMap;
import java.util.List;

public interface InterfaceBarrierTable {
    void put(int key, Pair<Integer, List<Integer>> value) throws ADTException;
    Pair<Integer, List<Integer>> get(int key) throws ADTException;
    boolean containsKey(int key);
    int getFreeAddress();
    void setFreeAddress(int freeAddress);
    void update(int key, Pair<Integer, List<Integer>> value) throws ADTException;
    HashMap<Integer, Pair<Integer, List<Integer>>> getBarrierTable();
    void setBarrierTable(HashMap<Integer, Pair<Integer, List<Integer>>> newBarrierTable);
}
