package view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Created by cactustribe on 24/03/17.
 */
public abstract class ObjectView extends StackPane {

    protected int SIZE_W = 128;
    protected int SIZE_H = 128;
    protected ImageView sprite;

    public void setSize(int w, int h){
        SIZE_W = w;
        SIZE_H = h;

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
        return this.SIZE_W;
    }

}
