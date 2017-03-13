package view;

import controller.ScreenController;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

/**
 * Created by cactustribe on 12/03/17.
 */
public class ScreenGame extends Screen{

    BorderPane borderPane;
    Button button_menu;
    Button button_play;
    Button button_options;
    PanelLives panelLives;
    ToolBar toolBar;

    private static Image IMG_PLAY = new Image("file:resources/images/play.png");
    private static Image IMG_PAUSE = new Image("file:resources/images/pause.png");
    private static Image IMG_HOME = new Image("file:resources/images/home.png");
    private static Image IMG_OPTIONS = new Image("file:resources/images/options.png");

    public ScreenGame(){
        this.controller = new ScreenController();

        borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundImage(new Image("file:resources/images/dirt.jpg"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));


        ImageView icon_play = new ImageView(IMG_PLAY);
        icon_play.setFitHeight(30);
        icon_play.setFitWidth(30);

        ImageView icon_pause = new ImageView(IMG_PAUSE);
        icon_pause.setFitHeight(30);
        icon_pause.setFitWidth(30);

        ImageView icon_home = new ImageView(IMG_HOME);
        icon_home.setFitHeight(30);
        icon_home.setFitWidth(30);

        ImageView icon_options = new ImageView(IMG_OPTIONS);
        icon_options.setFitHeight(30);
        icon_options.setFitWidth(30);

        button_menu = new Button();
        button_menu.setId("button-menu");
        button_menu.setGraphic(icon_home);

        button_play = new Button();
        button_play.setId("button-play");
        button_play.setGraphic(icon_play);

        button_options = new Button();
        button_options.setId("button-options");
        button_options.setGraphic(icon_options);

        panelLives = new PanelLives(3);
        panelLives.setCurrentLife(2);

        toolBar = new ToolBar();
        toolBar.getItems().addAll(button_menu, button_options, button_play, panelLives);

        borderPane.setTop(toolBar);

        this.getChildren().addAll(borderPane);


        button_menu.setOnAction(
                event -> {
                    this.controller.screensController.setScreen("menu");
                }
        );

    }
}
