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
import model.*;

public class GameViewDeuxContreDeux extends GameView {
	
	private CourtDeuxContreDeux court;
    private final Rectangle racketC, racketD;

    public GameViewDeuxContreDeux(CourtDeuxContreDeux court, Pane root, double scale) {
        super(court,root,scale);
        this.court = court;
		racketC = new Rectangle();
		racketC.setHeight(court.getRacketSize() * scale);
		racketC.setWidth(getRacketThickness());
		racketC.setFill(Color.LIGHTBLUE);
		
		racketC.setX(getMargin() - getRacketThickness());
		racketC.setY(court.getRacketC() * scale + getInTerface() + getMargin()/2);
		    
		racketD = new Rectangle();
		racketD.setHeight(court.getRacketSize() * scale);
		racketD.setWidth(getRacketThickness());
		racketD.setFill(Color.LIGHTBLUE);
		
		racketD.setX(court.getWidth() * scale + getMargin());
		racketD.setY(court.getRacketD() * scale + getInTerface() + getMargin()/2);
		    
        getGameRoot().getChildren().addAll(racketC, racketD);
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
                    getCourt().update((now - last) * 1.0e-9); // convert nanoseconds to seconds
                    last = now;
                    getRacketA().setY(getCourt().getRacketA() * getScale() + getMargin()/2 + getInTerface());
                    getRacketB().setY(getCourt().getRacketB() * getScale() + getMargin()/2 + getInTerface());
                    racketC.setY(court.getRacketC() * getScale() + getMargin()/2 + getInTerface());
                    racketD.setY(court.getRacketD() * getScale() + getMargin()/2 + getInTerface());
                    getBall().setCenterX(getCourt().getBallX() * getScale() + getMargin());
                    getBall().setCenterY(getCourt().getBallY() * getScale() + getMargin()/2 + getInTerface());
                }else{
                    last = 0;
                }    
                Timer--;
            }
        }.start();
    }
}
