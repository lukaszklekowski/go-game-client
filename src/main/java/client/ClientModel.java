package client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Klasa model odpowiedzialna za logikę głównego okna klienta
 */
public class ClientModel {

    /**
     * Metoda tworząca okno odpowiedzialne za tworzenie nowej gry
     * @param button
     */
    public void createNewGame(Button button){
        Client1.instance = new Client1();
        if(Client1.servercomm.checkVersion().equals("GOOD_VERSION")){
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("creatingGame/CreateGameController.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Tworzenie sesji");
                stage.setScene(new Scene(root));
                stage.show();
                stage.setResizable(false);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText(null);
                alert.setContentText("Problem z wczytaniem danych");
                alert.showAndWait();
            }
            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Wrong version");
            alert.showAndWait();
        }
    }

    /**
     * Metoda tworząca lobby
     * @param button
     */

    public void joinGame(Button button){
        Client1.instance = new Client1();
        if(Client1.servercomm.checkVersion().equals("GOOD_VERSION")) {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("creatingGame/LobbyController.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Tworzenie sesji");
                stage.setScene(new Scene(root));
                stage.show();
                stage.setResizable(false);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText(null);
                alert.setContentText("Problem z wczytaniem danych");
                alert.showAndWait();
            }
            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Wrong version");
            alert.showAndWait();
        }
    }

    /**
     * Metoda tworząca okno odpowiedzialne za tworzenie gry z botem
     * @param button
     */

    public void playWithBot(Button button){
        Client1.instance = new Client1();
        if(Client1.servercomm.checkVersion().equals("GOOD_VERSION")) {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("creatingGame/CreateBotGameController.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Tworzenie sesji z botem");
                stage.setScene(new Scene(root));
                stage.show();
                stage.setResizable(false);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText(null);
                alert.setContentText("Problem z wczytaniem danych");
                alert.showAndWait();
            }
            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Wrong version");
            alert.showAndWait();
        }
    }
}
