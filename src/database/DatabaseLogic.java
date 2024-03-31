package database;
import main.Book;
import main.BookLibrary;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class handles all database interactions.
 */
public class DatabaseLogic {
    static String url = "jdbc:sqlite:C:/Users/patmi/IdeaProjects/Tobey_Patrick_LMS/src/database/lms.db";

    /**
     * Queries the database and sets the book list in memory to database's current book list.
     */
    public static void select() {

        String query = "SELECT * FROM Books;";

        try {
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);
            ArrayList<Book> bookList = new ArrayList<>();
            LocalDate dueDate;

            while (results.next()) {
                String id = results.getString("id");
                String title = results.getString("title");
                String author = results.getString("author");
                String genre = results.getString("genre");
                String status = results.getString("status");
                if(results.getString("dueDate") != null) {
                    dueDate = LocalDate.parse(results.getString("dueDate"));
                }
                else {
                    dueDate = null;
                }
                bookList.add(new Book(id, title, author, genre, status, dueDate));
            }
            connection.close();
            BookLibrary.setBookList(bookList);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts all the books from the bookList argument into the database.
     * @param bookList The list of books to insert into the database.
     */
    public static void insert(ArrayList<Book> bookList) {
        String query = "INSERT INTO Books (id, title, author, genre, status, dueDate) VALUES (?, ?, ?, ?, 'Checked In', null);";

        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(query);
            for(Book book : bookList) {
                statement.setString(1, book.getId());
                statement.setString(2, book.getTitle());
                statement.setString(3, book.getAuthor());
                statement.setString(4, book.getGenre());

                statement.executeUpdate();
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a record from the database depending on the input and choice argument selections.
     * @param input The input string that should match a record in the database.
     * @param choice The choice of title or ID to compare the input to.
     */
    public static void delete(String input, String choice) {
        String query;
        if(choice.equals("Title")) {
            query = "DELETE FROM Books WHERE title = ?";
        }
        else if(choice.equals("ID")) {
            query = "DELETE FROM Books WHERE id = ?";
        }
        else {
            return;
        }

        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, input);
            statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a record's status and dueDate from the database, depending on the input and choice argument selections.
     * @param input The input string that should match a record in the database.
     * @param choice The choice of title or ID to compare the input to.
     * @param status The new status to change the selected book to.
     * @param dueDate The new due date to change the selected book to.
     */
    public static void update(String input, String choice, String status, LocalDate dueDate) {
        String query;
        String date;

        if(dueDate != null) {
            date = dueDate.toString();
        }
        else {
            date = null;
        }
        if (choice.equals("Title")) {
            query = "UPDATE Books SET status = ?, dueDate = ? WHERE title = ?";
        } else if (choice.equals("ID")) {
            query = "UPDATE Books SET status = ?, dueDate = ? WHERE id = ?";
        } else {
            return;
        }

        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, status);
            statement.setString(2, date);
            statement.setString(3, input);

            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
