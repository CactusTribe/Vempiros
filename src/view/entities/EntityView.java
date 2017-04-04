package view.entities;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.Direction;

/**
 * Created by cactustribe on 03/04/17.
 */
public abstract class EntityView extends StackPane {

    protected double WIDTH;
    protected double HEIGHT;
    protected Direction direction;
    protected ImageView sprite;

    public void setSize(double w, double h){
        this.WIDTH = w;
        this.HEIGHT = h;

        if(sprite != null){
            this.getChildren().clear();

            sprite.setFitWidth(WIDTH);
            sprite.setFitHeight(HEIGHT);

            this.getChildren().add(sprite);
        }
    }

    public double WIDTH(){
        return this.WIDTH;
    }
    public double HEIGHT(){
        return this.HEIGHT;
    }
}
