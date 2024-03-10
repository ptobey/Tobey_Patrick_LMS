import java.util.*;

/*
Name: Patrick Tobey
Course: Software Development 1
Date 3/10/2024

Class Name: BookLibrary

This class represents the book library information.
This class has three properties: bookList, existingIds, and idCounter.
All three properties have getters and methods to alter them.
The idCounter getter also increments it.
 */
public class BookLibrary {
    private static final ArrayList<Book> bookList = new ArrayList<>();
    private static final Set<String> existingIds = new HashSet<>();
    private static int idCounter = 0;

    /*
    Method Name: incrementIdCounter
    Arguments: none
    Returns: Incremented ID counter integer

    This method increments the ID counter and returns the new integer.
    */
    public static int incrementIdCounter() {
        return ++idCounter;
    }

    public static Set<String> getExistingIds() {
        return existingIds;
    }

    /*
    Method Name: addExistingId
    Arguments: ID string
    Returns: void

    This method adds an ID to the existing IDs list.
    */
    public static void addExistingId(String id) {
        existingIds.add(id);
    }

    /*
    Method Name: removeExistingId
    Arguments: ID string
    Returns: void

    This method removes an ID from the existing IDs list.
    */
    public static void removeExistingId(String id) {
        existingIds.remove(id);
    }

    public static ArrayList<Book> getBookList() {
        return bookList;
    }

    /*
    Method Name: addBook
    Arguments: book string
    Returns: void

    This method adds a book to the book list.
    */
    public static void addBook(Book book) {
        bookList.add(book);
    }

    /*
    Method Name: removeBook
    Arguments: book string
    Returns: void

    This method removes a book from the book list.
    */
    public static void removeBook(Book book) {
        bookList.remove(book);
    }
}
