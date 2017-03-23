package view;

import javafx.scene.image.Image;
import model.Direction;
import view.graphical.ImageViewAnimation;

/**
 * Created by cactustribe on 20/03/17.
 */
public class CowboyView extends CharacterView{

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



    public CowboyView(){
        this.setAnimation(Animations.IDLE, Direction.EAST);
    }


    public void setAnimation(Animations anim, Direction dir){
        this.getChildren().clear();

        if(current_image != null)
            current_image.stop();

        switch (anim){
            case WALK:
                if(dir == Direction.NORTH){
                    current_image = WALK_NORTH;
                }
                else if(dir == Direction.SOUTH){
                    current_image = WALK_SOUTH;
                }
                else if(dir == Direction.EAST){
                    current_image = WALK_EAST;
                }
                else if(dir == Direction.WEST){
                    current_image = WALK_WEST;
                }
                break;
            case ATTACK:
                break;
            case DEAD:
                current_image = DEAD;
                break;
            case IDLE:
                if(dir == Direction.NORTH){
                    current_image = IDLE_NORTH;
                }
                else if(dir == Direction.SOUTH){
                    current_image = IDLE_SOUTH;
                }
                else if(dir == Direction.EAST){
                    current_image = IDLE_EAST;
                }
                else if(dir == Direction.WEST){
                    current_image = IDLE_WEST;
                }
                break;
        }

        current_image.setFitWidth(SIZE_W);
        current_image.setFitHeight(SIZE_H);

        this.getChildren().addAll(current_image);
    }


}
