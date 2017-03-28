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
    private ImageView sprite;

    private int SIZE_W = 30;
    private int SIZE_H = 10;
    private Direction direction;

    public BulletView(Direction dir){
        this.direction = dir;

        sprite = new ImageView(SPRITE_BULLET);
        sprite.setFitWidth(SIZE_W);
        sprite.setFitHeight(SIZE_H);

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

        this.getChildren().addAll(sprite);
    }

    public void setSize(int w, int h){
        this.SIZE_W = w;
        this.SIZE_H = h;

        if(sprite != null){
            this.getChildren().clear();

            sprite.setFitWidth(SIZE_W);
            sprite.setFitHeight(SIZE_H);

            this.getChildren().add(sprite);
        }
    }

    public int width(){
        return this.SIZE_W;
    }

    public int height(){
        return this.SIZE_H;
    }

}
