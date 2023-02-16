package model.expressions;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceHeap;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.type.RefType;
import model.type.Type;
import model.values.RefValue;
import model.values.Value;

public class ReadHeapExp implements Exp{

    private Exp expression;

    public ReadHeapExp(Exp expression) {
        this.expression = expression;
    }

    @Override
    public Value eval(InterfaceDictionary<String, Value> table, InterfaceHeap heap) throws MyException, ExpressionException, ADTException {
        Value value = expression.eval(table,heap);
        if(value instanceof RefValue)
        {
            RefValue refValue = (RefValue) value;
            if(heap.containsKey(refValue.getAddress()))
            {
                return heap.get(refValue.getAddress());
            }
            else
                throw new ExpressionException("Address not defined in heap.");
        }
        else
            throw new ExpressionException(String.format("%s is not of RefType",value));
    }

    @Override
    public Exp deepCopy() {
        return new ReadHeapExp(expression.deepCopy());
    }

    @Override
    public Type typeCheck(InterfaceDictionary<String, Type> typeTable) throws MyException {
        Type type = expression.typeCheck(typeTable);
        if(type instanceof RefType)
        {
            RefType refType = (RefType) type;
            return refType.getInner();
        }
        else
            throw new MyException("the readHeap argument is not a Ref Type");

    }

    @Override
    public String toString() {
        return String.format("ReadHeap(%s)",expression);
    }
}
