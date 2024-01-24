/*
Name: Patrick Tobey
Course: Software Development 1
Date 1/28/2024

Class Name: Book

This class represents a single book.
This class has three properties: id, title, and author.
All three properties have getters but can only be set by the constructor.

 */
public class Book {
    private String id;
    private String title;
    private String author;

    /*
    The constructor used if an ID needs to be generated for the book
     */
    public Book( String title, String author) {
        String newId;

        this.title = title;
        this.author = author;

        do {
            newId = String.valueOf(Main.incrementIdCounter());
            System.out.println();
        } while(Main.getExistingIds().contains(newId));

        this.id = newId;
        Main.addExistingId(newId);
    }

    /*
    The constructor used if an ID is already created for the book
     */
    public Book( String id, String title, String author) {
        this.title = title;
        this.author = author;
        this.id = id;
        Main.addExistingId(id);
    }

    public String getId() {
        return this.id;
    }
    public String getTitle() {
        return this.title;
    }
    public String getAuthor() {
        return this.author;
    }
}
