package model.statements;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceStack;
import model.exceptions.ADTException;
import model.exceptions.MyException;
import model.PrgState;
import model.exceptions.StatementException;
import model.type.Type;

public class CompStmt implements IStmt{
    private IStmt first;
    private IStmt second;


    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    public IStmt getFirst() {
        return first;
    }

    public IStmt getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return String.format("(%s|%s)", first.toString(), second.toString());
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(),second.deepCopy());
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        return second.typeCheck(first.typeCheck(typeTable));
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        InterfaceStack<IStmt> stk = state.getExeStack();
        stk.push(second);
        stk.push(first);
        return null;
    }
}
