package controller;

import command.AddArticleCommand;
import command.AddBookCommand;
import factory.ArticleFactory;
import factory.BookFactory;
import lombok.Getter;
import model.Article;
import model.Book;
import persistance.ArticleDatabase;
import persistance.BookDatabase;

import java.util.Iterator;
import java.util.List;

public class ProductController {
    @Getter
    private static final ProductController instance = new ProductController();
    private final BookFactory bookFactory = new BookFactory();
    private final ArticleFactory articleFactory = new ArticleFactory();

    private ProductController() {
    } // Private constructor

    public void addProduct(String type, String title, String authorOrPublisher, String genreOrField, List<String> decorators) {
        if(type.equalsIgnoreCase("book"))
        {
            Book book = bookFactory.createProduct(title,authorOrPublisher, genreOrField);
            new AddBookCommand(book, decorators).execute();
        }
        else if(type.equalsIgnoreCase("article"))
        {
            Article article = articleFactory.createProduct(title, authorOrPublisher, genreOrField);
            new AddArticleCommand(article).execute();
        }
    }

    public void displayBooks() {
        Iterator<Book> it = BookDatabase.getInstance().iterator();
        if (!it.hasNext()) {
            System.out.println("No books available.");
        } else {
            while (it.hasNext()) {
                Book book = it.next();
                System.out.println(book.display());
            }
        }
    }

    public void displayArticles() {
        Iterator<Article> it = ArticleDatabase.getInstance().iterator();
        if (!it.hasNext()) {
            System.out.println("No articles available.");
        } else {
            while (it.hasNext()) {
                Article article = it.next();
                System.out.println(article.display());
            }
        }
    }

}