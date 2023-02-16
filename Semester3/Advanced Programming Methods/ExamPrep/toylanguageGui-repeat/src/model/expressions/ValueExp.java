package model.expressions;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceHeap;
import model.exceptions.MyException;
import model.type.Type;
import model.values.Value;

public class ValueExp implements Exp{

    Value e;

    public ValueExp(Value e) {
        this.e = e;
    }

    @Override
    public Value eval(InterfaceDictionary<String, Value> table, InterfaceHeap heap) throws MyException {
        return e;
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(e);
    }

    @Override
    public Type typeCheck(InterfaceDictionary<String, Type> typeTable) throws MyException {
        return e.getType();
    }

    @Override
    public String toString() {
        return this.e.toString();
    }
}
