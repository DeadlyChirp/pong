package model;

import java.util.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import gui.App;
import gui.GameView;
import javafx.scene.text.*;

import model.Court;


public class TimeMode extends Court {

  private Text tmp;
  private Timer timer;
  private int limit;
  private Score scoreFinal;
  private Score scoreManche;
  private int nbManche;
  private int nbMancheInitial;
 


  public TimeMode(RacketController playerA, RacketController playerB, double width, double height, int nbManche, int t) {
    super(playerA, playerB, width, height);
    tmp = new Text(String.valueOf(t));
    limit = t;
    timer = new Timer();
   
    this.nbManche = nbManche;
    this.nbMancheInitial = nbManche;
   
    scoreManche = this.getScore();
    scoreFinal = new Score();
    commencerTimer();
  }
  
@Override
public boolean updateBall(double deltaT) {
          if (nbManche == 0) {
            GameView.finGame = true;
            GameView.endGame(winner());
            
            timer.cancel();
          }
          // first, compute possible next position if nothing stands in the way
          double nextBallX = getBallX() + deltaT * getBallSpeedX();
          double nextBallY = getBallY() + deltaT * getBallSpeedY();
          double ballX = getBallX() ; 
          double ballY = getBallY() ; 
          double ballSpeedX = getBallSpeedX() ; 
          double ballSpeedY = getBallSpeedY() ; 

          // next, see if the ball would meet some obstacle
          if (nextBallY < 0 || nextBallY > getHeight()) {
              ballSpeedY = -ballSpeedY ;
              setBallSpeedY(ballSpeedY);
              nextBallY = ballY + deltaT * ballSpeedY ;
              nextBallX = ballX + ((ballSpeedX<0)?-1:+1)*deltaT * (new Random()).nextDouble(Math.abs(ballSpeedX)); 
          }
  
          if ((nextBallX < 0 && nextBallY > getRacketA() && nextBallY < getRacketA() + getRacketSize())  || (nextBallX > getWidth() && nextBallY > getRacketB() && nextBallY < getRacketB() + getRacketSize())) { 
              ballSpeedX = -ballSpeedX; 
              setBallSpeedX(ballSpeedX);
              nextBallX = ballX + deltaT * ballSpeedX ;
              nextBallY = ballY +  ((ballSpeedY<0)?-1:+1)*deltaT * (new Random()).nextDouble(Math.abs(ballSpeedY)); 
          }else if (nextBallX < 0) { 
              getScore().addScore1();
              return true;
          }else if (nextBallX > getWidth()) { 
              getScore().addScore2();
              return true;
          }
          setBallX(nextBallX);
          setBallY(nextBallY);
          return false;
}

  public void commencerTimer() {
    timer.cancel();
    tmp.setText(String.valueOf(limit));
    timer = new Timer();
    resetNbManche();
    TimerTask a = new TimerTask() { 
      public void run() {
        int n = Integer.valueOf(tmp.getText());
         if (!GameView.finGame) {
            if (!GameView.pause) {
              if (n != 0) tmp.setText(String.valueOf(n-1));
              else {
                tmp.setText(String.valueOf(limit));
                reset();
                finManche();
              }
            }
          }
        }
          
      };

   timer.scheduleAtFixedRate(a, 1000, 1000);
      
  }

  

  public void resetNbManche() {
    nbManche = nbMancheInitial;
  }


  public int getLimit() {
    return limit;
  }

  public int getNbManche() {
    return nbManche;
  }

  public void closeTimer() {
    timer.cancel();
  }

  public Text getTmp() {
    return tmp;
  }

  public int winner() {
    int s1 = Integer.valueOf(scoreFinal.getS1().getText());
    int s2 = Integer.valueOf(scoreFinal.getS2().getText());

    return (Math.max(s1, s2)==s1)?1:2; 
    
  }



  public void finManche() {
    int s1 = Integer.valueOf(scoreManche.getS1().getText());
    int s2 = Integer.valueOf(scoreManche.getS2().getText());

    if (s1 > s2) scoreFinal.addScore1();
    else if (s1 < s2) scoreFinal.addScore2();
    else {
      scoreFinal.addScore1();
      scoreFinal.addScore2();
    }
    scoreManche.reset();
    nbManche--;

    
  }


  // public void afficheVictoire() {
  //   Text a = new Text(winner());
  //   a.setStyle("-fx-font: 60 arial;");
  //   a.setX(getWidth() /2);
  //   a.setY(getHeight() /2);
  //   root.getChildren().addAll(a); 
  // }
  



}