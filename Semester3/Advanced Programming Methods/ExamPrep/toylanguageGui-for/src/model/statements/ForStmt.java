package model.statements;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceStack;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.expressions.Exp;
import model.expressions.RelationalExp;
import model.expressions.VarExp;
import model.type.IntType;
import model.type.Type;

public class ForStmt implements IStmt{
    private final String variable;

    private final Exp expression1;

    private final Exp expression2;
    private final Exp expression3;
    private final IStmt statement;

    public ForStmt(String variable, Exp expression1, Exp expression2, Exp expression3, IStmt statement) {
        this.variable = variable;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ADTException, ExpressionException {
        InterfaceStack<IStmt> exeStack = state.getExeStack();
        IStmt converted = new CompStmt(new AssignStmt(variable, expression1),
                                        new WhileStmt(new RelationalExp(new VarExp(variable),expression2,"<"),
                                                                new CompStmt(statement,new AssignStmt(variable,expression3)))
                                        );
        exeStack.push(converted);
        state.setExeStack(exeStack);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ForStmt(variable,expression1.deepCopy(),expression2.deepCopy(),expression3.deepCopy(),statement.deepCopy());
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        Type type1 = expression1.typeCheck(typeTable);
        Type type2 = expression2.typeCheck(typeTable);
        Type type3 = expression3.typeCheck(typeTable);
        if(type1.equals(new IntType()) && type2.equals(new IntType()) && type3.equals(new IntType()))
            return typeTable;
        else
            throw new MyException("The for statement is invalid!");
    }

    @Override
    public String toString() {
        return String.format("for(%s=%s; %s<%s; %s=%s) {%s}", variable, expression1, variable, expression2, variable, expression3, statement);
    }
}
