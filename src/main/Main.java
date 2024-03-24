package main;

import database.DatabaseLogic;
import ui.ScreenLogic;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;

/*
Name: Patrick Tobey
Course: Software Development 1
Date 3/24/2024

Class Name: Main

This class contains the main method (start) of the LMS application and runs all LMS processes.
This class starts, runs, and closes the RMS according to user inputs.

The goal of this LMS application is to allow users to remove, check in, check out, and see books in the RMS book collection.
Users can also add books by providing a CSV file that contains a positive integer ID, book title, and book on each line.
 */
public class Main extends Application {
    /*
    Method Name: start
    Arguments: stage Stage
    Returns: void

    This method is the start point of the program and runs the necessary methods to initialize and start the LMS.
    */
    @Override
    public void start(Stage stage) throws IOException {
        DatabaseLogic.select();
        ScreenLogic.setStage(stage);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../ui/main-screen.fxml")));
        stage.setScene(new Scene(root));
        BusinessLogic.initializeAlerts();
        stage.setTitle("Patrick's LMS");
        stage.show();
    }
}