import model.Book;

import java.util.List;

public interface Filter {
    List<Book> execute(List<Book> books);
}