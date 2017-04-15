package view.entities;

import javafx.scene.image.Image;
import model.Direction;
import view.graphical.ImageViewAnimation;

/**
 * Created by cactustribe on 02/04/17.
 */
public class VampireFView extends AnimatedView{

    private static final Image SPRITE_VAMP = new Image("images/vampire_F.png");
    private static final Image RIP_VAMP = new Image("images/rip.png");

    // WALKING ANIM
    private final ImageViewAnimation WALK_NORTH = new ImageViewAnimation(
            SPRITE_VAMP, 4, 4, 0, 48*3, 32, 48, FPS_ANIM);
    private final ImageViewAnimation WALK_SOUTH = new ImageViewAnimation(
            SPRITE_VAMP, 4, 4, 0, 0, 32, 48, FPS_ANIM);
    private final ImageViewAnimation WALK_EAST = new ImageViewAnimation(
            SPRITE_VAMP, 4, 4, 0, 48*2, 32, 48, FPS_ANIM);
    private final ImageViewAnimation WALK_WEST = new ImageViewAnimation(
            SPRITE_VAMP, 4, 4, 0, 48, 32, 48, FPS_ANIM);
    // DEAD
    private final ImageViewAnimation DEAD = new ImageViewAnimation(
            RIP_VAMP, 1, 1, 0, 0, 128, 128, FPS_ANIM);

    private int WIDTH = 40;
    private int HEIGHT = 60;

    public VampireFView(){
        this.addSprite("WALK_NORTH", WALK_NORTH, WIDTH, HEIGHT);
        this.addSprite("WALK_SOUTH", WALK_SOUTH, WIDTH, HEIGHT);
        this.addSprite("WALK_EAST", WALK_EAST, WIDTH, HEIGHT);
        this.addSprite("WALK_WEST", WALK_WEST, WIDTH, HEIGHT);
        this.addSprite("DEAD", DEAD, WIDTH, HEIGHT);

        this.setAnimation(Animations.WALK, Direction.EAST);
    }


    public void setAnimation(Animations anim, Direction dir){
        if(this.direction == null || this.direction != dir){

            if(sprite != null)
                ((ImageViewAnimation)sprite).stop();

            if(anim == Animations.WALK){
                switch (dir){

                    case NORTH:
                        this.setSprite("WALK_NORTH");
                        break;
                    case SOUTH:
                        this.setSprite("WALK_SOUTH");
                        break;
                    case EAST:
                        this.setSprite("WALK_EAST");
                        break;
                    case WEST:
                        this.setSprite("WALK_WEST");
                        break;
                    case NORTH_EAST:
                        this.setSprite("WALK_EAST");
                        break;
                    case NORTH_WEST:
                        this.setSprite("WALK_WEST");
                        break;
                    case SOUTH_EAST:
                        this.setSprite("WALK_EAST");
                        break;
                    case SOUTH_WEST:
                        this.setSprite("WALK_WEST");
                        break;
                }
            }
            else if(anim == Animations.ATTACK){

            }
            else if(anim == Animations.DEAD){
                this.setSprite("DEAD");
            }

            this.startAnimation();
        }
        this.direction = dir;
    }
}
