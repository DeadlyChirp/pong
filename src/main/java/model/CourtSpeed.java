package model;
import gui.GameView;

public class CourtSpeed extends Court {

    public CourtSpeed (RacketController playerA, RacketController playerB, double width, double height , int limit) {
        super(playerA, playerB, width, height , limit) ; 
    }

    
    public boolean updateBall(double deltaT) {

        double nextBallX = super.getBallX() + deltaT * super.getBallSpeedX();
        double nextBallY = super.getBallY() + deltaT * super.getBallSpeedY();

        if (nextBallY < 0 || nextBallY > super.getHeight()) {
            super.setBallSpeedY(-super.getBallSpeedY()*1.05);
            nextBallY = super.getBallY() + deltaT * super.getBallSpeedY();
        }

        if ((nextBallX < 0 && nextBallY > super.getRacketA() && nextBallY < super.getRacketA() + super.getRacketSize())
                 || (nextBallX > super.getWidth() && nextBallY > super.getRacketB() && nextBallY < super.getRacketB() + super.getRacketSize())) {
            super.setBallSpeedX(-super.getBallSpeedX()*1.05);
            super.setBallSpeedY(-super.getBallSpeedY()*1.05);
            nextBallX = super.getBallX() + deltaT * super.getBallSpeedX();
        } else if (nextBallX < 0) { 
            super.getScore().addScore1();
            if (super.getScore().endGame() != -1){
                GameView.finGame = true ;
            }
            return true;
        }else if (nextBallX > super.getWidth()) { 
            super.getScore().addScore2();
            if (super.getScore().endGame() != -1){
                GameView.finGame = true ;
            }
            return true;
        }
        super.setBallX(nextBallX);
        super.setBallY(nextBallY);
        return false;
    }


}
