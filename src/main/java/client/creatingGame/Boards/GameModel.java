package client.creatingGame.Boards;

import client.creatingGame.Game;
import client.LobbyCommunication;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

import static java.lang.Thread.sleep;

/**
 * Metoda model odpowiedzialna za logikę plansz
 */

public class GameModel {

    static Pawn[][] pawns;
    public static Rect[][] rectTable;
    public static Rectangle[][] territory;
    static Circle[][] alivepawns;
    public static Circle[][] oppalivepawns;
    static Circle[][] deadpawns;
    public static Circle[][] oppdeadpawns;
    static int[][] possible;
    public static LobbyCommunication lobbycomm;
    static int boardSize;

    public GameModel(int boardSize, LobbyCommunication lobbyCommunication){
        this.boardSize = boardSize;
        lobbycomm = lobbyCommunication;
        rectTable = new Rect[boardSize][boardSize];
        pawns = new Pawn[boardSize][boardSize];
        alivepawns = new Circle[boardSize][boardSize];
        oppalivepawns = new Circle[boardSize][boardSize];
        deadpawns = new Circle[boardSize][boardSize];
        oppdeadpawns = new Circle[boardSize][boardSize];
        territory = new Rectangle[boardSize][boardSize];
        possible = new int[boardSize][boardSize];
        for(int i = 0; i< boardSize; i++)
            for(int j = 0; j< boardSize; j++) {
                rectTable[i][j]= null;
                territory[i][j] = null;
                deadpawns[i][j] = null;
                pawns[i][j] = null;
                alivepawns[i][j] = null;
                possible[i][j] = 0;
                oppdeadpawns[i][j] = null;
                oppalivepawns[i][j] = null;
            }
    }

    /**
     * Metoda tworząca kwadraty w których można umieszczać kamienie
     * @param pane
     * @param buttons
     * @param labels
     */

    public void createrect(Pane pane, Button[] buttons, Label[] labels){
        if(Game.type.equals("19x19")) {
            int xx = 40;
            for (int i = 0; i < boardSize; i++) {
                int yy = 40;
                for (int j = 0; j < boardSize; j++) {
                    Rect rect = new Rect(pane, xx, yy, i, j, buttons, labels);
                    rectTable[j][i] = rect;
                    yy = yy + 25;
                }
                xx = xx + 25;
            }
        }
        if(Game.type.equals("13x13")){
            int x = 43;
            for(int i = 0; i< boardSize; i++) {
                int y = 43;
                for (int j = 0; j < boardSize; j++){
                    Rect rect = new Rect(pane, x, y, i, j, buttons, labels);
                    rectTable[j][i] = rect;
                    y = y + 37;
                }
                x = x + 37;
            }
        }
        if(Game.type.equals("9x9")){
            int x = 41;
            for(int i = 0; i< boardSize; i++) {
                int y = 41;
                for (int j = 0; j < boardSize; j++){
                    Rect rect = new Rect(pane, x, y, i, j, buttons, labels);
                    rectTable[j][i] = rect;
                    y = y + 56;
                }
                x = x + 56;
            }
        }
    }

    /**
     * Metoda wyświetlająca terytorium przeciwnika.
     */

