import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/*
Name: Patrick Tobey
Course: Software Development 1
Date 1/28/2024

Class Name: Main

This class contains the Main method of the LMS application and runs all LMS processes.
This class initializes the book list and starts, runs, and closes the RMS according to user inputs.

The goal of this LMS application is to allow users to add, remove, and see books in the RMS book collection.
 Users will also be able to provide a "books.txt" CSV file with a positive integer ID, book title, and book author.
 Entries in the CSV file will be added to the book list when the program starts.
 */
public class Main {
    private static final List<Book> bookList = new ArrayList<>();
    private static final Set<String> existingIds = new HashSet<>();
    private static final Scanner inputScanner = new Scanner(System.in);
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

    /*
    Method Name: main
    Arguments: args string array
    Returns: void

    This method is the start point of the program and runs the necessary methods to initialize and start the LMS.
    */
    public static void main(String[] args) {
        startLMS();
    }

    /*
    Method Name: listBooks
    Arguments: none
    Returns: void

    This method lists the books inside the book list.
    */
    private static void listBooks() {
        System.out.println("Here's the full book list:\n");
        for (Book book : bookList) {
            book.print();
        }
    }

    /*
    Method Name: listBooks
    Arguments: none
    Returns: void

    This method prompts the user to input a book ID, and it removes the specified book from the book list.
    */
    private static void removeBook() {

        String selection = "";
        boolean loop = true;

        while (loop) {
            if (!selection.equals("1") && !selection.equals("2")) {
                System.out.println("Please enter 1 if you want to remove a book by its title");
                System.out.println("Please enter 2 if you want to remove a book by its id");
                System.out.println("Please enter 0 if you want to cancel");

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
    Method Name: addBook
    Arguments: none
    Returns: void

    This method prompts the user to enter a title and author, and it creates a new book with that information.
    It then adds the new book to the book list.
    */
    private static void addBook() {
        System.out.println("Please enter the path of the text file that you want to add books from");
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

                    if (getExistingIds().contains(id)) {
                        bookList.add(new Book(title, author));
                    } else {
                        bookList.add(new Book(id, title, author));
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
                    for (Book book : bookList) {
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
                    } else if (existingIds.contains(checkInId)) {
                        for (Book book : bookList) {
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
                    for (Book book : bookList) {
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
                    } else if (existingIds.contains(checkInId)) {
                        for (Book book : bookList) {
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

    private static boolean removeBookById() {
        System.out.println("Please enter the number of the book that you want to remove or enter 0 to cancel");
        String removeId = inputScanner.nextLine();
        if (removeId.equals("0")) {
            System.out.println("You successfully cancelled removing a book!\n");
            return false;
        } else if (existingIds.contains(removeId)) {
            for (Book book : bookList) {
                if (removeId.equals(book.getId())) {
                    bookList.remove(book);
                    removeExistingId(removeId);
                    System.out.println(book.getTitle() + " by " + book.getAuthor() + " has been successfully removed from the LMS!\n");
                    listBooks();
                    return false;
                }
            }
        }
        System.out.println("Error: Invalid book number!\n");
        return true;
    }

    private static boolean removeBookByTitle() {
        System.out.println("Please enter the title of the book that you want to remove or enter 0 to cancel");
        String title = inputScanner.nextLine();
        if (title.equals("0")) {
            System.out.println("You successfully cancelled removing a book!\n");
            return false;
        } else {
            ArrayList<Book> removeList = getBooksByTitle(title);
            if (removeList.size() == 1) {
                for (Book book : bookList) {
                    if (title.equals(book.getTitle())) {
                        bookList.remove(book);
                        removeExistingId(book.getId());
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

    public static ArrayList<Book> getBooksByTitle(String title) {
        ArrayList<Book> list = new ArrayList<>();

        for (Book book : bookList) {
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
            System.out.println("    1. Add a book to the LMS");
            System.out.println("    2. Remove a book from the LMS");
            System.out.println("    3. List all books in the LMS");
            System.out.println("    4. Check in a book");
            System.out.println("    5. Check out a book");
            System.out.println("    6. Leave the LMS");

            String userInput = inputScanner.nextLine();

            switch (userInput) {
                case "1" -> addBook();
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
}