package model;

public class FictionBook extends Book {
    public FictionBook(String title, String author) {
        super(title, author, "Fiction");
    }

    @Override
    public String display() {
        return getTitle() + " by " + getAuthor() + " (Fiction)";
    }
}
