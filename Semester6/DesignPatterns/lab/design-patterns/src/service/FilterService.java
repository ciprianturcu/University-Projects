package service;

import model.Book;

import java.util.List;
import java.util.stream.Collectors;

public class FilterService {
    public List<Book> filterByAuthor(String author, List<Book> books) {
        return books.stream().filter(b -> b.getAuthor().equalsIgnoreCase(author)).collect(Collectors.toList());
    }

    public List<Book> filterByYearPublished(int year, List<Book> books) {
        return books.stream().filter(b -> b.getYearPublished() == year).collect(Collectors.toList());
    }
}