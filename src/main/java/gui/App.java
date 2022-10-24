
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
                    Image img = new Image("file:src/Pictures/fond.png");
                    BackgroundImage bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
                    Background bGround = new Background(bImg);
                    root.setBackground(bGround);
                    var court = new Court(playerA, playerB, 1000, 600);
                    var gameView = new GameView(court, root, 1);


                    //pour pause 
                    // Rectangle Sq = new Rectangle();
                    
                    // Sq.setFill(new ImagePattern(image2)); 
                    Image image2 = new Image(new File("src/Pictures/pause.gif").toURI().toString());

                    ImageView imageV = new ImageView(image2);
                    imageV.setX(290);
                    imageV.setY(200);
                    
                    Button Quitter= new Button("Quitter") ;
                    Quitter.setLayoutX(320);
                    Quitter.setLayoutY(350);
                    Quitter.setMinSize(80, 80);
                    Quitter.setEffect(new ImageInput(new Image("file:src/Pictures/retourM.png")));
                    Quitter.setSkin(new MyButtonSkin(Quitter));
                    Quitter.setOnMouseEntered(e -> {
                        Image a = new Image("file:src/Pictures/1.png");
                        ImageView image4 = new ImageView(a);
                        image4.setX(320);
                        image4.setY(430);
                        root.getChildren().add(image4);
                        Quitter.setOnMouseExited(ea ->{
                            root.getChildren().remove(image4);
                        });
                    });
                    



                    Button Reprendre= new Button("Reprendre") ;
                    Reprendre.setLayoutX(485);
                    Reprendre.setLayoutY(350);
                    Reprendre.setMinSize(80, 80);
                    Reprendre.setEffect(new ImageInput(new Image("file:src/Pictures/play.png")));
                    Reprendre.setSkin(new MyButtonSkin(Reprendre));
                    Reprendre.setOnMouseEntered(e -> {
                        Image a = new Image("file:src/Pictures/3.png");
                        ImageView image4 = new ImageView(a);
                        image4.setX(485);
                        image4.setY(450);
                        root.getChildren().add(image4);
                        Reprendre.setOnMouseExited(ea ->{
                            root.getChildren().remove(image4);
                        });
                    });

                    Button Recommencer= new Button("Recommencer") ;
                    Recommencer.setLayoutX(695);
                    Recommencer.setLayoutY(350);
                    Recommencer.setMinSize(80, 80);
                    Recommencer.setEffect(new ImageInput(new Image("file:src/Pictures/recommencer.png")));
                    Recommencer.setSkin(new MyButtonSkin(Recommencer));
                    Recommencer.setOnMouseEntered(e -> {
                        Image a = new Image("file:src/Pictures/2.png");
                        ImageView image4 = new ImageView(a);
                        image4.setX(695);
                        image4.setY(430);
                        root.getChildren().add(image4);
                        Recommencer.setOnMouseExited(ea ->{
                            root.getChildren().remove(image4);
                        });
                    });

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
                            case ESCAPE:
                               if(!gameView.pause){
                                
                                root.getChildren().add(imageV);
                                root.getChildren().addAll(Quitter, Reprendre, Recommencer);
                                gameView.pause = true;
                               }else{
                                root.getChildren().removeAll(imageV, Quitter, Reprendre, Recommencer);
                                gameView.pause = false ; 

                               }
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

                    gameView.animate();

                    Quitter.setOnAction(ev1 -> {
                        primaryStage.close();
                        Menu q = new Menu();
                        q.start(primaryStage);
                    });

                    Reprendre.setOnAction(ev1 ->{
                        root.getChildren().remove(imageV);
                        root.getChildren().removeAll(Quitter, Reprendre, Recommencer);
                        gameView.pause = false ; 
                    });

                    Recommencer.setOnAction(ev1 ->{
                        Pane root2 = new Pane();
                        gameScene.setRoot(root2);
                        App a = new App(root2, gameScene);
                        a.start(primaryStage);
                    });
				
				}


			

	        

       
    }
