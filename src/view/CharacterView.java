package view;

import javafx.scene.layout.StackPane;
import view.graphical.ImageViewAnimation;

/**
 * Created by cactustribe on 20/03/17.
 */
public abstract class CharacterView extends StackPane{

    public final static int FPS_ANIM = 15;
    protected ImageViewAnimation current_image;

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

}
