package view.entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by cactustribe on 24/03/17.
 */
public class RockView extends EntityView{

    private static final Image SPRITE_ROCK = new Image("images/rock.png");

    public RockView(){
        this.sprite = new ImageView(SPRITE_ROCK);
        this.setSize(80,80);
    }
}
