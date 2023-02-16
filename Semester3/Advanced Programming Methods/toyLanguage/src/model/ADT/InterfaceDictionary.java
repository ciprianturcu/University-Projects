package model.ADT;

import model.exceptions.ADTException;
import model.exceptions.MyException;

import java.util.Collection;
import java.util.Set;

public interface InterfaceDictionary<K,V>{
    public void add(K k,V v);
    public V lookUp(K id);
    public boolean isDefined(K id);
    public void update(K id, V value);
    public Set<K> keySet();
    public Collection<V> values();
    void put(K key, V value);
    void remove(K key) throws MyException;
    public InterfaceDictionary<K,V> deepCopy() throws ADTException;

}
