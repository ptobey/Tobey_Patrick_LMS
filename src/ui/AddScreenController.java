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
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import main.Book;
import main.BookLibrary;
import main.BusinessLogic;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddScreenController implements Initializable {

    @FXML
    private TextField path;

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
    void fileChooserButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add((new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt")));
        File file = fileChooser.showOpenDialog(null);
        if(file != null) {
            path.setText(file.getAbsolutePath());
        }
    }

    @FXML
    void addBooksButtonClicked(ActionEvent event) {
        if(BusinessLogic.addBooks(path.getText())) {
            path.setText("");
            ObservableList<Book> bookList = FXCollections.observableArrayList(BookLibrary.getBookList());
            table.setItems(bookList);
        }
    }

    ObservableList<Book> bookList = FXCollections.observableArrayList(BookLibrary.getBookList());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        tableDueDate.setCellValueFactory(new PropertyValueFactory<Book, String>("dueDate"));
        tableID.setCellValueFactory(new PropertyValueFactory<Book, String>("id"));
        tableStatus.setCellValueFactory(new PropertyValueFactory<Book, String>("status"));
        tableTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));

        table.setItems(bookList);
    }
}
