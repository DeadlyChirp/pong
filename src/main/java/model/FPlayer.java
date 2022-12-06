package model;

import javafx.scene.text.Text;

public class FPlayer implements RacketController {

    public static final int SIZE_COST = 7;
    public static final int SPEED_COST = 5;
    public static final int POWER_COST = 3;

    State state;
    private double size;
    private double position;
    private double speed;
    private int sizeLevel;
    private int speedLevel;
    private int powerAmount;
    private int point;
    private final Text pointText;
    public static boolean spec = true;

    public FPlayer() {
        state = State.IDLE;
        size = 80;
        speed = 220;
        position = 0;
        point = 0;
        sizeLevel = 1;
        speedLevel = 1;
        powerAmount = 0;
        pointText = new Text(String.valueOf(point));

        reset();
    }


    @Override
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public double getSize() {
        return size;
    }

    public int getSizeLevel() {
        return sizeLevel;
    }

    public boolean increaseSizeLevel() {
        if (point < SIZE_COST) {
            return false;
        }

        decreasePoint(SIZE_COST);
        size = size + 15;
        sizeLevel++;
        return true;

    }

    public double getSpeed() {
        return speed;
    }

    public int getSpeedLevel() {
        return speedLevel;
    }

    public boolean increaseSpeedLevel() {
        if (point < SPEED_COST) {
            return false;
        }

        decreasePoint(SPEED_COST);
        speed = speed + 20;
        speedLevel++;

        return true;
    }

    public int getPowerAmount() {
        return powerAmount;
    }

    public boolean increasePowerAmount() {
        if (point < POWER_COST) {
            return false;
        }

        decreasePoint(POWER_COST);
        powerAmount = powerAmount + 1;
        return true;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public void increasePoint() {
        increasePoint(1);
    }

    public void increasePoint(int n) {
        point = point + n;
        pointText.setText(String.valueOf(point));
    }

    public void decreasePoint() {
        decreasePoint(1);
    }

    public void decreasePoint(int n) {
        point = point - n;
        pointText.setText(String.valueOf(point));
    }

    public Text getPointText() {
        return pointText;
    }

    public void reset() {
        point = 0;
        size = 80;
        speed = 220;
        sizeLevel = 1;
        speedLevel = 1;
        powerAmount = 0;
        pointText.setText(String.valueOf(point));
    }

    //ball collision using if statements

}
