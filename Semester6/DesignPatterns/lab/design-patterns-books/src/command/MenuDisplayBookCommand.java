package command;

import controller.ProductController;

public class MenuDisplayBookCommand extends MenuCommand {
    public MenuDisplayBookCommand(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute() {
        ProductController.getInstance().displayBooks();
    }
}
