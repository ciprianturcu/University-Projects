package command;

import controller.BookController;
import util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuAddCommand extends MenuCommand{
    public MenuAddCommand(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter book type (Fiction/Non-Fiction): ");
        String type = Utils.safeNextLine(scanner, "Type cannot be empty.");
        System.out.print("Enter book title: ");
        String title = Utils.safeNextLine(scanner, "Title cannot be empty.");
        System.out.print("Enter book author: ");
        String author = Utils.safeNextLine(scanner, "Author cannot be empty.");

        List<String> decorators = new ArrayList<>();
        System.out.print("Decorate as borrowed? (yes/no): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            decorators.add("borrowed");
        }
        System.out.print("Decorate as reserved? (yes/no): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            decorators.add("reserved");
        }

        BookController.getInstance().addBook(type,title,author, decorators);

    }


}
