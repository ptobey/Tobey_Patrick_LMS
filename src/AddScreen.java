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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddScreen implements Initializable {

    private static Stage primaryStage;

    private static String path = "";
    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

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
    void addButtonClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("add-screen.fxml")));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    @FXML
    void mainButtonClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-screen.fxml")));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    void addBooksButtonClicked(ActionEvent event) {
        BusinessLogic.addBooks(path);
        ObservableList<Book> bookList = FXCollections.observableArrayList(BookLibrary.getBookList());
        table.setItems(bookList);
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
