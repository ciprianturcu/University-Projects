package command;

import controller.BookController;

import java.awt.*;

public class MenuDisplayCommand extends MenuCommand {
    public MenuDisplayCommand(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute() {
        BookController.getInstance().displayBooks();
    }
}
