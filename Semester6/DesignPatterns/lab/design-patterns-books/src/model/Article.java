package model;

public class Article implements Product{
    private final String title;
    private final String publication;
    private final String field;

    public Article(String title, String publication, String field) {
        this.title = title;
        this.publication = publication;
        this.field = field;
    }

    @Override
    public String display() {
        return "Article: " + title + " by " + publication + ", field: " + field;
    }
}
