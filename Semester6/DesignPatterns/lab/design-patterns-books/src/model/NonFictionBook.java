package model;


public class NonFictionBook extends Book {
    public NonFictionBook(String title, String author) {
        super(title, author, "Non-Fiction");
    }

    @Override
    public String display() {
        return getTitle() + " by " + getAuthor() + " (Non-Fiction)";
    }
}
