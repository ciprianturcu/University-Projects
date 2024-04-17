import model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookDatabase {
    private static BookDatabase instance;
    private List<Book> books;

    private BookDatabase() {
        books = new ArrayList<>();
    }

    public static synchronized BookDatabase getInstance() {
        if (instance == null) {
            instance = new BookDatabase();
        }
        return instance;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public List<Book> getBooks() {
        return books;
    }
}
