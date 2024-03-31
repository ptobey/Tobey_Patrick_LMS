package main;

import database.DatabaseLogic;
import javafx.scene.control.Alert;
import ui.ListCallback;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class contains all the business logic to add, remove, check in, and check out books.
 */
public class BusinessLogic {
    private BusinessLogic() {}

    static Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    static Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    static Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);

    /**
     * Initializes all the alerts that the BusinessLogic class uses to communicate important information to the user.
     */
    public static void initializeAlerts() {
        errorAlert.setTitle("Alert Dialog");
        errorAlert.setHeaderText("Error!");

        confirmationAlert.setTitle("Confirmation Dialog");
        confirmationAlert.setHeaderText("Success!");

        informationAlert.setTitle("Information Dialog");
        informationAlert.setHeaderText("Warning!");
    }

    /**
     * Takes in a file path to a CSV file, and it creates new books with that file's CSV information.
     * Adds the new books to the book list if there were no errors with any of the entries.
     * @param path The path of the CSV file that has the new books to add.
     * @return The status of if the method finished successfully.
     */
    public static boolean addBooks(String path) {
        int lineNumber = 0;
        ArrayList<Book> booksToAdd = new ArrayList<>();
        try {
            Scanner fileScanner = new Scanner(new File(path));
            while (fileScanner.hasNext()) {
                lineNumber++;
                String fileLine = fileScanner.nextLine();
                String[] bookRecords = fileLine.split(",");

                if (bookRecords.length == 4) {
                    String id = bookRecords[0];
                    String title = bookRecords[1];
                    String author = bookRecords[2];
                    String genre = bookRecords[3];

                    if (BookLibrary.getExistingIds().contains(id)) {
                        booksToAdd.add(new Book(title, author, genre));
                    } else {
                        booksToAdd.add(new Book(id, title, author, genre));
                    }
                } else {
                    errorAlert.setContentText("The entry on line " + lineNumber + " of the book collection is in an invalid format!");
                    errorAlert.showAndWait();
                    return false;
                }
            }

            fileScanner.close();
            DatabaseLogic.insert(booksToAdd);
            DatabaseLogic.select();
            confirmationAlert.setContentText("The books were added successfully!");
            confirmationAlert.show();
            return true;
        } catch (FileNotFoundException e) {
            errorAlert.setContentText("That file does not exist!");
            errorAlert.show();
            return false;
        }
    }

    /**
     * Removes a book that matches the provided input and choice type (Title or ID).
     * If there are multiple books that share a title with the book that is being removed, this method calls the listChanger callback and inputs the books.
     * @param input The input string that should match a record in the book list.
     * @param choice The choice of title or id to compare the input to.
     * @param listChanger The callback to change the shown book list to a certain set of books if needed.
     * @return The status of if the method finished successfully.
     */
    public static boolean removeBook(String input, String choice, ListCallback listChanger)  {
        if(choice.equals("Title")) {
            ArrayList<Book> removeList = getBooksByTitle(input);
            if (removeList.size() == 1) {
                for (Book book : BookLibrary.getBookList()) {
                    if (input.equals(book.getTitle())) {
                        DatabaseLogic.delete(input, choice);
                        DatabaseLogic.select();
                        BookLibrary.removeExistingId(book.getId());
                        confirmationAlert.setContentText("The book has been removed successfully!");
                        confirmationAlert.show();
                        return true;
                    }
                }
            } else if (removeList.size() > 1) {
                listChanger.change(removeList);
                informationAlert.setContentText("There are multiple books with that title! Please choose the ID of the book you want to remove from the list below.");
                informationAlert.show();
                return false;
            }
            errorAlert.setContentText("Invalid book title!");
            errorAlert.show();
            return false;
        } else if (choice.equals("ID")) {
            for (Book book : BookLibrary.getBookList()) {
                if (input.equals(book.getId())) {
                    DatabaseLogic.delete(input, choice);
                    DatabaseLogic.select();
                    BookLibrary.removeExistingId(book.getId());
                    confirmationAlert.setContentText("The book has been removed successfully!");
                    confirmationAlert.show();
                    return true;
                }
            }
            errorAlert.setContentText("Invalid book number!");
            errorAlert.show();
            return false;
        }
        else {
            errorAlert.setContentText("Unknown category selection!");
            errorAlert.show();
            return false;
        }
    }

    /**
     * Checks out a book that matches the provided input and choice type (Title or ID).
     * If there are multiple books that share a title with the book that is being checked out, this method calls the listChanger callback and inputs the books.
     * @param input The input string that should match a record in the book list.
     * @param choice The choice of title or id to compare the input to.
     * @param listChanger The callback to change the shown book list to a certain set of books if needed.
     * @return The status of if the method finished successfully.
     */
    public static boolean checkOutBook(String input, String choice, ListCallback listChanger) {
        ArrayList<Book> checkOutList = getBooksByTitle(input);

        if(choice.equals("Title")) {

            if (checkOutList.size() == 1) {
                for (Book book : BookLibrary.getBookList()) {
                    if (input.equals(book.getTitle()) && book.getStatus().equals("Checked In")) {
                        DatabaseLogic.update(input, choice, "Checked Out", LocalDate.now().plusWeeks(4));
                        DatabaseLogic.select();
                        confirmationAlert.setContentText("The book has been successfully checked out!");
                        confirmationAlert.show();
                        return true;
                    }
                }
                errorAlert.setContentText("That book is already checked out or does not exist in the LMS!");
                errorAlert.show();
                return false;
            }
            else if (checkOutList.size() >= 1) {
                informationAlert.setContentText("There are multiple books with that title! Please choose the ID of the book you want to check in or out from the list below.");
                informationAlert.show();
                listChanger.change(checkOutList);
                return false;
            }
            else {
                errorAlert.setContentText("That book is already checked out or does not exist in the LMS!");
                errorAlert.show();
                return false;
            }
        }
        else if(choice.equals("ID")) {

            if (BookLibrary.getExistingIds().contains(input)) {
                for (Book book : BookLibrary.getBookList()) {
                    if (input.equals(book.getId()) && book.getStatus().equals("Checked In")) {
                        DatabaseLogic.update(input, choice, "Checked Out", LocalDate.now().plusWeeks(4));
                        DatabaseLogic.select();
                        confirmationAlert.setContentText("The book has been successfully checked out!");
                        confirmationAlert.show();
                        return true;
                    }
                }
                errorAlert.setContentText("That book is already checked out!");
                errorAlert.show();
                return false;
            }
            errorAlert.setContentText("Invalid book number!");
            errorAlert.show();
            return false;

        }
        else {
            errorAlert.setContentText("That book is already checked out or does not exist in the LMS!");
            errorAlert.show();
            return false;
        }
    }

    /**
     * Checks in a book that matches the provided input and choice type (Title or ID).
     * If there are multiple books that share a title with the book that is being checked in, this method calls the listChanger callback and inputs the books.
     * @param input The input string that should match a record in the book list.
     * @param choice The choice of title or id to compare the input to.
     * @param listChanger The callback to change the shown book list to a certain set of books if needed.
     * @return The status of if the method finished successfully.
     */
    public static boolean checkInBook(String input, String choice, ListCallback listChanger) {
        ArrayList<Book> checkOutList = getBooksByTitle(input);

        if(choice.equals("Title")) {

            if (checkOutList.size() == 1) {
                for (Book book : BookLibrary.getBookList()) {
                    if (input.equals(book.getTitle()) && book.getStatus().equals("Checked Out")) {
                        DatabaseLogic.update(input, choice, "Checked In", null);
                        DatabaseLogic.select();
                        confirmationAlert.setContentText("The book has been successfully checked in!");
                        confirmationAlert.show();
                        return true;
                    }
                }
                errorAlert.setContentText("That book is already checked in or does not exist in the LMS!");
                errorAlert.show();
                return false;
            }
            else if (checkOutList.size() >= 1) {
                informationAlert.setContentText("There are multiple books with that title! Please choose the ID of the book you want to check in or out from the list below.");
                informationAlert.show();
                listChanger.change(checkOutList);
                return false;
            }
            else {
                errorAlert.setContentText("That book is already checked in or does not exist in the LMS!");
                errorAlert.show();
                return false;
            }
        }
        else if(choice.equals("ID")) {
            if (BookLibrary.getExistingIds().contains(input)) {
                for (Book book : BookLibrary.getBookList()) {
                    if (input.equals(book.getId()) && book.getStatus().equals("Checked Out")) {
                        DatabaseLogic.update(input, choice, "Checked In", null);
                        DatabaseLogic.select();
                        confirmationAlert.setContentText("The book has been successfully checked in!");
                        confirmationAlert.show();
                        return true;
                    }
                }
                errorAlert.setContentText("That book is already checked in!");
                errorAlert.show();
                return false;
            }
            errorAlert.setContentText("Invalid book number!");
            errorAlert.show();
            return false;
        }
        else {
            errorAlert.setContentText("That book is already checked in or does not exist in the LMS!");
            errorAlert.show();
            return false;
        }
    }

    /**
     * Returns all books that have the same book title as the title argument.
     * @param title The title of the books to find.
     * @return All the books that match with selected title.
     */
    public static ArrayList<Book> getBooksByTitle(String title) {
        ArrayList<Book> list = new ArrayList<>();

        for (Book book : BookLibrary.getBookList()) {
            if (title.equals(book.getTitle())) {
                list.add(book);
            }
        }
        return list;
    }
}
