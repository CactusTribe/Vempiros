package view.entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Direction;

/**
 * Created by cactustribe on 23/03/17.
 */
public class BulletView extends EntityView{

    private static final Image SPRITE_BULLET = new Image("images/bullet.png");

    public BulletView(Direction dir){
        this.direction = dir;
        this.sprite = new ImageView(SPRITE_BULLET);
        this.setSize(30,10);

        switch (this.direction){
            case NORTH:
                this.setRotate(270);
                break;
            case SOUTH:
                this.setRotate(90);
                break;
            case EAST:
                this.setRotate(0);
                break;
            case WEST:
                this.setRotate(180);
                break;
        }
    }
}
