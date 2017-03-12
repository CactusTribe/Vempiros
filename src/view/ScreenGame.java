package view;

import controller.ScreenController;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/**
 * Created by cactustribe on 12/03/17.
 */
public class ScreenGame extends Screen{

    public ScreenGame(){
        this.controller = new ScreenController();

        BorderPane borderPane = new BorderPane();

        Button button_menu = new Button("Menu");
        button_menu.setId("button-menu");

        borderPane.setTop(button_menu);

        this.getChildren().add(borderPane);

        button_menu.setOnAction(
                event -> {
                    this.controller.screensController.setScreen("menu");
                }
        );
    }
}
