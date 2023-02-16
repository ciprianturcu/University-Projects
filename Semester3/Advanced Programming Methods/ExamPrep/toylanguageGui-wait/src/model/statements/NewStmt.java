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


public class NewStmt implements IStmt{

    private final String varName;
    private final Exp expression;

    public NewStmt(String varName, Exp expression) {
        this.varName = varName;

        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, MyException, ADTException, ExpressionException {
        InterfaceDictionary<String, Value> symbolTable = state.getSymbolTable();
        InterfaceHeap heap = state.getHeap();
        if(symbolTable.isDefined(varName))
        {
            Value varValue = symbolTable.lookUp(varName);
            if(varValue.getType() instanceof RefType)
            {
                Value evaluated = expression.eval(symbolTable,state.getHeap());
                Type locationType = ((RefValue) varValue).getLocationType();
                if(locationType.equals(evaluated.getType()))
                {
                    int newPosition = heap.add(evaluated);
                    symbolTable.put(varName,new RefValue(newPosition,locationType));
                    state.setSymbolTable(symbolTable);
                    state.setHeap(heap);
                }
                else
                    throw new StatementException(String.format("%s not of %s", varName, evaluated.getType()));
            }
            else
                throw new StatementException(String.format("%s is not of RefType", varName));
        }
        else
            throw new StatementException(String.format("%s is not in symbol table.", varName));
        return null;
    }

    @Override
    public String toString() {
        return String.format("new(%s, %s)",varName,expression);

    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(varName,expression.deepCopy());
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        Type typeVar = typeTable.lookUp(varName);
        Type typeExp = expression.typeCheck(typeTable);
        if(typeVar.equals(new RefType(typeExp)))
            return typeTable;
        else
            throw new StatementException("NEW stmt: right hand side and left hand side have different types ");
    }
}
