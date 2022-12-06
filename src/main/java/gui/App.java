package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.image.ImageView;

//***************************************************** */

//App, fichier du jeu in-game
//Implémentation du menu pause : Fait
//Implémentation du menu de fin de jeu : fait

public class App {

    public static Pane root;
    public Scene gameScene;
    public int limite;

    App(Pane root, Scene a, int limite) {
        App.root = root;
        gameScene = a;
        this.limite = limite;
    }

    App(Pane root, Scene a){
        App.root = root;
        gameScene = a;
    }

    public static String[] commandes = {"A", "Q", "P", "M"};

    public static void setCommandes(String[] s) {
        commandes[0] = s[0];
        commandes[1] = s[1];
        commandes[2] = s[2];
        commandes[3] = s[3];
    }

    public static Button Quitter = new Button("Quitter");
    public static Button Reprendre = new Button("Reprendre");
    public static Button Recommencer = new Button("Recommencer");
    public static ImageView PauseImage;
    static Image image2 = new Image(new File("src/Pictures/pause1.gif").toURI().toString());
    public static ImageView imageV = new ImageView(image2);

    public void start(Stage primaryStage) {

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
                    var court = new Court(playerA, playerB, 1000, 600, limite);
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
                            if(!GameView.pause && !GameView.finGame){
                                root.getChildren().add(imageV);
                                root.getChildren().addAll(Quitter, Reprendre, Recommencer);
                                GameView.pause = true;
                           }else{
                                if(!GameView.finGame){
                                    root.getChildren().removeAll(imageV, Quitter, Reprendre, Recommencer);
                                    GameView.pause = false ; 
                                }
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
                        GameView.pause = false ; 
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
                        court.refresh(); 
                        GameView.pause = false ; 
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
                   if(!GameView.pause && !GameView.finGame){
                    root.getChildren().add(imageV);
                    root.getChildren().addAll(Quitter, Reprendre, Recommencer);
                    GameView.pause = true;
                   }else if(!GameView.finGame){
                    root.getChildren().removeAll(imageV, Quitter, Reprendre, Recommencer);
                    GameView.pause = false ; 
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
            GameView.pause = false ; 
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
           GameView.pause = false ; 
           GameView.finGame = false;
           if(court instanceof TimeMode) court.commencerTimer();
        });	
    }

    
    public void startSpeed(Stage primaryStage)  {

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
        var court = new CourtSpeed(playerA, playerB, 1000, 600, limite);
        var gameView = new GameView(court, root, 1);


        //Pour le menu de pause
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
                   if(!GameView.pause && !GameView.finGame){
                    root.getChildren().add(imageV);
                    root.getChildren().addAll(Quitter, Reprendre, Recommencer);
                    GameView.pause = true;
                   }else if(!GameView.finGame){
                    root.getChildren().removeAll(imageV, Quitter, Reprendre, Recommencer);
                    GameView.pause = false ; 
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
            GameView.pause = false ; 
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
            court.refresh();
            GameView.pause = false ; 
            GameView.finGame = false;
        });			

}

    public void startObstacles(Stage primaryStage , boolean vitesse)  {

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
        var court = new CourtObstacles(playerA, playerB, 1000, 600, this.limite) ; 
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
                if(!GameView.pause && !GameView.finGame){
                    root.getChildren().add(imageV);
                    root.getChildren().addAll(Quitter, Reprendre, Recommencer);
                    GameView.pause = true;
            }else{
                    if(!GameView.finGame){
                        root.getChildren().removeAll(imageV, Quitter, Reprendre, Recommencer);
                        GameView.pause = false ; 
                    }
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
            GameView.pause = false ; 
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
            court.refresh();
            GameView.pause = false ; 
            GameView.finGame = false;
        });			

    }
    public void startFire(Stage primaryStage) {
            ArrayList<String> firePopA = new ArrayList<String>();
             firePopA.add("COMPRIS");
             firePopA.add("PAS COMPRIS");       
            ChoiceDialog<String> firePop = new ChoiceDialog<String>("COMPRIS", firePopA);
            firePop.initOwner(primaryStage);
            firePop.setTitle("Touche du Fire Mode");
            firePop.setHeaderText("TOUCHE DU J1 : A , Q et D  |  TOUCHE DU J2 : P , M et Entrer (A, Q, P et M par defaut)");
            firePop.setContentText("Vous allez devoir jouer pour obtenir des points, \n Quand la balle touche votre raquette c'est 1 points \n Quand vous marquez un goal, c'est 5 points \n Dans le menu shop, vous choisissez vos power-up et confirmez avec D ou Entrer");
            firePop.setResizable(false);
            Optional<String> fireok = firePop.showAndWait();
            if(fireok.isEmpty()){
                Pane root1 = new Pane();
                gameScene.setRoot(root1);
                ModeDeJeu a = new ModeDeJeu(root1, gameScene);
                a.start(primaryStage);
            }

            if (fireok.isPresent()){
                if(fireok.get().equals("COMPRIS")){
                    var playerA = new FPlayer();
                    var playerB = new FPlayer();
                    Image img = new Image("file:src/Pictures/fondFire.png");
                    BackgroundImage bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
                    Background bGround = new Background(bImg);
                    root.setBackground(bGround);
                    var court = new FireMode(playerA, playerB, 1000, 600 , limite);
                    var gameView = new GameView(court, root, 1);

                    //Pour le menu de pause
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

                    gameView.animate();

                    //Action du bouton Quitter
                    Quitter.setOnAction(ev1 -> {
                        Pane root1 = new Pane();
                        gameScene.setRoot(root1);
                        Menu a = new Menu(root1, gameScene);
                        a.start(primaryStage);
                    });

                    //Action du bouton Reprendre
                    Reprendre.setOnAction(ev1 -> {
                        root.getChildren().removeAll(PauseImage, Quitter, Reprendre, Recommencer);
                        GameView.pause = false;
                    });

                    //Action du bouton Recommencer
                    Recommencer.setOnAction(ev1 -> {
                        Quitter.setLayoutX(320);
                        Recommencer.setLayoutX(695);
                        Recommencer.setLayoutY(350);
                        Quitter.setLayoutY(350);
                        root.getChildren().remove(PauseImage);
                        if (GameView.finGame) {
                            root.getChildren().remove(root.getChildren().size() - 3);
                            root.getChildren().remove(root.getChildren().size() - 3);
                        }

                        root.getChildren().removeAll(Quitter, Reprendre, Recommencer);
                        court.refresh();
                        GameView.pause = false;
                        GameView.finGame = false;
                    });

                    court.setGameView(gameView);
                    court.setKeyEvent(gameScene);
                }
                if(fireok.get().equals("PAS COMPRIS")){
                    Pane root1 = new Pane();
                    gameScene.setRoot(root1);
                    ModeDeJeu a = new ModeDeJeu(root1, gameScene);
                    a.start(primaryStage);
                }

                }
            }

            
            
        }


