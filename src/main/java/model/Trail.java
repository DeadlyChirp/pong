package model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;

public class Trail {

    protected Image image;
    protected Color color;
    protected float alpha;
    protected float life;
    protected Shape shape;
    protected TrailListener listener;

    public Trail(Shape shape, Image image, float life, TrailListener listener) {
        this.shape = shape;
        this.image = image;
        this.alpha = 0.8f;
        this.life = life;
        this.listener = listener;
    }
    public void tick() {
        if (alpha > life) {
            alpha = alpha - life;
            shape.setFill(new ImagePattern(image));

        } else {
            if (listener != null) {
                listener.onRemove(this);
            }
        }
    }
    public Shape getShape() {
        return shape;
    }
    public interface TrailListener {
        void onRemove(Trail trail);
    }
}
