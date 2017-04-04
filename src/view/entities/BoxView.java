package view.entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by cactustribe on 25/03/17.
 */
public class BoxView extends EntityView{

    private static final Image SPRITE_BOX = new Image("images/box.png");

    public BoxView(){
        this.sprite = new ImageView(SPRITE_BOX);
        this.setSize(80,80);
    }
}
