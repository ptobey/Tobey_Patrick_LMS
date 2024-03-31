package ui;

import main.Book;
import java.util.ArrayList;

/**
 * This interface provides a change callback method that has an Array List of Books as its argument and returns void.
 */
public interface ListCallback {
    /**
     * Used to change the shown book list through a callback.
     * @param bookList The new book list.
     */
    void change(ArrayList<Book> bookList);
}
