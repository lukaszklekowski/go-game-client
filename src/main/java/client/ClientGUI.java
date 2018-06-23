package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**\mainpage Klient do gry go.
 *  @author Łukasz Klekowski
 *  @version 1.0
 *  @since 23-12-2016
 */

/**  Główna klasa, rozpoczynająca działanie programu.
 *  Znajduje sie w niej publiczna metoda start otwierająca główne okno
 *  programu.
 */

public class ClientGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ClientController.fxml"));
        primaryStage.setTitle("Gra Go");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
