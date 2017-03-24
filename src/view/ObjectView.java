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
        this.SIZE_W = w;
        this.SIZE_H = h;

        if(sprite != null){
            sprite.setFitWidth(this.SIZE_W);
            sprite.setFitHeight(this.SIZE_H);

            this.getChildren().clear();
            this.getChildren().add(sprite);
        }
    }
}
