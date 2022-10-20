package model;

import javafx.scene.text.Text;

public class Score {
    public Text s1;
    public Text s2;


    Score(){
        s1 = new Text("0");
        s2 = new Text("0");
    }
    public void addScore(){
        s1.setText(String.valueOf(Integer.valueOf(s1.getText())+1));
        s2.setText(String.valueOf(Integer.valueOf(s2.getText())+1));
    }

}
