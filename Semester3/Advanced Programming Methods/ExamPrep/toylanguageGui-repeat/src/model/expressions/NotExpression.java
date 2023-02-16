package model.expressions;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceHeap;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.type.Type;
import model.values.BoolValue;
import model.values.Value;

public class NotExpression implements Exp{
    private final Exp expression;

    public NotExpression(Exp expression) {
        this.expression = expression;
    }

    @Override
    public Value eval(InterfaceDictionary<String, Value> table, InterfaceHeap heap) throws MyException, ExpressionException, ADTException {
        BoolValue value  = (BoolValue) expression.eval(table,heap);
        if(!value.getVal())
            return new BoolValue(true);
        else
            return new BoolValue(false);
    }

    @Override
    public Exp deepCopy() {
        return new NotExpression(expression.deepCopy());
    }

    @Override
    public Type typeCheck(InterfaceDictionary<String, Type> typeTable) throws MyException {
        return expression.typeCheck(typeTable);
    }
}
