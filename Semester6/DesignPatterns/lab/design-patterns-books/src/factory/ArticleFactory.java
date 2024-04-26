package factory;

import lombok.Getter;
import model.Article;
import model.Product;

@Getter
public class ArticleFactory extends ProductFactory {

    @Override
    public Article createProduct(String title, String publication, String field) {
        return new Article(title, publication, field);
    }
}