package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Book;
import main.BookLibrary;
import main.BusinessLogic;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * This class handles all the controls for the check in/out screen.
 */
public class CheckInOutScreenController implements Initializable {

    ObservableList<Book> bookList = FXCollections.observableArrayList(BookLibrary.getBookList());

    String labelText = "Check in or out a book by its ";

    @FXML
    private Label label;

    @FXML
    private TextField input;

    @FXML
    private TableView<Book> table;

    @FXML
    private TableColumn<Book, String> tableAuthor;

    @FXML
    private TableColumn<Book, String> tableGenre;

    @FXML
    private TableColumn<Book, LocalDate> tableDueDate;

    @FXML
    private TableColumn<Book, String> tableID;

    @FXML
    private TableColumn<Book, String> tableStatus;

    @FXML
    private TableColumn<Book, String> tableTitle;

    /**
     * Runs the method that changes the screen back to the main screen, or it changes the selection type back to "Title" if it is currently "ID."
     */
    @FXML
    void backButtonClicked(ActionEvent event) throws IOException {
        if(input.getPromptText().equals("ID")) {
            input.setPromptText("Title");
            input.setText("");
            table.setItems(bookList);
        }
        else {
            ScreenLogic.changeToMain();
        }
    }

    /**
     * Attempts to check in the specified book.
     */
    @FXML
    void checkInButtonClicked(ActionEvent event) {
        if(BusinessLogic.checkInBook(input.getText(), input.getPromptText(), newBookList -> {
            table.setItems(FXCollections.observableArrayList(newBookList));
            input.setText("");
            input.setPromptText("ID");
            label.setText(labelText+"ID");
        })) {
            input.setText("");
            input.setPromptText("Title");
            label.setText(labelText+"title");
            table.setItems(FXCollections.observableArrayList(BookLibrary.getBookList()));
            table.refresh();
        }
        input.setText("");
    }

    /**
     * Attempts to check out the specified book.
     */
    @FXML
    void checkOutButtonClicked(ActionEvent event) {
        if(BusinessLogic.checkOutBook(input.getText(), input.getPromptText(), newBookList -> {
            table.setItems(FXCollections.observableArrayList(newBookList));
            input.setPromptText("ID");
            label.setText(labelText+"ID");
        })) {
            input.setPromptText("Title");
            label.setText(labelText+"title");
            table.setItems(FXCollections.observableArrayList(BookLibrary.getBookList()));
            table.refresh();
        }
        input.setText("");
    }

    /**
     * Runs to initialize the page, but it is being used here to set up and display the book list in the table.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        tableDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        tableGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        tableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        table.setItems(bookList);
    }
}
