package client.creatingGame.Boards;

import client.Client1;
import client.creatingGame.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *Klasa kontroler odpowiedzialna za działanie przycisków w oknie gry.
 */

public class Game13x13Controller implements Initializable {
    public Pane pane;
    public Button StartButton;
    public Label OpponentPoints;
    public Label WhiteName;
    public Label BlackName;
    public Label PlayersPoints;
    public GameModel g13x13Model = new GameModel(13, Client1.servercomm);
    public Button passbutton;
    public Button proposeteritory;
    public Button rejectterritory;
    public Button acceptterritory;
    public Button resumegame;
    public Button resignbutton;
    Button table[];
    Label label[];

    /**
     * Metoda odpowiedzialna za działanie przycisku start. Po jego wciśnięciu klient wysyła gotowość do gry
     * oraz nasłuchuje odpowiedzi z serwera.
     * @param actionEvent
     */

    public void StartButtonAction(ActionEvent actionEvent) {
        StartButton.setDisable(true);
        if (Game.Playercolor.equals(Color.BLACK)) {
            GameModel.service service = new GameModel.service();
            service.start();
            service.setOnSucceeded(event -> {
                String ans = (String)(event.getSource().getValue());
                String split[] = ans.split(" ");
                WhiteName.setText(split[1]);
                passbutton.setDisable(false);
                resignbutton.setDisable(false);
                g13x13Model.createrect(pane,table, label);
            });
        } else {
            GameModel.service service = new GameModel.service();
            service.start();
            service.setOnSucceeded(event -> {
                String ans = (String)(event.getSource().getValue());
                String split[] = ans.split(" ");
                BlackName.setText(split[1]);
                g13x13Model.createrect(pane, table, label);
                for (int i = 0; i < 13; i++)
                    for (int j = 0; j < 13; j++) {
                        g13x13Model.rectTable[i][j].rect.setVisible(false);
                    }
                GameModel.service1 service1 = new GameModel.service1();
                service1.start();
                service1.setOnSucceeded(event1 -> {
                    String answ = (String) (event1.getSource().getValue());
                    if(answ.equals("OPPONENT_LEFT_GAME")){
                        g13x13Model.InfoWindow("Opponent left the game", passbutton);
                    }
                    if(answ.equals("OPPONENT_RESIGNED")){
                        g13x13Model.InfoWindow("Opponent resigned", passbutton);
                    }
                    if(answ.equals("OPPONENT_PASSED")){
                        for (int i = 0; i < 13; i++)
                            for (int j = 0; j < 13; j++) {
                                g13x13Model.rectTable[i][j].rect.setVisible(true);
                            }
                        passbutton.setDisable(false);
                        resignbutton.setDisable(false);
                    }
                    if(answ.startsWith("OPPONENT_PLACE_PAWN")){
                        passbutton.setDisable(false);
                        resignbutton.setDisable(false);
                        g13x13Model.getpawn(pane, answ);
                        try {
                            System.out.println(GameModel.lobbycomm.in.readLine());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            });
        }
    }

    /**
     * Metoda wysyłająca pas gracza.
     * @param actionEvent
     */

    public void onpassaction(ActionEvent actionEvent) {
        g13x13Model.sendpassed(pane, table,label);
    }

    /**
     * Metoda inicjalizująca początkowy wygląd planszy
     * @param location
     * @param resources
     */


    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        if(Game.Playercolor==Color.BLACK){
            BlackName.setText(Game.PlayerName);
        }else{
            WhiteName.setText(Game.PlayerName);
        }
        passbutton.setDisable(true);
        resignbutton.setDisable(true);
        table = new Button[7];
        table[0] = StartButton;
        table[1] = passbutton;
        table[2] = resignbutton;
        table[3] = proposeteritory;
        table[4] = acceptterritory;
        table[5] = rejectterritory;
        table[6] = resumegame;
        label = new Label[2];
        if(Game.Playercolor==Color.BLACK){
            OpponentPoints.setText(Double.toString(Game.OpponentPoints));
            PlayersPoints.setText(Double.toString(Game.PlayerPoints));
            label[0] = PlayersPoints;
            label[1] = OpponentPoints;
        }else{
            PlayersPoints.setText(Double.toString(Game.OpponentPoints));
            OpponentPoints.setText(Double.toString(Game.PlayerPoints));
            label[0] = OpponentPoints;
            label[1] = PlayersPoints;
        }
    }

    /**
     * Metoda proponująca terytorium.
     * @param actionEvent
     */

    public void ProposeTerritory(ActionEvent actionEvent) {
        g13x13Model.sendterritory(pane, table, label);
    }

    /**
     * Metoda odzrucajaca terytorium przeciwnika.
     * @param actionEvent
     */

    public void RejectTerritory(ActionEvent actionEvent) {
        g13x13Model.rejectterritory(pane, table);
    }

    /**
     * Metoda akceptująca terytorium przeciwnika.
     * @param actionEvent
     */


    public void AcceptTerritory(ActionEvent actionEvent) {
        g13x13Model.acceptterritory(table, label);
    }

    /**
     * Metoda wznawiająca grę.
     * @param actionEvent
     */

    public void Resumegame(ActionEvent actionEvent) { g13x13Model.resume(pane,table,label);
    }

    /**
     * Metoda wywoływana w razie rezygnacji gracza.
     * @param actionEvent
     */

    public void resign(ActionEvent actionEvent) { g13x13Model.resign(pane, table);
    }
}