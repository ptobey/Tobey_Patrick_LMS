import java.time.LocalDate;
import java.util.Objects;

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
    private final String id;
    private final String title;
    private final String author;
    private String status;
    private LocalDate dueDate;

    /*
    The constructor used if an ID needs to be generated for the book
     */
    public Book(String title, String author) {
        String newId;

        this.title = title;
        this.author = author;

        do {
            newId = String.valueOf(Main.incrementIdCounter());
            System.out.println();
        } while (Main.getExistingIds().contains(newId));

        this.id = newId;
        this.dueDate = null;
        this.status = "Checked In";
        Main.addExistingId(newId);
    }

    /*
    The constructor used if an ID is already created for the book
     */
    public Book(String id, String title, String author) {
        this.title = title;
        this.author = author;
        this.id = id;
        this.dueDate = null;
        this.status = "Checked In";
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

    public String getStatus() {
        return this.status;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public void checkIn() {
        if (Objects.equals(status, "Checked Out")) {
            status = "Checked In";
            dueDate = null;
        } else {
            System.out.println("There was an unexpected error checking in that book. Please contact the LMS developer if you receive this error!");
        }
    }

    public void checkOut() {
        if (Objects.equals(status, "Checked In")) {
            status = "Checked Out";
            dueDate = LocalDate.now().plusWeeks(2);
        } else {
            System.out.println("There was an unexpected error checking out that book. Please contact the LMS developer if you receive this error!");
        }
    }

    public void print() {
        System.out.println("Book #" + id);
        System.out.println(title + " by " + author);
        System.out.println("Status: " + status);
        if (Objects.equals(status, "Checked Out")) {
            System.out.println("Due Date: " + dueDate);
        }
        System.out.println();
    }
}
