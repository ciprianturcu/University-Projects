package view.cli;

import controller.Controller;
import model.ADT.*;
import model.exceptions.ADTException;
import model.exceptions.MyException;
import model.PrgState;
import model.exceptions.StatementException;
import model.expressions.*;
import model.statements.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.InterfaceRepository;
import repository.Repository;

import java.io.IOException;

class Interpreter {
    public static void main(String[] args) throws IOException, MyException {

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        /*
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v",
                new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));

        try
        {
            ex1.typeCheck(new DictionaryClass<>());
            PrgState prg1 = new PrgState(new StackClass<>(), new DictionaryClass<>(), new ListClass<>(), ex1, new DictionaryClass<>(), new HeapClass());
            InterfaceRepository repo1 = new Repository(prg1, "log1.txt");
            Controller ctr1 = new Controller(repo1);
            menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        } catch (StatementException | ADTException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(
                new VarDeclStmt("b", new IntType()), new CompStmt(new AssignStmt("a", new ArithExp(
                new ValueExp(new IntValue(2)), new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(
                new IntValue(5)), "*"), "+")), new CompStmt(new AssignStmt("b", new ArithExp(
                new VarExp("a"), new ValueExp(new IntValue(1)), "+")), new PrintStmt(new VarExp("b"))))));


        try
        {
            ex2.typeCheck(new DictionaryClass<>());
            PrgState prg2 = new PrgState(new StackClass<>(), new DictionaryClass<>(), new ListClass<>(), ex2, new DictionaryClass<>(), new HeapClass());
            InterfaceRepository repo2 = new Repository(prg2, "log2.txt");
            Controller ctr2 = new Controller(repo2);
            menu.addCommand(new RunExample("2", ex2.toString(), ctr2));

        } catch (StatementException | ADTException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(
                        new AssignStmt("a", new ValueExp(new BoolValue(true))),
                        new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v",
                                new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                VarExp("v"))))));

        try
        {
            ex3.typeCheck(new DictionaryClass<>());
            PrgState prg3 = new PrgState(new StackClass<>(), new DictionaryClass<>(), new ListClass<>(), ex3, new DictionaryClass<>(), new HeapClass());
            InterfaceRepository repo3 = new Repository(prg3, "log3.txt");
            Controller ctr3 = new Controller(repo3);
            menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        } catch (StatementException | ADTException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }



        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenReadFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")), new CloseReadFile(new VarExp("varf"))))))))));

        try
        {
            ex4.typeCheck(new DictionaryClass<>());
            PrgState prg4 = new PrgState(new StackClass<>(), new DictionaryClass<>(), new ListClass<>(), ex4, new DictionaryClass<>(), new HeapClass());
            InterfaceRepository repo4 = new Repository(prg4, "log4.txt");
            Controller ctr4 = new Controller(repo4);
            menu.addCommand(new RunExample("4", ex4.toString(), ctr4));

        } catch (StatementException | ADTException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }



        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                                new CompStmt(new VarDeclStmt("a", new RefType( new RefType( new IntType()))),
                                        new CompStmt(new NewStmt("a", new VarExp("v")),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new VarExp("a")))))));

        try
        {
            ex5.typeCheck(new DictionaryClass<>());
            PrgState prg5 = new PrgState(new StackClass<>(), new DictionaryClass<>(), new ListClass<>(), ex5, new DictionaryClass<>(), new HeapClass());
            InterfaceRepository repo5 = new Repository(prg5, "log5.txt");
            Controller ctr5 = new Controller(repo5);
            menu.addCommand(new RunExample("5", ex5.toString(), ctr5));

        } catch (StatementException | ADTException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }



        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                        new CompStmt(new NewStmt("a", new VarExp("v")),
                                                new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                        new PrintStmt( new ArithExp(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))), new ValueExp(new IntValue(5)), "+")))))));

        try
        {
            ex6.typeCheck(new DictionaryClass<>());
            PrgState prg6 = new PrgState(new StackClass<>(), new DictionaryClass<>(), new ListClass<>(), ex6, new DictionaryClass<>(), new HeapClass());
            InterfaceRepository repo6 = new Repository(prg6, "log6.txt");
            Controller ctr6 = new Controller(repo6);
            menu.addCommand(new RunExample("6", ex6.toString(), ctr6));

        } catch (StatementException | ADTException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType( new IntType())),
                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                            new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                new CompStmt(new HeapWriteStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp(new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5)),"+"))))));

        try
        {
            ex7.typeCheck(new DictionaryClass<>());
            PrgState prg7 = new PrgState(new StackClass<>(), new DictionaryClass<>(), new ListClass<>(), ex7, new DictionaryClass<>(), new HeapClass());
            InterfaceRepository repo7 = new Repository(prg7, "log7.txt");
            Controller ctr7 = new Controller(repo7);
            menu.addCommand(new RunExample("7", ex7.toString(), ctr7));

        } catch (StatementException | ADTException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                        new CompStmt(new NewStmt("a", new VarExp("v")),
                                                new CompStmt(new NewStmt("v",new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));

        try
        {
            ex8.typeCheck(new DictionaryClass<>());
            PrgState prg8 = new PrgState(new StackClass<>(), new DictionaryClass<>(), new ListClass<>(), ex8, new DictionaryClass<>(), new HeapClass());
            InterfaceRepository repo8 = new Repository(prg8, "log8.txt");
            Controller ctr8 = new Controller(repo8);
            menu.addCommand(new RunExample("8", ex8.toString(), ctr8));

        } catch (StatementException | ADTException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                               new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",new ArithExp(new VarExp("v"),new ValueExp(new IntValue(1)),"-")))),
                                new PrintStmt(new VarExp("v")))));

        try
        {
            ex9.typeCheck(new DictionaryClass<>());
            PrgState prg9 = new PrgState(new StackClass<>(), new DictionaryClass<>(), new ListClass<>(), ex9, new DictionaryClass<>(), new HeapClass());
            InterfaceRepository repo9 = new Repository(prg9, "log9.txt");
            Controller ctr9 = new Controller(repo9);
            menu.addCommand(new RunExample("9", ex9.toString(), ctr9));

        } catch (StatementException | ADTException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt( new VarDeclStmt("a", new RefType(new IntType())),
                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                        new CompStmt(new NewStmt("a",new ValueExp(new IntValue(22))),
                                                new CompStmt(new ForkStmt( new CompStmt( new HeapWriteStmt("a",new ValueExp(new IntValue(30))),new CompStmt( new AssignStmt("v",new ValueExp(new IntValue(32))), new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));

        try
        {
            ex10.typeCheck(new DictionaryClass<>());
            PrgState prg10 = new PrgState(new StackClass<>(), new DictionaryClass<>(), new ListClass<>(), ex10, new DictionaryClass<>(), new HeapClass());
            InterfaceRepository repo10 = new Repository(prg10, "log10.txt");
            Controller ctr10 = new Controller(repo10);
            menu.addCommand(new RunExample("10", ex10.toString(), ctr10));
        } catch (StatementException | ADTException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }*/
        menu.show();
    }
}