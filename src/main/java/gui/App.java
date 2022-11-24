
package gui;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import model.Court; //plus tard pour paramétrer taille, etc
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import model.RacketController;
import javafx.scene.layout.*;
import javafx.scene.effect.ImageInput;
import model.Score;
import model.TimeMode;



//*************************************TEST*********** */
import java.io.File; 

//***************************************************** */

//App, fichier du jeu in-game
//Implémentation du menu pause : Fait
//Implémentation du menu de fin de jeu : fait

public class App extends Application {

    public static Pane root;
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

        public static Button Quitter= new Button("Quitter") ;
        public static Button Reprendre= new Button("Reprendre") ;
        public static Button Recommencer= new Button("Recommencer") ;



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
                    var court = new Court(playerA, playerB, 1000, 600, 2);
                    var gameView = new GameView(court, root, 1);


                    //Pour le menu de pause
                    Image image2 = new Image(new File("src/Pictures/pause1.gif").toURI().toString());
                    ImageView imageV = new ImageView(image2);
                    imageV.setX(290);
                    imageV.setY(200);
                    
                    Quitter.setLayoutX(320);
                    Quitter.setLayoutY(350);
                    Quitter.setMinSize(80, 80);
                    Quitter.setEffect(new ImageInput(new Image("file:src/Pictures/retourM.png")));
                    Quitter.setSkin(new MyButtonSkin(Quitter));
                  
                    
                    Reprendre.setLayoutX(485);
                    Reprendre.setLayoutY(350);
                    Reprendre.setMinSize(80, 80);
                    Reprendre.setEffect(new ImageInput(new Image("file:src/Pictures/play.png")));
                    Reprendre.setSkin(new MyButtonSkin(Reprendre));
                   

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
                               if(!gameView.pause && !GameView.getFin()){
                                root.getChildren().add(imageV);
                                root.getChildren().addAll(Quitter, Reprendre, Recommencer);
                                gameView.pause = true;
                               }else if(!GameView.getFin()){
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


                    gameView.animate();
                    

                    //Action du bouton Quitter
                    Quitter.setOnAction(ev1 -> {
                        Pane root1 = new Pane();
                    gameScene.setRoot(root1);
                    Menu a = new Menu(root1, gameScene);
                    a.start(primaryStage);
                    });

                    //Action du bouton Reprendre
                    Reprendre.setOnAction(ev1 ->{
                        root.getChildren().removeAll(imageV, Quitter, Reprendre, Recommencer);
                        gameView.pause = false ; 
                    });

                    //Action du bouton Recommencer
                    Recommencer.setOnAction(ev1 ->{
                        Quitter.setLayoutX(320);
                        Recommencer.setLayoutX(695);
                        Recommencer.setLayoutY(350);
                        Quitter.setLayoutY(350);
                        root.getChildren().remove(imageV);
                        if (GameView.finGame){
                            root.getChildren().remove(root.getChildren().size()-3) ; 
                            root.getChildren().remove(root.getChildren().size()-3) ;  
                        } 
                        root.getChildren().removeAll(Quitter, Reprendre, Recommencer);
                        court.reset() ; 
                        court.getScore().reset();
                        gameView.pause = false ; 
                        GameView.finGame = false;
                    });			

    }
    
    //pour le timer de timermode
    public void startTimer(Stage primaryStage, int nbManche, int t)  {

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
        var court = new TimeMode(playerA, playerB, 1000, 600, nbManche, t);
        var gameView = new GameView(court, root, 1);

         //Pour le menu de pause
         Image image2 = new Image(new File("src/Pictures/pause1.gif").toURI().toString());
         ImageView imageV = new ImageView(image2);
         imageV.setX(290);
         imageV.setY(200);
         
         Quitter.setLayoutX(320);
         Quitter.setLayoutY(350);
         Quitter.setMinSize(80, 80);
         Quitter.setEffect(new ImageInput(new Image("file:src/Pictures/retourM.png")));
         Quitter.setSkin(new MyButtonSkin(Quitter));
       
         
         Reprendre.setLayoutX(485);
         Reprendre.setLayoutY(350);
         Reprendre.setMinSize(80, 80);
         Reprendre.setEffect(new ImageInput(new Image("file:src/Pictures/play.png")));
         Reprendre.setSkin(new MyButtonSkin(Reprendre));
        

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
                   if(!gameView.pause && !GameView.getFin()){
                    root.getChildren().add(imageV);
                    root.getChildren().addAll(Quitter, Reprendre, Recommencer);
                    gameView.pause = true;
                   }else if(!GameView.getFin()){
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


        gameView.animate();
        

        //Action du bouton Quitter
        Quitter.setOnAction(ev1 -> {
            Pane root1 = new Pane();
            gameScene.setRoot(root1);
            Menu a = new Menu(root1, gameScene);
            if(court instanceof TimeMode) {
                court.closeTimer();
                court.resetNbManche();
            }
            a.start(primaryStage);
        });

        //Action du bouton Reprendre
        Reprendre.setOnAction(ev1 ->{
            root.getChildren().removeAll(imageV, Quitter, Reprendre, Recommencer);
            gameView.pause = false ; 
        });

        //Action du bouton Recommencer
        Recommencer.setOnAction(ev1 ->{
            Quitter.setLayoutX(320);
            Recommencer.setLayoutX(695);
            Recommencer.setLayoutY(350);
            Quitter.setLayoutY(350);
            root.getChildren().remove(imageV);
            if (GameView.finGame){
                root.getChildren().remove(root.getChildren().size()-3) ; 
                root.getChildren().remove(root.getChildren().size()-3) ;  
            }           
            root.getChildren().removeAll(Quitter, Reprendre, Recommencer);
            court.reset() ;  
           court.getScore().reset();
           gameView.pause = false ; 
           gameView.finGame = false;
           if(court instanceof TimeMode) court.commencerTimer();
        });	
    }
}
