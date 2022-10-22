package gui;

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

/* ------------------------------------------------------------------------------------------------------*/

//Jerem Fichier Menu
public class Menu extends Application{

    public void start (Stage primaryStage) {
        Pane root = new Pane() ;
        Scene gameScene = new Scene(root) ;

        //Logo du millieu
        Image image = new Image("file:src/Pictures/pong1.png");
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(350);
        imageView.setLayoutY(10);

        //Bouton Play
        Button play = new Button("play") ;
        play.setLayoutX(525);
        play.setLayoutY(534);
        play.setEffect(new ImageInput(new Image("file:src/Pictures/play.png")));
        play.setSkin(new MyButtonSkin(play));

        //Bouton Option
        Button option = new Button("option");
        option.setLayoutX(421);
        option.setLayoutY(580);
        option.setEffect(new ImageInput(new Image("file:src/Pictures/option.png")));
        option.setSkin(new MyButtonSkin(option));

        //Bouton quitter
        Button quitter = new Button("quitter");
        quitter.setLayoutX(704);
        quitter.setLayoutY(580);
        quitter.setEffect(new ImageInput(new Image("file:src/Pictures/exit.png")));
        quitter.setSkin(new MyButtonSkin(quitter));


        Button Easter = new Button("Easter");
        Easter.setLayoutX(10);
        Easter.setLayoutY(700);
        Easter.setMinSize(100, 100);
        Easter.setOpacity(0);

        Image img = new Image("file:src/Pictures/fond.png");
        BackgroundImage bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        root.setBackground(bGround);

       
        
    

       //Setting du Stage
        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("file:src/Pictures/pong.png")) ;
        primaryStage.setTitle("Pong");

        //Ajout des boutons sur le stage
        
        root.getChildren().addAll(imageView, play, option, quitter, Easter) ;
        primaryStage.setScene(gameScene);
        primaryStage.show(); 

       
            

        option.setOnAction(ev2 ->{
            root.getChildren().removeAll(play, option, quitter);
            Button Commande= new Button("play") ;
            Commande.setLayoutX(538);
            Commande.setLayoutY(580);
            Commande.setEffect(new ImageInput(new Image("file:src/Pictures/commande2.png")));
            Commande.setSkin(new MyButtonSkin(Commande));

            
            Button Stat = new Button("option");
            Stat.setLayoutX(421);
            Stat.setLayoutY(580);
            Stat.setEffect(new ImageInput(new Image("file:src/Pictures/stat.png")));
            Stat.setSkin(new MyButtonSkin(Stat));

            //Bouton quitter
            Button Retour = new Button("quitter");
            Retour.setLayoutX(704);
            Retour.setLayoutY(580);
            Retour.setEffect(new ImageInput(new Image("file:src/Pictures/retour.png")));
            Retour.setSkin(new MyButtonSkin(Retour));

            root.getChildren().addAll(Commande, Stat, Retour) ;

            Retour.setOnAction(ev3 ->{
                root.getChildren().removeAll(Commande, Stat, Retour);
                root.getChildren().addAll(play, option, quitter);
            });


        });



        //Action du bouton Play 
        play.setOnAction(ev1 -> {
            // Pane root1 = new Pane();
            // gameScene.setRoot(root1);
            // App a = new App(root1, gameScene);
            // a.start(primaryStage);

            Pane root1 = new Pane();
            gameScene.setRoot(root1);
            ModeDeJeu a = new ModeDeJeu(root1, gameScene);
            a.start(primaryStage);
        });
      
       quitter.setOnAction(ev3 ->{
           System.exit(0);
       });
       
       Easter.setOnAction(ev8 ->{
        Button Easter2 = new Button("Easter");
        Easter2.setLayoutX(1100);
        Easter2.setLayoutY(700);
        Easter2.setMinSize(100, 100);
        Easter2.setOpacity(0);
        root.getChildren().add(Easter2);
        Easter2.setOnAction(ev7 ->{
            Button Easter3 = new Button("Easter");
            Easter3.setLayoutX(1100);
            Easter3.setLayoutY(10);
            Easter3.setMinSize(100, 100);
            Easter3.setOpacity(0);
            root.getChildren().add(Easter3);
            Easter3.setOnAction(ev5 ->{
                Image image2 = new Image("file:src/Pictures/Easter1.jpg");
                ImageView imageView2 = new ImageView(image2);
                imageView2.setLayoutX(350);
                imageView2.setLayoutY(10);
                ImageView imageView3 = new ImageView(image2);
                ImageView imageView4 = new ImageView(image2);
                imageView3.setLayoutX(150);
                imageView3.setLayoutY(10);
                imageView4.setLayoutX(650);
                imageView4.setLayoutY(10);
                root.getChildren().addAll(imageView2, imageView3, imageView4);
            });
        });
    });
    }
}



