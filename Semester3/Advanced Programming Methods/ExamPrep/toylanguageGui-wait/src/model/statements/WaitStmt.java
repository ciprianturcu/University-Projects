package model.statements;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceStack;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.expressions.ValueExp;
import model.type.Type;
import model.values.IntValue;

public class WaitStmt implements IStmt{
    private final int value;

    public WaitStmt(int value) {
        this.value = value;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ADTException, ExpressionException {
        if(value != 0)
        {
            InterfaceStack<IStmt> exeStack = state.getExeStack();
            exeStack.push(new CompStmt(new PrintStmt( new ValueExp(new IntValue( value))), new WaitStmt(value - 1)));
            state.setExeStack(exeStack);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WaitStmt(value);
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("wait(%s)",value);
    }
}
