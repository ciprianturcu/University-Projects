package view;

import command.MenuAddProductCommand;
import command.MenuCommand;
import command.MenuDisplayArticleCommand;
import command.MenuDisplayBookCommand;

import java.util.*;

public class View {

    private final Map<String, MenuCommand> commands = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    public View() {
        this.fillCommandsMap();
    }

    private void printMenu() {
        for(var command: this.commands.values()){
            System.out.println(command.getKey() + " " + command.getDescription());
        }
    }

    private void addCommand(MenuCommand c) {
        this.commands.put(c.getKey(), c);
    }

    private void fillCommandsMap() {
        this.addCommand(new MenuAddProductCommand("1", "Add Product"));
        this.addCommand(new MenuDisplayArticleCommand("2", "Display Articles"));
        this.addCommand(new MenuDisplayBookCommand("3", "Display Books"));
    }

    public void run() {
        while (true) {
            printMenu();
            System.out.println("Input the option: ");
            String key = scanner.nextLine();
            MenuCommand com = commands.get(key);
            if (com == null){
                System.out.println("Invalid Option");
                continue;
            }
            try {
                com.execute();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
