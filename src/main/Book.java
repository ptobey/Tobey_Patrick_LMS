package main;

import java.time.LocalDate;
import java.util.Objects;

/**
 * This class represents a single book.
 * It has five properties: id, title, author, genre, status, and dueDate.
 * All six properties have getters, but only the status and dueDate has setters.
 */
public class Book {
    private final String id;
    private final String title;
    private final String author;
    private final String genre;
    private LocalDate dueDate;
    private String status;

    /**
     * The constructor used if an ID needs to be generated for the book
     * @param title The title of the new book.
     * @param author The author of the new book.
     * @param genre The genre of the new book.
     */
    public Book(String title, String author, String genre) {
        String newId;

        this.title = title;
        this.author = author;
        this.genre = genre;

        do {
            newId = String.valueOf(BookLibrary.incrementIdCounter());
            System.out.println();
        } while (BookLibrary.getExistingIds().contains(newId));

        this.id = newId;
        this.status = "Checked In";
        this.dueDate = null;
        BookLibrary.addExistingId(newId);
    }

    /**
     * The constructor used if an ID is already created for the book
     * @param id The id of the new book.
     * @param title The title of the new book.
     * @param author The author of the new book.
     * @param genre The genre of the new book.
     */
    public Book(String id, String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.id = id;
        this.status = "Checked In";
        this.dueDate = null;
        BookLibrary.addExistingId(id);
    }

    /**
     * The constructor used if everything is already created for the book
     * @param id The id of the new book.
     * @param title The title of the new book.
     * @param author The author of the new book.
     * @param genre The genre of the new book.
     * @param status The status of the new book.
     * @param dueDate The due date of the new book.
     */
    public Book(String id, String title, String author, String genre, String status, LocalDate dueDate) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.id = id;
        this.status = status;
        this.dueDate = dueDate;
        BookLibrary.addExistingId(id);
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

    public String getGenre() {
        return this.genre;
    }

    public String getStatus() {
        return this.status;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    /**
     * Processes the checking in of the book by changing its status and resetting its due date.
     */
    public void checkIn() {
        if (Objects.equals(status, "Checked Out")) {
            status = "Checked In";
            dueDate = null;
        } else {
            System.out.println("There was an unexpected error checking in that book. Please contact the LMS developer if you receive this error!");
        }
    }

    /**
     * Processes the checking out of the book by changing its status and setting its due date.
     */
    public void checkOut() {
        if (Objects.equals(status, "Checked In")) {
            status = "Checked Out";
            dueDate = LocalDate.now().plusWeeks(4);
        } else {
            System.out.println("There was an unexpected error checking out that book. Please contact the LMS developer if you receive this error!");
        }
    }
}
