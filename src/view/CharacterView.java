package view;

import javafx.scene.layout.StackPane;
import model.Direction;
import view.graphical.ImageViewAnimation;

/**
 * Created by cactustribe on 20/03/17.
 */
public abstract class CharacterView extends StackPane{

    public final static int FPS_ANIM = 15;
    protected ImageViewAnimation current_image;
    protected double SIZE_W = 128;
    protected double SIZE_H = 128;
    protected Direction direction;

    public enum Animations {
        WALK,
        ATTACK,
        DEAD,
        IDLE
    }

    public void startAnimation(){
        current_image.start();
    }

    public void stopAnimation(){
        current_image.stop();
    }

    public void setSize(double w, double h){
        this.SIZE_W = w;
        this.SIZE_H = h;

        if(current_image != null){
            //this.getChildren().clear();

            current_image.setFitWidth(SIZE_W);
            current_image.setFitHeight(SIZE_H);

            //this.getChildren().add(current_image);
        }
    }

    public double width(){
        return this.SIZE_W;
    }

    public double height(){
        return this.SIZE_W;
    }


    public abstract void setAnimation(Animations anim, Direction dir);

}
