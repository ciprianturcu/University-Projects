package command;

import controller.ProductController;
import util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuAddProductCommand extends MenuCommand {
    public MenuAddProductCommand(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter product type (Book/Article): ");
        String type = Utils.safeNextLine(scanner, "Type cannot be empty.");
        System.out.print("Enter title: ");
        String title = Utils.safeNextLine(scanner, "Title cannot be empty.");
        System.out.print("Enter author/publisher: ");
        String authorOrPublisher = Utils.safeNextLine(scanner, "Author cannot be empty.");
        System.out.println("Enter genre/field: ");
        String genreOrField = Utils.safeNextLine(scanner, "Genre cannot be empty");
        List<String> decorators = new ArrayList<>();
        if (type.equalsIgnoreCase("book")) {
            System.out.print("Decorate as borrowed? (yes/no): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                decorators.add("borrowed");
            }
            System.out.print("Decorate as reserved? (yes/no): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                decorators.add("reserved");
            }
        }
        ProductController.getInstance().addProduct(type, title, authorOrPublisher, genreOrField, decorators);

    }


}
