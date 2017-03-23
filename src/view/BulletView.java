package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.Direction;

/**
 * Created by cactustribe on 23/03/17.
 */
public class BulletView extends StackPane{

    private static final Image SPRITE_BULLET = new Image("images/bullet.png");

    private int SIZE_W = 30;
    private int SIZE_H = 10;
    private Direction direction;

    public BulletView(Direction dir){
        this.direction = dir;

        ImageView bullet = new ImageView(SPRITE_BULLET);
        bullet.setFitWidth(SIZE_W);
        bullet.setFitHeight(SIZE_H);

        switch (this.direction){

            case NORTH:
                bullet.setRotate(270);
                break;
            case SOUTH:
                bullet.setRotate(90);
                break;
            case EAST:
                bullet.setRotate(0);
                break;
            case WEST:
                bullet.setRotate(180);
                break;
        }

        this.getChildren().addAll(bullet);
    }

}
