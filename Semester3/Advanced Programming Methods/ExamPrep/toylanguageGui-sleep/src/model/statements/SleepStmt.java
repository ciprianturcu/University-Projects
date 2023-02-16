package model.statements;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceStack;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.type.Type;

public class SleepStmt implements IStmt{
    private final int value;

    public SleepStmt(int value) {
        this.value = value;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ADTException, ExpressionException {
        if(value!=0) {
            InterfaceStack<IStmt> exeStack = state.getExeStack();
            exeStack.push(new SleepStmt(value-1));
            state.setExeStack(exeStack);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new SleepStmt(value);
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("sleep(%s)", value);
    }
}
