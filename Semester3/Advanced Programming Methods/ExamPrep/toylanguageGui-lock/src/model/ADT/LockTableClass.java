package model.ADT;

import model.exceptions.ADTException;

import java.util.HashMap;
import java.util.Set;

public class LockTableClass implements InterfaceLockTable{

    private HashMap<Integer, Integer> lockTable;
    private int freeLocation = 0;

    public LockTableClass() {
        this.lockTable = new HashMap<>();
    }

    @Override
    public int getFreeValue() {
        synchronized (this)
        {
            freeLocation++;
            return freeLocation;
        }
    }

    @Override
    public void put(int key, int value) throws ADTException {
        synchronized (this)
        {
            if(!lockTable.containsKey(key))
                lockTable.put(key,value);
            else
                throw new ADTException(String.format("Lock table already contains the key %d!", key));
        }
    }

    @Override
    public HashMap<Integer, Integer> getContent() {
        synchronized (this)
        {
            return lockTable;
        }
    }

    @Override
    public boolean containsKey(int key) {
        synchronized (this)
        {
            return lockTable.containsKey(key);
        }
    }

    @Override
    public int get(int position) throws ADTException {
        synchronized (this)
        {
            if(!lockTable.containsKey(position))
                throw new ADTException(String.format("%d is not present in the table!", position));
            return lockTable.get(position);
        }
    }

    @Override
    public void update(int position, int value) throws ADTException {
        synchronized (this)
        {
            if(lockTable.containsKey(position))
                lockTable.replace(position,value);
            else
                throw new ADTException(String.format("%d is not present in the table!", position));
        }
    }

    @Override
    public void setContent(HashMap<Integer, Integer> newMap) {
        synchronized (this)
        {
            this.lockTable = newMap;
        }
    }

    @Override
    public Set<Integer> keySet() {
        synchronized (this)
        {
            return lockTable.keySet();
        }
    }

    @Override
    public String toString() {
        return lockTable.toString();
    }
}
