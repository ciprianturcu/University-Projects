package decorator;

import model.Book;

public abstract class BookDecorator extends Book {
    protected Book decoratedBook;

    public BookDecorator(Book decoratedBook) {
        super(decoratedBook.getTitle(), decoratedBook.getAuthor(), decoratedBook.getGenre());
        this.decoratedBook = decoratedBook;
    }

    @Override
    public abstract String display();
}


