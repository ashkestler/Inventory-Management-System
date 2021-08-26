package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** FUTURE ENHANCEMENT: A future feature that could be implemented is to link the inventory management program to an
 * online ordering website.
 *
 *
 * @author Ashley Kestler
 */

// javadoc file is located in the C482 folder

public class Main extends Application {

    /** The start method creates the FXML stage.
     *
     * @param primaryStage
     * @throws java.lang.Exception*/
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 1400, 675));
        primaryStage.show();
    }

    /** This is the main method. It is the entry point of the application and launches the application.
     *
     * @param args */
    public static void main(String[] args) {
        launch(args);
    }
}
