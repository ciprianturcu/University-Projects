package model.statements;

import com.sun.jdi.BooleanValue;
import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceStack;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.expressions.Exp;
import model.type.BoolType;
import model.type.Type;
import model.values.BoolValue;
import model.values.Value;

public class WhileStmt implements IStmt{

    private final Exp expression;
    private final IStmt statement;

    public WhileStmt(Exp expression, IStmt statement) {
        this.expression = expression;
        this.statement = statement;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ADTException, ExpressionException {
        Value value = expression.eval(state.getTopSymTable(),state.getHeap());
        InterfaceStack<IStmt> stack = state.getExeStack();
        if(!value.getType().equals(new BoolType()))
            throw  new StatementException(String.format("%s is not of BoolType",value));
        if(!(value instanceof BoolValue))
            throw new StatementException(String.format("%s is not a BoolValue", value));
        BoolValue boolValue = (BoolValue) value;
        if(boolValue.getVal())
        {
            state.getExeStack().push(this);
            state.getExeStack().push(statement);
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("while(%s){%s}", expression, statement);
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(expression.deepCopy(),statement.deepCopy());
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        Type type = expression.typeCheck(typeTable);
        if(type.equals(new BoolType())) {
            statement.typeCheck(typeTable.deepCopy());
            return typeTable;

        }
        throw new StatementException("The condition of While has not the type bool");

    }
}
