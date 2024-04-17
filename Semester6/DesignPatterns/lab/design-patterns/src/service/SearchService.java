package service;

import model.Book;

import java.util.List;
import java.util.stream.Collectors;

public class SearchService {
    public List<Book> findByTitle(String title, List<Book> books) {
        return books.stream().filter(b -> b.getTitle().equalsIgnoreCase(title)).collect(Collectors.toList());
    }

    public List<Book> findByAuthor(String author, List<Book> books) {
        return books.stream().filter(b -> b.getAuthor().equalsIgnoreCase(author)).collect(Collectors.toList());
    }

    public List<Book> findByISBN(String isbn, List<Book> books) {
        return books.stream().filter(b -> b.getIsbn().equalsIgnoreCase(isbn)).collect(Collectors.toList());
    }
}
