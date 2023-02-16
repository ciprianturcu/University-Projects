package model.expressions;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceHeap;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.type.Type;
import model.values.Value;

public interface Exp {
    Value eval(InterfaceDictionary<String,Value> table, InterfaceHeap heap) throws MyException, ExpressionException, ADTException;
    Exp deepCopy();

    Type typeCheck(InterfaceDictionary<String, Type> typeTable) throws MyException;
}
