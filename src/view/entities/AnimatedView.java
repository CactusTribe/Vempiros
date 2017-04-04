package view.entities;

import model.Direction;
import view.graphical.ImageViewAnimation;

/**
 * Created by cactustribe on 03/04/17.
 */
public abstract class AnimatedView extends EntityView{

    public final static int FPS_ANIM = 15;

    public enum Animations {
        WALK,
        ATTACK,
        DEAD,
        IDLE
    }

    public void startAnimation(){
        ((ImageViewAnimation)sprite).start();
    }
    public void stopAnimation() {
        ((ImageViewAnimation) sprite).stop();
    }

    public abstract void setAnimation(AnimatedView.Animations anim, Direction dir);
}
