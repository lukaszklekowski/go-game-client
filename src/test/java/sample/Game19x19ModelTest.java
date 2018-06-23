package sample;

import client.creatingGame.Boards.GameModel;
import client.creatingGame.Game;

import client.LobbyCommunication;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.*;

/**
 * Created by Lukasz on 2016-12-23.
 */
public class Game19x19ModelTest {

    GameModel model;
    LobbyCommunication client;
    Pane pane;
    Button[] button;
    Label[] label;
    server serv;
    Game game;

    public void setUp(String message) throws IOException
    {

        game = new Game("playername","gamename","opponentname", Color.WHITE, Color.BLACK,"19x19");
        serv = new server();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                serv.listensocket();
            }
        });
        t.start();

        client = new LobbyCommunication("localhost", 9444);

        model = new GameModel(19, client);
        button = new Button[7];
        label = new Label[2];
        pane= new Pane();
    }

    BufferedReader getAnswer(ByteArrayOutputStream out) throws IOException
    {
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(out.toByteArray())));
    }

    @Test
    public void createrectTest() throws Exception {
        setUp("");
        model.createrect(pane,button,label);
        for(int i = 0;i<19; i++){
            for(int j=0; j<19; j++){
                assertNotNull(model.rectTable[i][j]);
            }
        }
        serv.serv.close();
    }

    @Test
    public void opponentProposeTerritoryTest() throws Exception {
        setUp("");
        serv.out.println("OPPONENT_FINISHED_MOVE");
        serv.out.println("OPPONENT_FINISHED_MOVE");
        serv.out.println("2 4");
        serv.out.println("END_TERRITORY");
        serv.out.println("2 3");
        serv.out.println("END_LIVING_STONES");
        serv.out.println("1 7");
        serv.out.println("END_DEAD_STONES");
        model.createrect(pane, button, label);
        model.opponentPlacePawn(pane,"OPPONENT_PLACE_PAWN 2 3");
        model.opponentPlacePawn(pane,"OPPONENT_PLACE_PAWN 1 7");
        model.opponentProposeTerritory(pane);
        assertNotNull(model.territory[2][4]);
        assertNotNull(model.oppalivepawns[2][3]);
        assertNotNull(model.oppdeadpawns[1][7]);
        serv.serv.close();
    }

    @Test
    public void opponentPlacePawn() throws Exception {
        setUp("");
        model.createrect(pane, button, label);
        serv.out.println("OPPONENT_FINISHED_MOVE");
        model.opponentPlacePawn(pane,"OPPONENT_PLACE_PAWN 2 3");
        serv.serv.close();
    }

    @Test
    public void prepareMapAfterResume() throws Exception {

    }

    @Test
    public void sendpawn() throws Exception {

    }

    @Test
    public void getpawn() throws Exception {

    }

    @Test
    public void removepawn() throws Exception {

    }

    @Test
    public void sendready() throws Exception {

    }

    @Test
    public void waittoopponent() throws Exception {

    }

    @Test
    public void waittostart() throws Exception {

    }

    @Test
    public void errorWindow() throws Exception {

    }

    @Test
    public void infoWindow() throws Exception {

    }

    @Test
    public void removeTerritory() throws Exception {

    }

    @Test
    public void ispossibleteritory() throws Exception {

    }

    @Test
    public void proposeTerritory() throws Exception {

    }

    @Test
    public void getTerritory() throws Exception {

    }

    @Test
    public void sendpassed() throws Exception {

    }

    @Test
    public void sendterritory() throws Exception {

    }

    @Test
    public void removeDeadPawns() throws Exception {

    }

    @Test
    public void removeAlivePawns() throws Exception {

    }

    @Test
    public void rejectterritory() throws Exception {

    }

    @Test
    public void acceptterritory() throws Exception {

    }

    @Test
    public void getAlivePawns() throws Exception {

    }

    @Test
    public void getDeadPawns() throws Exception {

    }

    @Test
    public void resume() throws Exception {

    }

    @Test
    public void resign() throws Exception {

    }
class server{
        BufferedReader in;
        PrintWriter out;
    ServerSocket serv;
    Socket client = null;
    server(){
        try {
            serv =  new ServerSocket(9444);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void listensocket(){
        try {
            client = serv.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    }
}