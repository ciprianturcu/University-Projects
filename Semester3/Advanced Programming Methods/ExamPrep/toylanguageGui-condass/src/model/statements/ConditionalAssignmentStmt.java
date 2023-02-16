package model.statements;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceStack;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.expressions.Exp;
import model.expressions.VarExp;
import model.type.BoolType;
import model.type.Type;

public class ConditionalAssignmentStmt  implements IStmt{
    private final String variable;
    private final Exp expression1;
    private final Exp expression2;
    private final Exp expression3;

    public ConditionalAssignmentStmt(String variable, Exp expression1, Exp expression2, Exp expression3) {
        this.variable = variable;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ADTException, ExpressionException {
        InterfaceStack<IStmt> exeStack = state.getExeStack();
        IStmt convertedToIfStmt = new IfStmt(expression1, new AssignStmt(variable,expression2), new AssignStmt(variable,expression3));
        exeStack.push(convertedToIfStmt);
        state.setExeStack(exeStack);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ConditionalAssignmentStmt(variable, expression1.deepCopy(),expression2.deepCopy(),expression3.deepCopy());
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        Type variableType = new VarExp(variable).typeCheck(typeTable);
        Type typeExp1 = expression1.typeCheck(typeTable);
        Type typeExp2 = expression2.typeCheck(typeTable);
        Type typeExp3 = expression3.typeCheck(typeTable);
        if(typeExp1.equals(new BoolType()) && typeExp2.equals(variableType) && typeExp3.equals(variableType))
        {
            return typeTable;
        }
        else
            throw new StatementException("The conditional assignment is invalid!");
    }

    @Override
    public String toString() {
        return String.format("%s=(%s)? %s:%s", variable,expression1,expression2,expression3);
    }
}
