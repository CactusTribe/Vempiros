package view.entities;

import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Direction;
import model.entities.Entity;
import view.graphical.ImageViewAnimation;

/**
 * Created by cactustribe on 20/03/17.
 */
public class PlayerView extends AnimatedView{

    private static final Image SPRITE_COWBOY = new Image("images/cowboy.png");
    private static final Image RIP_COWBOY = new Image("images/rip.png");

    // IDLE ANIM
    private final ImageViewAnimation IDLE_NORTH = new ImageViewAnimation(
            SPRITE_COWBOY, 1, 1, 0, 128*5, 128, 128, FPS_ANIM);
    private final ImageViewAnimation IDLE_SOUTH = new ImageViewAnimation(
            SPRITE_COWBOY, 1, 1, 0, 128*9, 128, 128, FPS_ANIM);
    private final ImageViewAnimation IDLE_EAST = new ImageViewAnimation(
            SPRITE_COWBOY, 1, 1, 0, 0, 128, 128, FPS_ANIM);
    private final ImageViewAnimation IDLE_WEST = new ImageViewAnimation(
            SPRITE_COWBOY, 1, 1, 0, 128, 128, 128, FPS_ANIM);
    // WALKING ANIM
    private final ImageViewAnimation WALK_NORTH = new ImageViewAnimation(
            SPRITE_COWBOY, 8, 8, 0, 128*5, 128, 128, FPS_ANIM);
    private final ImageViewAnimation WALK_SOUTH = new ImageViewAnimation(
            SPRITE_COWBOY, 8, 8, 0, 128*9, 128, 128, FPS_ANIM);
    private final ImageViewAnimation WALK_EAST = new ImageViewAnimation(
            SPRITE_COWBOY, 8, 8, 0, 0, 128, 128, FPS_ANIM);
    private final ImageViewAnimation WALK_WEST = new ImageViewAnimation(
            SPRITE_COWBOY, 8, 8, 0, 128, 128, 128, FPS_ANIM);
    // DEAD
    private final ImageViewAnimation DEAD = new ImageViewAnimation(
            RIP_COWBOY, 1, 1, 0, 0, 128, 128, FPS_ANIM);



    public PlayerView(){
        this.addSprite("IDLE_NORTH", IDLE_NORTH, 128, 128);
        this.addSprite("IDLE_SOUTH", IDLE_SOUTH, 128, 128);
        this.addSprite("IDLE_EAST", IDLE_EAST, 128, 128);
        this.addSprite("IDLE_WEST", IDLE_WEST, 128, 128);
        this.addSprite("WALK_NORTH", WALK_NORTH, 128, 128);
        this.addSprite("WALK_SOUTH", WALK_SOUTH, 128, 128);
        this.addSprite("WALK_EAST", WALK_EAST, 128, 128);
        this.addSprite("WALK_WEST", WALK_WEST, 128, 128);
        this.addSprite("DEAD", DEAD, 80, 80);

        this.setAnimation(Animations.IDLE, Direction.EAST);
    }

    public void update(Entity entity) {
        BoundingBox obj_box = entity.getBounds();
        this.setLayoutX(obj_box.getMinX() - (WIDTH() / 2) + (obj_box.getWidth() / 2));
        this.setLayoutY(obj_box.getMinY() - (HEIGHT() / 2) + (obj_box.getHeight() / 2) - 15);
    }


    public void setAnimation(Animations anim, Direction dir){
        if(sprite != null)
            ((ImageViewAnimation)sprite).stop();

        switch (anim){
            case WALK:
                if(dir == Direction.NORTH){
                    this.setSprite("WALK_NORTH");
                }
                else if(dir == Direction.SOUTH){
                    this.setSprite("WALK_SOUTH");
                }
                else if(dir == Direction.EAST){
                    this.setSprite("WALK_EAST");
                }
                else if(dir == Direction.WEST){
                    this.setSprite("WALK_WEST");
                }
                break;
            case ATTACK:
                break;
            case DEAD:
                this.setSprite("DEAD");
                break;
            case IDLE:
                if(dir == Direction.NORTH){
                    this.setSprite("IDLE_NORTH");
                }
                else if(dir == Direction.SOUTH){
                    this.setSprite("IDLE_SOUTH");
                }
                else if(dir == Direction.EAST){
                    this.setSprite("IDLE_EAST");
                }
                else if(dir == Direction.WEST){
                    this.setSprite("IDLE_WEST");
                }
                break;
        }
    }
}
