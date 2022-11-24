package gui;
import javafx.scene.text.*;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import model.Court;
import model.CourtObstacles.Obstacle;
import model.*;

/* ------------------------------------------------------------------------------------------------------*/

//Modification du terrain de jeu, nouvelles délimitations du terrain, etc

public class GameView {

   
    // class parameters
    private final Court court;
    private final Pane gameRoot; // main node of the game
    private final double scale;
    private final double margin = 100.0, racketThickness = 10.0, inTerface = 100.0; // pixels
    public static String theme ; 

    // children of the game main node
    private final Rectangle racketA, racketB;
    private final Circle ball;
    public static boolean finGame;
    public static boolean pause ;

    int Timer = 60; //2sec




    /**
     * @param court le "modèle" de cette vue (le terrain de jeu de raquettes et tout ce qu'il y a dessus)
     * @pic boolean pause ;aram root  le nœud racine dans la scène JavaFX dans lequel le jeu sera affiché
     * @param scale le facteur d'échelle entre les distances du modèle et le nombre de pixels correspondants dans la vue
     */
    public GameView(Court court, Pane root, double scale ) {
        this.court = court;
        this.gameRoot = root;
        this.scale = scale; 

        pause = false ; 
        finGame = false ; 

        root.setMinWidth(court.getWidth() * scale + 2 * margin);
        root.setMinHeight(court.getHeight() * scale + margin + inTerface);

        //Affichage de la balle et des raquettes

            racketA = new Rectangle();
            racketA.setHeight(court.getRacketSize() * scale);
            racketA.setWidth(racketThickness);
            racketA.setFill(Color.valueOf("#375745"));

            racketA.setX(margin - racketThickness);
            racketA.setY(court.getRacketA() * scale + inTerface + margin/2);

            racketB = new Rectangle();
            racketB.setHeight(court.getRacketSize() * scale);
            racketB.setWidth(racketThickness);
            racketB.setFill(Color.valueOf("#375745"));

            racketB.setX(court.getWidth() * scale + margin);
            racketB.setY(court.getRacketB() * scale + inTerface + margin/2);

            ball = new Circle();
            ball.setRadius(court.getBallRadius());
            ball.setFill(Color.valueOf("#375745"));

            ball.setCenterX(court.getBallX() * scale + margin);
            ball.setCenterY(court.getBallY() * scale + inTerface +  margin/2);


        //Affichage de l'interface

            Group inter = new Group();

                Rectangle cadre = new Rectangle();
                cadre.setX(margin/2);
                cadre.setY(margin/4);
                cadre.setWidth(court.getWidth() + margin );
                cadre.setHeight(inTerface);
                cadre.setStroke(Color.valueOf("#375745"));
                cadre.setStrokeWidth(5);
                cadre.setFill(null);      
                inter.getChildren().addAll(cadre);

                Line l1 = new Line();
                l1.setStartX(margin/2);
                l1.setStartY(inTerface + margin/2 - ball.getRadius());
                l1.setEndX(margin + margin/2 + court.getWidth());
                l1.setEndY(inTerface + margin/2 - ball.getRadius());
                l1.setStroke(Color.valueOf("#375745"));
                l1.setStrokeWidth(5);

                Line l2 = new Line();
                l2.setStartX(margin/2);
                l2.setStartY(inTerface + margin/2 + court.getHeight() + ball.getRadius());
                l2.setEndX(margin + margin/2 + court.getWidth());
                l2.setEndY(inTerface + margin/2 + court.getHeight() + ball.getRadius());
                l2.setStroke(Color.valueOf("#375745"));
                l2.setStrokeWidth(5);

                Rectangle zoneDeJeu = new Rectangle();
                zoneDeJeu.setX(margin);
                zoneDeJeu.setY(inTerface + margin/2);
                zoneDeJeu.setWidth(court.getWidth());
                zoneDeJeu.setHeight(court.getHeight());
                zoneDeJeu.setFill(Color.valueOf("#aeb8b2"));
                //Player1
                court.getScore().getS1().setStyle("-fx-font: 60 arial;");
                court.getScore().getS1().setX(1030);
                court.getScore().getS1().setY(95);
                //Player2
                court.getScore().getS2().setStyle("-fx-font: 60 arial;");
                court.getScore().getS2().setX(130);
                court.getScore().getS2().setY(95);

        if (court instanceof TimeMode) {
            TimeMode t = (TimeMode)court;
            Text afficheT = new Text("Temps : ");

           afficheT.setStyle("-fx-font: 40 arial;");
            afficheT.setX(265);
            afficheT.setY(95);

            t.getTmp().setStyle("-fx-font: 40 arial;");
            t.getTmp().setX(455);
            t.getTmp().setY(95);

            Text t1 = new Text("Manche : ");

            t1.setStyle("-fx-font: 40 arial;");
            t1.setX(655);
            t1.setY(95);

            t.getNbManche().setStyle("-fx-font: 40 arial;");
            t.getNbManche().setX(855);
            t.getNbManche().setY(95);




            t.getTmp().setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
    

            gameRoot.getChildren().addAll( court.getScore().getS2(), court.getScore().getS1(), zoneDeJeu, l1, l2, racketA, racketB, ball, inter, t.getTmp(), t1, t.getNbManche(), afficheT);
            return;
        }

        gameRoot.getChildren().addAll( court.getScore().getS2(), court.getScore().getS1(), zoneDeJeu, l1, l2, racketA, racketB, ball, inter);


         if (court instanceof CourtObstacles) {
            ((CourtObstacles)court).setGameView(this);
            Obstacle [] t = ((CourtObstacles)court).getObstacles() ; 
            for (int i = 0; i < t.length; i++) {
                addObst(t[i]);
            }
            return ; 
        }
         gameRoot.getChildren().addAll( court.getScore().getS2(), court.getScore().getS1(), zoneDeJeu, l1, l2, racketA, racketB, ball, inter);
    }

