package gui;
import javafx.event.Event;
import javafx.scene.layout.Pane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/*********************************************************************************************************************** */

//Classe de départ pour lancer le menu

public class Start extends Application {
  
    public void start (Stage primaryStage) {
        Pane root = new Pane() ;
        Scene gameScene = new Scene(root) ;
        Menu a = new Menu(root, gameScene);
        a.start(primaryStage);
    }

}
