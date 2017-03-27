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
    protected int SIZE_W = 128;
    protected int SIZE_H = 128;

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

    public void setSize(int w, int h){
        this.SIZE_W = w;
        this.SIZE_H = h;

        if(current_image != null){
            this.getChildren().clear();

            current_image.setFitWidth(SIZE_W);
            current_image.setFitHeight(SIZE_H);

            this.getChildren().add(current_image);
        }
    }

    public int width(){
        return this.SIZE_W;
    }

    public int height(){
        return this.SIZE_W;
    }


    public abstract void setAnimation(Animations anim, Direction dir);

}