    public static void opponentProposeTerritory(Pane pane){
        String answer= null;
        try {
            answer = lobbycomm.in.readLine();
            while (!answer.equals("END_TERRITORY")) {
                String[] place = answer.split(" ");
                int px = Integer.parseInt(place[0]);
                int py = Integer.parseInt(place[1]);
                Rectangle rec = new Rectangle(rectTable[py][px].rect.getX(), rectTable[py][px].rect.getY(), 9, 9);
                rec.setFill(Game.OpponentColor);
                territory[px][py] = rec;
                pane.getChildren().add(rec);
                answer = lobbycomm.in.readLine();
            }
            answer = lobbycomm.in.readLine();
            while (!answer.equals("END_LIVING_STONES")) {
                String[] place = answer.split(" ");
                int px = Integer.parseInt(place[0]);
                int py = Integer.parseInt(place[1]);
                Circle cir = new Circle(pawns[px][py].circle.getCenterX(), pawns[px][py].circle.getCenterY(), 5);
                cir.setFill(Color.GREEN);
                oppalivepawns[px][py] = cir;
                pane.getChildren().add(cir);
                answer = lobbycomm.in.readLine();
            }
            answer = lobbycomm.in.readLine();
            while (!answer.equals("END_DEAD_STONES")) {
                String[] place = answer.split(" ");
                int px = Integer.parseInt(place[0]);
                int py = Integer.parseInt(place[1]);
                Circle cir = new Circle(pawns[px][py].circle.getCenterX(), pawns[px][py].circle.getCenterY(), 5);
                cir.setFill(Color.RED);
                oppdeadpawns[px][py] = cir;
                pane.getChildren().add(cir);
                answer = lobbycomm.in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda informująca o opuszczeniu gry przez przeciwnika, oraz "wyłączająca" planszę
     * @param buttons
     */

    public static void opponentLeftGame(Button[] buttons){
        InfoWindow("Opponent left the game", buttons[1]);
        for (int i = 0; i < 7; i++) {
            buttons[i].setDisable(true);
        }
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (alivepawns[i][j] != null) {
                    alivepawns[i][j].setDisable(true);
                }
                if (deadpawns[i][j] != null) {
                    deadpawns[i][j].setDisable(true);
                }
                if (pawns[i][j] != null) {
                    pawns[i][j].circle.setDisable(true);
                }
                if (territory[i][j] != null) {
                    territory[i][j].setDisable(true);
                }
                rectTable[i][j].rect.setDisable(true);
            }
        }
    }

    /**
     * Metoda "wyłączająca mapę po rezygnacji"
     * @param buttons
     */


    public static void prepareMapAfterResign(Button[] buttons){
        for (int i = 0; i < 7; i++) {
            buttons[i].setDisable(true);
        }
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (alivepawns[i][j] != null) {
                    alivepawns[i][j].setDisable(true);
                }
                if (deadpawns[i][j] != null) {
                    deadpawns[i][j].setDisable(true);
                }
                if (pawns[i][j] != null) {
                    pawns[i][j].circle.setDisable(true);
                }
                if (territory[i][j] != null) {
                    territory[i][j].setDisable(true);
                }
                rectTable[i][j].rect.setDisable(true);
            }
        }
    }

    /**
     * Metoda stawiająca pionki przeciwnika oraz powodująca ewentualne usunięcie pionków gracza
     * @param pane
     * @param answer
     */

    public static void opponentPlacePawn(Pane pane, String answer){
        while (!answer.equals("OPPONENT_FINISHED_MOVE")) {
            if (answer.startsWith("OPPONENT_PLACE_PAWN"))
                getpawn(pane, answer);
            if (answer.startsWith("REMOVED_PAWN")) {
                while (!answer.equals("END_REMOVE_PAWN")) {
                    String[] place = answer.split(" ");
                    int px = Integer.parseInt(place[1]);
                    int py = Integer.parseInt(place[2]);
                    removepawn(pane, px, py);
                    Game.OpponentPoints = Game.OpponentPoints + 1;
                    try {
                        answer = lobbycomm.in.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                answer = lobbycomm.in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Metoda "włączająca" planszę po wznowieniu przez przeciwnika
     */

    public static void prepareMapAfterResume(Pane pane){

        Game.placceptterritory = false;
        Game.oppacceptterritory = false;
        Game.playerpassed = false;
        Game.opponentpassed = false;
        Game.isproposing = false;

        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++) {
                if (pawns[i][j] != null) {
                    pawns[i][j].circle.setDisable(false);
                }
                rectTable[i][j].rect.setVisible(true);
                if (territory[i][j] != null) {
                    pane.getChildren().remove(territory[i][j]);
                    territory[i][j] = null;
                }
                if (oppalivepawns[i][j] != null) {
                    pane.getChildren().remove(oppalivepawns[i][j]);
                    oppalivepawns[i][j] = null;
                }
                if (oppdeadpawns[i][j] != null) {
                    pane.getChildren().remove(oppdeadpawns[i][j]);
                    oppdeadpawns[i][j] = null;
                }
                if (alivepawns[i][j] != null) {
                    pane.getChildren().remove(alivepawns[i][j]);
                    alivepawns[i][j] = null;
                }
                if (deadpawns[i][j] != null) {
                    pane.getChildren().remove(deadpawns[i][j]);
                    deadpawns[i][j] = null;
                }
            }
    }

    /**
     * Metoda wysyłająca serwerowi postawiony pionek oraz nasłuchująca odpowiedzi ze strony serwera
     */

    public static void sendpawn(int xx, int yy, int x, int y, Pane pane, Button[] buttons, Label[] labels) throws IOException {

            Game.opponentpassed=false;
            String answer;
            lobbycomm.out.println("PLACE_PAWN " + x + " " + y);
            lobbycomm.out.flush();
            answer = lobbycomm.in.readLine();

            if(answer.equals("OPPONENT_LEFT_GAME")){
                opponentLeftGame(buttons);
            }else {
                if (answer.equals("PLACED")) {
                    buttons[1].setDisable(true);
                    buttons[2].setDisable(true);
                    Pawn pw = new Pawn(pane, xx, yy, x, y, Game.Playercolor);
                    pawns[x][y] = pw;
                    for (int i = 0; i < boardSize; i++)
                        for (int j = 0; j < boardSize; j++) {
                            rectTable[i][j].rect.setVisible(false);
                        }
                    service1 service1 = new service1();
                    service1.start();
                    service1.setOnSucceeded(event -> {
                        String answer1 = (String) event.getSource().getValue();
                        if (answer1.equals("OPPONENT_LEFT_GAME")) {
                            InfoWindow("Opponent left the game", buttons[1]);
                        }
                        if (answer1.startsWith("REMOVED_PAWN")) {
                            while (!answer1.equals("END_REMOVE_PAWN")) {
                                String[] place = answer1.split(" ");
                                int px = Integer.parseInt(place[1]);
                                int py = Integer.parseInt(place[2]);
                                removepawn(pane, px, py);
                                Game.PlayerPoints = Game.PlayerPoints + 1;
                                try {
                                    answer1 = lobbycomm.in.readLine();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            labels[0].setText(Double.toString(Game.PlayerPoints));
                            service1 service11 = new service1();
                            service11.start();
                            service11.setOnSucceeded(event1 -> {
                                String answer11 = (String) event1.getSource().getValue();
                                if (answer11.equals("OPPONENT_LEFT_GAME")) {
                                    InfoWindow("Opponent left the game", buttons[1]);
                                }
                                if (answer11.equals("OPPONENT_RESIGNED")) {
                                    prepareMapAfterResign(buttons);
                                    InfoWindow("Opponent resigned", buttons[1]);
                                }
                                if (answer11.equals("OPPONENT_PASSED")) {
                                    buttons[1].setDisable(false);
                                    buttons[2].setDisable(false);
                                    for (int i = 0; i < boardSize; i++)
                                        for (int j = 0; j < boardSize; j++) {
                                            rectTable[i][j].rect.setVisible(true);
                                        }
                                    Game.opponentpassed = true;
                                    if (Game.playerpassed == true && Game.opponentpassed == true) {
                                        Game.isproposing = true;
                                        proposeTerritory(pane);
                                    }
                                }
                                if (answer11.startsWith("OPPONENT_PLACE_PAWN")) {
                                    buttons[1].setDisable(false);
                                    buttons[2].setDisable(false);
                                    opponentPlacePawn(pane, answer11);
                                }
                                labels[1].setText(Double.toString(Game.OpponentPoints));
                            });
                        } else {
                            if (answer1.equals("OPPONENT_RESIGNED")) {
                                prepareMapAfterResign(buttons);
                                InfoWindow("Opponent resigned", buttons[1]);
                            }
                            if (answer1.equals("OPPONENT_PASSED")) {
                                buttons[1].setDisable(false);
                                buttons[2].setDisable(false);
                                for (int i = 0; i < boardSize; i++)
                                    for (int j = 0; j < boardSize; j++) {
                                        rectTable[i][j].rect.setVisible(true);
                                    }
                                Game.opponentpassed = true;
                                if (Game.playerpassed == true && Game.opponentpassed == true) {
                                    Game.isproposing = true;
                                    proposeTerritory(pane);
                                }
                            }
                            if (answer1.startsWith("OPPONENT_PLACE_PAWN")) {
                                buttons[1].setDisable(false);
                                buttons[2].setDisable(false);
                                opponentPlacePawn(pane, answer1);
                                labels[1].setText(Double.toString(Game.OpponentPoints));
                            }
                        }
                    });
                } else {
                    ErrorWindow(answer.substring(14), buttons[1]);
                }
            }
    }

    /**
     * Stawianie pionków przeciwnika
     * @param pane
     * @param answer
     */

    public static void getpawn(Pane pane, String answer){
        if(answer.startsWith("OPPONENT_PLACE_PAWN")){
            String command = answer.substring(20);
            String[] place = command.split(" ");
            int px = Integer.parseInt(place[0]);
            int py = Integer.parseInt(place[1]);
            int px1 = rectTable[py][px].px1;
            int py1 = rectTable[py][px].py1;
            Pawn pw = new Pawn(pane,px1,py1, px, py, Game.OpponentColor);
            pawns[px][py] = pw;
            for(int i = 0; i< boardSize; i++)
                for(int j = 0; j< boardSize; j++){
                    rectTable[i][j].rect.setVisible(true);
                }
        }
    }

    /**
     * Metoda usuwająca pionki
     * @param pane
     * @param x
     * @param y
     */

    public static void removepawn(Pane pane, int x, int y){
        pane.getChildren().remove(pawns[x][y].circle);
        pawns[x][y] = null;
    }

    /**
     * Metoda wysyłająca gotowość do gry
     * @return
     * @throws IOException
     */

    public static String sendready() throws IOException {
        String answer;
        lobbycomm.out.println("READY");
        lobbycomm.out.flush();
        answer = lobbycomm.in.readLine();
            if(answer.equals("MADE_READY")){
               answer = waittostart();
            }
        return answer;
    }

    /**
     * Metoda czekająca na odpowiedź przeciwnika
     * @return
     * @throws IOException
     */

    public static String waittoopponent() throws IOException{
        String answer;
        answer = lobbycomm.in.readLine();
      return answer;
    }

    /**
     * Metoda czekająca na start gry
     * @return
     * @throws IOException
     */

    public static String waittostart() throws IOException {
        String answer;
        answer = lobbycomm.in.readLine();
        return answer;
    }

    /**
     * Metoda usuwająca terytorium po odrzuceniu
     * @param pane
     * @param x
     * @param y
     */

    public static void removeTerritory(Pane pane, int x, int y){
        try{
            if(pawns[x][y] == null && territory[x][y]!=null) {
                pane.getChildren().remove(territory[x][y]);
                territory[x][y] = null;
                removeTerritory(pane, x-1, y);
                removeTerritory(pane, x+1, y);
                removeTerritory(pane, x, y-1);
                removeTerritory(pane, x, y+1);
            }
        }catch(ArrayIndexOutOfBoundsException e) {}

    }

    /**
     * Metoda sprawdzająca czy terytorium jest poprawne
     * @param x
     * @param y
     */
    public static void ispossibleteritory(int x, int y) {
        try {
            if (pawns[x][y] != null) {
                if (pawns[x][y].circle.getFill() == Game.OpponentColor) {
                    Game.possible = false;
                }
            }
            if (pawns[x][y] == null && possible[x][y] == 0) {
                    possible[x][y] = 1;
                    ispossibleteritory(x - 1, y);
                    ispossibleteritory(x + 1, y);
                    ispossibleteritory(x, y - 1);
                    ispossibleteritory(x, y + 1);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
    }

    /**
     * Metoda wyswietlająca proponowane terytorium przez klienta
      */

    public static void proposeTerritory(Pane pane){
        for(int i=0; i<boardSize; i++){
            for(int j=0; j<boardSize; j++){
                if(pawns[i][j]==null && territory[i][j]==null){
                    ispossibleteritory(i,j);
                    if(Game.possible==true){
                        getTerritory(pane,i,j);
                    }
                    for(int k=0; k<boardSize; k++) {
                        for (int l = 0; l < boardSize; l++) {
                            possible[k][l]=0;
                        }
                    }
                    Game.possible=true;
                }
            }
        }
    }

    /**
     * Metoda wyswietlająca proponowane terytorium
     */

    public static void getTerritory(Pane pane, int x, int y) {
        try{
            if(pawns[x][y] == null && territory[x][y]==null) {
                Rectangle rec = new Rectangle(rectTable[y][x].rect.getX(), rectTable[y][x].rect.getY(),9,9);
                rec.setFill(Game.Playercolor);
                rec.setOnMouseClicked(event -> {
                    removeTerritory(pane,x,y);
                });
                territory[x][y] = rec;
                pane.getChildren().add(rec);
                getTerritory(pane, x-1, y);
                getTerritory(pane, x+1, y);
                getTerritory(pane, x, y-1);
                getTerritory(pane, x, y+1);
            }
        }catch(ArrayIndexOutOfBoundsException e) {}
    }

    /**
     * Metoda wysyłająca do serwera spasowanie przez gracza i czekająca na odpowiedź
     * @param pane
     * @param buttons
     * @param labels
     */


    public void sendpassed(Pane pane, Button[] buttons, Label[] labels) {
        lobbycomm.out.println("PASS");
        lobbycomm.out.flush();
        try {
            String answer = lobbycomm.in.readLine();
            if(answer.equals("OPPONENT_LEFT_GAME")){
                opponentLeftGame(buttons);
            }else{
                if (answer.equals("PASSED")) {
                    buttons[1].setDisable(true);
                    buttons[2].setDisable(true);
                    for (int i = 0; i < boardSize; i++)
                        for (int j = 0; j < boardSize; j++) {
                            rectTable[i][j].rect.setVisible(false);
                        }
                    Game.playerpassed = true;
                    if (Game.playerpassed == true && Game.opponentpassed == true) {
                        Game.isproposing = true;
                        for (int i = 3; i < 7; i++) {
                            buttons[i].setVisible(true);
                            buttons[i].setDisable(true);
                        }
                        for (int i = 0; i < boardSize; i++) {
                            for (int j = 0; j < boardSize; j++) {
                                if (pawns[i][j] != null && pawns[i][j].circle.getFill() == Game.Playercolor)
                                    pawns[i][j].circle.setDisable(true);
                            }
                        }
                        proposeTerritory(pane);
                    }
                    service1 service1 = new service1();
                    service1.start();
                    service1.setOnSucceeded(event1 -> {
                        String answer11 = (String) event1.getSource().getValue();
                        if (answer11.equals("OPPONENT_LEFT_GAME")) {
                            InfoWindow("Opponent left the game", buttons[1]);
                        }
                        if (answer11.equals("OPPONENT_RESIGNED")) {
                            prepareMapAfterResign(buttons);
                            InfoWindow("Opponent resigned", buttons[1]);
                        }
                        if (answer11.equals("OPPONENT_PASSED")) {
                            for (int i = 0; i < boardSize; i++)
                                for (int j = 0; j < boardSize; j++) {
                                    rectTable[i][j].rect.setVisible(true);
                                }
                            Game.opponentpassed = true;
                            if (Game.playerpassed == true && Game.opponentpassed == true) {
                                Game.isproposing = true;
                                for (int i = 3; i < 7; i++) {
                                    buttons[i].setVisible(true);
                                }
                                buttons[2].setDisable(false);
                                buttons[3].setDisable(false);
                                buttons[4].setDisable(true);
                                buttons[5].setDisable(true);
                                buttons[6].setDisable(false);
                                proposeTerritory(pane);
                            }
                        } else {
                            if (answer11.equals("OPPONENT_RESUMED")) {
                                buttons[1].setDisable(false);
                                buttons[2].setDisable(false);
                                for (int i = 3; i < 7; i++)
                                    buttons[i].setVisible(false);
                                prepareMapAfterResume(pane);
                            }
                            if (answer11.equals("OPPONENT_PROPOSED_TERRITORY")) {
                                buttons[2].setDisable(false);
                                buttons[4].setDisable(false);
                                buttons[5].setDisable(false);
                                buttons[6].setDisable(false);
                                opponentProposeTerritory(pane);
                            }
                            if (answer11.startsWith("OPPONENT_PLACE_PAWN")) {
                                buttons[1].setDisable(false);
                                buttons[2].setDisable(false);
                                Game.playerpassed = false;
                                opponentPlacePawn(pane, answer11);
                                labels[1].setText(Double.toString(Game.OpponentPoints));
                            }
                        }
                    });
                } else {
                    ErrorWindow(answer, buttons[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda wysyłająca terytorium i czekająca na odpowiedź
     * @param pane
     * @param buttons
     * @param labels
     */

    public void sendterritory(Pane pane, Button[] buttons, Label[] labels) {
        String ans = null;
        lobbycomm.out.println("PROPOSE_TERRITORY");
        lobbycomm.out.flush();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (territory[i][j] != null) {
                    if (territory[i][j].getFill() == Game.Playercolor) {
                        lobbycomm.out.println(i + " " + j);
                        lobbycomm.out.flush();
                        territory[i][j].setDisable(true);
                    }
                }
            }
        }
        lobbycomm.out.println("END_TERRITORY");
        lobbycomm.out.flush();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (alivepawns[i][j] != null) {
                    lobbycomm.out.println(i + " " + j);
                    lobbycomm.out.flush();
                    alivepawns[i][j].setDisable(true);
                }
            }
        }
        lobbycomm.out.println("END_LIVING_STONES");
        lobbycomm.out.flush();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (deadpawns[i][j] != null) {
                    lobbycomm.out.println(i + " " + j);
                    lobbycomm.out.flush();
                    deadpawns[i][j].setDisable(true);
                }
            }
        }
        lobbycomm.out.println("END_DEAD_STONES");
        lobbycomm.out.flush();
        try {
            ans = lobbycomm.in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ans.equals("OPPONENT_LEFT_GAME")){
            opponentLeftGame(buttons);
        }else {
            if (ans.equals("PROPOSED")) {
                for (int i = 2; i < 7; i++) {
                    buttons[i].setDisable(true);
                }
                for (int i = 0; i < boardSize; i++)
                    for (int j = 0; j < boardSize; j++) {
                        if (pawns[i][j] != null && pawns[i][j].circle.getFill() == Game.Playercolor)
                            pawns[i][j].circle.setDisable(true);
                        rectTable[i][j].rect.setVisible(false);
                    }
                service1 service1 = new service1();
                service1.start();
                service1.setOnSucceeded(event1 -> {
                    String answer1 = (String) event1.getSource().getValue();
                    if (answer1.equals("OPPONENT_LEFT_GAME")) {
                        InfoWindow("Opponent left the game", buttons[1]);
                    }
                    if (answer1.equals("OPPONENT_RESIGNED")) {
                        prepareMapAfterResign(buttons);
                        InfoWindow("Opponent resigned", buttons[1]);
                    }
                    if (answer1.equals("OPPONENT_RESUMED")) {
                        buttons[1].setDisable(false);
                        buttons[2].setDisable(false);
                        for (int i = 3; i < 7; i++)
                            buttons[i].setVisible(false);
                        prepareMapAfterResume(pane);
                    }
                    if (answer1.equals("OPPONENT_REFUSED_TERRITORY")) {
                        for (int i = 0; i < boardSize; i++)
                            for (int j = 0; j < boardSize; j++) {
                                if (pawns[i][j] != null && pawns[i][j].circle.getFill() == Game.Playercolor)
                                    pawns[i][j].circle.setDisable(true);
                                if (territory[i][j] != null) {
                                    if (territory[i][j].getFill() == Game.Playercolor) {
                                        pane.getChildren().remove(territory[i][j]);
                                        territory[i][j] = null;
                                    }
                                }
                                if (alivepawns[i][j] != null) {
                                    pane.getChildren().remove(alivepawns[i][j]);
                                    alivepawns[i][j] = null;
                                }
                                if (deadpawns[i][j] != null) {
                                    pane.getChildren().remove(deadpawns[i][j]);
                                    deadpawns[i][j] = null;
                                }
                            }
                        if (!Game.placceptterritory) {
                            service1 service11 = new service1();
                            service11.start();
                            service11.setOnSucceeded(event11 -> {
                                String answer11 = (String) event11.getSource().getValue();
                                if (answer11.equals("OPPONENT_LEFT_GAME")) {
                                    InfoWindow("Opponent left the game", buttons[1]);
                                }
                                if (answer11.equals("OPPONENT_RESIGNED")) {
                                    prepareMapAfterResign(buttons);
                                    InfoWindow("Opponent resigned", buttons[1]);
                                }
                                if (answer11.equals("OPPONENT_RESUMED")) {
                                    buttons[1].setDisable(false);
                                    buttons[2].setDisable(false);
                                    for (int i = 3; i < 7; i++)
                                        buttons[i].setVisible(false);
                                    prepareMapAfterResume(pane);
                                }
                                if (answer11.equals("OPPONENT_PROPOSED_TERRITORY")) {
                                    buttons[2].setDisable(false);
                                    buttons[4].setDisable(false);
                                    buttons[5].setDisable(false);
                                    buttons[6].setDisable(false);
                                    opponentProposeTerritory(pane);
                                }
                            });
                        } else {
                            buttons[2].setDisable(false);
                            buttons[3].setDisable(false);
                            buttons[6].setDisable(false);
                            for (int i = 0; i < boardSize; i++)
                                for (int j = 0; j < boardSize; j++) {
                                    if (pawns[i][j] != null && pawns[i][j].circle.getFill() == Game.Playercolor)
                                        pawns[i][j].circle.setDisable(false);
                                    rectTable[i][j].rect.setVisible(true);
                                }
                        }
                    }
                    if (answer1.equals("OPPONENT_ACCEPTED_TERRITORY")) {
                        Game.oppacceptterritory = true;
                        if (Game.oppacceptterritory == true && Game.placceptterritory == true) {
                            for (int i = 0; i < 7; i++)
                                buttons[i].setDisable(true);
                            for (int i = 0; i < boardSize; i++)
                                for (int j = 0; j < boardSize; j++) {
                                    rectTable[i][j].rect.setVisible(false);
                                }
                            try {
                                String answer = lobbycomm.in.readLine();
                                String[] split = answer.split(" ");
                                if(Game.Playercolor==Color.BLACK) {
                                    if (split[1].equals("BLACK")){
                                        labels[0].setText(split[2]);
                                        labels[1].setText(split[3]);
                                        InfoWindow("You win.", buttons[1]);
                                    }else{
                                        labels[0].setText(split[3]);
                                        labels[1].setText(split[2]);
                                        InfoWindow("Opponent win.", buttons[1]);
                                    }
                                }else{
                                    if (split[1].equals("WHITE")){
                                        labels[0].setText(split[2]);
                                        labels[1].setText(split[3]);
                                        InfoWindow("You win.", buttons[1]);
                                    }else{
                                        labels[0].setText(split[3]);
                                        labels[1].setText(split[2]);
                                        InfoWindow("Opponent win.", buttons[1]);
                                    }
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            service1 service11 = new service1();
                            service11.start();
                            service11.setOnSucceeded(event11 -> {
                                String answer11 = (String) event11.getSource().getValue();
                                if (answer11.equals("OPPONENT_LEFT_GAME")) {
                                    InfoWindow("Opponent left the game", buttons[1]);
                                }
                                if (answer11.equals("OPPONENT_RESIGNED")) {
                                    prepareMapAfterResign(buttons);
                                    InfoWindow("Opponent resigned", buttons[1]);
                                }
                                if (answer11.equals("OPPONENT_RESUMED")) {
                                    buttons[1].setDisable(false);
                                    buttons[2].setDisable(false);
                                    for (int i = 3; i < 7; i++)
                                        buttons[i].setVisible(false);
                                    prepareMapAfterResume(pane);
                                }
                                if (answer11.equals("OPPONENT_PROPOSED_TERRITORY")) {
                                    buttons[2].setDisable(false);
                                    buttons[4].setDisable(false);
                                    buttons[5].setDisable(false);
                                    buttons[6].setDisable(false);
                                    opponentProposeTerritory(pane);
                                }

                            });
                        }
                    }
                });
            } else {
                ErrorWindow(ans+" no co to ma być", buttons[1]);
            }
        }
    }

    /**
     * Metoda usuwająca zaznaczenie martwych kamieni
     * @param pane
     * @param x
     * @param y
     */

    public static void removeDeadPawns(Pane pane, int x, int y){
        try{
            if(pawns[x][y] != null && deadpawns[x][y]!=null) {
                pane.getChildren().remove(deadpawns[x][y]);
                deadpawns[x][y] = null;
                pawns[x][y].circle.setDisable(false);
                removeDeadPawns(pane, x-1, y);
                removeDeadPawns(pane, x+1, y);
                removeDeadPawns(pane, x, y-1);
                removeDeadPawns(pane, x, y+1);
            }
        }catch(ArrayIndexOutOfBoundsException e) {}

    }

    /**
     * Metoda usuwająca zaznaczenie żywych kamieni
     * @param pane
     * @param x
     * @param y
     */

    public static void removeAlivePawns(Pane pane, int x, int y){
        try{
            if(pawns[x][y] != null && alivepawns[x][y]!=null) {
                pane.getChildren().remove(alivepawns[x][y]);
                alivepawns[x][y] = null;
                pawns[x][y].circle.setDisable(false);
                removeAlivePawns(pane, x-1, y);
                removeAlivePawns(pane, x+1, y);
                removeAlivePawns(pane, x, y-1);
                removeAlivePawns(pane, x, y+1);
            }
        }catch(ArrayIndexOutOfBoundsException e) {}

    }

    /**
     * Metoda odrzucająca terytorium i nasłuchująca odpowiedzi
     * @param pane
     * @param buttons
     */

    public void rejectterritory(Pane pane, Button[] buttons) {
        String ans = null;
        lobbycomm.out.println("REFUSE_TERRITORY");
        lobbycomm.out.flush();
        try {
            ans = lobbycomm.in.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ans.equals("OPPONENT_LEFT_GAME")){
            opponentLeftGame(buttons);
        }else {
            if (ans.equals("REFUSED")) {

                if (Game.oppacceptterritory == true) {
                    buttons[2].setDisable(true);
                    buttons[4].setDisable(true);
                    buttons[5].setDisable(true);
                    buttons[6].setDisable(true);
                    for (int i = 0; i < boardSize; i++)
                        for (int j = 0; j < boardSize; j++) {
                            if (pawns[i][j] != null && pawns[i][j].circle.getFill() == Game.Playercolor)
                                pawns[i][j].circle.setDisable(true);
                            rectTable[i][j].rect.setVisible(false);
                            if (territory[i][j] != null) {
                                if (territory[i][j].getFill() == Game.OpponentColor) {
                                    pane.getChildren().remove(territory[i][j]);
                                    territory[i][j] = null;
                                }
                            }
                            if (oppalivepawns[i][j] != null) {
                                pane.getChildren().remove(oppalivepawns[i][j]);
                                oppalivepawns[i][j] = null;
                            }
                            if (oppdeadpawns[i][j] != null) {
                                pane.getChildren().remove(oppdeadpawns[i][j]);
                                oppdeadpawns[i][j] = null;
                            }
                        }

                    service1 service11 = new service1();
                    service11.start();
                    service11.setOnSucceeded(event11 -> {
                        String answer11 = (String) event11.getSource().getValue();
                        if (answer11.equals("OPPONENT_LEFT_GAME")) {
                            InfoWindow("Opponent left the game", buttons[1]);
                        }
                        if (answer11.equals("OPPONENT_RESIGNED")) {
                            prepareMapAfterResign(buttons);
                            InfoWindow("Opponent resigned", buttons[1]);
                        }
                        if (answer11.equals("OPPONENT_RESUMED")) {
                            buttons[1].setDisable(false);
                            buttons[2].setDisable(false);
                            for (int i = 3; i < 7; i++)
                                buttons[i].setVisible(false);
                           prepareMapAfterResume(pane);
                        }
                        if (answer11.equals("OPPONENT_PROPOSED_TERRITORY")) {
                            buttons[2].setDisable(false);
                            buttons[4].setDisable(false);
                            buttons[5].setDisable(false);
                            buttons[6].setDisable(false);
                            opponentProposeTerritory(pane);
                        }
                    });
                } else {
                    buttons[2].setDisable(false);
                    buttons[3].setDisable(false);
                    buttons[4].setDisable(true);
                    buttons[5].setDisable(true);
                    buttons[6].setDisable(false);
                    for (int i = 0; i < boardSize; i++)
                        for (int j = 0; j < boardSize; j++) {
                            if (pawns[i][j] != null && pawns[i][j].circle.getFill() == Game.Playercolor)
                                pawns[i][j].circle.setDisable(false);
                            rectTable[i][j].rect.setVisible(true);
                            if (territory[i][j] != null) {
                                if (territory[i][j].getFill() == Game.OpponentColor) {
                                    pane.getChildren().remove(territory[i][j]);
                                    territory[i][j] = null;
                                }
                            }
                            if (oppalivepawns[i][j] != null) {
                                pane.getChildren().remove(oppalivepawns[i][j]);
                                oppalivepawns[i][j] = null;
                            }
                            if (oppdeadpawns[i][j] != null) {
                                pane.getChildren().remove(oppdeadpawns[i][j]);
                                oppdeadpawns[i][j] = null;
                            }
                        }
                }
            } else {
                ErrorWindow(ans, buttons[1]);
            }
        }
    }

    /**
     * Metoda akceptująca terytorium
     * @param buttons
     * @param labels
     */

    public void acceptterritory(Button[] buttons, Label[] labels) {
        String ans = null;
        lobbycomm.out.println("ACCEPT_TERRITORY");
        lobbycomm.out.flush();
        try {
            ans = lobbycomm.in.readLine();
            System.out.println(ans);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ans.equals("OPPONENT_LEFT_GAME")){
            opponentLeftGame(buttons);
        }else {
            if (ans.equals("ACCEPTED")) {
                if (!(Game.placceptterritory == true && Game.oppacceptterritory == true)) {
                    buttons[2].setDisable(false);
                    buttons[3].setDisable(false);
                    buttons[4].setDisable(true);
                    buttons[5].setDisable(true);
                    buttons[6].setDisable(false);
                }
                for (int i = 0; i < boardSize; i++)
                    for (int j = 0; j < boardSize; j++) {
                        rectTable[i][j].rect.setVisible(true);
                        if (pawns[i][j] != null && pawns[i][j].circle.getFill() == Game.Playercolor)
                            pawns[i][j].circle.setDisable(false);
                    }
                Game.placceptterritory = true;
                if (Game.placceptterritory == true && Game.oppacceptterritory == true) {
                    for (int i = 0; i < 7; i++)
                        buttons[i].setDisable(true);
                    for (int i = 0; i < boardSize; i++)
                        for (int j = 0; j < boardSize; j++) {
                            rectTable[i][j].rect.setVisible(false);
                        }
                    try {
                        String answer = lobbycomm.in.readLine();
                        String[] split = answer.split(" ");
                        if(Game.Playercolor==Color.BLACK) {
                            if (split[1].equals("BLACK")){
                                labels[0].setText(split[2]);
                                labels[1].setText(split[3]);
                                InfoWindow("You win.", buttons[1]);
                            }else{
                                labels[0].setText(split[3]);
                                labels[1].setText(split[2]);
                                InfoWindow("Opponent win.", buttons[1]);
                            }
                        }else{
                            if (split[1].equals("WHITE")){
                                labels[0].setText(split[2]);
                                labels[1].setText(split[3]);
                                InfoWindow("You win.", buttons[1]);
                            }else{
                                labels[0].setText(split[3]);
                                labels[1].setText(split[2]);
                                InfoWindow("Opponent win.", buttons[1]);
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                ErrorWindow(ans, buttons[1]);
            }
        }
    }

    /**
     * Metoda zaznaczająca żywe kamienie
     * @param pane
     * @param x
     * @param y
     */

    public static void getAlivePawns(Pane pane, int x, int y) {
        try{
            if(pawns[x][y] != null && alivepawns[x][y]==null) {
                if(pawns[x][y].color1 == Game.Playercolor) {
                    Circle cir = new Circle(pawns[x][y].circle.getCenterX(),pawns[x][y].circle.getCenterY(),5,Color.GREEN);
                    pane.getChildren().add(cir);
                    cir.setOnMouseClicked(event -> {
                        removeAlivePawns(pane,x,y);
                    });
                    alivepawns[x][y] = cir;
                    pawns[x][y].circle.setDisable(true);
                    getAlivePawns(pane, x - 1, y);
                    getAlivePawns(pane, x + 1, y);
                    getAlivePawns(pane, x, y - 1);
                    getAlivePawns(pane, x, y + 1);
                }
            }
        }catch(ArrayIndexOutOfBoundsException e) {}
    }

    /**
     * Metoda zaznaczająca martwe kamienie
     * @param pane
     * @param x
     * @param y
     */

    public static void getDeadPawns(Pane pane, int x, int y) {
        try{
            if(pawns[x][y] != null && deadpawns[x][y]==null) {
                if(pawns[x][y].color1 == Game.Playercolor) {
                    Circle cir = new Circle(pawns[x][y].circle.getCenterX(),pawns[x][y].circle.getCenterY(),5,Color.RED);
                    pane.getChildren().add(cir);
                    cir.setOnMouseClicked(event -> {
                        removeDeadPawns(pane,x,y);
                    });
                    deadpawns[x][y] = cir;
                    pawns[x][y].circle.setDisable(true);
                    getDeadPawns(pane, x - 1, y);
                    getDeadPawns(pane, x + 1, y);
                    getDeadPawns(pane, x, y - 1);
                    getDeadPawns(pane, x, y + 1);
                }
            }
        }catch(ArrayIndexOutOfBoundsException e) {}
    }

    /**
     * Metoda wznawiająca grę i nasłuchująca odpowiedzi
     * @param pane
     * @param buttons
     * @param labels
     */

    public void resume(Pane pane, Button[] buttons, Label[] labels) {
        String ans = null;
        lobbycomm.out.println("RESUME");
        lobbycomm.out.flush();
        try {
           ans = lobbycomm.in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ans.equals("OPPONENT_LEFT_GAME")){
            opponentLeftGame(buttons);
        }else {
            if (ans.equals("RESUMED")) {
                for (int i = 3; i < 7; i++)
                    buttons[i].setVisible(false);
                buttons[2].setDisable(true);
                prepareMapAfterResume(pane);
                service1 service1 = new service1();
                service1.start();
                service1.setOnSucceeded(event -> {
                    String answer1 = (String) event.getSource().getValue();
                    if (answer1.equals("OPPONENT_LEFT_GAME")) {
                        InfoWindow("Opponent left the game", buttons[1]);
                    }
                    if (answer1.equals("OPPONENT_RESIGNED")) {
                        prepareMapAfterResign(buttons);
                        InfoWindow("Opponent resigned", buttons[1]);
                    }
                    if (answer1.equals("OPPONENT_PASSED")) {
                        for (int i = 0; i < boardSize; i++)
                            for (int j = 0; j < boardSize; j++) {
                                rectTable[i][j].rect.setVisible(true);
                            }
                        buttons[1].setDisable(false);
                        buttons[2].setDisable(false);
                        Game.opponentpassed = true;
                    }
                    if (answer1.startsWith("OPPONENT_PLACE_PAWN")) {
                        buttons[1].setDisable(false);
                        buttons[2].setDisable(false);
                        opponentPlacePawn(pane, answer1);
                        labels[1].setText(Double.toString(Game.OpponentPoints));
                    }

                });
            } else {
                ErrorWindow(ans, buttons[1]);
            }
        }
    }

    /**
     * Metoda wysyłająca rezygnację
     * @param pane
     * @param buttons
     */

    public void resign(Pane pane, Button[] buttons) {
        String ans = null;
        lobbycomm.out.println("RESIGN");
        lobbycomm.out.flush();
        try {
           ans = lobbycomm.in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ans.equals("OPPONENT_LEFT_GAME")){
            opponentLeftGame(buttons);
        }else {
            if (ans.equals("RESIGNED")) {
                prepareMapAfterResign(buttons);
            } else {
                ErrorWindow(ans, buttons[1]);
            }
        }
    }

    /**
     * Metoda wyświetlająca ewentualne błędy
     * @param message
     * @param button
     */

    public static void ErrorWindow(String message,Button button){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setX(button.getScene().getWindow().xProperty().getValue()+200);
        alert.setY(button.getScene().getWindow().yProperty().getValue()+200);
        alert.showAndWait();
    }

    /**
     * Metoda wyświetlająca ewentualne informacje odnośnie gry
     * @param message
     * @param button
     */

    public static void InfoWindow(String message, Button button){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setX(button.getScene().getWindow().xProperty().getValue()+200);
        alert.setY(button.getScene().getWindow().yProperty().getValue()+200);
        alert.showAndWait();
    }


    static class  service extends Service<String> {
    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() {
                String answer = null;
                try {
                    answer = sendready();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return answer;
            }
        };
    }
}
    static class service1 extends Service<String>{

        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                @Override
                protected String call() {
                    String answer = null;
                    try {
                        answer = waittoopponent();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return answer;
                }
            };
        }
    }
}
