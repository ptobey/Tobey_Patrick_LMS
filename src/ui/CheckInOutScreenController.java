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
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import main.Book;
import main.BookLibrary;
import main.BusinessLogic;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
    private TableColumn<Book, LocalDate> tableDueDate;

    @FXML
    private TableColumn<Book, String> tableID;

    @FXML
    private TableColumn<Book, String> tableStatus;

    @FXML
    private TableColumn<Book, String> tableTitle;

    @FXML
    void mainButtonClicked(ActionEvent event) throws IOException {
        if(input.getPromptText().equals("ID")) {
            input.setPromptText("Title");
            input.setText("");
            table.setItems(bookList);
        }
        else {
            ScreenLogic.changeToMain();
        }
    }

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
            table.setItems(FXCollections.observableArrayList(bookList));
            table.refresh();
        }
    }

    @FXML
    void checkOutButtonClicked(ActionEvent event) {
        if(BusinessLogic.checkOutBook(input.getText(), input.getPromptText(), newBookList -> {
            table.setItems(FXCollections.observableArrayList(newBookList));
            input.setPromptText("ID");
            label.setText(labelText+"ID");
        })) {
            input.setPromptText("Title");
            label.setText(labelText+"title");
            table.setItems(FXCollections.observableArrayList(bookList));
            table.refresh();
        }
        input.setText("");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        tableDueDate.setCellValueFactory(new PropertyValueFactory<Book, LocalDate>("dueDate"));
        tableID.setCellValueFactory(new PropertyValueFactory<Book, String>("id"));
        tableStatus.setCellValueFactory(new PropertyValueFactory<Book, String>("status"));
        tableTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));

        table.setItems(bookList);
    }
}
