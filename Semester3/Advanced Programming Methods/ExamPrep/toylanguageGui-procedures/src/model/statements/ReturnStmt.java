package model.statements;

import model.ADT.InterfaceDictionary;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.type.Type;

public class ReturnStmt implements IStmt{


    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ADTException, ExpressionException {
        state.getSymbolTable().pop();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReturnStmt();
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        return typeTable;
    }

    @Override
    public String toString() {
        return "return";
    }
}
