import model.Book;

import java.util.List;
import java.util.stream.Collectors;

public class YearFilter implements Filter {
    private int year;

    public YearFilter(int year) {
        this.year = year;
    }

    @Override
    public List<Book> execute(List<Book> books) {
        return books.stream().filter(book -> book.getYearPublished() == year).collect(Collectors.toList());
    }
}