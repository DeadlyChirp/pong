
package gui;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import model.Court; //plus tard pour paramétrer taille, etc
import javafx.stage.Stage;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Court;
import model.RacketController;

//*************************************TEST*********** */
import java.io.File; 
import java.io.IOException; 
import java.util.Scanner; 
  
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException;
//***************************************************** */

//Jerem Menu Fichier
public class App extends Application {

    public Pane root;
    public Scene gameScene;

    App(Pane root, Scene a){
        this.root = root;
        gameScene = a;
    }

    @Override
    public void start(Stage primaryStage)  {

                    class Player implements RacketController {
                        State state = State.IDLE;
                        @Override
                        public State getState() {
                            return state;
                        }
                    }
                    var playerA = new Player();
                    var playerB = new Player();
                    // var J1Code = new Code();
                    // var J2Code = new Code();
                    gameScene.setOnKeyPressed(ev -> {
                        switch (ev.getCode()) {
                            case A:
                                playerA.state = RacketController.State.GOING_UP;
                                break;
                            case Q:
                                playerA.state = RacketController.State.GOING_DOWN;
                                break;
                            case P:
                                playerB.state = RacketController.State.GOING_UP;
                                break;
                            case M:
                                playerB.state = RacketController.State.GOING_DOWN;
                                break;
                        }
                    });
                    gameScene.setOnKeyReleased(ev -> {
                        switch (ev.getCode()) {
                            case A:
                                if (playerA.state == RacketController.State.GOING_UP) playerA.state = RacketController.State.IDLE;
                                break;
                            case Q:
                                if (playerA.state == RacketController.State.GOING_DOWN) playerA.state = RacketController.State.IDLE;
                                break;
                            case P:
                                if (playerB.state == RacketController.State.GOING_UP) playerB.state = RacketController.State.IDLE;
                                break;
                            case M:
                                if (playerB.state == RacketController.State.GOING_DOWN) playerB.state = RacketController.State.IDLE;
                                break;
                        }
                    });
                    var court = new Court(playerA, playerB, 1000, 600);
                    var gameView = new GameView(court, root, 0.89);
                    javafx.scene.control.Button Retour= new Button("Retour") ;
                    Retour.setLayoutX(920);
                    Retour.setLayoutY(30);
                    root.getChildren().add(Retour);
                    gameView.animate();

                    Retour.setOnMouseClicked(ev1 -> {
                        primaryStage.close();
                        Menu q = new Menu();
                        q.start(primaryStage);
                    });
				
				}


			

	        

       
    }

