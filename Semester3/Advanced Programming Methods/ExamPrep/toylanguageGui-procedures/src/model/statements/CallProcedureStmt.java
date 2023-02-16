package model.statements;

import model.ADT.DictionaryClass;
import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceHeap;
import model.ADT.InterfaceProcedureTable;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.expressions.Exp;
import model.type.Type;
import model.values.Value;

import java.util.List;

public class CallProcedureStmt implements IStmt{
    private final String procedureName;
    private final List<Exp> expressions;

    public CallProcedureStmt(String procedureName, List<Exp> expressions) {
        this.procedureName = procedureName;
        this.expressions = expressions;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ADTException, ExpressionException {
        InterfaceDictionary<String, Value> symbolTable = state.getTopSymTable();
        InterfaceHeap heap = state.getHeap();
        InterfaceProcedureTable procedureTable = state.getProcedureTable();
        if(procedureTable.isDefined(procedureName))
        {
            List<String> variables = procedureTable.lookUp(procedureName).getKey();
            IStmt statement = procedureTable.lookUp(procedureName).getValue();
            InterfaceDictionary<String,Value> newSymbolTable = new DictionaryClass<>();
            for(String variable : variables)
            {
                int id = variables.indexOf(variable);
                newSymbolTable.put(variable,expressions.get(id).eval(symbolTable,heap));
            }
            state.getSymbolTable().push(newSymbolTable);
            state.getExeStack().push(new ReturnStmt());
            state.getExeStack().push(statement);
        }
        else
            throw new StatementException("Procedure not defined!");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CallProcedureStmt(procedureName,expressions);
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("call %s%s", procedureName, expressions.toString());
    }
}
