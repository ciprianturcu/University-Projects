package model.statements;

import javafx.util.Pair;
import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceSemaphoreTable;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.type.IntType;
import model.type.Type;
import model.values.IntValue;
import model.values.Value;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AcquireStmt implements IStmt{
    private final String variable;
    private static final Lock lock =  new ReentrantLock();

    public AcquireStmt(String variable) {
        this.variable = variable;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ADTException, ExpressionException {
        lock.lock();
        InterfaceDictionary<String, Value> symbolTable = state.getSymbolTable();
        InterfaceSemaphoreTable semaphoreTable = state.getSemaphoreTable();
        if(symbolTable.isDefined(variable))
        {
            if(symbolTable.lookUp(variable).getType().equals(new IntType()))
            {
                IntValue fi = (IntValue) symbolTable.lookUp(variable);
                int foundIndex = fi.getVal();
                if(semaphoreTable.getSemaphoreTable().containsKey(foundIndex))
                {
                    Pair<Integer, List<Integer>> foundSemaphore = semaphoreTable.get(foundIndex);
                    int NL = foundSemaphore.getValue().size();
                    int N1 = foundSemaphore.getKey();
                    if(N1>NL)
                    {
                        if(!foundSemaphore.getValue().contains(state.getId()))
                        {
                            foundSemaphore.getValue().add(state.getId());
                            semaphoreTable.update(foundIndex, new Pair<>(N1,foundSemaphore.getValue()));
                        }
                    }
                    else
                    {
                        state.getExeStack().push(this);
                    }
                }
                else
                    throw new StatementException("Index not a key in the semaphore table!");
            }
            else
                throw new StatementException("Index must be of int type!");
        }
        else
            throw new StatementException("Index not in symbol table!");
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AcquireStmt(variable);
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        if(typeTable.lookUp(variable).equals(new IntType()))
            return typeTable;
        else
            throw new MyException(String.format("%s is not int!", variable));
    }

    @Override
    public String toString() {
        return String.format("acquire(%s)", variable);
    }
}
