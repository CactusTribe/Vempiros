package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by cactustribe on 24/03/17.
 */
public class RockView extends ObjectView{

    private static final Image SPRITE_ROCK = new Image("images/rock.png");

    public RockView(){
        sprite = new ImageView(SPRITE_ROCK);
        setSize(80,80);
    }
}
