package command;

import decorator.BorrowedBookDecorator;
import decorator.ReservedBookDecorator;
import model.Book;
import persistance.BookDatabase;

import java.util.List;

public class AddBookCommand implements ProductCommand {
    private Book book;

    public AddBookCommand(Book book, List<String> decorators) {
        for (String decorator : decorators) {
            switch (decorator.toLowerCase()) {
                case "borrowed":
                    book = new BorrowedBookDecorator(book);
                    break;
                case "reserved":
                    book = new ReservedBookDecorator(book);
                    break;
                default:
                    System.out.println("No decorator found for: " + decorator);
                    break;
            }
        }
        this.book = book;
    }

    @Override
    public void execute() {
        BookDatabase.getInstance().addBook(book);
    }
}