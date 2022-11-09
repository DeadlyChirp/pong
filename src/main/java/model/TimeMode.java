package model;

import java.util.*;
import javafx.scene.layout.Pane;

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

  public void commencerTimer() {
    timer.cancel();
    tmp.setText(String.valueOf(limit));
    timer = new Timer();
    resetNbManche();
    TimerTask a = new TimerTask() { 
      public void run() {
        int n = Integer.valueOf(tmp.getText());
         if (!GameView.finGame) {
            if (getNbManche() == 0) {
              GameView.finGame = true;
              System.out.println(winner());
              timer.cancel();
    
            }
            else if (!GameView.pause) {
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

  public String winner() {
    int s1 = Integer.valueOf(scoreFinal.getS1().getText());
    int s2 = Integer.valueOf(scoreFinal.getS2().getText());

    if (s1 > s2) return "Le gagnant est le joueur 1 avec un score de "+scoreFinal.getS1().getText()+" à "+scoreFinal.getS2().getText();
    else if (s1 < s2) return "Le gagnant est le joueur 2 avec un score de "+scoreFinal.getS2().getText()+" à "+scoreFinal.getS1().getText();
    return "Egalité avec un score de "+scoreFinal.getS2().getText()+" à "+scoreFinal.getS1().getText();

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