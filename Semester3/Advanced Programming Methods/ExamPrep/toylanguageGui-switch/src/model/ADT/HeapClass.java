package model.ADT;

import model.exceptions.ADTException;
import model.values.Value;

import java.util.HashMap;
import java.util.Set;

public class HeapClass implements InterfaceHeap{

    HashMap<Integer,Value> heap;
    Integer freeLocation;

    public HeapClass() {
        this.heap = new HashMap<>();
        this.freeLocation=1;
    }

    public int newLocation()
    {
        freeLocation+=1;
        /*while(freeLocation==0 || heap.containsKey(freeLocation))
        {
            freeLocation+=1;
        }*/
        return freeLocation;

    }

    @Override
    public HashMap<Integer, Value> getContent() {
        return heap;
    }

    @Override
    public void setContent(HashMap<Integer, Value> newMap) {
        this.heap = newMap;
    }

    @Override
    public int add(Value value) {
        heap.put(freeLocation,value);
        Integer returnValue = freeLocation;
        freeLocation = newLocation();
        return returnValue;
    }

    @Override
    public void update(Integer position, Value value) throws ADTException {
        if(!heap.containsKey(position))
        {
            throw new ADTException(String.format("%d is not in the heap.", position));
        }
        heap.put(position,value);
    }

    @Override
    public void remove(Integer position) throws ADTException {
        if(!heap.containsKey(position))
        {
            throw new ADTException(String.format("%d is not in the heap.", position));
        }
        //freeLocation = position;
        heap.remove(position);
    }

    @Override
    public Value get(Integer position) throws ADTException {
        if(!heap.containsKey(position))
        {
            throw new ADTException(String.format("%d is not in the heap.", position));
        }
        return heap.get(position);
    }

    @Override
    public boolean containsKey(Integer position) {
        return heap.containsKey(position);
    }

    @Override
    public Set<Integer> keySet() {
        return this.heap.keySet();
    }

    @Override
    public int getFreeAddress() {
        return freeLocation;
    }

    @Override
    public String toString() {
        return this.heap.toString();
    }
}
