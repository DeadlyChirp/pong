package gui;
import java.util.*;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.event.Event;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import model.Court;
import model.RacketController;
import java.io.InputStream;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import java.io.File; 
import java.io.IOException; 
import java.util.Scanner;


import javafx.application.Application;
import javafx.event.Event;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import model.Court;
import model.RacketController;
import java.io.InputStream;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import java.io.File; 
import java.io.IOException; 
import java.util.Scanner;


/*********************************************************************************************************************** */


//Menu pour les différents modes de jeu interne. Il reste timer mode, fire mode et un autre mode à implémenter

public class ModeDeJeuInt extends Application {

    public Pane root;
    public Scene gameScene;

    ModeDeJeuInt(Pane root, Scene a){
        this.root = root;
        gameScene = a;
    }

    public void start(Stage primaryStage) {
     
        //Logo du pong
        Image image = new Image("file:src/Pictures/pong1.png");
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(50);
        imageView.setLayoutY(190);

        //Bouton lifemode
        Button lifemode = new Button("Life Mode") ;
        lifemode.setLayoutX(820);
        lifemode.setLayoutY(200);
        lifemode.setEffect(new ImageInput(new Image("file:src/Pictures/lifemode.png")));
        lifemode.setSkin(new MyButtonSkin(lifemode));

        //Bouton timermode
        Button timermode = new Button("Timer Mode");
        timermode.setLayoutX(820);
        timermode.setLayoutY(400);
        timermode.setEffect(new ImageInput(new Image("file:src/Pictures/timermode.png")));
        timermode.setSkin(new MyButtonSkin(timermode));

        //Bouton firemode
        Button firemode = new Button("Fire Mode");
        firemode.setLayoutX(820);
        firemode.setLayoutY(570);
        firemode.setEffect(new ImageInput(new Image("file:src/Pictures/firemode.png")));
        firemode.setSkin(new MyButtonSkin(firemode));

        //Bouton SpeedMode
        Button speedmode = new Button("Speed Mode");
        speedmode.setLayoutX(520);
        speedmode.setLayoutY(200);
        speedmode.setEffect(new ImageInput(new Image("file:src/Pictures/speedmode.png")));
        speedmode.setSkin(new MyButtonSkin(speedmode));

        //Bouton ScoreMode
        Button scoreMode = new Button("Score Mode");
        scoreMode.setLayoutX(520);
        scoreMode.setLayoutY(400);
        scoreMode.setEffect(new ImageInput(new Image("file:src/Pictures/scoremode.png")));
        scoreMode.setSkin(new MyButtonSkin(scoreMode));

        //Bouton Obstacle Mode
        Button obstaclemode = new Button("obstacle mode");
        obstaclemode.setLayoutX(520);
        obstaclemode.setLayoutY(570);
        obstaclemode.setEffect(new ImageInput(new Image("file:src/Pictures/obstaclemode.png")));
        obstaclemode.setSkin(new MyButtonSkin(obstaclemode));

        //Bouton retour
        Button Retour= new Button("Retour") ;
        Retour.setLayoutX(1100);
        Retour.setLayoutY(25);
        Retour.setEffect(new ImageInput(new Image("file:src/Pictures/retour.png")));
        Retour.setSkin(new MyButtonSkin(Retour));

        //Background
        Image img = new Image("file:src/Pictures/ModeDeJeu.png");
        BackgroundImage bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        root.setBackground(bGround);


        root.getChildren().add(imageView);
        root.getChildren().addAll(timermode, lifemode, firemode, speedmode, scoreMode, obstaclemode, Retour);

        Retour.setOnAction(ev1 -> {
            Pane root1 = new Pane();
            gameScene.setRoot(root1);
            ModeDeJeu a = new ModeDeJeu(root1, gameScene);
            a.start(primaryStage);
        });

        scoreMode.setOnAction(ev1 -> {
            Pane root1 = new Pane();
            gameScene.setRoot(root1);
            App a = new App(root1, gameScene); //Appel de la classe App classique qui permet de lancer le mode de score (définir la limite du score au début)
            a.start(primaryStage);
        });

        speedmode.setOnAction(ev1->{

            //Utilser pour speedmode de Samy
        });

        lifemode.setOnAction(ev1->{

            //Utilser lifemode d'Emir
        });

        firemode.setOnAction(ev1->{

            //Utilser firemode de Thanh
        });

        obstaclemode.setOnAction(ev1->{

            //Utilser obstaclemode de Samy
        });


        timermode.setOnAction(ev1->{
            ArrayList<Integer> duree = new ArrayList<Integer>();
            duree.add(15);
            duree.add(30);
            duree.add(60);
            duree.add(120);
    
            ArrayList<Integer> manche = new ArrayList<Integer>();
            manche.add(5);
            manche.add(10);
            manche.add(15);
            manche.add(20);
    
    
            ChoiceDialog<Integer> dialogManche = new ChoiceDialog<Integer>(5, manche);
            dialogManche.initOwner(primaryStage);
            dialogManche.setTitle("Limite de la partie");
            dialogManche.setHeaderText("Veuillez choisir un nombre de manches");
            dialogManche.setContentText("Nombre : ");
    
            Optional<Integer> ecouteManche = dialogManche.showAndWait();
            ecouteManche.ifPresent(limit -> {
                ChoiceDialog<Integer> dialogDuree = new ChoiceDialog<Integer>(15, duree);
                dialogDuree.initOwner(primaryStage);
                dialogDuree.setTitle("Mode Timer");
                dialogDuree.setHeaderText("Veuillez choisir la durée de chaque manche");
                dialogDuree.setContentText("durée : ");
    
                Optional<Integer> ecouteDuree = dialogDuree.showAndWait();
                ecouteDuree.ifPresent(time -> {
    
                Pane root1 = new Pane();
                gameScene.setRoot(root1);
                App a = new App(root1, gameScene);
                a.startTimer(primaryStage, limit, time);
                });
            
            });
        
        });
        
       
        primaryStage.setScene(gameScene);
        primaryStage.show(); 
        }

}
