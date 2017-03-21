package view;

import javafx.scene.layout.StackPane;
import view.graphical.ImageViewAnimation;

/**
 * Created by cactustribe on 20/03/17.
 */
public abstract class CharacterView extends StackPane{

    public final static int FPS_ANIM = 15;
    protected ImageViewAnimation current_image;
    protected static int SPRITE_SIZE = 100;

    public enum Animations {
        WALK_UP,
        WALK_DOWN,
        WALK_LEFT,
        WALK_RIGHT,
        ATTACK_UP,
        ATTACK_DOWN,
        ATTACK_LEFT,
        ATTACK_RIGHT,
        IDLE
    }

    public void startAnimation(){
        current_image.start();
    }

    public void stopAnimation(){
        current_image.stop();
    }

    public abstract void setAnimation(Animations anim);

}
