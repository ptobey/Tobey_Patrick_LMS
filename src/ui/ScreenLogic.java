package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/**
 * This class contains the screen logic common to all the screens.
 * It also stores the primaryStage set in the main method so all the screens can use it to change screens.
 */
public class ScreenLogic {
    /**
     * The default constructor that should never be used.
     */
    private ScreenLogic() {}

    private static Stage primaryStage;

    /**
     * Sets the stage for the application to use.
     * @param stage The application's stage.
     */
    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    /**
     * Gets the application's stage
     * @return The application's stage.
     */
    public static Stage getStage() {
        return primaryStage;
    }

    /**
     * Changes the screen back to the main screen.
     * @exception IOException This exception happens if the fxml file does not exist.
     */
    public static void changeToMain() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(ScreenLogic.class.getResource("main-screen.fxml")));
        ScreenLogic.getStage().setScene(new Scene(root, ScreenLogic.getStage().getScene().getWidth(), ScreenLogic.getStage().getScene().getHeight()));
        ScreenLogic.getStage().show();
    }
}
