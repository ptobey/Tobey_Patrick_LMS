import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LMSTests {

    static Book testBook;

    @org.junit.jupiter.api.BeforeAll
    static void setUp() {
        testBook = new Book("1", "Test1", "Patrick Tobey");
        testBook.checkOut();
        BookLibrary.addBook(testBook);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Add Book Test")
    void addBook() {
        assertEquals(testBook, BookLibrary.getBookList().get(0));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Check Out Book Test")
    void checkOutBook() {
        assertEquals("Checked Out", BookLibrary.getBookList().get(0).getStatus());
        assertEquals(LocalDate.now().plusWeeks(4), BookLibrary.getBookList().get(0).getDueDate());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Check In Book Test")
    void checkInBook() {
        testBook.checkIn();
        assertEquals("Checked In", BookLibrary.getBookList().get(0).getStatus());
        assertNull(BookLibrary.getBookList().get(0).getDueDate());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Remove Book Test")
    void removeBook() {
        BookLibrary.removeBook(testBook);
        assertEquals(0, BookLibrary.getBookList().size());
    }
}