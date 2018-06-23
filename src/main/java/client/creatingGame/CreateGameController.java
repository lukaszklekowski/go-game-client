package client.creatingGame;

import client.Client1;
import client.creatingGame.CreateGameModel;
import client.creatingGame.Game;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Klasa kontroler pobierająca inforamcje (nick, nazwa gry, rodzaj mapy) od użytkownika w oknie tworzenia nowej gry.
 */
public class CreateGameController {

    public RadioButton Rbutton19;
    public Button AcceptButton;
    public RadioButton Rbutton13;
    public RadioButton Rbutton9;
    public TextField NickTField;
    public TextField TableName;
    public ToggleGroup group;
    public Pane pane;
    private CreateGameModel createGModel = new CreateGameModel(Client1.servercomm);

    /**
     * Metoda przypisuje przyciskowi OK event odpowiedzialny za tworzenie mapy 19x19
     * @param actionEvent
     */
    public void Rbutton19OnAction(ActionEvent actionEvent) {
        AcceptButton.setOnAction(event -> {
            try {
                String Nick = NickTField.getText();
                String GameName = TableName.getText();
                if (Nick.equals("")||GameName.equals("")){
                    createGModel.ErrorWindow("Please enter nick or name of the game", AcceptButton);
                }else {
                    if( createGModel.nicksended==false) {
                        String answer = createGModel.sendNick(NickTField.getText());
                        if (answer.equals("NICK_SAVED")) {
                            createGModel.nicksended=true;
                            Game gm = new Game(NickTField.getText(), null, "", Color.WHITE, Color.BLACK, "19x19");
                        } else {
                            createGModel.ErrorWindow(answer, AcceptButton);
                        }
                    }
                    if(createGModel.nicksended==true){
                        String answer = createGModel.sendNewGame(TableName.getText(), "19x19");
                        if (answer.equals("JOINED")) {
                            Game.GameName = TableName.getText();
                            createGModel.createmap("19x19");
                            Stage stage = (Stage) AcceptButton.getScene().getWindow();
                            stage.close();
                        } else {
                            createGModel.ErrorWindow(answer, AcceptButton);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Metoda przypisuje przyciskowi OK event odpowiedzialny za tworzenie mapy 9x9
     * @param actionEvent
     */

    public void Rbutton9OnAction(ActionEvent actionEvent) {
        AcceptButton.setOnAction((ActionEvent event) -> {
            try {
                String Nick = NickTField.getText();
                String GameName = TableName.getText();
                if (Nick.equals("")||GameName.equals("")){
                    createGModel.ErrorWindow("Please enter nick or name of the game", AcceptButton);
                }else {
                    if( createGModel.nicksended==false) {
                        String answer = createGModel.sendNick(NickTField.getText());
                        if (answer.equals("NICK_SAVED")) {
                            createGModel.nicksended=true;
                            Game gm = new Game(NickTField.getText(), null, "", Color.WHITE, Color.BLACK, "9x9");
                        } else {
                            createGModel.ErrorWindow(answer, AcceptButton);
                        }
                    }
                    if(createGModel.nicksended==true){
                        String answer = createGModel.sendNewGame(TableName.getText(), "9x9");
                        if (answer.equals("JOINED")) {
                            Game.GameName = TableName.getText();
                            createGModel.createmap("9x9");
                            Stage stage = (Stage) AcceptButton.getScene().getWindow();
                            stage.close();
                        } else {
                            createGModel.ErrorWindow(answer, AcceptButton);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Metoda przypisuje przyciskowi OK event odpowiedzialny za tworzenie mapy 13x13
     * @param actionEvent
     */

    public void Rbutton13OnAction(ActionEvent actionEvent) {
        AcceptButton.setOnAction(event -> {
            try {
                String Nick = NickTField.getText();
                String GameName = TableName.getText();
                if (Nick.equals("")||GameName.equals("")){
                    createGModel.ErrorWindow("Please enter nick or name of the game", AcceptButton);
                }else {
                    if( createGModel.nicksended==false) {
                        String answer = createGModel.sendNick(NickTField.getText());
                        if (answer.equals("NICK_SAVED")) {
                            createGModel.nicksended=true;
                            Game gm = new Game(NickTField.getText(), null, "", Color.WHITE, Color.BLACK, "13x13");
                        } else {
                            createGModel.ErrorWindow(answer, AcceptButton);
                        }
                    }
                    if(createGModel.nicksended==true){
                        String answer = createGModel.sendNewGame(TableName.getText(), "13x13");
                        if (answer.equals("JOINED")) {
                            Game.GameName = TableName.getText();
                            createGModel.createmap("13x13");
                            Stage stage = (Stage) AcceptButton.getScene().getWindow();
                            stage.close();
                        } else {
                            createGModel.ErrorWindow(answer, AcceptButton);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
