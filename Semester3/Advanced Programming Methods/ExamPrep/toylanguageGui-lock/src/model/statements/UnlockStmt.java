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

public class UnlockStmt implements IStmt{
    private final String variable;
    private static final Lock lock = new ReentrantLock();

    public UnlockStmt(String variable) {
        this.variable = variable;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ADTException, ExpressionException {
        lock.lock();
        InterfaceDictionary<String, Value> symbolTable = state.getSymbolTable();
        InterfaceLockTable lockTable = state.getLockTable();
        if(symbolTable.isDefined(variable))
        {
            if(symbolTable.lookUp(variable).getType().equals(new IntType()))
            {
                IntValue fi = (IntValue) symbolTable.lookUp(variable);
                int foundIndex = fi.getVal();
                if(lockTable.containsKey(foundIndex))
                {
                    if(lockTable.get(foundIndex) == state.getId())
                        lockTable.update(foundIndex,-1);
                }
                else
                    throw new MyException("Index not in the lock table!");
            }
            else
                throw new MyException("Var is not of int type!");
        }
        else
            throw new MyException("Variable is not defined!");
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new UnlockStmt(variable);
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        if(typeTable.lookUp(variable).equals(new IntType()))
            return typeTable;
        else
            throw new MyException("Var is not of type int!");
    }

    @Override
    public String toString() {
        return String.format("unlock(%s)", variable);
    }
}
