package model.statements;

import model.ADT.InterfaceDictionary;
import model.ADT.InterfaceStack;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.PrgState;
import model.exceptions.StatementException;
import model.expressions.Exp;
import model.type.Type;
import model.values.Value;

import javax.swing.plaf.nimbus.State;

public class AssignStmt implements IStmt{
    String id;
    Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    public String getId() {
        return id;
    }

    public Exp getExp() {
        return exp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return String.format("%s = %s", id, exp.toString());
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, exp.deepCopy());
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws MyException, StatementException {
        Type typeVar = typeTable.lookUp(id);
        Type typeExp = exp.typeCheck(typeTable);
        if(typeVar.equals(typeExp))
            return typeTable;
        else
            throw new StatementException("Assignment: right hand side and left hand side have different types ");
    }


    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException, ExpressionException {
        InterfaceStack<IStmt> stk = state.getExeStack();
        InterfaceDictionary<String, Value> symbolTable = state.getTopSymTable();
        if(symbolTable.isDefined(id))
        {
            Value val = exp.eval(symbolTable,state.getHeap());
            Type typeID=(symbolTable.lookUp(id)).getType();
            if(val.getType().equals(typeID))
            {
                symbolTable.update(id,val);
            }
            else{
                throw new MyException("declared type of variable"+id+"and type of the assigned expression do not match.");
            }
        }
        else
        {
            throw new MyException("the used variable" + id + "was not declared before");
        }
        return null;
    }
}
