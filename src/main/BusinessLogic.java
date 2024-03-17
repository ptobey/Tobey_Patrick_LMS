package main;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BusinessLogic {
    static Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    static Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);

    public static void initializeAlerts() {
        errorAlert.setTitle("Alert Dialog");
        errorAlert.setHeaderText("Error!");

        confirmationAlert.setTitle("Confirmation Dialog");
        confirmationAlert.setHeaderText("Success!");
    }

    public static boolean addBooks(String path) {
        initializeAlerts();
        int lineNumber = 0;
        ArrayList<Book> booksToAdd = new ArrayList<>();
        try {
            Scanner fileScanner = new Scanner(new File(path));
            while (fileScanner.hasNext()) {
                lineNumber++;
                String fileLine = fileScanner.nextLine();
                String[] bookRecords = fileLine.split(",");

                if (bookRecords.length == 3) {
                    String id = bookRecords[0];
                    String title = bookRecords[1];
                    String author = bookRecords[2];

                    if (BookLibrary.getExistingIds().contains(id)) {
                        booksToAdd.add(new Book(title, author));
                    } else {
                        booksToAdd.add(new Book(id, title, author));
                    }
                } else {
                    errorAlert.setContentText("The entry on line " + lineNumber + " of the book collection is in an invalid format!");
                    errorAlert.showAndWait();
                    return false;
                }
            }

            for(Book book : booksToAdd) {
                BookLibrary.addBook(book);
            }

            fileScanner.close();
            confirmationAlert.setContentText("The books were added successfully!");
            confirmationAlert.show();
            return true;
        } catch (FileNotFoundException e) {
            errorAlert.setContentText("That file does not exist!");
            errorAlert.show();
            return false;
        }
    }
}
