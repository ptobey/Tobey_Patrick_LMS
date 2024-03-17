import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/*
Name: Patrick Tobey
Course: Software Development 1
Date 3/10/2024

Class Name: Main

This class contains the Main method of the LMS application and runs all LMS processes.
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
    private static void listBooks() {
        System.out.println("Here's the full book list:\n");
        for (Book book : BookLibrary.getBookList()) {
            book.print();
        }
    }

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
    private static void addBooks() {
        System.out.println("Please enter the path of the CSV file that you want to add books from");
        String path = inputScanner.nextLine();
        int lineNumber = 0;
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
                        BookLibrary.addBook(new Book(title, author));
                    } else {
                        BookLibrary.addBook(new Book(id, title, author));
                    }
                } else {
                    System.out.println("Error: The entry on line " + lineNumber + " of the book collection is in an invalid format!\n");
                }
            }
            fileScanner.close();
            System.out.println("The books were added successfully!\n");
            listBooks();
        } catch (FileNotFoundException e) {
            System.out.println("Error: The book collection text file does not exist!\n");
        }
    }

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
                            listBooks();
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
                                listBooks();
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
                            listBooks();
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
                                listBooks();
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
                    listBooks();
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
                        listBooks();
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
    Returns: ArrayList<Book>

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
    private static void startLMS() {
        System.out.println("Welcome to Patrick's LMS!");
        while (true) {
            System.out.println("Please select an option by replying with its associated number:");
            System.out.println("    1. Add books to the LMS");
            System.out.println("    2. Remove a book from the LMS");
            System.out.println("    3. List all books in the LMS");
            System.out.println("    4. Check in a book");
            System.out.println("    5. Check out a book");
            System.out.println("    6. Leave the LMS");

            String userInput = inputScanner.nextLine();

            switch (userInput) {
                case "1" -> addBooks();
                case "2" -> removeBook();
                case "3" -> listBooks();
                case "4" -> checkInBook();
                case "5" -> checkOutBook();
                case "6" -> {
                    System.out.println("Thank you for using Patrick's LMS");
                    System.out.println("Good bye!");
                    return;
                }
                default -> {
                    System.out.println("Error: Unknown option!");
                    System.out.println("Please try again!\n");
                }
            }
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        ScreenController.setStage(stage);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-screen.fxml")));
        stage.setScene(new Scene(root));
        stage.show();
    }
}