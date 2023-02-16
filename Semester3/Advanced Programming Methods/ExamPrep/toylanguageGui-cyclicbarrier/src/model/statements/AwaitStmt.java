package model.statements;

import javafx.util.Pair;
import model.ADT.InterfaceBarrierTable;
import model.ADT.InterfaceDictionary;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.type.IntType;
import model.type.Type;
import model.values.IntValue;
import model.values.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStmt implements IStmt{
    private final String variable;
    private static final Lock lock = new ReentrantLock();

    public AwaitStmt(String variable) {
        this.variable = variable;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ADTException, ExpressionException {
        lock.lock();
        InterfaceDictionary<String, Value> symbolTable = state.getSymbolTable();
        InterfaceBarrierTable barrierTable = state.getBarrierTable();
        if(symbolTable.isDefined(variable))
        {
            IntValue f = (IntValue) symbolTable.lookUp(variable);
            int foundIndex = f.getVal();
            if(barrierTable.containsKey(foundIndex))
            {
                Pair<Integer, List<Integer>> foundBarrier = barrierTable.get(foundIndex);
                int NL = foundBarrier.getValue().size();
                int N1 = foundBarrier.getKey();
                ArrayList<Integer> list = (ArrayList<Integer>) foundBarrier.getValue();
                if(N1 > NL)
                {
                    if(list.contains(state.getId()))
                        state.getExeStack().push(this);
                    else
                    {
                        list.add(state.getId());
                        barrierTable.update(foundIndex, new Pair<>(N1,list));
                        state.setBarrierTable(barrierTable);
                    }
                }
            }
            else
                throw new StatementException("Index not in Barrier Table!");
        }
        else
            throw new StatementException("Var is not defined!");
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AwaitStmt(variable);
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        if (typeTable.lookUp(variable).equals(new IntType()))
            return typeTable;
        else
            throw new MyException("Var is not of type int!");
    }

    @Override
    public String toString() {
        return String.format("await(%s)", variable);
    }
}
