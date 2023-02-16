package model.statements;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceStack;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.expressions.Exp;
import model.expressions.NotExpression;
import model.type.BoolType;
import model.type.Type;

public class RepeatUntilStmt implements IStmt{
    private final IStmt statement;
    private final Exp expression;

    public RepeatUntilStmt(IStmt statement, Exp expression) {
        this.statement = statement;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ADTException, ExpressionException {
        InterfaceStack<IStmt> exeStack = state.getExeStack();
        IStmt converted = new CompStmt(statement, new WhileStmt(new NotExpression(expression), statement));
        exeStack.push(converted);
        state.setExeStack(exeStack);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new RepeatUntilStmt(statement.deepCopy(),expression.deepCopy());
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        Type type = expression.typeCheck(typeTable);
        if(type.equals(new BoolType()))
        {
            statement.typeCheck(typeTable.deepCopy());
            return typeTable;
        }
        else
            throw new MyException("Expression in the repeat statement must be of Bool type!");
    }

    @Override
    public String toString() {
        return String.format("repeat(%s) until (%s)", statement, expression);
    }
}
