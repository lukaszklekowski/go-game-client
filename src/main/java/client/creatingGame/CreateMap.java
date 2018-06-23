package client.creatingGame;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Klasa tworząca plansze
 */
public class CreateMap{

    /**
     * Metoda tworząca plansze
     * @param type typ planszy
     */

    public void createmap(String type){
        if(type.equals("19x19")){
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("Boards/Game19x19Controller.fxml"));
                Stage stage = new Stage();
                stage.setTitle(Game.GameName);
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
        }
        if(type.equals("13x13")){
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("Boards/Game13x13Controller.fxml"));
                Stage stage = new Stage();
                stage.setTitle(Game.GameName);
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
        }
        if(type.equals("9x9")){
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("Boards/Game9x9Controller.fxml"));
                Stage stage = new Stage();
                stage.setTitle(Game.GameName);
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
        }
    }
}
