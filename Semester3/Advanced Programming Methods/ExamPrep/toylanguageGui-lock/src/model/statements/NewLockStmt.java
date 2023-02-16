package model.statements;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceLockTable;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.type.IntType;
import model.type.Type;
import model.values.IntValue;
import model.values.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLockStmt implements IStmt{
    private final String variable;
    private static final Lock lock = new ReentrantLock();

    public NewLockStmt(String variable) {
        this.variable = variable;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ADTException, ExpressionException {
        lock.lock();
        InterfaceDictionary<String, Value> symbolTable = state.getSymbolTable();
        InterfaceLockTable lockTable = state.getLockTable();
        int freeAddress = lockTable.getFreeValue();
        lockTable.put(freeAddress,-1);
        if(symbolTable.isDefined(variable) && symbolTable.lookUp(variable).getType().equals(new IntType()))
            symbolTable.update(variable,new IntValue(freeAddress));
        else
            throw new ADTException("Variable not declared");
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewLockStmt(variable);
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        if(typeTable.lookUp(variable).equals(new IntType()))
            return typeTable;
        else
            throw new MyException("Var is not of int type!");
    }

    @Override
    public String toString() {
        return String.format("newLock(%s)", variable);
    }
}
