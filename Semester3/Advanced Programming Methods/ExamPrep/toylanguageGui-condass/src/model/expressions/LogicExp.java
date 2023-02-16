package model.expressions;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceHeap;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.values.BoolValue;
import model.values.Value;

public class LogicExp implements Exp{

    Exp expression1;
    Exp expression2;
    String operation;

    public LogicExp(Exp expression1, Exp expression2, String operation) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public String toString() {
        return expression1.toString() + " " + operation+ " "+ expression2;

    }

    @Override
    public Value eval(InterfaceDictionary<String, Value> table, InterfaceHeap heap) throws MyException, ExpressionException, ADTException {
        Value value1, value2;
        value1 = expression1.eval(table,heap);
        if(value1.getType().equals(new BoolType()))
        {
            value2 = expression2.eval(table,heap);
            if(value2.getType().equals(new BoolType()))
            {
                BoolValue bool1 = (BoolValue) value1;
                BoolValue bool2 = (BoolValue) value2;
                boolean nr1,nr2;
                nr1 = bool1.getVal();
                nr2 = bool2.getVal();
                switch (operation)
                {
                    case "&&": {
                        return new BoolValue(nr1 && nr2);
                    }
                    case "||":
                    {
                        return new BoolValue(nr1 || nr2);
                    }
                }
            }
            else
                throw new MyException("Second operand is not a boolean.");
        }
        else throw new MyException("First operand is not a boolean.");
        return null;
    }

    @Override
    public Exp deepCopy() {
        return new ArithExp(expression1.deepCopy(),expression2.deepCopy(),operation);
    }

    @Override
    public Type typeCheck(InterfaceDictionary<String, Type> typeTable) throws MyException {
        Type type1, type2;
        type1 = expression1.typeCheck(typeTable);
        type2 = expression2.typeCheck(typeTable);
        if(type1.equals(new BoolType()))
        {
            if(type2.equals(new BoolType()))
                return  new BoolType();
            else
                throw new MyException("second operand is not boolean");
        }
        else
            throw new MyException("first operand is not boolean");
    }
}
