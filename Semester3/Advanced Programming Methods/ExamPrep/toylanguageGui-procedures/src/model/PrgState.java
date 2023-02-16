package model;

import model.ADT.*;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.statements.IStmt;
import model.values.Value;

import java.io.BufferedReader;
import java.util.List;

public class PrgState {
    private InterfaceStack<IStmt> exeStack;
    private InterfaceStack<InterfaceDictionary<String, Value>> symbolTable;
    private InterfaceDictionary<String, BufferedReader> fileTable;
    private InterfaceList<Value> out;
    private InterfaceHeap heap;
    private InterfaceProcedureTable procedureTable;
    private int id;
    private static int lastId =0;
    private IStmt originalProgram;


    public PrgState(InterfaceStack<IStmt> exeStack, InterfaceStack<InterfaceDictionary<String, Value>> symbolTable, InterfaceList<Value> out, IStmt program, InterfaceDictionary<String, BufferedReader> fileTable, InterfaceHeap heap, InterfaceProcedureTable procedureTable) {
        this.exeStack = exeStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap=heap;
        this.procedureTable = procedureTable;
        this.originalProgram = program.deepCopy();
        this.exeStack.push(originalProgram);
        this.id = setId();
    }

    public synchronized int setId()
    {
        lastId++;
        return lastId;
    }

    public synchronized int getId()
    {
        return id;
    }

    public PrgState oneStep() throws MyException, StatementException, ADTException, ExpressionException
    {
        if(exeStack.isEmpty())
            throw new MyException("Program stack is empty.");
        IStmt currentStatement = exeStack.pop();
        return currentStatement.execute(this);
    }

    public InterfaceStack<IStmt > getExeStack() {
        return exeStack;
    }

    public InterfaceStack<InterfaceDictionary<String, Value>> getSymbolTable() {
        return symbolTable;
    }

    public Boolean isNotCompleted()
    {
        return !exeStack.isEmpty();
    }

    public InterfaceList<Value> getOut() {
        return out;
    }

    public InterfaceDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public InterfaceHeap getHeap() {
        return heap;
    }

    public void setHeap(InterfaceHeap heap) {
        this.heap = heap;
    }

    public InterfaceProcedureTable getProcedureTable() {
        return procedureTable;
    }

    public void setProcedureTable(InterfaceProcedureTable procedureTable) {
        this.procedureTable = procedureTable;
    }

    public void setExeStack(InterfaceStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public void setOut(InterfaceList<Value> out) {
        this.out = out;
    }

    public void setSymbolTable(InterfaceStack<InterfaceDictionary<String, Value>> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public InterfaceDictionary<String, Value> getTopSymTable() {
        try {
            return symbolTable.top();
        }
        catch (ADTException e) {
            System.out.println("Stack is empty!");
            return null;
        }
    }
    public void setFileTable(InterfaceDictionary<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public String exeStackToString()
    {
        StringBuilder exeStackStringBuilder = new StringBuilder();
        List<IStmt> stack  = exeStack.getAllReversed();
        for(IStmt statement :stack)
        {
            exeStackStringBuilder.append(statement.toString()).append("\n");
        }
        return exeStackStringBuilder.toString();

    }

    public String symbolTableToString()
    {
        StringBuilder symbolTableStringBuilder = new StringBuilder();
        List<InterfaceDictionary<String,Value>> stack = symbolTable.getAllReversed();
        for(InterfaceDictionary<String,Value> table : stack)
        {
            for(String key : table.keySet())
                symbolTableStringBuilder.append(String.format("%s --> %s\n",key,table.lookUp(key).toString()));
            symbolTableStringBuilder.append("\n");
        }
        return symbolTableStringBuilder.toString();

    }

    public String procedureTableToString() throws ADTException {
        StringBuilder procTableStringBuilder = new StringBuilder();
        for(String key : procedureTable.keySet())
        {
            procTableStringBuilder.append(String.format("%s -> Parameters: %s, Statement %s\n", key, procedureTable.lookUp(key).getKey(),procedureTable.lookUp(key).getValue()));
        }
        return procTableStringBuilder.toString();
    }

    public String outToString()
    {
        StringBuilder outStringBuilder = new StringBuilder();

        for(Value element : out.getList())
        {
            outStringBuilder.append(String.format("%s\n",element.toString()));
        }
        return outStringBuilder.toString();

    }

    public String fileTableToString()
    {
        StringBuilder outStringBuilder = new StringBuilder();

        for(String key : fileTable.keySet())
        {
            outStringBuilder.append(String.format("%s\n",key));
        }
        return outStringBuilder.toString();

    }

    public String heapToString() throws ADTException
    {
        StringBuilder heapStringBuilder = new StringBuilder();
        for(int key : heap.keySet())
        {
            heapStringBuilder.append(String.format("%d --> %s\n",key,heap.get(key)));
        }
        return heapStringBuilder.toString();
    }

    public String programStateToString() throws ADTException {
        return "\n-------------------------------------------------------------------------\n"
                +"\nId: "+ id + "\nExeStack:\n" + exeStackToString() + "\nSymtable:\n" + symbolTableToString() + "\nOut:\n" + outToString()
                + "\nFileTable:\n" + fileTableToString() + "\nHeap:\n" + heapToString()+ "\nProcedure Table:\n" + procedureTableToString();
    }

    @Override
    public String toString() {
        try {
            return "\n-------------------------------------------------------------------------\n"
                    +"\nId: "+ id + "\nExeStack:\n" + exeStackToString() + "\nSymtable:\n" + symbolTableToString() + "\nOut:\n" + outToString()
                    + "\nFileTable:\n" + fileTableToString() + "\nHeap:\n" + heapToString()+ "\nProcedure Table:\n" + procedureTableToString();
        } catch (ADTException e) {
            throw new RuntimeException(e);
        }
    }
}
