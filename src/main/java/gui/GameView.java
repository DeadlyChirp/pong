package gui;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.*;
import model.CourtObstacles.Obstacle;

import java.awt.*;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* ------------------------------------------------------------------------------------------------------*/

//Modification du terrain de jeu, nouvelles délimitations du terrain, etc

public class GameView {

   
    // class parameters
    private Court court;
    private final Pane gameRoot; // main node of the game
    private double scale;
    private final double margin = 100.0, racketThickness = 10.0, inTerface = 100.0; // pixels
    public static String theme ; 

    // children of the game main node
    private final Rectangle racketA, racketB;
    private final Circle ball;
    public static boolean finGame;
    public static boolean pause ;
    private final LinkedList<Trail> trails;

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

        trails = new LinkedList<>();

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

        if (court instanceof FireMode fireMode) {

            Text p1 = new Text();
            p1.setText("Coins P1");
            p1.setStyle("-fx-font: 20 arial;");
            p1.setFill(Color.valueOf("#ff5252"));
            p1.setX(300);
            p1.setY(45);

            Text p2 = new Text();
            p2.setText("Coins P2");
            p2.setStyle("-fx-font: 20 arial;");
            p2.setFill(Color.valueOf("#189ad3"));
            p2.setX(800);
            p2.setY(45);

            Text p1Skill = new Text();
            p1Skill.setText("Coins P1");
            p1Skill.setStyle("-fx-font: 20 arial;");
            p1Skill.setFill(Color.valueOf("#ff5252"));
            p1Skill.setX(500);
            p1Skill.setY(45);

            Text p2Skill = new Text();
            p2Skill.setText("Coins P2");
            p2Skill.setStyle("-fx-font: 20 arial;");
            p2Skill.setFill(Color.valueOf("#189ad3"));
            p2Skill.setX(600);
            p2Skill.setY(45);


            fireMode.getPlayerA().getPointText().setStyle("-fx-font: 60 arial;-fx-fill: #ff5252;");
            fireMode.getPlayerA().getPointText().setX(330);
            fireMode.getPlayerA().getPointText().setY(95);

            fireMode.getPlayerB().getPointText().setStyle("-fx-font: 60 arial;-fx-fill: #189ad3;");
            fireMode.getPlayerB().getPointText().setX(830);
            fireMode.getPlayerB().getPointText().setY(95);

            fireMode.getPlayerA().getPowerAmountText().setStyle("-fx-font: 60 arial;-fx-fill: #ff5252 ;");
            fireMode.getPlayerA().getPowerAmountText().setX(530);
            fireMode.getPlayerA().getPowerAmountText().setY(95);

            fireMode.getPlayerB().getPowerAmountText().setStyle("-fx-font: 60 arial;-fx-fill: #189ad3;");
            fireMode.getPlayerB().getPowerAmountText().setX(630);
            fireMode.getPlayerB().getPowerAmountText().setY(95);

            racketA.setFill(Color.valueOf("#ff5252")); //Couleur de la raquette
            racketB.setFill(Color.valueOf("#189ad3"));

            //resize racketA and racketB thickness
            racketA.setWidth(racketThickness * 2.5);
            racketB.setWidth(racketThickness * 2.5);

            //move racketA to the left of the screen
            racketA.setX(margin - racketThickness * 2.5);


            Image image = new Image("file:src/Pictures/fireball3.gif");
            //set image to ballShape
            ball.setFill(new ImagePattern(image));








            gameRoot.getChildren().addAll(fireMode.getPlayerA().getPointText(), fireMode.getPlayerB().getPointText(), p1,p2, p1Skill,p2Skill, fireMode.getPlayerB().getPowerAmountText(),fireMode.getPlayerA().getPowerAmountText());
        }


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


