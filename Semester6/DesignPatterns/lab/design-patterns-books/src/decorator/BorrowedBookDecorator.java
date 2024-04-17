package decorator;

import model.Book;

public class BorrowedBookDecorator extends BookDecorator {
    public BorrowedBookDecorator(Book book) {
        super(book);
    }

    @Override
    public String display() {
        return decoratedBook.display() + " (Borrowed)";
    }
}