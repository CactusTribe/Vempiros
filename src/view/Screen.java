package view;

import controller.ScreenController;
import javafx.scene.layout.StackPane;

public abstract class Screen extends StackPane{
    ScreenController screenController;


    public ScreenController getController(){
        return this.screenController;
    }

}
