package model.ADT;

import javafx.util.Pair;
import model.exceptions.ADTException;

import java.util.HashMap;
import java.util.List;

public class BarrierTableClass implements InterfaceBarrierTable{

    private HashMap<Integer, Pair<Integer,List<Integer>>> barrierTable;
    private int freeLocation = 0;

    public BarrierTableClass() {
        this.barrierTable = new HashMap<>();
    }

    @Override
    public void put(int key, Pair<Integer, List<Integer>> value) throws ADTException {
        synchronized (this)
        {
            if(!barrierTable.containsKey(key))
            {
                barrierTable.put(key,value);
            }
            else
                throw new ADTException(String.format("Barrier table already contains the key %d!", key));
        }
    }

    @Override
    public Pair<Integer, List<Integer>> get(int key) throws ADTException {
        synchronized (this)
        {
            if (barrierTable.containsKey(key)) {
                return barrierTable.get(key);
            } else {
                throw new ADTException(String.format("Barrier table doesn't contain the key %d!", key));
            }
        }
    }

    @Override
    public boolean containsKey(int key) {
        synchronized (this) {
            return barrierTable.containsKey(key);
        }
    }

    @Override
    public int getFreeAddress() {
        synchronized (this) {
            freeLocation++;
            return freeLocation;
        }
    }

    @Override
    public void setFreeAddress(int freeAddress) {
        synchronized (this) {
            this.freeLocation = freeAddress;
        }
    }

    @Override
    public void update(int key, Pair<Integer, List<Integer>> value) throws ADTException {
        synchronized (this) {
            if (barrierTable.containsKey(key)) {
                barrierTable.replace(key, value);
            } else {
                throw new ADTException(String.format("Barrier table doesn't contain key %d!", key));
            }
        }
    }

    @Override
    public HashMap<Integer, Pair<Integer, List<Integer>>> getBarrierTable() {
        synchronized (this) {
            return barrierTable;
        }
    }

    @Override
    public void setBarrierTable(HashMap<Integer, Pair<Integer, List<Integer>>> newBarrierTable) {
        synchronized (this) {
            this.barrierTable = newBarrierTable;
        }
    }

    @Override
    public String toString() {
        return this.barrierTable.toString();
    }
}
