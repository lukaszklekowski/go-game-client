package client.creatingGame.Boards;


import client.creatingGame.Game;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * Klasa tworząca prostokaty pozwalajace postawienie pionka lub zaznaczenie terytorium
 */
public class Rect extends Rectangle {

    public Rectangle rect;
    Lighting light;
    public int px1;
    public int py1;

    public Rect(Pane pane, int pxx, int pxy, int x, int y, Button[] table1, Label[] label){
        px1=pxx;
        py1=pxy;
        rect = new Rectangle(9,9);
        rect.setX(pxx-4);
        rect.setY(pxy-4);
        rect.setFill(Color.BLUE);
        rect.setOpacity(0);
        rect.setOnMouseEntered(event -> {
            light = new Lighting();
            rect.setOpacity(1);
        });
        rect.setOnMouseExited(event -> rect.setOpacity(0));
        rect.setOnMouseClicked(event -> {// eventy stawiające pionki lub zaznaczające terytorium
                if (!Game.isproposing) {
                    try {
                        GameModel.sendpawn(pxx, pxy, x, y, pane, table1, label);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    GameModel.ispossibleteritory(x, y);
                    if(Game.type.equals("19x19")) {
                        for (int i = 0; i < 19; i++)
                            for (int j = 0; j < 19; j++) {
                                GameModel.possible[i][j] = 0;
                            }
                    }
                    if(Game.type.equals("13x13")) {
                        for (int i = 0; i < 13; i++)
                            for (int j = 0; j < 13; j++) {
                                GameModel.possible[i][j] = 0;
                            }
                    }
                    if(Game.type.equals("9x9")) {
                        for (int i = 0; i < 9; i++)
                            for (int j = 0; j < 9; j++) {
                                GameModel.possible[i][j] = 0;
                            }
                    }
                    if (Game.possible) {
                        GameModel.getTerritory(pane, x, y);
                    }
                    Game.possible = true;
                }
        });
        pane.getChildren().add(rect);
    }
}
