import model.Book;

public class AddBookCommand implements Command {
    private BookManagerFacade facade;
    private Book book;

    public AddBookCommand(BookManagerFacade facade, Book book) {
        this.facade = facade;
        this.book = book;
    }

    @Override
    public void execute() {
        facade.insertBookToDatabase(book);
    }

    @Override
    public void undo() {

    }
}
