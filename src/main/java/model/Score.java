package model;

import javafx.scene.text.Text;

public class Score {
    private Text s1;
    private Text s2;

    private int limitePoint;

    public Text getS1(){
        return this.s1;
    }

    public Text getS2(){
        return this.s2;
    }

    Score(){
        s1 = new Text("0");
        s2 = new Text("0");
    }

    Score (int limit) {
        this() ;
        this.limitePoint = limit ; 
    }

    public int getLimit(){
        return this.limitePoint;
    }

    public void addScore1(){
        s1.setText(String.valueOf(Integer.valueOf(s1.getText())+1));
    }

    public void addScore2(){
        s2.setText(String.valueOf(Integer.valueOf(s2.getText())+1));
    }

    public int endGame() {
        if (Integer.valueOf(s1.getText()) == limitePoint) return 1 ; 
        if (Integer.valueOf(s2.getText()) == limitePoint) return 1 ; 
        return -1 ; 
    }

    public void reset(){
        s1.setText("0");
        s2.setText("0");
    }
}