package main;

import ui.ScreenLogic;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

/*
Name: Patrick Tobey
Course: Software Development 1
Date 3/10/2024

Class Name: main.Main

This class contains the main.Main method of the LMS application and runs all LMS processes.
This class starts, runs, and closes the RMS according to user inputs.

The goal of this LMS application is to allow users to remove, check in, check out, and see books in the RMS book collection.
Users can also add books by providing a CSV file that contains a positive integer ID, book title, and book on each line.
 */
public class Main extends Application {
    private static final Scanner inputScanner = new Scanner(System.in);

    /*
    Method Name: main
    Arguments: args string array
    Returns: void

    This method is the start point of the program and runs the necessary methods to initialize and start the LMS.
    */


    /*
    Method Name: listBooks
    Arguments: none
    Returns: void

    This method lists the books inside the book list.
    */

    /*
    Method Name: listBooks
    Arguments: none
    Returns: void

    This method prompts the user to choose whether they want to remove a book by its title or id, and calls the appropriate method.
    */
    private static void removeBook() {

        String selection = "";
        boolean loop = true;

        while (loop) {
            if (!selection.equals("1") && !selection.equals("2")) {
                System.out.println("Please select an option by replying with its associated number:");
                System.out.println("    1. Remove a book by its title");
                System.out.println("    2. Remove a book by its number");
                System.out.println("    0. Cancel");

                selection = inputScanner.nextLine();
            }

            switch (selection) {
                case "1" -> loop = removeBookByTitle();
                case "2" -> loop = removeBookById();
                case "0" -> {
                    System.out.println("You successfully cancelled removing a book!\n");
                    return;
                }
                default -> System.out.println("Error: Invalid selection!\n");
            }
        }
    }

    /*
    Method Name: addBooks
    Arguments: none
    Returns: void

    This method prompts the user to enter a file path to a CSV file, and it creates new books with the CSV information.
    It then adds the new books to the book list.
    */

    /*
    Method Name: checkInBook
    Arguments: none
    Returns: void

    This method prompts the user to enter the title of the book they want to check in.
    It then checks the specified book in, or asks for the book number if there are multiple books with the same title and checks that book in.
    */
    private static void checkInBook() {
        while (true) {
            System.out.println("Please enter the title of the book you want to check in or enter 0 to cancel");
            String title = inputScanner.nextLine();

            if (title.equals("0")) {
                System.out.println("You successfully cancelled checking in a book!\n");
                return;
            } else {
                ArrayList<Book> checkInList = getBooksByTitle(title);

                if (checkInList.size() == 1) {
                    for (Book book : BookLibrary.getBookList()) {
                        if (title.equals(book.getTitle()) && book.getStatus().equals("Checked Out")) {
                            book.checkIn();
                            System.out.println(book.getTitle() + " by " + book.getAuthor() + " has been successfully checked in!\n");

                            return;
                        }
                    }
                } else if (checkInList.size() >= 1) {
                    System.out.println("There are multiple books with that title!\n");
                    for (Book book : checkInList) {
                        book.print();
                    }
                    System.out.println("Please enter the number of the book that you want to remove or enter 0 to cancel");
                    String checkInId = inputScanner.nextLine();
                    if (checkInId.equals("0")) {
                        System.out.println("You successfully cancelled checking in a book!\n");
                        return;
                    } else if (BookLibrary.getExistingIds().contains(checkInId)) {
                        for (Book book : BookLibrary.getBookList()) {
                            if (checkInId.equals(book.getId()) && book.getStatus().equals("Checked Out")) {
                                book.checkIn();
                                System.out.println(book.getTitle() + " by " + book.getAuthor() + " has been successfully checked in!\n");
                                return;
                            }
                        }
                    }
                }
                System.out.println("Error: That book is already checked in or does not exist in the LMS!\n");
            }
        }
    }

