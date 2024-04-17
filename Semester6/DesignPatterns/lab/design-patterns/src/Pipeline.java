import model.Book;

import java.util.List;

public class Pipeline {
    private List<Filter> filters;

    public Pipeline(List<Filter> filters) {
        this.filters = filters;
    }

    public List<Book> process(List<Book> input) {
        List<Book> books = input;
        for (Filter filter : filters) {
            books = filter.execute(books);
        }
        return books;
    }
}