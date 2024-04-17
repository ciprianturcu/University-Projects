package view;

import command.MenuAddCommand;
import command.MenuCommand;
import command.MenuDisplayCommand;
import model.Book;

import java.util.*;

public class View {

    private Map<String, MenuCommand> commands = new HashMap<>();
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
        this.addCommand(new MenuAddCommand("1", "Add Book"));
        this.addCommand(new MenuDisplayCommand("2", "Display Books"));
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