    /*
    Method Name: checkOutBook
    Arguments: none
    Returns: void

    This method prompts the user to enter the title of the book they want to check out.
    It then checks the specified book out, or asks for the book number if there are multiple books with the same title and checks that book out.
    */
    private static void checkOutBook() {
        while (true) {
            System.out.println("Please enter the title of the book you want to check out or enter 0 to cancel");
            String title = inputScanner.nextLine();

            if (title.equals("0")) {
                System.out.println("You successfully cancelled checking out a book!\n");
                return;
            } else {
                ArrayList<Book> checkOutList = getBooksByTitle(title);

                if (checkOutList.size() == 1) {
                    for (Book book : BookLibrary.getBookList()) {
                        if (title.equals(book.getTitle()) && book.getStatus().equals("Checked In")) {
                            book.checkOut();
                            System.out.println(book.getTitle() + " by " + book.getAuthor() + " has been successfully checked out!\n");
                            return;
                        }
                    }
                } else if (checkOutList.size() >= 1) {
                    System.out.println("There are multiple books with that title!\n");
                    for (Book book : checkOutList) {
                        book.print();
                    }
                    System.out.println("Please enter the number of the book that you want to remove or enter 0 to cancel");
                    String checkInId = inputScanner.nextLine();
                    if (checkInId.equals("0")) {
                        System.out.println("You successfully cancelled checking out a book!\n");
                        return;
                    } else if (BookLibrary.getExistingIds().contains(checkInId)) {
                        for (Book book : BookLibrary.getBookList()) {
                            if (checkInId.equals(book.getId()) && book.getStatus().equals("Checked In")) {
                                book.checkOut();
                                System.out.println(book.getTitle() + " by " + book.getAuthor() + " has been successfully check out!\n");
                                return;
                            }
                        }
                    }
                }
            }
            System.out.println("Error: That book is already checked out or does not exist in the LMS!\n");
        }
    }

    /*
    Method Name: removeBookById
    Arguments: none
    Returns: void

    This method prompts the user to input a book ID, and it removes the specified book from the book list.
    */
    private static boolean removeBookById() {
        System.out.println("Please enter the number of the book that you want to remove or enter 0 to cancel");
        String removeId = inputScanner.nextLine();
        if (removeId.equals("0")) {
            System.out.println("You successfully cancelled removing a book!\n");
            return false;
        } else if (BookLibrary.getExistingIds().contains(removeId)) {
            for (Book book : BookLibrary.getBookList()) {
                if (removeId.equals(book.getId())) {
                    BookLibrary.removeBook(book);
                    BookLibrary.removeExistingId(removeId);
                    System.out.println(book.getTitle() + " by " + book.getAuthor() + " has been successfully removed from the LMS!\n");
                    return false;
                }
            }
        }
        System.out.println("Error: Invalid book number!\n");
        return true;
    }

    /*
    Method Name: removeBookById
    Arguments: none
    Returns: void

    This method prompts the user to input a book title, and it removes the specified book from the book list.
    */
    private static boolean removeBookByTitle() {
        System.out.println("Please enter the title of the book that you want to remove or enter 0 to cancel");
        String title = inputScanner.nextLine();
        if (title.equals("0")) {
            System.out.println("You successfully cancelled removing a book!\n");
            return false;
        } else {
            ArrayList<Book> removeList = getBooksByTitle(title);
            if (removeList.size() == 1) {
                for (Book book : BookLibrary.getBookList()) {
                    if (title.equals(book.getTitle())) {
                        BookLibrary.removeBook(book);
                        BookLibrary.removeExistingId(book.getId());
                        System.out.println(book.getTitle() + " by " + book.getAuthor() + " has been successfully removed from the LMS!\n");
                        return false;
                    }
                }
            } else if (removeList.size() > 1) {
                System.out.println("There are multiple books with that title!\n");
                for (Book book : removeList) {
                    book.print();
                }
                removeBookById();
                return false;
            }
        }
        System.out.println("Error: Invalid book title!\n");
        return true;
    }

    /*
    Method Name: getBooksByTitle
    Arguments: title String
    Returns: ArrayList<main.Book>

    This method returns all books that have the same book title as the title argument.
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

    /*
    Method Name: startLMS
    Arguments: none
    Returns: void

    This method runs the main functions of the LMS based on the user's input.
    */

    @Override
    public void start(Stage stage) throws IOException {
        ScreenLogic.setStage(stage);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../ui/main-screen.fxml")));
        stage.setScene(new Scene(root));
        BusinessLogic.initializeAlerts();
        stage.show();
    }
}