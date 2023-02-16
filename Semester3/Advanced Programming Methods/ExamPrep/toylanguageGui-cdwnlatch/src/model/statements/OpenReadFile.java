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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenReadFile implements IStmt{

    private Exp expression;

    public OpenReadFile(Exp expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException, ExpressionException {
        Value value = expression.eval(state.getSymbolTable(),state.getHeap());
        if(value.getType().equals(new StringType()))
        {
            StringValue fileName = (StringValue) value;
            InterfaceDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if(!fileTable.isDefined(fileName.getValue()))
            {
                BufferedReader br;
                try
                {
                    br = new BufferedReader(new FileReader(fileName.getValue()));

                } catch (FileNotFoundException e) {
                    throw new MyException(String.format("%s could not be opened", fileName.getValue()));
                }
                fileTable.update(fileName.getValue(),br);
            }
            else throw new MyException(String.format("%s is already open", fileName.getValue()));
        }
        else throw new MyException(String.format(String.format("%s does not evaluate to StringType", expression.toString())));
        return null;

    }

    @Override
    public String toString() {
        return String.format("OpenReadFile(%s)", expression.toString());
    }

    @Override
    public IStmt deepCopy() {
        return new OpenReadFile(expression.deepCopy());
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        if(!expression.typeCheck(typeTable).equals(new StringType()))
            throw new StatementException("ERROR: OpenReadFile requires a string expression");
        return typeTable;
    }
}
