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
import java.util.ArrayList;
import java.util.ResourceBundle;
import main.Book;
import main.BookLibrary;
import main.BusinessLogic;

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
    private TableColumn<Book, String> tableDueDate;

    @FXML
    private TableColumn<Book, String> tableID;

    @FXML
    private TableColumn<Book, String> tableStatus;

    @FXML
    private TableColumn<Book, String> tableTitle;

    @FXML
    void mainButtonClicked(ActionEvent event) throws IOException {
        ScreenLogic.changeToMain();
    }

    @FXML
    void removeBooksButtonClicked(ActionEvent event) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        tableDueDate.setCellValueFactory(new PropertyValueFactory<Book, String>("dueDate"));
        tableID.setCellValueFactory(new PropertyValueFactory<Book, String>("id"));
        tableStatus.setCellValueFactory(new PropertyValueFactory<Book, String>("status"));
        tableTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));

        table.setItems(bookList);
        categoryChoice.setItems(choiceList);
        categoryChoice.setValue("ID");
    }
}
