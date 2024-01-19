import java.util.*;

public class Main {
    private static List<Book> bookList = new ArrayList<>();
    private static Set<Integer> exisitingIds = new HashSet<>();

    private static Scanner inputScanner = new Scanner(System.in);
    private static int idCounter = 0;

    public static int incrementIdCounter() {
        return ++idCounter;
    }
    public static Set<Integer> getExistingIds() {
        return exisitingIds;
    }
    public static void addExistingId(int id) {
        exisitingIds.add(id);
    }

    public static void main(String[] args) {
        initializeBookList();
        startLMS();
    }
    private static void initializeBookList() {
        Book b1 = new Book( "The Great Alone", "Tawnia");
        Book b2 = new Book( "I spy", "Me");

       // List<Book> bookList = new ArrayList<>();

        bookList.add(b1);
        bookList.add(b2);
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
            System.out.println("Please enter the number of the book that you want to remove");
            String removeId = inputScanner.nextLine();
            try {
                for (Book book : bookList) {
                    if (book.getId() == Integer.parseInt(removeId)) {
                        bookList.remove(book);
                        System.out.println(book.getTitle() + " by " + book.getAuthor() +" has been successfully removed from the LMS!\n");
                        return;
                    }
                }
                System.out.println("Error: Invalid book number!\n");
            }
            catch(NumberFormatException e) {
                System.out.println("Error: The user did not provide a number!\n");
            }
        }
    }

    public static void addBook() {
        System.out.println("Please enter the title of the book you want to add");
        String title = inputScanner.nextLine();
        System.out.println("Please enter the name of the author of the book you want to add");
        String author = inputScanner.nextLine();
        Book newBook = new Book( title, author);
        bookList.add(newBook);

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