package model;
import java.util.Random;

import gui.GameView;

public class CourtDeuxContreDeux extends Court{
    // instance parameters
    private final RacketController playerC, playerD;
    // instance state
    private double racketC; // m
    private double racketD; // m

    public CourtDeuxContreDeux(RacketController playerA, RacketController playerB, RacketController playerC, RacketController playerD, double width, double height) {
    	super(playerA, playerB, width, height);
        this.playerC = playerC;
        this.playerD = playerD;
        reset();
    }
    
    public RacketController getPlayerC() {
        return playerC;
    }
    
    public RacketController getPlayerD() {
        return playerD;
    }
    
    public void setRacketC(double r) {
        this.racketC = r;
    }
    
    public void setRacketD(double r) {
        this.racketD = r;
    }
    
    public double getRacketC() {
        return racketC;
    }
    
    public double getRacketD() {
        return racketD;
    }

    public void update(double deltaT) {
    	super.update(deltaT);
        switch (playerC.getState()) {
        	case GOING_UP:
	            racketC -= getRacketSpeed() * deltaT;
	            if (racketC < 0.0) racketC = 0.0;
	            break;
        	case IDLE:
        		break;
        	case GOING_DOWN:
        		racketC += getRacketSpeed() * deltaT;
        		if (racketC + getRacketSize() > getHeight()) racketC = getHeight() - getRacketSize();
        		break;
        }
        switch (playerD.getState()) {
	        case GOING_UP:
	            racketD -= getRacketSpeed() * deltaT;
	            if (racketD < 0.0) racketD = 0.0;
	            break;
	        case IDLE:
	            break;
	        case GOING_DOWN:
	            racketD += getRacketSpeed() * deltaT;
	            if (racketD + getRacketSize() > getHeight()) racketD = getHeight() - getRacketSize();
	            break;
    }
    }


    /**
     * @return true if a player lost
     */
    public boolean updateBall(double deltaT) {
        // first, compute possible next position if nothing stands in the way
        double nextBallX = getBallX() + deltaT * getBallSpeedX();
        double nextBallY = getBallY() + deltaT * getBallSpeedY();
        // next, see if the ball would meet some obstacle
        if (nextBallY < 0 || nextBallY > getHeight()) {
            setBallSpeedY(-getBallSpeedY());
            nextBallY = getBallY() + deltaT * getBallSpeedY();
            nextBallX = getBallX() + ((getBallSpeedX()<0)?-1:+1)*deltaT * (new Random()).nextDouble(Math.abs(getBallSpeedX())); 
        }
        if ((nextBallX < 0 && nextBallY > getRacketA() && nextBallY < getRacketA() + getRacketSize())
        		|| (nextBallX < 0 && nextBallY > racketC && nextBallY < racketC + getRacketSize())
                || (nextBallX > getWidth() && nextBallY > getRacketB() && nextBallY < getRacketB() + getRacketSize())
                || (nextBallX > getWidth() && nextBallY > racketD && nextBallY < racketD + getRacketSize())) {
            setBallSpeedX(-getBallSpeedX());
            nextBallX = getBallX() + deltaT * getBallSpeedX();
        } else if (nextBallX < 0) {
            getScore().addScore1();
            if (getScore().endGame() == 1){
                GameView.finGame = true ;
                GameView.endGame(1);
            }
            return true;
        } else if (nextBallX > getWidth()) {
            getScore().addScore2();
            if (getScore().endGame() == 1){
                GameView.finGame = true ;
                GameView.endGame(2);
            }
            return true;
        }
        setBallX(nextBallX);
        setBallY(nextBallY);
        return false;
    }

    public void reset() {
    	super.reset();
        this.racketC = getHeight() / 4;
        this.racketD = getHeight() / 4;
    }
}
