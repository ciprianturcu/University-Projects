import model.Book;
import service.FilterService;
import service.SearchService;
import service.SortService;

import java.util.Arrays;
import java.util.List;

public class BookManagerFacade {
    private BookDatabase database = BookDatabase.getInstance();
    private SearchService searchService = new SearchService();
    private SortService sortService = new SortService();
    private FilterService filterService = new FilterService();
    private CommandManager commandManager = new CommandManager();

    public void addBook(String title, String author, String isbn, int yearPublished) {
        Book book = new Book(title, author, isbn, yearPublished);
        commandManager.executeCommand(new AddBookCommand(book));
    }

    private void insertBookToDatabase(Book book) {

    }

    public List<Book> getAllBooks() {
        return database.getBooks();
    }

    public List<Book> prepareBooksForYearDisplay(int year) {
        Pipeline pipeline = new Pipeline(Arrays.asList(
                new YearFilter(year),
                new TitleSortFilter(),
                new FormatToDisplay()
        ));
        return pipeline.process(getAllBooks());
    }

    public List<Book> searchBooksByTitle(String title) {
        return searchService.findByTitle(title, database.getBooks());
    }

    public List<Book> searchBooksByAuthor(String author) {
        return searchService.findByAuthor(author, database.getBooks());
    }

    public List<Book> searchBooksByISBN(String isbn) {
        return searchService.findByISBN(isbn, database.getBooks());
    }

    public List<Book> sortBooksByTitle() {
        return sortService.sortByTitle(database.getBooks());
    }

    public List<Book> sortBooksByAuthor() {
        return sortService.sortByAuthor(database.getBooks());
    }

    public List<Book> sortBooksByYearPublished() {
        return sortService.sortByYearPublished(database.getBooks());
    }

    public List<Book> filterBooksByAuthor(String author) {
        return filterService.filterByAuthor(author, database.getBooks());
    }

    public List<Book> filterBooksByYear(int year) {
        return filterService.filterByYearPublished(year, database.getBooks());
    }
}