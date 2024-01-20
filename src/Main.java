import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private static List<Book> bookList = new ArrayList<>();
    private static Set<String> exisitingIds = new HashSet<>();
    private static Scanner inputScanner = new Scanner(System.in);
    private static int idCounter = 0;

    public static int incrementIdCounter() {
        return ++idCounter;
    }
    public static Set<String> getExistingIds() {
        return exisitingIds;
    }
    public static void addExistingId(String id) {
        exisitingIds.add(id);
    }

    public static void main(String[] args) {
        initializeBookList();
        startLMS();
    }
    private static void initializeBookList() {
        try {
            Scanner fileScanner = new Scanner(new File(".\\src\\books.txt"));
            while(fileScanner.hasNext()) {
                String fileLine = fileScanner.nextLine();
                String[] bookRecords = fileLine.split(",");

                String id = bookRecords[0];
                String title = bookRecords[1];
                String author = bookRecords[2];

                bookList.add(new Book(id, title, author));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Error: The book collection text file does not exist\n");
            e.printStackTrace();
        }
    }
    public static void listBooks() {
        System.out.println("Here's the full book list:\n");
        for(Book book : bookList) {
            System.out.println("Book #"+book.getId());
            System.out.println(book.getTitle()+" by "+book.getAuthor());
            System.out.println();
        }
    }


    public static void removeBook() {
        while(true) {
            System.out.println("Please enter the number of the book that you want to remove or enter 0 to cancel");
            String removeId = inputScanner.nextLine();
            if(Objects.equals("0", removeId)) {
                System.out.println("You successfully cancelled removing a book!\n");
                return;
            }
                for (Book book : bookList) {
                    if (Objects.equals(book.getId(), removeId)) {
                        bookList.remove(book);
                        System.out.println(book.getTitle() + " by " + book.getAuthor() +" has been successfully removed from the LMS!\n");
                        return;
                    }
                }
            System.out.println("Error: Invalid book number!\n");
        }
    }

    public static void addBook() {
        System.out.println("Please enter the title of the book you want to add");
        String title = inputScanner.nextLine();
        System.out.println("Please enter the name of the author of the book you want to add");
        String author = inputScanner.nextLine();

        bookList.add(new Book(title, author));

        System.out.println(title+" by "+author+" has been successfully added to the LMS!\n");
        }
    private static void startLMS() {
        System.out.println("Welcome to Patrick's LMS!");
        while(true) {
            System.out.println("Please select an option by replying with its associated number:");
            System.out.println("    1. Add a book to the LMS");
            System.out.println("    2. Remove a book from the LMS");
            System.out.println("    3. List all books in the LMS");
            System.out.println("    4. Leave the LMS");

            String userInput = inputScanner.nextLine();

            switch(userInput) {
                case "1": {
                    addBook();
                    break;
                }
                case "2": {
                    removeBook();
                    break;
                }
                case "3": {
                    listBooks();
                    break;
                }
                case "4": {
                    System.out.println("Thank you for using Patrick's LMS");
                    System.out.println("Good bye!");
                    return;
                }
                default: {
                    System.out.println("Error: Unknown option");
                    System.out.println("Please try again!\n");
                }
            }
        }
    }
}