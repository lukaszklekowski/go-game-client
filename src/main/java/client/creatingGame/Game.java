package client.creatingGame;

import javafx.scene.paint.Color;

/**
 * Klasa zawierająca informacje na temat aktualnie toczącej się gry.
 */
public class Game {
    public static String PlayerName;
    static String GameName;
    static String OpponentName;
    public static Color Playercolor;
    public static Color OpponentColor;
    public static boolean isproposing;
    public static boolean playerpassed;
    public static boolean opponentpassed;
    public static boolean oppacceptterritory;
    public static boolean placceptterritory;
    public static boolean possible;
    public static String type;
    public static double PlayerPoints;
    public static double OpponentPoints;

    public Game(String playername, String gamename, String opponentname, Color opponentColor, Color playercolor, String typ){
        type = typ;
        OpponentName = opponentname;
        PlayerName = playername;
        GameName = gamename;
        Playercolor = playercolor;
        OpponentColor = opponentColor;
        isproposing = false;
        playerpassed = false;
        opponentpassed = false;
        oppacceptterritory = false;
        placceptterritory = false;
        possible = true;
        PlayerPoints = 0;
        OpponentPoints = 0;
    }

}