    public void addObst (Obstacle obst) {//done
        obst.getShape().setStroke(Color.BLACK);
        if (obst.isDestroyable()) {
            // transparent avec bordures noires option 
            // requires avis !
            //obst.getShape().setFill(Color.rgb(0, 0, 0 , 0));
            obst.getShape().setFill(Color.rgb(51, 204, 00 , 0.4));
        }else{
            obst.getShape().setFill(Color.rgb(240, 40, 14 , 0.4));            
        }
        if (obst.getId() == 0) {
            Rectangle rec = ((Rectangle)obst.getShape()) ; 
            rec.setX(obst.getPosX()*scale + margin);
            rec.setY(obst.getPosY()*scale + margin/2 + inTerface);
            gameRoot.getChildren().addAll(rec) ; 
        }else{
            Circle cir = ((Circle)obst.getShape()) ; 
            cir.setCenterX(obst.getPosX()*scale + margin);
            cir.setCenterY(obst.getPosY()*scale + margin/2 + inTerface);
            gameRoot.getChildren().addAll(cir) ; 
        }
    }

    public void destroyObst (Obstacle obst) {//done
        gameRoot.getChildren().removeAll((obst.getId() == 0)?(Rectangle)obst.getShape():(Circle)obst.getShape()) ; 
    }

    public void updateObstacle (Obstacle obstacle) {//done
        if (obstacle.getId() == 0 ) {
            ((Rectangle)obstacle.getShape()).setY(obstacle.getPosY() + margin/2 + inTerface );
            return ; 
        }
        ((Circle)obstacle.getShape()).setCenterY(obstacle.getPosY() + margin/2 + inTerface);
    }



    public static void endGame (int player) {
        Image fin = new Image((player==1)?"file:src/Pictures/WinJ22.png":(player ==2)?"file:src/Pictures/WinJ11.png":"file:src/Pictures/egalite.png");
        ImageView finJ = new ImageView(fin);
        Image smoke = new Image("file:src/Pictures/whitesmoke.png");
        ImageView whitesmoke = new ImageView(smoke);
        App.root.getChildren().add(whitesmoke);
        App.root.getChildren().add(finJ);
        App.Quitter.setLayoutX(370);
        App.Recommencer.setLayoutX(695);
        App.Recommencer.setLayoutY(400);
        App.Quitter.setLayoutY(390);
        App.root.getChildren().addAll(gui.App.Quitter, gui.App.Recommencer);
    }

    public void animate() {
        new AnimationTimer() {
            long last = 0;

            @Override
            public void handle(long now) {
                if(!pause && !finGame){

                    if (last == 0) { // ignore the first tick, just compute the first deltaT
                        last = now;
                        return;
                    }
                    
                    
                    court.update((now - last) * 1.0e-9); // convert nanoseconds to seconds
                    last = now;
                    racketA.setY(court.getRacketA() * scale + margin/2 + inTerface);
                    racketB.setY(court.getRacketB() * scale + margin/2 + inTerface);
                    ball.setCenterX(court.getBallX() * scale + margin);
                    ball.setCenterY(court.getBallY() * scale + margin/2 + inTerface);
                }else{
                    last = 0 ; 
                }
                Timer--;
            }
        }.start();
    }
}
