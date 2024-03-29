package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import main.Book;
import main.BookLibrary;
import main.BusinessLogic;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/*
Name: Patrick Tobey
Course: Software Development 1
Date 4/7/2024

Class Name: AddScreenController

This class handles all the controls for the add screen.
 */
public class AddScreenController implements Initializable {

    ObservableList<Book> bookList = FXCollections.observableArrayList(BookLibrary.getBookList());

    @FXML
    private TextField path;

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
    Method Name: fileChooserButtonClicked
    Arguments: event ActionEvent
    Returns: void

    This method prompts users to pick a file they want to add books from
    */
    @FXML
    void fileChooserButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add((new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt")));
        File file = fileChooser.showOpenDialog(null);
        if(file != null) {
            path.setText(file.getAbsolutePath());
        }
    }

    /*
    Method Name: addBooksButtonClicked
    Arguments: event ActionEvent
    Returns: void

    This method attempts to add the books from the selected file path to the LMS.
    */
    @FXML
    void addBooksButtonClicked(ActionEvent event) {
        if(BusinessLogic.addBooks(path.getText())) {
            path.setText("");
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
        tableGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        tableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        table.setItems(bookList);
    }
}
