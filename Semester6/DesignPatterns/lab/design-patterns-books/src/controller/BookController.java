package controller;

import command.AddBookCommand;
import factory.BookFactory;
import lombok.Getter;
import model.Book;
import persistance.Database;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class BookController {
    @Getter
    private static final BookController instance = new BookController();

    private BookController() {
    } // Private constructor

    public void addBook(String type, String title, String author, List<String> decorators) {
        Book book = BookFactory.createBook(type, title, author);
        new AddBookCommand(book, decorators).execute();
    }

    public void displayBooks() {
        Iterator<Book> it = Database.getInstance().iterator();
        if (!it.hasNext()) {
            System.out.println("No books available.");
        } else {
            while (it.hasNext()) {
                Book book = it.next();
                System.out.println(book.display());
            }
        }
    }

}