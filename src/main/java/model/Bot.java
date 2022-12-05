package model;

public class Bot implements RacketController {
    private State state ;
    private int difficulte ; 
    private boolean renvoieBalle ; 
    private boolean calculDirection ; 

    Bot (int difficulte) {
        this.difficulte = difficulte ; 
        this.state = State.IDLE ; 
    }

    @Override
    public State getState() {
        return state ; 
    }

    public void calculDirection (double posX , double posY , double nextBallX , double nextBallY) {
        // calcule la direction vers laquelle la raquette doit se diriger
    }

    public void play () {
        // fonctioin qui va faire jouer le bot
    }
}
