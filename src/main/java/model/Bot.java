package model;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Bot implements RacketController {
    private State state ;
    private int difficulte ; 
    private boolean renvoieBalle ; 
    private boolean calculRenvoieBalle ; 
    private boolean letToTimer ; 
    private javax.swing.Timer timer ;   
    private Court court ; 

    public Bot (int difficulte) {
        this.difficulte = difficulte ; 
        timer = new javax.swing.Timer(1000+(new Random()).nextInt(500), ev -> {
            if (letToTimer || court.getBallX() <= court.getWidth()/2 ) {// on bouge aleatoirement
                renvoieBalle = false ; 
                calculRenvoieBalle = false ; 
                letToTimer = false ; 
                moveRandomDirection(new Random());
                return ; 
            }
        });
        timer.start();  
        this.state = State.IDLE ; 
    }

    public void setCourt (Court court) {
        this.court = court ; 
    }

    @Override
    public State getState() {
        return state ; 
    }

    private int calculDirection (double posY) {
        timer.setDelay(1000+(new Random()).nextInt(500)); 
        if (posY <= court.getRacketB()) return 1 ; 
        if (posY >= court.getRacketB() + court.getRacketSize()) return -1 ; 
        return 0 ; // la balle reste immobile
    }

    private void moveRandomDirection (Random rm) {
        int direction = rm.nextInt(3) ; 
            switch(direction) {
                case 0 :
                state = State.IDLE ; 
                    break; 
                case 1 : 
                state = State.GOING_DOWN ; 
                    break ; 
                case 2 :
                    state = State.GOING_UP ; 
                    break ; 
            }
    }

    public void play (double posY ,  double nextBallY , double width) {
        Random rm = new Random() ; 
        if (court.getBallX() <= court.getWidth()/2 ) {// on bouge aleatoirement
            renvoieBalle = false ; 
            calculRenvoieBalle = false ; 
        }
        
        if (renvoieBalle) {// on doit renvoyer la balle
            switch(calculDirection(nextBallY)) {
                case -1 :
                    state = State.GOING_DOWN ; 
                    break ; 
                case 0 :
                    state = State.IDLE ; 
                    break ;
                case 1 :
                    state = State.GOING_UP ; 
                    break ;         
            }
            return ; 
        }

        if (!calculRenvoieBalle) {// on doit savoir si on renvoit la balle ou pas
            calculRenvoieBalle = true ; 
            switch(difficulte) {
                case 1 :
                    renvoieBalle = rm.nextInt(4) == 1 ;
                    // renvoie 1/4 des balles
                    break ; 
                case 2 :
                    renvoieBalle = rm.nextInt(5) >= 2 ; 
                    // renvoie 3/5 des balles
                    break ; 
                case 3 :
                    renvoieBalle = rm.nextInt(100) >= 10 ; 
                    // renvoie 90/100 balles
                    break ;          
            }
            return ; 
        }
    
        letToTimer = true ; 
    }
}
