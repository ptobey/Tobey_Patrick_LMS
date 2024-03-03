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
public class BookLibrary {
    private final String id;
    private final String title;
    private final String author;
    private String status;
    private LocalDate dueDate;

    public BookLibrary(String title, String author) {
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
}
