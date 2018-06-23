package client.creatingGame.Boards;

import client.creatingGame.Game;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Klasa tworząca pionki w grze
 */
public class Pawn extends Circle {

    public Circle circle;
    int pxx;
    int pxy;
    public Color color1;


    public Pawn(Pane pane, int pxx, int pxy, int x, int y, Color color){
        this.pxx = pxx;
        this.pxy = pxy;
        this.color1 = color;
        circle = new Circle(11, color);
        circle.setCenterX(pxx);
        circle.setCenterY(pxy);
        circle.setOnMouseClicked(event -> {//eventy odpowiedzialne za możliwość oznaczania ich jako martwe lub żywe
                if (Game.isproposing && circle.getFill() == color1) {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        GameModel.getAlivePawns(pane, x, y);
                    } else {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            GameModel.getDeadPawns(pane, x, y);
                        }
                    }
                }
        });
        pane.getChildren().add(circle);

    }
}