import model.Book;

import java.util.List;
import java.util.stream.Collectors;

public class FormatToDisplay implements Filter {
    @Override
    public List<Book> execute(List<Book> books) {
        return books.stream().map(book -> new Book(
                "Title: " + book.getTitle(),
                "Author: " + book.getAuthor(),
                "ISBN: " + book.getIsbn(),
                book.getYearPublished()
        )).collect(Collectors.toList());
    }
}