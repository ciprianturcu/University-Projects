package command;

import controller.ProductController;

public class MenuDisplayArticleCommand extends MenuCommand{
    public MenuDisplayArticleCommand(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute() {
        ProductController.getInstance().displayArticles();
    }
}
