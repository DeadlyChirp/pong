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


/*********************************************************************************************************************** */


public class ModeDeJeu extends Application {
    public Pane root;
    public Scene gameScene;

    ModeDeJeu(Pane root, Scene a){
        this.root = root;
        gameScene = a;
    }

    public void start (Stage primaryStage) {
     
    Image image = new Image("file:src/Pictures/pong1.png");
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(350);
        imageView.setLayoutY(10);

        //Bouton Play
        Button oneVSone = new Button("1 VS 1") ;
        oneVSone.setLayoutX(570);
        oneVSone.setLayoutY(580);
        oneVSone.setSkin(new MyButtonSkin(oneVSone));

        //Bouton Option
        Button oneVSBot = new Button("1 VS Bot");
        oneVSBot.setLayoutX(421);
        oneVSBot.setLayoutY(580);
        oneVSBot.setSkin(new MyButtonSkin(oneVSBot));

        //Bouton quitter
        Button twoVStwo = new Button("2 VS 2");
        twoVStwo.setLayoutX(704);
        twoVStwo.setLayoutY(580);
        twoVStwo.setSkin(new MyButtonSkin(twoVStwo));

        Button Retour= new Button("Retour") ;
        Retour.setLayoutX(1100);
        Retour.setLayoutY(25);

        Image img = new Image("file:src/Pictures/fond.png");
        BackgroundImage bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        root.setBackground(bGround);

    root.getChildren().add(imageView);
    root.getChildren().addAll(oneVSone, oneVSBot, twoVStwo, Retour);

    Retour.setOnAction(ev1 -> {
        primaryStage.close();
        Menu q = new Menu();
        q.start(primaryStage);
    });

    oneVSone.setOnAction(ev1 -> {
       Pane root1 = new Pane();
            gameScene.setRoot(root1);
            App a = new App(root1, gameScene);
            a.start(primaryStage);
    });


     

    primaryStage.setScene(gameScene);
    primaryStage.show(); 

    }

}
