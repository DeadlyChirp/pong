package model;

import javafx.stage.Stage;

import java.awt.*;

import static javafx.application.Application.launch;

public class CompteurPoints extends Rectangle {

    //private text
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int player1;
    int player2;

    CompteurPoints(int GAME_WIDTH, int GAME_HEIGHT){
        CompteurPoints.GAME_WIDTH = GAME_WIDTH;
        CompteurPoints.GAME_HEIGHT = GAME_HEIGHT;
    }
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Consolas",Font.PLAIN,60));

        g.drawLine(GAME_WIDTH/2, 0, GAME_WIDTH/2, GAME_HEIGHT);
        //draw a line in the middle of the panel
        g.drawString(String.valueOf(player1/10)+String.valueOf(player1%10), (GAME_WIDTH/2)-85, 50);
        //string, 00-01 on top in the middle
        g.drawString(String.valueOf(player2/10)+String.valueOf(player2%10), (GAME_WIDTH/2)+20, 50);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

    }
}
