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
        Image image = new Image("file:src/main/java/gui/pong.png");
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(250);
        imageView.setLayoutY(-90);

        //Bouton Play
        Button play = new Button("play") ;
        play.setLayoutX(425);
        play.setLayoutY(328);
        play.setEffect(new ImageInput(new Image("file:src/main/java/gui/play.png")));

        //Bouton Option
        Button option = new Button("option");
        option.setLayoutX(321);
        option.setLayoutY(374);
        option.setEffect(new ImageInput(new Image("file:src/main/java/gui/option.png")));

        //Bouton quitter
        Button quitter = new Button("quitter");
        quitter.setLayoutX(604);
        quitter.setLayoutY(374);
        quitter.setEffect(new ImageInput(new Image("file:src/main/java/gui/exit.png")));



       //Setting du Stage
        primaryStage.setWidth(1000);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("file:src/main/java/gui/pong.png")) ;
        primaryStage.setTitle("Pong");

        //Ajout des boutons sur le stage
        // Sound z = new Sound("src/Audio/Afterburner.mp3");
        // z.audio();
        root.getChildren().addAll(imageView, play, option, quitter) ;
        primaryStage.setScene(gameScene);
        primaryStage.show(); 

        option.setOnAction(ev2 ->{
            root.getChildren().removeAll(play, option, quitter);
            Button Commande= new Button("play") ;
            Commande.setLayoutX(438);
            Commande.setLayoutY(365);
            Commande.setEffect(new ImageInput(new Image("file:src/main/java/gui/commande2.png")));

            
            Button Stat = new Button("option");
            Stat.setLayoutX(321);
            Stat.setLayoutY(374);
            Stat.setEffect(new ImageInput(new Image("file:src/main/java/gui/stat.png")));

            //Bouton quitter
            Button Retour = new Button("quitter");
            Retour.setLayoutX(604);
            Retour.setLayoutY(374);
            Retour.setEffect(new ImageInput(new Image("file:src/main/java/gui/retour.png")));
            root.getChildren().addAll(Commande, Stat, Retour) ;

            Retour.setOnMouseClicked(ev3 ->{
                root.getChildren().removeAll(Commande, Stat, Retour);
                root.getChildren().addAll(play, option, quitter);
            });


        });



        //Action du bouton Play 
        play.setOnMouseClicked(ev1 -> {
            Pane root1 = new Pane();
            gameScene.setRoot(root1);
            App a = new App(root1, gameScene);
            a.start(primaryStage);
        });
      
       quitter.setOnMouseClicked(ev3 ->{
           System.exit(0);
       });
       
    }
}



