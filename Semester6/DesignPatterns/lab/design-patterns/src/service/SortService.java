package service;

import model.Book;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortService {
    public List<Book> sortByTitle(List<Book> books) {
        return books.stream().sorted(Comparator.comparing(Book::getTitle)).collect(Collectors.toList());
    }

    public List<Book> sortByAuthor(List<Book> books) {
        return books.stream().sorted(Comparator.comparing(Book::getAuthor)).collect(Collectors.toList());
    }

    public List<Book> sortByYearPublished(List<Book> books) {
        return books.stream().sorted(Comparator.comparing(Book::getYearPublished)).collect(Collectors.toList());
    }
}