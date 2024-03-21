package test;

import main.Book;
import main.BookLibrary;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/*
Name: Patrick Tobey
Course: Software Development 1
Date 3/24/2024

Class Name: LSTests

This class tests the functionality of the LMS system.
 */
class LMSTests {

    static Book testBook;

    //This method runs before all the tests and sets up their test data.
    @org.junit.jupiter.api.BeforeAll
    static void setUp() {
        testBook = new Book("1", "Test1", "Patrick Tobey");
        testBook.checkOut();
        BookLibrary.addBook(testBook);
    }

    //This test asserts that books can be added to the book library.
    @org.junit.jupiter.api.Test
    @DisplayName("Add main.Book Test")
    void addBook() {
        assertEquals(testBook, BookLibrary.getBookList().get(0));
    }

    //This test asserts that books can be checked out properly.
    @org.junit.jupiter.api.Test
    @DisplayName("Check Out main.Book Test")
    void checkOutBook() {
        assertEquals("Checked Out", BookLibrary.getBookList().get(0).getStatus());
        assertEquals(LocalDate.now().plusWeeks(4), BookLibrary.getBookList().get(0).getDueDate());
    }

    //This test asserts that books can be checked in properly.
    @org.junit.jupiter.api.Test
    @DisplayName("Check In main.Book Test")
    void checkInBook() {
        testBook.checkIn();
        assertEquals("Checked In", BookLibrary.getBookList().get(0).getStatus());
        assertNull(BookLibrary.getBookList().get(0).getDueDate());
    }

    //This test asserts that books can be removed from the book library.
    @org.junit.jupiter.api.Test
    @DisplayName("Remove main.Book Test")
    void removeBook() {
        BookLibrary.removeBook(testBook);
        assertEquals(0, BookLibrary.getBookList().size());
    }
}