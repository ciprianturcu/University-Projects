package model.statements;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceHeap;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.expressions.Exp;
import model.type.RefType;
import model.type.Type;
import model.values.RefValue;
import model.values.Value;

import javax.swing.plaf.nimbus.State;

public class HeapWriteStmt implements IStmt{

    private final String varName;
    private final Exp expression;

    public HeapWriteStmt(String varName, Exp expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ADTException, ExpressionException {
        InterfaceDictionary<String, Value> symbolTable = state.getTopSymTable();
        InterfaceHeap heap = state.getHeap();
        if(symbolTable.isDefined(varName))
        {
            Value value  = symbolTable.lookUp(varName);
            if(value.getType() instanceof RefType)
            {
                RefValue refValue = (RefValue) value;
                if(heap.containsKey(refValue.getAddress()))
                {
                    Value evaluated  = expression.eval(symbolTable,heap);
                    if(evaluated.getType().equals(refValue.getLocationType()))
                    {
                        heap.update(refValue.getAddress(),evaluated);
                        state.setHeap(heap);
                    }
                    else throw new StatementException(String.format("%s is not of %s", evaluated, refValue));
                }
                else throw new StatementException(String.format("RefValue %s not defined in the heap.", value));
            }
            else throw new StatementException(String.format("%s is not a RefType", value));

        }
        else
            throw new StatementException(String.format("%s is not defined in the symbol table.", varName));
        return null;
    }

    @Override
    public String toString() {
        return String.format("WriteHeap(%s, %s)", varName, expression);
    }

    @Override
    public IStmt deepCopy() {
        return new HeapWriteStmt(varName,expression.deepCopy());
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        if(typeTable.lookUp(varName).equals(new RefType(expression.typeCheck(typeTable))))
            return typeTable;
        throw new StatementException("HeapWrite: right hand side and left hand side have different types");

    }
}
