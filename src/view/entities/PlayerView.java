package view.entities;

import javafx.scene.image.Image;
import model.Direction;
import view.graphical.ImageViewAnimation;

/**
 * Created by cactustribe on 20/03/17.
 */
public class PlayerView extends AnimatedView{

    private static final Image SPRITE_COWBOY = new Image("images/cowboy.png");
    private static final Image RIP_COWBOY = new Image("images/rip.png");

    // IDLE ANIM
    private static final ImageViewAnimation IDLE_NORTH = new ImageViewAnimation(
            SPRITE_COWBOY, 1, 1, 0, 128*5, 128, 128, FPS_ANIM);

    private static final ImageViewAnimation IDLE_SOUTH = new ImageViewAnimation(
            SPRITE_COWBOY, 1, 1, 0, 128*9, 128, 128, FPS_ANIM);

    private static final ImageViewAnimation IDLE_EAST = new ImageViewAnimation(
            SPRITE_COWBOY, 1, 1, 0, 0, 128, 128, FPS_ANIM);

    private static final ImageViewAnimation IDLE_WEST = new ImageViewAnimation(
            SPRITE_COWBOY, 1, 1, 0, 128, 128, 128, FPS_ANIM);

    // WALKING ANIM
    private static final ImageViewAnimation WALK_NORTH = new ImageViewAnimation(
            SPRITE_COWBOY, 8, 8, 0, 128*5, 128, 128, FPS_ANIM);

    private static final ImageViewAnimation WALK_SOUTH = new ImageViewAnimation(
            SPRITE_COWBOY, 8, 8, 0, 128*9, 128, 128, FPS_ANIM);

    private static final ImageViewAnimation WALK_EAST = new ImageViewAnimation(
            SPRITE_COWBOY, 8, 8, 0, 0, 128, 128, FPS_ANIM);

    private static final ImageViewAnimation WALK_WEST = new ImageViewAnimation(
            SPRITE_COWBOY, 8, 8, 0, 128, 128, 128, FPS_ANIM);

    // DEAD
    private static final ImageViewAnimation DEAD = new ImageViewAnimation(
            RIP_COWBOY, 1, 1, 0, 0, 128, 128, FPS_ANIM);



    public PlayerView(){
        this.setAnimation(Animations.IDLE, Direction.EAST);
        this.setSize(128,128);
    }


    public void setAnimation(Animations anim, Direction dir){
        this.getChildren().clear();

        if(sprite != null)
            ((ImageViewAnimation)sprite).stop();

        switch (anim){
            case WALK:
                if(dir == Direction.NORTH){
                    sprite = WALK_NORTH;
                }
                else if(dir == Direction.SOUTH){
                    sprite = WALK_SOUTH;
                }
                else if(dir == Direction.EAST){
                    sprite = WALK_EAST;
                }
                else if(dir == Direction.WEST){
                    sprite = WALK_WEST;
                }
                break;
            case ATTACK:
                break;
            case DEAD:
                sprite = DEAD;
                break;
            case IDLE:
                if(dir == Direction.NORTH){
                    sprite = IDLE_NORTH;
                }
                else if(dir == Direction.SOUTH){
                    sprite = IDLE_SOUTH;
                }
                else if(dir == Direction.EAST){
                    sprite = IDLE_EAST;
                }
                else if(dir == Direction.WEST){
                    sprite = IDLE_WEST;
                }
                break;
        }

        this.getChildren().addAll(sprite);
    }


}
