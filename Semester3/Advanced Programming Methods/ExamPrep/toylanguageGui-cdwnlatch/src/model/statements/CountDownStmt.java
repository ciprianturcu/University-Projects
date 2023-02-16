package model.statements;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceLatchTable;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.expressions.ValueExp;
import model.type.IntType;
import model.type.Type;
import model.values.IntValue;
import model.values.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownStmt implements IStmt{
    private final String variable;

    private static final Lock lock = new ReentrantLock();

    public CountDownStmt(String variable) {
        this.variable = variable;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ADTException, ExpressionException {
        lock.lock();
        InterfaceDictionary<String, Value> symbolTable = state.getSymbolTable();
        InterfaceLatchTable latchTable = state.getLatchTable();
        if(symbolTable.isDefined(variable))
        {
            IntValue fi = (IntValue) symbolTable.lookUp(variable);
            int foundIndex = fi.getVal();
            if(latchTable.containsKey(foundIndex))
            {
                if(latchTable.get(foundIndex)>0)
                    latchTable.update(foundIndex, latchTable.get(foundIndex)-1);
                state.getExeStack().push(new PrintStmt(new ValueExp(new IntValue(state.getId()))));
            }
            else
                throw new StatementException("Index not found in the latch table! count");
        }
        else
            throw new StatementException("Variable not defined!");
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CountDownStmt(variable);
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        if(typeTable.lookUp(variable).equals(new IntType()))
            return typeTable;
        else
            throw new MyException(String.format("%s is not of int type!", variable));
    }

    @Override
    public String toString() {
        return String.format("countDown(%s)",variable);
    }
}
