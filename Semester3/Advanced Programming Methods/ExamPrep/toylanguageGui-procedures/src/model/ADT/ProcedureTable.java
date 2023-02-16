package model.ADT;

import javafx.util.Pair;
import model.exceptions.ADTException;
import model.statements.IStmt;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ProcedureTable implements InterfaceProcedureTable{
    private HashMap<String,  Pair<List<String>, IStmt>> procTable;

    public ProcedureTable() {
        this.procTable = new HashMap<>();
    }

    @Override
    public boolean isDefined(String key) {
        synchronized (this)
        {
            return this.procTable.containsKey(key);
        }
    }

    @Override
    public void put(String key, Pair<List<String>, IStmt> value) {
        synchronized (this)
        {
            this.procTable.put(key,value);
        }
    }

    @Override
    public Pair<List<String>, IStmt> lookUp(String key) throws ADTException {
        synchronized (this)
        {
            if(!isDefined(key))
                throw new ADTException(key + "is not defined");
            return this.procTable.get(key);
        }
    }

    @Override
    public void update(String key, Pair<List<String>, IStmt> value) throws ADTException {
        synchronized (this)
        {
            if(!isDefined(key))
                throw new ADTException(key + "is not defined");
            this.procTable.replace(key,value);
        }
    }

    @Override
    public Collection<Pair<List<String>, IStmt>> values() {
        synchronized (this)
        {
            return this.procTable.values();
        }
    }

    @Override
    public void remove(String key) throws ADTException {
        synchronized (this)
        {
            if(!isDefined(key))
                throw new ADTException(key + "is not defined");
            this.procTable.remove(key);
        }
    }

    @Override
    public Set<String> keySet() {
        synchronized (this)
        {
            return procTable.keySet();
        }
    }

    @Override
    public HashMap<String, Pair<List<String>, IStmt>> getContent() {
        synchronized (this)
        {
            return procTable;
        }
    }

    @Override
    public InterfaceDictionary<String, Pair<List<String>, IStmt>> deepCopy() throws ADTException {
        InterfaceDictionary<String, Pair<List<String>, IStmt>> toReturn = new DictionaryClass<>();
        for(String key : keySet())
        {
            try{
                toReturn.put(key,lookUp(key));
            }
            catch(ADTException e)
            {
                throw new ADTException(e.getMessage());
            }
        }
        return toReturn;
    }

    @Override
    public String toString() {
        return procTable.toString();
    }
}
