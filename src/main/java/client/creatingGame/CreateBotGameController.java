package client.creatingGame;

import client.Client1;
import client.creatingGame.CreateBotGameModel;
import client.creatingGame.Game;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Klasa kontroler pobierająca inforamcje (nick, nazwa gry, rodzaj mapy) od użytkownika w oknie tworzenia gry z botem.
 */
public class CreateBotGameController {

    public RadioButton Rbutton19;
    public Button AcceptButton;
    public RadioButton Rbutton13;
    public RadioButton Rbutton9;
    public TextField NickTField;
    public TextField TableName;
    public ToggleGroup group;
    public Pane pane;
    private CreateBotGameModel createGBModel = new CreateBotGameModel(Client1.servercomm);

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
                    createGBModel.ErrorWindow("Please enter nick or name of the game", AcceptButton);
                }else {
                    if( createGBModel.nicksended==false) {
                        String answer = createGBModel.sendNick(NickTField.getText());
                        if (answer.equals("NICK_SAVED")) {
                            createGBModel.nicksended=true;
                            Game gm = new Game(NickTField.getText(), null, "", Color.BLACK, Color.WHITE, "19x19");
                        } else {
                            createGBModel.ErrorWindow(answer, AcceptButton);
                        }
                    }
                    if(createGBModel.nicksended==true){
                        String answer = createGBModel.sendNewGame(TableName.getText(), "19x19");
                        if (answer.equals("JOINED")) {
                            Game.GameName = TableName.getText();
                            createGBModel.createmap("19x19");
                            Stage stage = (Stage) AcceptButton.getScene().getWindow();
                            stage.close();
                        } else {
                            createGBModel.ErrorWindow(answer, AcceptButton);
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
                    createGBModel.ErrorWindow("Please enter nick or name of the game", AcceptButton);
                }else {
                    if( createGBModel.nicksended==false) {

                        String answer = createGBModel.sendNick(NickTField.getText());
                        System.out.println(answer);
                        if (answer.equals("NICK_SAVED")) {
                            createGBModel.nicksended=true;
                            Game gm = new Game(NickTField.getText(), null, "", Color.BLACK, Color.WHITE, "9x9");
                        } else {
                            createGBModel.ErrorWindow(answer, AcceptButton);
                        }
                    }
                    if(createGBModel.nicksended==true){
                        String answer = createGBModel.sendNewGame(TableName.getText(), "9x9");
                        if (answer.equals("JOINED")) {
                            Game.GameName = TableName.getText();
                            createGBModel.createmap("9x9");
                            Stage stage = (Stage) AcceptButton.getScene().getWindow();
                            stage.close();
                        } else {
                            createGBModel.ErrorWindow(answer, AcceptButton);
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
                    createGBModel.ErrorWindow("Please enter nick or name of the game", AcceptButton);
                }else {
                    if( createGBModel.nicksended==false) {
                        String answer = createGBModel.sendNick(NickTField.getText());
                        if (answer.equals("NICK_SAVED")) {
                            createGBModel.nicksended=true;
                            Game gm = new Game(NickTField.getText(), null, "", Color.BLACK, Color.WHITE, "13x13");
                        } else {
                            createGBModel.ErrorWindow(answer, AcceptButton);
                        }
                    }
                    if(createGBModel.nicksended==true){
                        String answer = createGBModel.sendNewGame(TableName.getText(), "13x13");
                        if (answer.equals("JOINED")) {
                            Game.GameName = TableName.getText();
                            createGBModel.createmap("13x13");
                            Stage stage = (Stage) AcceptButton.getScene().getWindow();
                            stage.close();
                        } else {
                            createGBModel.ErrorWindow(answer, AcceptButton);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
