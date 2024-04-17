package persistance;


import lombok.Getter;
import model.Book;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Database implements Iterable<Book> {
    @Getter
    private static Database instance = new Database();
    private List<Book> books = new ArrayList<>();

    private Database() {}  // Private constructor to ensure Singleton

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book saved to database: " + book.display());
    }

    @Override
    public Iterator<Book> iterator() {
        return books.iterator();
    }
}

