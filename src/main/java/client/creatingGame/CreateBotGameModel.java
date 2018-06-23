package client.creatingGame;

import client.LobbyCommunication;
import client.creatingGame.Game;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Klasa model odpowiedzialna za logikę okna tworzącego grę z botem.
 */
public class CreateBotGameModel {

    LobbyCommunication lobbycomm;

    boolean nicksended;

    CreateBotGameModel(LobbyCommunication lobbyCommunication){
        this.lobbycomm = lobbyCommunication;
        this.nicksended = false;

    }

    /**
     * Metoda tworzy klasę tworzącą planszę
     */

    public void createmap(String type){
        CreateMap cm = new CreateMap();
        cm.createmap(type);
    }


    /**
     * Metoda wysyłająca serwerowi nick gracza.
     * @param nick nick gracza.
     * @return Zwraca odpowiedź serwera.
     * @throws IOException
     */

    public String sendNick(String nick) throws IOException {
        lobbycomm.out.println("NICK "+ nick);
        lobbycomm.out.flush();
        return lobbycomm.in.readLine();
    }

    /**
     * Metoda wysyłająca serwerowi nową grę.
     * @param GameName nazwa gry.
     * @param Type rodzaj mapy.
     * @return zwraca odpowiedź serwera.
     * @throws IOException
     */

    public String sendNewGame(String GameName, String Type) throws IOException {
        String answer = null;
        lobbycomm.out.println("CREATE_BOT_GAME " +GameName + " " + Type);
        lobbycomm.out.flush();
        return answer = lobbycomm.in.readLine();
    }

    /**
     * Metoda wyświetlająca okno z błędem w razie jego wystąpienia.
     * @param message treść błędu.
     * @param button
     */

    public void ErrorWindow(String message, Button button){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setX(button.getScene().getWindow().xProperty().getValue());
        alert.setY(button.getScene().getWindow().yProperty().getValue());
        alert.showAndWait();
    }
}
