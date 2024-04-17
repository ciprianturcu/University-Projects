package decorator;

import model.Book;

public class ReservedBookDecorator extends BookDecorator {
    public ReservedBookDecorator(Book book) {
        super(book);
    }

    @Override
    public String display() {
        return decoratedBook.display() + " (Reserved)";
    }
}