package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Book;
import main.BookLibrary;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

/*
Name: Patrick Tobey
Course: Software Development 1
Date 4/7/2024

Class Name: MainScreenController

This class handles all the controls for the main screen.
 */
public class MainScreenController implements Initializable {

    ObservableList<Book> bookList = FXCollections.observableArrayList(BookLibrary.getBookList());

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
    Method Name: addButtonClicked
    Arguments: event ActionEvent
    Returns: void

    This method changes the screen to the add screen.
    */
    @FXML
    void addButtonClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("add-screen.fxml")));
        ScreenLogic.getStage().setScene(new Scene(root, ScreenLogic.getStage().getScene().getWidth(), ScreenLogic.getStage().getScene().getHeight()));
        ScreenLogic.getStage().show();
    }

    /*
    Method Name: checkInOutButtonClicked
    Arguments: event ActionEvent
    Returns: void

    This method changes the screen to the check in/out screen.
    */
    @FXML
    void checkInOutButtonClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("check-in-out-screen.fxml")));
        ScreenLogic.getStage().setScene(new Scene(root, ScreenLogic.getStage().getScene().getWidth(), ScreenLogic.getStage().getScene().getHeight()));
        ScreenLogic.getStage().show();
    }

    /*
    Method Name: removeButtonClicked
    Arguments: event ActionEvent
    Returns: void

    This method changes the screen to the remove screen.
    */
    @FXML
    void removeButtonClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("remove-screen.fxml")));
        ScreenLogic.getStage().setScene(new Scene(root, ScreenLogic.getStage().getScene().getWidth(), ScreenLogic.getStage().getScene().getHeight()));
        ScreenLogic.getStage().show();
    }

    /*
    Method Name: initialize
    Arguments: url URL and resourceBundle ResourceBundle
    Returns: void

    This method runs to initialize the page, but it is being used here to set up and display the book list in the table.
    */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        tableDueDate.setCellValueFactory(new PropertyValueFactory<Book, LocalDate>("dueDate"));
        tableGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        tableID.setCellValueFactory(new PropertyValueFactory<Book, String>("id"));
        tableStatus.setCellValueFactory(new PropertyValueFactory<Book, String>("status"));
        tableTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));

        table.setItems(bookList);
    }
}
