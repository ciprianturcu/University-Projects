package model.statements;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceHeap;
import model.ADT.InterfaceLatchTable;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.expressions.Exp;
import model.type.IntType;
import model.type.Type;
import model.values.IntValue;
import model.values.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatchStmt implements IStmt{
    private final String variable;
    private final Exp expression;
    private static final Lock lock = new ReentrantLock();

    public NewLatchStmt(String variable, Exp expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ADTException, ExpressionException {
        lock.lock();
        InterfaceDictionary<String, Value> symbolTable = state.getSymbolTable();
        InterfaceHeap heap = state.getHeap();
        InterfaceLatchTable latchTable = state.getLatchTable();
        IntValue nr = (IntValue) (expression.eval(symbolTable,heap));
        int number = nr.getVal();
        int freeAddress = latchTable.getFreeAddress();
        latchTable.put(freeAddress,number);
        if(symbolTable.isDefined(variable))
            symbolTable.update(variable, new IntValue(freeAddress));
        else
            throw new StatementException(String.format("%s is not defined in the symbol table!", variable));
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewLatchStmt(variable, expression.deepCopy());
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        if(typeTable.lookUp(variable).equals(new IntType()))
        {
            if(expression.typeCheck(typeTable).equals(new IntType()))
                return typeTable;
            else
                throw new MyException("Expression doesn't have the int type!");
        }
        else
            throw new MyException(String.format("%s is not of int type!", variable));
    }

    @Override
    public String toString() {
        return String.format("newLatch(%s,%s)", variable,expression);
    }
}
