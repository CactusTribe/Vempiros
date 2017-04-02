package view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Created by cactustribe on 24/03/17.
 */
public abstract class ObjectView extends StackPane {

    protected double SIZE_W = 128;
    protected double SIZE_H = 128;
    protected ImageView sprite;

    public void setSize(double w, double h){
        SIZE_W = w;
        SIZE_H = h;

        if(sprite != null){
            this.getChildren().clear();

            sprite.setFitWidth(SIZE_W);
            sprite.setFitHeight(SIZE_H);

            this.getChildren().add(sprite);
        }
    }

    public double width(){
        return this.SIZE_W;
    }

    public double height(){
        return this.SIZE_W;
    }

}
