package view;

import controller.ScreenController;
import javafx.scene.layout.StackPane;

/**
 * Created by cactustribe on 12/03/17.
 */
public abstract class Screen extends StackPane{
    ScreenController screenController;


    public ScreenController getController(){
        return this.screenController;
    }

}
