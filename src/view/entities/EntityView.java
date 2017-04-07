package view.entities;

import javafx.geometry.BoundingBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.Direction;
import model.entities.Entity;
import java.util.HashMap;

/**
 * Created by cactustribe on 03/04/17.
 */
public abstract class EntityView extends StackPane {

    protected Direction direction;

    protected HashMap<String, ImageView> sprites = new HashMap<>();
    protected ImageView sprite;

    public void setScale(double factor){
        for(ImageView img : sprites.values()){
            img.setFitWidth(img.getFitWidth() * factor);
            img.setFitHeight(img.getFitHeight() * factor);
        }
    }

    public void setSprite(String name){
        this.sprite = this.sprites.get(name);

        if(sprite != null){
            this.getChildren().clear();
            this.getChildren().add(sprite);
        }
    }

    public void addSprite(String name, ImageView view, double width, double height){
        view.setFitWidth(width);
        view.setFitHeight(height);
        this.sprites.put(name, view);
    }

    public void update(Entity entity){
        BoundingBox obj_box = entity.getBounds();
        this.setLayoutX(obj_box.getMinX()-(WIDTH()/2)+(obj_box.getWidth() / 2));
        this.setLayoutY(obj_box.getMinY()-(HEIGHT()/2)+(obj_box.getHeight() / 2));
    }

    public double WIDTH(){
        return this.sprite.getFitWidth();
    }
    public double HEIGHT() {
        return this.sprite.getFitHeight();
    }
}
