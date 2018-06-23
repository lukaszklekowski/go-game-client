package client.creatingGame;

import client.Client1;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Klasa kontroler pobierająca od użytkownika informacje (nick, gra do której chce dołączyć) w oknie lobby.
 */
public class LobbyController implements Initializable {
    public Pane pane;
    public Button refresh;

    public LobbyModel lmodel = new LobbyModel(Client1.servercomm);
    public ListView listview;
    public Button joinbutton;
    public TextField nick;

    /**
     * Metoda powodująca odświeżenie listy z dostępnymi grami.
     * @param actionEvent
     */

    public void refreshbutton(ActionEvent actionEvent) {
                listview.getItems().clear();
                 try {
                    lmodel.getList(listview);
                } catch (IOException e) {
                    e.printStackTrace();
                }
    }

    /**
     * Metoda inicjalizująca początkowy wygląd lobby.
     * @param location
     * @param resources
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        listview.setEditable(true);
            try {
                lmodel.getList(listview);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    /**
     * Metoda odpowiedzialna za pobranie nicku oraz gry do której gracz chce dołączyć.
     */

    public void join(ActionEvent actionEvent) {
            String Nick = nick.getText();
            String gamename = (String) listview.getSelectionModel().getSelectedItem();
            if(Nick.equals("")){
                lmodel.ErrorWindow("Please enter nick", joinbutton);
            }else {
                if(gamename == null){
                    lmodel.ErrorWindow("Please select the game", joinbutton);
                }else{
                    try {
                        lmodel.jointogame(gamename, Nick, joinbutton);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    }
}
