package persistance;

import factory.ArticleFactory;
import lombok.Getter;
import model.Article;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArticleDatabase implements Iterable<Article>{

    @Getter
    private static ArticleDatabase instance = new ArticleDatabase();
    private List<Article> articles = new ArrayList<>();

    private ArticleDatabase() {
    }

    public void addArticle(Article article) {
        articles.add(article);
        System.out.println("Article saved to database: " + article.display());
    }
    @Override
    public Iterator<Article> iterator() {
        return articles.iterator();
    }
}
