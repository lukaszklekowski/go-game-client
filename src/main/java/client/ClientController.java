package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Klasa kontroler odpowiadająca za działanie przycisków głównego okna programu
 * gdzie użytkownik wybiera grę z botem, utworzenie nowej gry lub dołączenie do gry.
 */

public class ClientController {

    public Button CreateGameButton;
    public Button JoinToGameButton;
    public Button ExitButton;
    public Button BotButton;
    ClientModel Cmodel = new ClientModel();

    public void CreateGame(ActionEvent actionEvent) {
       Cmodel.createNewGame(CreateGameButton);
    }

    public void JoinToGame(ActionEvent actionEvent) {
        Cmodel.joinGame(CreateGameButton);
    }

    public void ExitAction(ActionEvent actionEvent) {
        Stage stage = (Stage) CreateGameButton.getScene().getWindow();
        stage.close();
    }

    public void PlayWithBot(ActionEvent actionEvent) {
        Cmodel.playWithBot(CreateGameButton);
    }
}
