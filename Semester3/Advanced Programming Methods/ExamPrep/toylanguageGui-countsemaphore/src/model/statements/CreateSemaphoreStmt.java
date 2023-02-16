package model.statements;

import javafx.util.Pair;
import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceHeap;
import model.ADT.InterfaceSemaphoreTable;
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

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CreateSemaphoreStmt implements IStmt{

    private final String variable;
    private final Exp expression;

    private static final Lock lock = new ReentrantLock();

    public CreateSemaphoreStmt(String variable, Exp expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ADTException, ExpressionException {
        lock.lock();
        InterfaceDictionary<String, Value> symbolTable = state.getSymbolTable();
        InterfaceSemaphoreTable semaphoreTable = state.getSemaphoreTable();
        InterfaceHeap heap = state.getHeap();
        IntValue nr = (IntValue) (expression.eval(symbolTable,heap));
        int number = nr.getVal();
        int freeAddress = semaphoreTable.getFreeAddress();
        semaphoreTable.put(freeAddress,new Pair<>(number, new ArrayList<>()));
        if(symbolTable.isDefined(variable)&& symbolTable.lookUp(variable).getType().equals(new IntType()))
            symbolTable.update(variable,new IntValue(freeAddress));
        else
            throw new StatementException(String.format("Error for variable %s: not defined/does not have int type!", variable));
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CreateSemaphoreStmt(variable,expression.deepCopy());
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        if(typeTable.lookUp(variable).equals(new IntType()))
        {
            if(expression.typeCheck(typeTable).equals(new IntType()))
                return typeTable;
            else
                throw new MyException("Expression is not of int type!");
        }
        else
            throw new MyException(String.format("%s is not of type int!", variable));
    }

    @Override
    public String toString() {
        return String.format("createSemaphore(%s, %s)", variable, expression);
    }
}
