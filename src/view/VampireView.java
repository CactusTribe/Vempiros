package view;

import javafx.scene.image.Image;
import model.Direction;
import view.graphical.ImageViewAnimation;

/**
 * Created by cactustribe on 02/04/17.
 */
public class VampireView extends CharacterView{

    private static final Image SPRITE_VAMP = new Image("images/vampire_M.png");
    private static final Image RIP_VAMP = new Image("images/rip.png");

    // WALKING ANIM
    private static final ImageViewAnimation WALK_NORTH = new ImageViewAnimation(
            SPRITE_VAMP, 4, 4, 0, 48*3, 48, 48, FPS_ANIM);

    private static final ImageViewAnimation WALK_SOUTH = new ImageViewAnimation(
            SPRITE_VAMP, 4, 4, 0, 0, 48, 48, FPS_ANIM);

    private static final ImageViewAnimation WALK_EAST = new ImageViewAnimation(
            SPRITE_VAMP, 4, 4, 0, 48*2, 48, 48, FPS_ANIM);

    private static final ImageViewAnimation WALK_WEST = new ImageViewAnimation(
            SPRITE_VAMP, 4, 4, 0, 48, 48, 48, FPS_ANIM);

    // DEAD
    private static final ImageViewAnimation DEAD = new ImageViewAnimation(
            RIP_VAMP, 1, 1, 0, 0, 128, 128, FPS_ANIM);


    public VampireView(){
        this.setAnimation(Animations.WALK, Direction.EAST);
        this.setSize(80,80);
    }


    public void setAnimation(Animations anim, Direction dir){
        if(this.direction == null || this.direction != dir){

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
            }

            current_image.setFitWidth(SIZE_W);
            current_image.setFitHeight(SIZE_H);

            this.getChildren().addAll(current_image);

            System.out.println("Vampire start anim");
            this.startAnimation();
        }
        this.direction = dir;
    }
}
