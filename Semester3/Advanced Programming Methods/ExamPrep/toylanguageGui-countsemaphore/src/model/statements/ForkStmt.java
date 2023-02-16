package model.statements;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceStack;
import model.ADT.StackClass;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.type.Type;

public class ForkStmt implements IStmt{

    private final IStmt statement;

    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ADTException, ExpressionException {
        InterfaceStack<IStmt> newExeStack = new StackClass<>();
        return new PrgState(newExeStack,state.getSymbolTable().deepCopy(),state.getOut(),statement, state.getFileTable(),state.getHeap(), state.getSemaphoreTable());
    }

    @Override
    public String toString() {
        return String.format("Fork(%s)",statement.toString());

    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(statement.deepCopy());
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        statement.typeCheck(typeTable.deepCopy());
        return typeTable;
    }
}
