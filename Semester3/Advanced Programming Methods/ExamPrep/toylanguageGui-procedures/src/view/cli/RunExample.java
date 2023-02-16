package view.cli;

import controller.Controller;
import model.exceptions.ADTException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import view.cli.Command;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class RunExample extends Command {
    private final Controller controller;
    public RunExample(String key, String desc,Controller ctr){
        super(key, desc);
        this.controller = ctr;
    }
    @Override
    public void execute() throws MyException, IOException {
        try{
            System.out.println("Do you want to display the steps?[Y/n]");
            Scanner readOption = new Scanner(System.in);
            String option = readOption.next();
            controller.setDisplayFlag(Objects.equals(option, "Y"));
            controller.allStep();
        } catch (InterruptedException | ADTException | StatementException exception) {
            System.out.println("\u001B[31m" + exception.getMessage() + "\u001B[0m");
        }
    }
}
