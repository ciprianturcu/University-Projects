package model.statements;

import model.ADT.InterfaceDictionary;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.PrgState;
import model.exceptions.StatementException;
import model.type.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException, StatementException, ADTException, ExpressionException;
    String toString();
    IStmt deepCopy();
    InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String,Type> typeTable) throws StatementException, MyException, ADTException;
}
