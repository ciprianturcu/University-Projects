package model.ADT;

import javafx.util.Pair;
import model.exceptions.ADTException;

import java.util.HashMap;
import java.util.List;

public class SemaphoreTableClass implements InterfaceSemaphoreTable{
    private HashMap<Integer, Pair<Integer, List<Integer>>> semaphoreTable;
    private int freeLocation = 0;

    public SemaphoreTableClass() {
        this.semaphoreTable = new HashMap<>();
    }

    @Override
    public void put(int key, Pair<Integer, List<Integer>> value) throws ADTException {
        synchronized (this)
        {
            if(!semaphoreTable.containsKey(key))
                semaphoreTable.put(key,value);
            else
                throw new ADTException(String.format("Semaphore table already contains the key %d!", key));
        }
    }

    @Override
    public Pair<Integer, List<Integer>> get(int key) throws ADTException {
        synchronized (this)
        {
            if(semaphoreTable.containsKey(key))
                return semaphoreTable.get(key);
            else
                throw new ADTException(String.format("Semaphore table doesn't contain the key %d!", key));
        }
    }

    @Override
    public boolean containsKey(int key) {
        synchronized (this)
        {
            return semaphoreTable.containsKey(key);
        }
    }

    @Override
    public int getFreeAddress() {
        synchronized (this)
        {
            freeLocation++;
            return  freeLocation;
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
        synchronized (this)
        {
            if(semaphoreTable.containsKey(key))
                semaphoreTable.replace(key,value);
            else
                throw new ADTException(String.format("Semaphore table doesn't contain the key %d!", key));
        }
    }

    @Override
    public HashMap<Integer, Pair<Integer, List<Integer>>> getSemaphoreTable() {
        synchronized (this){
            return semaphoreTable;
        }
    }

    @Override
    public void setSemaphoreTable(HashMap<Integer, Pair<Integer, List<Integer>>> newSemaphoreTable) {
        synchronized (this)
        {
            this.semaphoreTable = newSemaphoreTable;
        }
    }

    @Override
    public String toString() {
        return semaphoreTable.toString();
    }
}
