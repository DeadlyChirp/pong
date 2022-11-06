package gui;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/* ------------------------------------------------------------------------------------------------------*/

//Test pour le menu Commande, à refaire en entier. 

public class Stat extends Application{

    public Pane root;
    public Scene gameScene;
   
    Button D= new Button("Quitter") ;
    Button S= new Button("Quitter") ;
    Button Q= new Button("Quitter") ;

    Stat(Pane root, Scene a){
        this.root = root;
        gameScene = a;
    }

    public void start (Stage primaryStage) {

        Button Retour= new Button("Retour") ;
        Retour.setLayoutX(1100);
        Retour.setLayoutY(25);
        Retour.setEffect(new ImageInput(new Image("file:src/Pictures/retour.png")));
        Retour.setSkin(new MyButtonSkin(Retour));

        ObservableList<Button> l = FXCollections.observableArrayList(Q, S, D);

        ChoiceBox<Button> choiceBox = new ChoiceBox<Button>(l);
        Button Confirmer = new Button("Confirmer");
    

        root.getChildren().addAll(choiceBox, Confirmer, Retour);

        primaryStage.setScene(gameScene);
        primaryStage.show();

        Retour.setOnAction(ev1 -> {
            Pane root1 = new Pane();
            gameScene.setRoot(root1);
            Menu a = new Menu(root1, gameScene);
            a.start(primaryStage);
        });


    }
}