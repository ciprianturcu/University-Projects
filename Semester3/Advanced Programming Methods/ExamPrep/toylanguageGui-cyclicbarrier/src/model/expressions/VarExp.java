package model.expressions;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceHeap;
import model.exceptions.MyException;
import model.type.Type;
import model.values.Value;

public class VarExp implements Exp{

    String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Value eval(InterfaceDictionary<String, Value> table, InterfaceHeap heap) throws MyException {
        return table.lookUp(id);
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(id);
    }

    @Override
    public Type typeCheck(InterfaceDictionary<String, Type> typeTable) throws MyException {
        return typeTable.lookUp(id);

    }
}
