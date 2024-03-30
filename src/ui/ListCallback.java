package ui;

import main.Book;
import java.util.ArrayList;

/**
 * This interface provides a change callback method that has an Array List of Books as its argument and returns void.
 */
public interface ListCallback {
    void change(ArrayList<Book> b);
}
