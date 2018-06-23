package client.creatingGame;

import client.LobbyCommunication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Klasa model odpowiedzialna za logikę lobby.
 */
public class LobbyModel {

    public LobbyCommunication lobbycomm;
    String[] parameters;
    ObservableList<String> ob = FXCollections.observableArrayList();

    LobbyModel(LobbyCommunication lobbyCommunication) {
        this.parameters = null;
        this.lobbycomm = lobbyCommunication;
    }

    /**
     * Metoda powierająca z serwera dostępne gry
     * @param listView
     * @throws IOException
     */

    public void getList(ListView listView) throws IOException {
        lobbycomm.out.println("PING");
        lobbycomm.out.flush();
        String answer = lobbycomm.in.readLine();
        if (answer.equals("PONG")) {
        }
        lobbycomm.out.println("LIST_GAMES");
        lobbycomm.out.flush();
        answer = lobbycomm.in.readLine();
        if (!(answer.equals("START_LIST"))) {
            System.out.println(answer);
        } else {
            answer = lobbycomm.in.readLine();
            while (!(answer.equals("END_LIST"))) {
                System.out.println(answer);
                ob.add(answer);
                answer = lobbycomm.in.readLine();

            }
            listView.setItems(ob);
        }

    }

    /**
     * Metoda powodująca dołączenie do gry
     * @param gamename Nazwa gry do której gracz chce dołączyć
     * @param nick Nick gracza
     * @param joinbutton
     * @throws IOException
     */

    public synchronized void jointogame(String gamename, String nick, Button joinbutton) throws IOException {
        lobbycomm.out.println("NICK " + nick);
        lobbycomm.out.flush();
        String ans = lobbycomm.in.readLine();
        if(ans.equals("NICK_SAVED")){
            parameters = gamename.split(" ");
            lobbycomm.out.println("JOIN_GAME " + parameters[0]);
            lobbycomm.out.flush();
            ans = lobbycomm.in.readLine();
            if(ans.equals("JOINED")){
                createmap(nick);
                Stage stage = (Stage) joinbutton.getScene().getWindow();
                stage.close();
            }else{
                ErrorWindow(ans, joinbutton);
            }

        }else {
            ErrorWindow(ans, joinbutton);
        }

    }

    /**
     * Metoda tworząca odpowiednią mapę.
     * @param nick
     */

    public  void createmap(String nick) {
        if (parameters[3].equals("19x19")) {
            if(parameters[1].equals("NO_PLAYER")) {
                Game game = new Game(nick, parameters[0], parameters[2], Color.WHITE, Color.BLACK, parameters[3]);
            }else{
                Game game = new Game(nick, parameters[0], parameters[1], Color.BLACK, Color.WHITE, parameters[3]);
            }
        }
        if(parameters[3].equals("13x13")){
            if(parameters[1].equals("NO_PLAYER")) {
                Game game = new Game(nick, parameters[0], parameters[2], Color.WHITE, Color.BLACK, parameters[3]);

            }else{
                Game game = new Game(nick, parameters[0], parameters[1], Color.BLACK, Color.WHITE, parameters[3]);
            }
        }
        if(parameters[3].equals("9x9")){
            if(parameters[1].equals("NO_PLAYER")) {
                Game game = new Game(nick, parameters[0], parameters[2], Color.WHITE, Color.BLACK, parameters[3]);
            }else{
                Game game = new Game(nick, parameters[0], parameters[1], Color.BLACK, Color.WHITE, parameters[3]);
            }
        }
        CreateMap cm = new CreateMap();
        cm.createmap(parameters[3]);
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
        alert.setX(button.getScene().getWindow().xProperty().getValue()+200);
        alert.setY(button.getScene().getWindow().yProperty().getValue()+200);
        alert.showAndWait();
    }
}