package model.ADT;

import javafx.util.Pair;
import model.exceptions.ADTException;
import model.statements.IStmt;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface InterfaceProcedureTable {
    boolean isDefined(String key);
    void put(String key, Pair<List<String>, IStmt> value);
    Pair<List<String>, IStmt> lookUp(String key) throws ADTException;
    void update(String key,  Pair<List<String>, IStmt> value) throws ADTException;
    Collection< Pair<List<String>, IStmt>> values();
    void remove(String key) throws ADTException;
    Set<String> keySet();
    HashMap<String,  Pair<List<String>, IStmt>> getContent();
    InterfaceDictionary<String, Pair<List<String>, IStmt>> deepCopy() throws ADTException;
}
