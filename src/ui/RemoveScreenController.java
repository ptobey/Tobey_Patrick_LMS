package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import main.Book;
import main.BookLibrary;
import main.BusinessLogic;

/*
Name: Patrick Tobey
Course: Software Development 1
Date 3/24/2024

Class Name: RemoveScreenController

This class handles all the controls for the remove screen.
 */
public class RemoveScreenController implements Initializable {

    ObservableList<Book> bookList = FXCollections.observableArrayList(BookLibrary.getBookList());
    ObservableList<String> choiceList = FXCollections.observableArrayList("ID", "Title");

    @FXML
    private ChoiceBox<String> categoryChoice;

    @FXML
    private TextField input;

    @FXML
    private TableView<Book> table;

    @FXML
    private TableColumn<Book, String> tableAuthor;

    @FXML
    private TableColumn<Book, LocalDate> tableDueDate;

    @FXML
    private TableColumn<Book, String> tableID;

    @FXML
    private TableColumn<Book, String> tableStatus;

    @FXML
    private TableColumn<Book, String> tableTitle;

    /*
    Method Name: backButtonClicked
    Arguments: event ActionEvent
    Returns: void

    This method runs the method that changes the screen back to the main screen.
    */
    @FXML
    void backButtonClicked(ActionEvent event) throws IOException {
        ScreenLogic.changeToMain();
    }

    /*
    Method Name: removeButtonClicked
    Arguments: event ActionEvent
    Returns: void

    This method attempts to remove the specified book from the LMS.
    */
    @FXML
    void removeButtonClicked(ActionEvent event) {
        if(BusinessLogic.removeBook(input.getText(), categoryChoice.getValue(), newBookList -> {
            table.setItems(FXCollections.observableArrayList(newBookList));
            categoryChoice.setValue("ID");
            input.setText("");
        })) {
            input.setText("");
            ObservableList<Book> bookList = FXCollections.observableArrayList(BookLibrary.getBookList());
            table.setItems(bookList);
        }
    }

    /*
    Method Name: initialize
    Arguments: url URL and resourceBundle ResourceBundle
    Returns: void

    This method runs to initialize the page, but it is being used here to set up and display the book list in the table.
    */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        tableDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        tableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        table.setItems(bookList);
        categoryChoice.setItems(choiceList);
        categoryChoice.setValue("ID");
    }
}
