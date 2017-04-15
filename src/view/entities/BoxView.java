package view.entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by cactustribe on 25/03/17.
 */
public class BoxView extends EntityView{

    private static final Image SPRITE_BOX = new Image("images/box.png");

    private int WIDTH = 50;
    private int HEIGHT = 50;

    public BoxView(){
        this.addSprite("SPRITE_BOX", new ImageView(SPRITE_BOX), WIDTH, HEIGHT);
        this.setSprite("SPRITE_BOX");
    }
}
