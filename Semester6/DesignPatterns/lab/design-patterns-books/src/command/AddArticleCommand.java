package command;

import model.Article;
import persistance.ArticleDatabase;
import persistance.BookDatabase;

public class AddArticleCommand implements ProductCommand{

    private final Article article;

    public AddArticleCommand(Article article) {
        this.article = article;
    }

    @Override
    public void execute() {
        ArticleDatabase.getInstance().addArticle(article);
    }
}
