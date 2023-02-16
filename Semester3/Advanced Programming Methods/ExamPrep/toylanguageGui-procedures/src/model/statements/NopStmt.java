package model.statements;

import model.ADT.InterfaceDictionary;
import model.exceptions.ADTException;
import model.exceptions.MyException;
import model.PrgState;
import model.exceptions.StatementException;
import model.type.Type;

public class NopStmt implements IStmt{

    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public String toString() {
        return "NopStmt";
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        return typeTable;
    }
}
