package model.expressions;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceHeap;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.type.IntType;
import model.type.Type;
import model.values.Value;

public class MULExpression implements Exp{
    private final Exp expression1;
    private final Exp expression2;

    public MULExpression(Exp expression1, Exp expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public Value eval(InterfaceDictionary<String, Value> table, InterfaceHeap heap) throws MyException, ExpressionException, ADTException {
        Exp converted = new ArithExp(new ArithExp(expression1,expression2,"*"), new ArithExp(expression1,expression2,"+"),"-");
        return converted.eval(table,heap);
    }

    @Override
    public Exp deepCopy() {
        return new MULExpression(expression1.deepCopy(),expression2.deepCopy());
    }

    @Override
    public Type typeCheck(InterfaceDictionary<String, Type> typeTable) throws MyException {
        Type type1 = expression1.typeCheck(typeTable);
        Type type2 = expression2.typeCheck(typeTable);
        if(type1.equals(new IntType()) && type2.equals(new IntType()))
            return new IntType();
        else
            throw new MyException("Expressions int the MUL should be int!");
    }

    @Override
    public String toString() {
        return "MULExpression{" +
                "expression1=" + expression1 +
                ", expression2=" + expression2 +
                '}';
    }
}
