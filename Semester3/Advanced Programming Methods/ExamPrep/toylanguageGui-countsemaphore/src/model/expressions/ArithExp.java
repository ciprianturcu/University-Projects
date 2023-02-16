package model.expressions;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceHeap;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.type.IntType;
import model.type.Type;
import model.values.IntValue;
import model.exceptions.MyException;
import model.values.Value;

public class ArithExp implements Exp{

    Exp expression1;
    Exp expression2;
    String  operation; // 1-> + ; 2-> - ; 3-> * ; 4-> /

    public ArithExp(Exp expression1, Exp expression2, String operation) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

//    private String opDecider(String operation)
//    {
//        if(operation == 1)
//            return "+";
//        else if(operation == 2)
//            return "-";
//        else if(operation == 3)
//            return "*";
//        else if(operation == 4)
//            return "/";
//        else
//            return "";
//
//    }

    @Override
    public String toString() {
        return expression1.toString() + " "+operation+ " "  + expression2.toString();
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
                    case "+":
                    {
                        return new IntValue(nr1+nr2);
                    }
                    case "-":
                    {
                        return new IntValue(nr1-nr2);
                    }
                    case "*":
                    {
                        return new IntValue(nr1*nr2);
                    }
                    case "/":
                    {
                        if(nr2==0) throw new MyException("Divizion by zero.");
                        else return new IntValue(nr1/nr2);
                    }
                    default:
                    {
                        throw new MyException("Not valid operation");
                    }
                }
            }
            else {
                throw new MyException("Second operand is not an integer.");

            }
        }
        else {throw new MyException("First operand is not an integer.");}
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
