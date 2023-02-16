package model.statements;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceList;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.PrgState;
import model.exceptions.StatementException;
import model.expressions.Exp;
import model.type.Type;
import model.values.Value;

public class PrintStmt implements IStmt{
    Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "print(" + exp.toString()+")";
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        exp.typeCheck(typeTable);
        return typeTable;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException, ExpressionException {
        InterfaceDictionary<String,Value> symbolTable = state.getSymbolTable();
        InterfaceList<Value> out = state.getOut();
        out.add(exp.eval(symbolTable,state.getHeap()));
        return null;
    }
}
