package model.statements;

import model.ADT.InterfaceDictionary;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.PrgState;
import model.exceptions.StatementException;
import model.expressions.Exp;
import model.type.StringType;
import model.type.Type;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFile implements IStmt{

    private Exp expression;

    public CloseReadFile(Exp expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException, ExpressionException {

        Value value = expression.eval(state.getSymbolTable(),state.getHeap());
        if(value.getType().equals(new StringType()))
        {
            StringValue fileName = (StringValue) value;
            InterfaceDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if(fileTable.isDefined(fileName.getValue()))
            {
                BufferedReader br = fileTable.lookUp(fileName.getValue());
                try{
                    br.close();
                } catch (IOException e) {
                    throw new MyException(String.format("Unexpected error in closing %s", value));
                }
                fileTable.remove(fileName.getValue());
                state.setFileTable(fileTable);
            }
            else throw new MyException(String.format("%s is not present in the FileTable", value));
        }
        else throw new MyException(String.format("%s is not of type StringType", expression.toString()));

        return null;
    }

    @Override
    public String toString() {
        return String.format("CloseReadFile(%s)", expression.toString());
    }

    @Override
    public IStmt deepCopy() {
        return new CloseReadFile(expression.deepCopy());
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException {
        if(!expression.typeCheck(typeTable).equals(new StringType()))
            throw new StatementException("ERROR: CloseReadFile requires a string expression");
        return typeTable;
    }
}
