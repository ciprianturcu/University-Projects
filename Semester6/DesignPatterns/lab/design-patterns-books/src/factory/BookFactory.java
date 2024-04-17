package factory;

import model.Book;
import model.FictionBook;
import model.NonFictionBook;

public class BookFactory {
    public static Book createBook(String type, String title, String author) {
        switch (type.toLowerCase()) {
            case "fiction":
                return new FictionBook(title, author);
            case "non-fiction":
                return new NonFictionBook(title, author);
            default:
                throw new IllegalArgumentException("Unknown book type " + type);
        }
    }
}