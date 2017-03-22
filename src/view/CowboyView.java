package view;

import javafx.scene.image.Image;
import view.graphical.ImageViewAnimation;

/**
 * Created by cactustribe on 20/03/17.
 */
public class CowboyView extends CharacterView{

    private static final Image SPRITE_COWBOY = new Image("file:resources/images/cowboy.png");

    private static final ImageViewAnimation cowboy_walk_R = new ImageViewAnimation(
            SPRITE_COWBOY, 8, 8, 128, 0, 128, 128, FPS_ANIM);
    private static final ImageViewAnimation cowboy_walk_L = new ImageViewAnimation(
            SPRITE_COWBOY, 8, 8, 128, 128, 128, 128, FPS_ANIM);

    private static final ImageViewAnimation cowboy_walk_UP = new ImageViewAnimation(
            SPRITE_COWBOY, 8, 8, 128, 128*5, 128, 128, FPS_ANIM);

    private static final ImageViewAnimation cowboy_walk_DW = new ImageViewAnimation(
            SPRITE_COWBOY, 8, 8, 128, 128*9, 128, 128, FPS_ANIM);

    private static final ImageViewAnimation cowboy_IDLE = new ImageViewAnimation(
            SPRITE_COWBOY, 1, 1, 0, 128*2, 128, 128, FPS_ANIM);



    public CowboyView(){
        this.setAnimation(Animations.IDLE);
    }

    public void setAnimation(Animations anim){
        this.getChildren().remove(current_image);

        if(current_image != null)
            current_image.stop();

        switch (anim){
            case WALK_UP:
                current_image = cowboy_walk_UP;
                break;
            case WALK_DOWN:
                current_image = cowboy_walk_DW;
                break;
            case WALK_LEFT:
                current_image = cowboy_walk_L;
                break;
            case WALK_RIGHT:
                current_image = cowboy_walk_R;
                break;
            case ATTACK_UP:
                break;
            case ATTACK_DOWN:
                break;
            case ATTACK_LEFT:
                break;
            case ATTACK_RIGHT:
                break;
            case IDLE:
                current_image = cowboy_IDLE;
                break;
        }

        current_image.setFitWidth(SIZE_W);
        current_image.setFitHeight(SIZE_H);

        this.getChildren().addAll(current_image);
    }


}
