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
import model.values.IntValue;
import model.values.Value;

public class RelationalExp implements Exp{

    Exp expression1;
    Exp expression2;
    String operation;

    public RelationalExp(Exp expression1, Exp expression2, String operation) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

//    private String opDecider(int operation)
//    {
//        if(operation == 1)
//            return "&&";
//        else if(operation == 2)
//            return "||";
//        else if()
//        else
//            return "";
//
//    }

    @Override
    public String toString() {
        return expression1.toString() + " " + operation+ " "+ expression2;

    }

    @Override
    public Value eval(InterfaceDictionary<String, Value> table, InterfaceHeap heap) throws MyException, ExpressionException, ADTException {
        Value value1, value2;
        value1 = expression1.eval(table,heap);
        if(value1.getType().equals(new IntType()))
        {
            value2 = expression2.eval(table,heap);
            if(value2.getType().equals(new IntType()))
            {
                IntValue int1 = (IntValue) value1;
                IntValue int2 = (IntValue) value2;
                int nr1,nr2;
                nr1 = int1.getVal();
                nr2 = int2.getVal();
                switch (operation)
                {
                    case "<": {
                        return new BoolValue(nr1 < nr2);
                    }
                    case "<=":
                    {
                        return new BoolValue(nr1 <= nr2);
                    }
                    case "==":
                    {
                        return new BoolValue(nr1 == nr2);
                    }
                    case "!=":
                    {
                        return new BoolValue(nr1 != nr2);
                    }
                    case ">":
                    {
                        return new BoolValue(nr1 > nr2);
                    }
                    case ">=":
                    {
                        return new BoolValue(nr1 >= nr2);
                    }

                }
            }
            else
                throw new MyException("Second operand is not an integer.");
        }
        else throw new MyException("First operand is not an integer.");
        return null;
    }

    @Override
    public Exp deepCopy() {
        return new RelationalExp(expression1.deepCopy(),expression2.deepCopy(),operation);
    }

    @Override
    public Type typeCheck(InterfaceDictionary<String, Type> typeTable) throws MyException {
        Type type1, type2;
        type1 = expression1.typeCheck(typeTable);
        type2 = expression2.typeCheck(typeTable);
        if(type1.equals(new IntType()))
        {
            if(type2.equals(new IntType()))
                return  new IntType();
            else
                throw new MyException("second operand is not an integer");
        }
        else
            throw new MyException("first operand is not an integer");
    }
}
