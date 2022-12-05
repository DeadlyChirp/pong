package model;

import java.util.Random;

public class Bot implements RacketController {
    private State state ;
    private int difficulte ; 
    private boolean renvoieBalle ; 
    private boolean calculRenvoieBalle ; 

    Bot (int difficulte) {
        this.difficulte = difficulte ; 
        this.state = State.IDLE ; 
    }

    @Override
    public State getState() {
        return state ; 
    }

    private int calculDirection (double posY , double nextBallY) {
        if (nextBallY > posY) return -1 ; // la balle se dirige vers le bas
        if (nextBallY < posY) return 1 ; // la balle se dirige vers le haut
        return 0 ; // la balle reste immobile
    }

    private void moveRandomDirection (Random rm) {
        int direction = rm.nextInt(100) ; 
            if (direction < 10) {
                state = State.IDLE ; 
                return ; 
            }
            if (direction >= 10 && direction < 50 ) {
                state = State.GOING_UP ; 
                return ; 
            }
            if (direction >= 50) {
                state = State.GOING_DOWN ; 
                return ; 
            }
    }

    public void play (double posX , double nextBallX , double width) {
        Random rm = new Random() ; 
        if (posX <= width/2) {// on bouge aleatoirement
            renvoieBalle = false ; 
            calculRenvoieBalle = false ; 
            moveRandomDirection(rm);
            return ; 
        }
        
        if (renvoieBalle) {// on doit renvoyer la balle
            switch(calculDirection(posX , nextBallX)) {
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
    
        moveRandomDirection(rm);
    }
}
