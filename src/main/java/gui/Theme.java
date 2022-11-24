package gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/* ------------------------------------------------------------------------------------------------------*/

//Test pour le menu Commande, à refaire en entier. 

public class Theme {

    public Pane root;
    public Scene gameScene;
   
    Theme(Pane root, Scene a){
        this.root = root;
        gameScene = a;
    }

    public String Wallpaper(int a){
        if(a == 1) return "file:src/Pictures/blabla.png";
        if(a == 2) return "file:src/Pictures/blabla.png";
        if(a == 3) return "file:src/Pictures/blabla.png";
        return null;
    }

    public void start (Stage primaryStage) {

        Button Retour= new Button("Retour") ;
        Retour.setLayoutX(1100);
        Retour.setLayoutY(25);
        Retour.setEffect(new ImageInput(new Image("file:src/Pictures/retour.png")));
        Retour.setSkin(new MyButtonSkin(Retour));


        root.getChildren().add(Retour);

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
