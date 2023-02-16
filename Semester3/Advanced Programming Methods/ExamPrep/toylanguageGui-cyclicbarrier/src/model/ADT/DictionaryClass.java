package model.ADT;
import model.exceptions.ADTException;
import model.exceptions.MyException;

import java.util.*;

public class DictionaryClass<K,V> implements InterfaceDictionary<K,V>{

    private HashMap<K,V> dictionary;

    public DictionaryClass() {
        this.dictionary= new HashMap<>();
    }

    @Override
    public void add(K k, V v) {
        synchronized (this){
            dictionary.put(k,v);
        }
    }

    public V lookUp(K id)
    {
        synchronized (this){
            return dictionary.get(id);
        }
    }

    @Override
    public boolean isDefined(K id) {
        synchronized (this){
            return dictionary.get(id) != null;
        }
    }

    @Override
    public void update(K id, V value) {
        synchronized (this){
            dictionary.put(id,value);
        }

    }

    @Override
    public Set<K> keySet() {
        synchronized (this){
            return dictionary.keySet();
        }
    }

    @Override
    public Collection<V> values() {
        synchronized (this){
            return dictionary.values();
        }
    }

    @Override
    public void put(K key, V value) {
        synchronized (this){
            this.dictionary.put(key, value);
        }
    }

    @Override
    public void remove(K key) throws MyException {
        synchronized (this) {
            if (!this.isDefined(key))
                throw new MyException(key + " is not defined.");
            this.dictionary.remove(key);
        }
    }

    @Override
    public InterfaceDictionary<K, V> deepCopy() throws ADTException {
        InterfaceDictionary<K,V> toReturn = new DictionaryClass<>();
        for(K key : keySet())
        {
            toReturn.put(key,lookUp(key));
        }
        return toReturn;
    }

    @Override
    public Map<K, V> getContent() {
        synchronized (this){
            return dictionary;
        }
    }

    public String toString() {
        return dictionary.toString();
    }

}
