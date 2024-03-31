package main;

import java.util.*;

/**
 * This class represents the book library information.
 * It has three properties: bookList, existingIds, and idCounter.
 * All three properties have getters and methods to alter them.
 * The idCounter getter also increments it.
 */
public class BookLibrary {
    private BookLibrary() {}

    private static ArrayList<Book> bookList = new ArrayList<>();
    private static final Set<String> existingIds = new HashSet<>();
    private static int idCounter = 0;

    /**
     * Increments the ID counter and returns the new integer.
     * @return The incremented id value.
     */
    public static int incrementIdCounter() {
        return ++idCounter;
    }

    /**
     * Gets the existing ID list.
     * @return The existing ID list.
     */
    public static Set<String> getExistingIds() {
        return existingIds;
    }

    /**
     * Adds an ID to the existing IDs list.
     * @param id The new ID to add to the existing ID list.
     */
    public static void addExistingId(String id) {
        existingIds.add(id);
    }

    /**
     * Removes an ID from the existing IDs list.
     * @param id The ID to remove from the existing ID list.
     */
    public static void removeExistingId(String id) {
        existingIds.remove(id);
    }

    /**
     * Gets the book list.
     * @return The book list.
     */
    public static ArrayList<Book> getBookList() {
        return bookList;
    }

    /**
     * Adds a book to the book list.
     * @param book The book to add to the book list.
     */
    public static void addBook(Book book) {
        bookList.add(book);
    }

    /**
     * Sets the book list.
     * @param books The book list to set to.
     */
    public static void setBookList(ArrayList<Book> books) {
        bookList = books;
    }

    /**
     * Removes a book from the book list.
     * @param book The book to remove from the book list.
     */
    public static void removeBook(Book book) {
        bookList.remove(book);
    }
}
