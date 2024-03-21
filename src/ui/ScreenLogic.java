package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/*
Name: Patrick Tobey
Course: Software Development 1
Date 3/24/2024

Class Name: RemoveScreenController

This class contains the screen logic common to all the screens.
This class also stores the primaryStage set in the main method so all the screens can use it to change screens.
 */
public class ScreenLogic {
    private static Stage primaryStage;

    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static Stage getStage() {
        return primaryStage;
    }

    /*
    Method Name: changeToMain
    Arguments: none
    Returns: void

    This method changes the screen back to the main screen.
    */
    public static void changeToMain() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(ScreenLogic.class.getResource("main-screen.fxml")));
        ScreenLogic.getStage().setScene(new Scene(root, ScreenLogic.getStage().getScene().getWidth(), ScreenLogic.getStage().getScene().getHeight()));
        ScreenLogic.getStage().show();
    }
}
