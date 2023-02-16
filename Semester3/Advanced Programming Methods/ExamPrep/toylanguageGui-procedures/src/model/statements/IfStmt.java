package model.statements;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceStack;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.PrgState;
import model.exceptions.StatementException;
import model.expressions.Exp;
import model.type.BoolType;
import model.type.Type;
import model.values.BoolValue;
import model.values.Value;

import java.util.Objects;

public class IfStmt implements IStmt{
    Exp expression;
    IStmt thenStatement;
    IStmt elseStatement;

    public IfStmt(Exp expression, IStmt thenStatement, IStmt elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public String toString() {
        return String.format("if(%s){%s}else{%s}" , expression.toString(), thenStatement.toString(), elseStatement.toString());
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(expression.deepCopy(),thenStatement.deepCopy(),elseStatement.deepCopy());
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        if(!expression.typeCheck(typeTable).equals(new BoolType()))
            throw new StatementException("The condition of If doesn't have the type bool");
        thenStatement.typeCheck(typeTable.deepCopy());
        elseStatement.typeCheck(typeTable.deepCopy());
        return typeTable;

    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException, ExpressionException {
        InterfaceStack<IStmt> stk = state.getExeStack();
        InterfaceDictionary<String,Value> symbolTable = state.getTopSymTable();
        Value condition = expression.eval(symbolTable,state.getHeap());
        if(!Objects.equals(condition.getType(), new BoolType()))
        {
            throw new MyException("conditional expression is not a boolean.");
        }
        else
        {
            BoolValue b1 = (BoolValue) condition;
            if(b1.getVal())
                stk.push(thenStatement);
            else
                stk.push(elseStatement);
        }
        return null;
    }
}
