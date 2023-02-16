package model.statements;

import javafx.util.Pair;
import model.ADT.InterfaceBarrierTable;
import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceHeap;
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

public class NewBarrierStmt implements IStmt{
    private final String variable;
    private final Exp expression;
    private static final Lock lock = new ReentrantLock();

    public NewBarrierStmt(String variable, Exp expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ADTException, ExpressionException {
        lock.lock();
        InterfaceBarrierTable barrierTable = state.getBarrierTable();
        InterfaceDictionary<String, Value> symbolTable = state.getSymbolTable();
        InterfaceHeap heap = state.getHeap();
        IntValue number = (IntValue) (expression.eval(symbolTable,heap));
        int nr = number.getVal();
        int freeAddress = barrierTable.getFreeAddress();
        barrierTable.put(freeAddress,new Pair<>(nr, new ArrayList<>()));
        if(symbolTable.isDefined(variable))
            symbolTable.update(variable, new IntValue(freeAddress));
        else
            throw new StatementException(String.format("%s is not defined in the symbol table!", variable));

        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewBarrierStmt(variable,expression.deepCopy());
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        if (typeTable.lookUp(variable).equals(new IntType()))
            if (expression.typeCheck(typeTable).equals(new IntType()))
                return typeTable;
            else
                throw new MyException("Expression is not of type int!");
        else
            throw new MyException("Variable is not of type int!");
    }

    @Override
    public String toString() {
        return String.format("newBarrier(%s, %s)", variable, expression);
    }
}
