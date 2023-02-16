package model.statements;

import model.ADT.InterfaceDictionary;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.PrgState;
import model.exceptions.StatementException;
import model.expressions.Exp;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.type.Type;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt{

    private final Exp expression;
    private final String varName;

    public ReadFile(Exp expression, String varName) {
        this.expression = expression;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException, ExpressionException {
        InterfaceDictionary<String, Value> symbolTable = state.getSymbolTable();
        InterfaceDictionary<String, BufferedReader> fileTable = state.getFileTable();

        if(symbolTable.isDefined(varName))
        {
            Value value = symbolTable.lookUp(varName);
            if(value.getType().equals(new IntType()))
            {
                Value fileNameValue = expression.eval(symbolTable,state.getHeap());
                if(fileNameValue.getType().equals(new StringType()))
                {
                    StringValue fileName = (StringValue) fileNameValue;
                    if(fileTable.isDefined(fileName.getValue()))
                    {
                        BufferedReader br = fileTable.lookUp(fileName.getValue());
                        try
                        {
                            String line  = br.readLine();
                            if(line == null)
                                line = "0";
                            symbolTable.put(varName, new IntValue(Integer.parseInt(line)));

                        } catch (IOException e) {
                            throw new MyException(String.format("Could not read from file %s", fileName));
                        }
                    }
                    else throw new MyException(String.format("The file table does not contain %s", fileName));
                }
                else throw new MyException(String.format("%s does not evaluate to StringType", value));
            }
            else throw new MyException(String.format("%s is not of type IntType", value));
        }
        else throw new MyException(String.format("%s is not present in the symbolTable.", varName));
        return null;
    }

    @Override
    public String toString() {
        return String.format("ReadFile(%s, %s)", expression.toString(), varName);
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFile(expression.deepCopy(),varName);
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeTable) throws StatementException, MyException, ADTException {
        if(!expression.typeCheck(typeTable).equals(new StringType()))
            throw new StatementException("ERROR: ReadFile requires a string as expression parameter");
        if(!typeTable.lookUp(varName).equals(new IntType()))
            throw new StatementException("ERROR: ReadFile requires an int as variable parameter");
        return typeTable;
    }
}
