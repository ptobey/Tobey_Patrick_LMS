package ui;

import main.Book;
import java.util.ArrayList;

/*
Name: Patrick Tobey
Course: Software Development 1
Date 3/24/2024

Interface Name: ListCallback

This interface provides a change callback method that has an Array List of Books as its argument and returns void.
 */
public interface ListCallback {
    void change(ArrayList<Book> b);
}
