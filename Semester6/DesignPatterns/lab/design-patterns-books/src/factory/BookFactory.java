package factory;

import model.Book;
import model.Product;


public class BookFactory extends ProductFactory {

    @Override
    public Book createProduct(String title, String author, String genre) {
        return new Book(title, author, genre);
    }
}