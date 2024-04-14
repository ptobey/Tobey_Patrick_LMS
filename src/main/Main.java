package main;

import database.DatabaseLogic;
import ui.ScreenLogic;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

/**
 * This class contains the main method (start) of the LMS application and runs all LMS processes.
 * It starts, runs, and closes the RMS according to user inputs.
 * The goal of this LMS application is to allow users to remove, check in, check out, and see books in the RMS book collection.
 * Users can also add books by providing a CSV file that contains a positive integer ID, book title, and book on each line.
 */
public class Main extends Application {
    public Main() {}

    /**
     * Is the starting point of the program and runs the necessary methods to initialize and start the LMS.
     * @param stage
     * The stage of the application's UI.
     */
    @Override
    public void start(Stage stage) throws IOException {
        DatabaseLogic.select();
        ScreenLogic.setStage(stage);

        FXMLLoader loader = new FXMLLoader();
        URL url = getClass().getResource("/ui/main-screen.fxml");

        assert url != null;
        Parent root = loader.load(url.openStream());

        stage.setScene(new Scene(root));
        BusinessLogic.initializeAlerts();
        stage.setTitle("Patrick's LMS");
        stage.show();
    }
}