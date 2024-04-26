package factory;

import model.Product;

public abstract class ProductFactory {
    public abstract Product createProduct(String title, String authorOrPublication, String genreOrField);
}
