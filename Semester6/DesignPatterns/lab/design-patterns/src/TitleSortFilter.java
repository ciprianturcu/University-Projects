import model.Book;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TitleSortFilter implements Filter {
    @Override
    public List<Book> execute(List<Book> books) {
        return books.stream().sorted(Comparator.comparing(Book::getTitle)).collect(Collectors.toList());
    }
}
