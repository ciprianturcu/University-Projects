package model;

import lombok.Getter;

@Getter
public class Book implements Product{
    private final String title;
    private final String author;
    private final String genre;

    public Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    @Override
    public String display() {
        return title + " by " + author + ", genre: " + genre;
    }
}