
package gui;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import model.Court; //plus tard pour paramétrer taille, etc
import javafx.stage.Stage;
import java.awt.Color;
import javafx.scene.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Court;
import model.RacketController;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.effect.ImageInput;
import model.Score;



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

//App, fichier du jeu in-game
//Implémentation du menu pause : Fait
//Implémentation du menu de fin de jeu : à faire (besoin du score et du timer)

public class App extends Application {

    public Pane root;
    public Scene gameScene;

    App(Pane root, Scene a){
        this.root = root;
        gameScene = a;
    }

    public static String[] commandes = {"A", "Q", "P", "M"};

        public static void setCommandes(String[] s){
            commandes[0] = s[0];
            commandes[1] = s[1];
            commandes[2] = s[2];
            commandes[3] = s[3];
        }

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
                    Image img = new Image("file:src/Pictures/fond.png");
                    BackgroundImage bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
                    Background bGround = new Background(bImg);
                    root.setBackground(bGround);
                    var court = new Court(playerA, playerB, 1000, 600, 3);
                    var gameView = new GameView(court, root, 1);


                    //Pour le menu de pause
                    Image image2 = new Image(new File("src/Pictures/pause1.gif").toURI().toString());
                    ImageView imageV = new ImageView(image2);
                    imageV.setX(290);
                    imageV.setY(200);
                    
                    Button Quitter= new Button("Quitter") ;
                    Quitter.setLayoutX(320);
                    Quitter.setLayoutY(350);
                    Quitter.setMinSize(80, 80);
                    Quitter.setEffect(new ImageInput(new Image("file:src/Pictures/retourM.png")));
                    Quitter.setSkin(new MyButtonSkin(Quitter));
                  
                    
                    Button Reprendre= new Button("Reprendre") ;
                    Reprendre.setLayoutX(485);
                    Reprendre.setLayoutY(350);
                    Reprendre.setMinSize(80, 80);
                    Reprendre.setEffect(new ImageInput(new Image("file:src/Pictures/play.png")));
                    Reprendre.setSkin(new MyButtonSkin(Reprendre));
                   

                    Button Recommencer= new Button("Recommencer") ;
                    Recommencer.setLayoutX(695);
                    Recommencer.setLayoutY(350);
                    Recommencer.setMinSize(80, 80);
                    Recommencer.setEffect(new ImageInput(new Image("file:src/Pictures/recommencer.png")));
                    Recommencer.setSkin(new MyButtonSkin(Recommencer));
                  
                    //Switch pour les boutons de jeu, in-game.
                    gameScene.setOnKeyPressed(ev -> {
                        String s = ev.getCode().toString();

                            if(s == commandes[0]){
                                playerA.state = RacketController.State.GOING_UP;
                            } else if(s == commandes[1]){
                                playerA.state = RacketController.State.GOING_DOWN;
                            } else if(s == commandes[2]){
                                playerB.state = RacketController.State.GOING_UP;
                            } else if(s == commandes[3]){
                                playerB.state = RacketController.State.GOING_DOWN;
                            } else if(s == "ESCAPE"){
                               if(!gameView.pause){
                                root.getChildren().add(imageV);
                                root.getChildren().addAll(Quitter, Reprendre, Recommencer);
                                gameView.pause = true;
                               }else{
                                root.getChildren().removeAll(imageV, Quitter, Reprendre, Recommencer);
                                gameView.pause = false ; 
                               }
                        }
                    });


                    //Switch bouton in-game, uniquement pour les boutons de jeu. 
                    gameScene.setOnKeyReleased(ev -> {
                        String s = ev.getCode().toString();

                        if(s == commandes[0]){
                            if (playerA.state == RacketController.State.GOING_UP) playerA.state = RacketController.State.IDLE;
                        } else if(s == commandes[1]){
                            if (playerA.state == RacketController.State.GOING_DOWN) playerA.state = RacketController.State.IDLE;
                        } else if(s == commandes[2]){
                            if (playerB.state == RacketController.State.GOING_UP) playerB.state = RacketController.State.IDLE;
                        } else if(s == commandes[3]){
                            if (playerB.state == RacketController.State.GOING_DOWN) playerB.state = RacketController.State.IDLE;
                        }
                    });

                  
                        if(GameView.finGame == true ){
                            Image fin = new Image("file:src/Pictures/WinJ1.png");
                            ImageView finJ1 = new ImageView(fin);
                            root.getChildren().add(finJ1);
                            root.getChildren().addAll(Quitter, Reprendre, Recommencer);
                            // imageV.setX(290);
                            // imageV.setY(200);                   
                        }
                    

                    gameView.animate();

                    //Action du bouton Quitter
                    Quitter.setOnAction(ev1 -> {
                        primaryStage.close();
                        Menu q = new Menu();
                        q.start(primaryStage);
                    });

                    //Action du bouton Reprendre
                    Reprendre.setOnAction(ev1 ->{
                        root.getChildren().removeAll(imageV, Quitter, Reprendre, Recommencer);
                        gameView.pause = false ; 
                    });

                    //Action du bouton Recommencer
                    Recommencer.setOnAction(ev1 ->{
                        root.getChildren().remove(imageV);
                        root.getChildren().removeAll(Quitter, Reprendre, Recommencer);
                       court.reset() ; 
                       court.getScore().reset();
                       gameView.pause = false ; 
                       GameView.finGame = false;
                    });			

    }       
}
