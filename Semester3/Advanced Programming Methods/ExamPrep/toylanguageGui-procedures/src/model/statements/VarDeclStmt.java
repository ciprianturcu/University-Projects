package model.statements;

import model.ADT.InterfaceDictionary;
import model.exceptions.ADTException;
import model.exceptions.MyException;
import model.PrgState;
import model.exceptions.StatementException;
import model.type.Type;
import model.values.Value;

public class VarDeclStmt implements IStmt{
    String name;
    Type type;

    public VarDeclStmt(String v, Type Type) {
        this.name=v;
        this.type=Type;
    }

    @Override
    public String toString() {
        return String.format("%s %s", type.toString(), name);
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name,type);
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        typeTable.add(name,type);
        return typeTable;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException {
        InterfaceDictionary<String, Value> symbolTable = state.getTopSymTable();
        if(symbolTable.isDefined(name))
            throw new MyException("variable already declared.");
        else
            symbolTable.add(name, type.defaultValue());
        return null;
    }
}
