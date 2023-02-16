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
import model.type.Type;

public class SwitchStmt implements IStmt{
    private final Exp mainExpression;
    private final Exp expression1;
    private final Exp expression2;
    private final IStmt statement1;
    private final IStmt statement2;
    private final IStmt defaultStatement;

    public SwitchStmt(Exp mainExpression, Exp expression1, Exp expression2, IStmt statement1, IStmt statement2, IStmt defaultStatement) {
        this.mainExpression = mainExpression;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.statement1 = statement1;
        this.statement2 = statement2;
        this.defaultStatement = defaultStatement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ADTException, ExpressionException {
        InterfaceStack<IStmt> exeStack = state.getExeStack();
        IStmt convertedToIfStmt = new IfStmt(new RelationalExp(mainExpression,expression1,"=="), statement1 ,new IfStmt(new RelationalExp(mainExpression,expression2,"=="),statement2, defaultStatement));
        exeStack.push(convertedToIfStmt);
        state.setExeStack(exeStack);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new SwitchStmt(mainExpression.deepCopy(),expression1.deepCopy(),expression2.deepCopy(),statement1.deepCopy(),statement2.deepCopy(),defaultStatement.deepCopy());
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        Type mainType = mainExpression.typeCheck(typeTable);
        Type type1 = expression1.typeCheck(typeTable);
        Type type2 = expression2.typeCheck(typeTable);
        if(mainType.equals(type1) && mainType.equals(type2))
        {
            statement1.typeCheck(typeTable.deepCopy());
            statement2.typeCheck(typeTable.deepCopy());
            defaultStatement.typeCheck(typeTable.deepCopy());
            return typeTable;
        }
        else
            throw new StatementException("The expression types don't match in the switch statement!");
    }

    @Override
    public String toString() {
        return String.format("switch(%s){(case(%s): %s)(case(%s): %s)(default: %s)}", mainExpression,expression1,statement1,expression2,statement2,defaultStatement);
    }
}