    private static final Point[] A_POINTS = new Point[] {new Point(100, 180), new Point(100, 340), new Point(100, 500)};
    private static final Point[] B_POINTS = new Point[] {new Point(890, 180), new Point(890, 340), new Point(890, 500)};
    private Rectangle selectionA = new Rectangle();
    private Rectangle selectionB = new Rectangle();
    private Text sizeLvAText, speedLvAText, powerAmountAText, sizeLvBText, speedLvBText, powerAmountBText;
    private int selectionAIndex, selectionBIndex;
    public void upgradeRacket() { //thanh
        if (finGame || gameRoot.getScene() == null) {
            return;
        }

        selectionAIndex = 0;
        selectionBIndex = 0;

        //Color menu boxx
        selectionA.setX(A_POINTS[selectionAIndex].x);
        selectionA.setY(A_POINTS[selectionAIndex].y);
        selectionA.setWidth(250);
        selectionA.setHeight(120);
        selectionA.setStroke(Color.valueOf("#1a34ff")); //blue
        selectionA.setStrokeWidth(5);
        selectionA.setFill(Color.WHITE);

        selectionB.setX(B_POINTS[selectionBIndex].x);
        selectionB.setY(B_POINTS[selectionBIndex].y);
        selectionB.setWidth(250);
        selectionB.setHeight(120);
        selectionB.setStroke(Color.valueOf("#f5400a")); //red
        selectionB.setStrokeWidth(5);
        selectionB.setFill(Color.WHITE);

        if (court instanceof FireMode fireMode) {
            pause = true; //pause game

            Image image = new Image("file:src/Pictures/MenuFmod.png");
            ImageView imageView = new ImageView(image);
            //Box menu buy A
            Text coinsA = new Text("Coins : ");
            coinsA.setStyle("-fx-font: 40 arial;");
            coinsA.setFill(Color.YELLOW);
            coinsA.setX(110);
            coinsA.setY(100);




            //Box menu buy
            Text sizeAText = new Text("Size");
            sizeAText.setStyle("-fx-font: 48 arial;");
            sizeAText.setX(110);
            sizeAText.setY(240);

            Text sizeCostAText = new Text("Cost: " + FPlayer.SIZE_COSTP1);
            sizeCostAText.setStyle("-fx-font: 24 arial;");
            sizeCostAText.setX(120);
            sizeCostAText.setY(280);

            sizeLvAText = new Text("Level: " + fireMode.getPlayerA().getSizeLevel());
            sizeLvAText.setStyle("-fx-font: 24 arial;");
            sizeLvAText.setX(210);
            sizeLvAText.setY(280);

            //
            Text speedAText = new Text("Speed");
            speedAText.setStyle("-fx-font: 48 arial;");
            speedAText.setX(110);
            speedAText.setY(400);

            Text speedCostAText = new Text("Cost: " + FPlayer.SPEED_COSTP1);
            speedCostAText.setStyle("-fx-font: 24 arial;");
            speedCostAText.setX(120);
            speedCostAText.setY(440);

            speedLvAText = new Text("Level: " + fireMode.getPlayerA().getSpeedLevel());
            speedLvAText.setStyle("-fx-font: 24 arial;");
            speedLvAText.setX(210);
            speedLvAText.setY(440);

            //
            Text powerAText = new Text("Power"); // power
            powerAText.setStyle("-fx-font: 48 arial;"); // power
            powerAText.setX(110);
            powerAText.setY(560);

            Text powerCostAText = new Text("Cost: " + FPlayer.POWER_COSTP1);
            powerCostAText.setStyle("-fx-font: 24 arial;");
            powerCostAText.setX(120);
            powerCostAText.setY(600);

            powerAmountAText = new Text("Amount: " + fireMode.getPlayerA().getPowerAmount());
            powerAmountAText.setStyle("-fx-font: 24 arial;");
            powerAmountAText.setX(210);
            powerAmountAText.setY(600);

            //Buy player B
            Text coinsB = new Text("Coins : ");
            coinsB.setStyle("-fx-font: 40 arial;");
            coinsB.setFill(Color.YELLOW);
            coinsB.setX(930);
            coinsB.setY(100);


            Text sizeBText = new Text("Size");
            sizeBText.setStyle("-fx-font: 48 arial;");
            sizeBText.setX(900);
            sizeBText.setY(240);

            Text sizeCostBText = new Text("Cost: " + FPlayer.SIZE_COSTP2);
            sizeCostBText.setStyle("-fx-font: 24 arial;");
            sizeCostBText.setX(910);
            sizeCostBText.setY(280);

            sizeLvBText = new Text("Level: " + fireMode.getPlayerB().getSizeLevel());
            sizeLvBText.setStyle("-fx-font: 24 arial;");
            sizeLvBText.setX(1000);
            sizeLvBText.setY(280);

            //
            Text speedBText = new Text("Speed");
            speedBText.setStyle("-fx-font: 48 arial;");
            speedBText.setX(900);
            speedBText.setY(400);

            Text speedCostBText = new Text("Cost: " + FPlayer.SPEED_COSTP2);
            speedCostBText.setStyle("-fx-font: 24 arial;");
            speedCostBText.setX(910);
            speedCostBText.setY(440);

            speedLvBText = new Text("Level: " + fireMode.getPlayerB().getSpeedLevel());
            speedLvBText.setStyle("-fx-font: 24 arial;");
            speedLvBText.setX(1000);
            speedLvBText.setY(440);

            //
            Text powerBText = new Text("Power");
            powerBText.setStyle("-fx-font: 48 arial;");
            powerBText.setX(900);
            powerBText.setY(560);

            Text powerCostBText = new Text("Cost: " + FPlayer.POWER_COSTP2);
            powerCostBText.setStyle("-fx-font: 24 arial;");
            powerCostBText.setX(910);
            powerCostBText.setY(600);

            powerAmountBText = new Text("Amount: " + fireMode.getPlayerB().getPowerAmount());
            powerAmountBText.setStyle("-fx-font: 24 arial;");
            powerAmountBText.setX(1000);
            powerAmountBText.setY(600);

            gameRoot.getChildren().addAll(imageView, selectionA, selectionB,
                                          sizeAText, sizeCostAText, sizeLvAText,
                                          speedAText, speedCostAText, speedLvAText,
                                          powerAText, powerCostAText, powerAmountAText,
                                          sizeBText, sizeCostBText, sizeLvBText,
                                          speedBText, speedCostBText, speedLvBText,
                                          powerBText, powerCostBText, powerAmountBText

            );

            gameRoot.getScene().setOnKeyPressed(event -> {
                String s  = event.getCode().toString();
                if (s == App.commandes[0]) {
                    if (selectionAIndex > 0) {
                        selectionAIndex--;

                        selectionA.setX(A_POINTS[selectionAIndex].x);
                        selectionA.setY(A_POINTS[selectionAIndex].y);
                    }
                } else if (s == App.commandes[1]) {
                    if (selectionAIndex < 2) {
                        selectionAIndex++;

                        selectionA.setX(A_POINTS[selectionAIndex].x); // power
                        selectionA.setY(A_POINTS[selectionAIndex].y);
                    }
                } else if (s == KeyCode.D.toString()) {
                    switch (selectionAIndex) {
                        case 0:
                            if (fireMode.getPlayerA().increaseSizeLevelP1()) {
                                sizeLvAText.setText("Level: " + fireMode.getPlayerA().getSizeLevel());
                                if (fireMode.getPlayerA().getSizeLevel() == 2) {
                                    ImagePattern lvl1 = new ImagePattern(new Image("file:src/Pictures/Racketlvl1.png"));
                                    racketA.setFill(lvl1);
                                } else if (fireMode.getPlayerA().getSizeLevel() == 3) {
                                    ImagePattern lvl2 = new ImagePattern(new Image("file:src/Pictures/Racketlvl2.png"));
                                    racketA.setFill(lvl2);
                                } else if (fireMode.getPlayerA().getSizeLevel() == 4) {
                                    ImagePattern lvl3 = new ImagePattern(new Image("file:src/Pictures/Racketlvl3.png"));
                                    racketA.setFill(lvl3);
                                }
                            }
                            break;

                        case 1:
                            if (fireMode.getPlayerA().increaseSpeedLevelP1()) {
                                speedLvAText.setText("Level: " + fireMode.getPlayerA().getSpeedLevel());
                                //verify that if size level is 2 and if speed level is to then change image of racket else change image of racket


                            }
                            break;

                        case 2:
                            if (fireMode.getPlayerA().increasePowerAmountP1()) {
                                powerAmountAText.setText("Level: " + fireMode.getPlayerA().getPowerAmount());
                            }
                            break;
                    }
                } else if (s == App.commandes[2]) { //UP p2
                    if (selectionBIndex > 0) {
                        selectionBIndex--;

                        selectionB.setX(B_POINTS[selectionBIndex].x);
                        selectionB.setY(B_POINTS[selectionBIndex].y);
                    }
                } else if (s == App.commandes[3]) { //Down p2
                    if (selectionBIndex < 2) {
                        selectionBIndex++;

                        selectionB.setX(B_POINTS[selectionBIndex].x);
                        selectionB.setY(B_POINTS[selectionBIndex].y);
                    }
                } else if (event.getCode() == KeyCode.ENTER) {
                    switch (selectionBIndex) {
                        case 0:
                            if (fireMode.getPlayerB().increaseSizeLevelP2()) {
                                sizeLvBText.setText("Level: " + fireMode.getPlayerB().getSizeLevel());
                                //SKIN FOR PLAYER B
                                if (fireMode.getPlayerB().getSizeLevel() == 2) {
                                    ImagePattern lvl1 = new ImagePattern(new Image("file:src/Pictures/Racketlvl1.png"));
                                    racketB.setFill(lvl1);
                                } else if (fireMode.getPlayerB().getSizeLevel() == 3) {
                                    ImagePattern lvl2 = new ImagePattern(new Image("file:src/Pictures/Racketlvl2.png"));
                                    racketB.setFill(lvl2);
                                } else if (fireMode.getPlayerB().getSizeLevel() == 4) {
                                    ImagePattern lvl3 = new ImagePattern(new Image("file:src/Pictures/Racketlvl3.png"));
                                    racketB.setFill(lvl3);
                                }
                            }
                            break;

                        case 1:
                            if (fireMode.getPlayerB().increaseSpeedLevelP2()) {
                                speedLvBText.setText("Level: " + fireMode.getPlayerB().getSpeedLevel());
                            }
                            break;

                        case 2:
                            if (fireMode.getPlayerB().increasePowerAmountP2()) {
                                powerAmountBText.setText("Level: " + fireMode.getPlayerB().getPowerAmount());
//
                            }
                            break;
                    }
                }
            });

            ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
            Runnable task = () -> {
                fireMode.setKeyEvent(gameRoot.getScene());

                Platform.runLater(() -> {
                    gameRoot.getChildren().removeAll(imageView, selectionA, selectionB,
                                                     sizeAText, sizeCostAText, sizeLvAText,
                                                     speedAText, speedCostAText, speedLvAText,
                                                     powerAText, powerCostAText, powerAmountAText,
                                                     sizeBText, sizeCostBText, sizeLvBText,
                                                     speedBText, speedCostBText, speedLvBText,
                                                     powerBText, powerCostBText, powerAmountBText
                    );
                });

                pause = false;
            };
            ses.schedule(task, 8, TimeUnit.SECONDS);
            ses.shutdown();
        }
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

                    if (court instanceof FireMode fireMode) {
                        for (Trail trail : trails) {
                            trail.tick();
                        }
                        Circle ballShape = new Circle();
                        ballShape.setRadius(fireMode.getBallRadius());
                        ballShape.setCenterX(fireMode.getBallX() * scale + margin);
                        ballShape.setCenterY(fireMode.getBallY() * scale + margin / 2 + inTerface);
                        Image image = new Image("file:src/Pictures/SmokeTraill.gif");
                        //set image to ballShape
                        ballShape.setFill(Color.TRANSPARENT);
                        gameRoot.getChildren().addAll(ballShape);
                        Trail ballTrail = new Trail(ballShape, image, 0.15f, trail1 -> {
                            gameRoot.getChildren().removeAll(trail1.getShape());
                            Platform.runLater(() -> trails.remove(trail1));
                        });
                        trails.add(ballTrail);
                    }
                    racketA.setY(court.getRacketA() * scale + margin/2 + inTerface);
                    racketB.setY(court.getRacketB() * scale + margin/2 + inTerface);
                    ball.setCenterX(court.getBallX() * scale + margin);
                    ball.setCenterY(court.getBallY() * scale + margin / 2 + inTerface);
                } else {
                    last = 0;
                }
                Timer--;
            }
        }.start();
    }

    public Circle getBall() { return this.ball; }
    public Pane getGameRoot() { return gameRoot;}
    public Court getCourt() { return court;}
    public Rectangle getRacketA() { return racketA;}
    public Rectangle getRacketB() { return racketB;}
    public double getRacketThickness() { return racketThickness;}
    public double getMargin() { return margin;}
    public double getInTerface() { return inTerface;}
    public double getScale() {return scale;}
    public void setCourt(Court c) { this.court = c;}
    public void setScale(double d) { this.scale = d;}
}
