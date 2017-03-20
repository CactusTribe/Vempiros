package view;

import controller.ScreenController;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import view.graphical.ImageViewAnimation;
import view.graphical.SpriteAnimation;

/**
 * Created by cactustribe on 12/03/17.
 */
public class ScreenGame extends Screen{

    BorderPane borderPane;
    Button button_menu;
    Button button_play;
    Button button_options;
    Text label_speed;
    Slider slider_speed;
    PanelLives panelLives;
    ToolBar toolBar;

    private static int TOOLBAR_H = 50;

    private static Image IMG_PLAY = new Image("file:resources/images/play.png");
    private static Image IMG_PAUSE = new Image("file:resources/images/pause.png");
    private static Image IMG_HOME = new Image("file:resources/images/home.png");
    private static Image IMG_OPTIONS = new Image("file:resources/images/options.png");


    public ScreenGame(){
        this.controller = new ScreenController();

        borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundImage(new Image("file:resources/images/sand1.jpg"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));


        ImageView icon_play = new ImageView(IMG_PLAY);
        icon_play.setFitHeight(TOOLBAR_H);
        icon_play.setFitWidth(TOOLBAR_H);

        ImageView icon_pause = new ImageView(IMG_PAUSE);
        icon_pause.setFitHeight(TOOLBAR_H);
        icon_pause.setFitWidth(TOOLBAR_H);

        ImageView icon_home = new ImageView(IMG_HOME);
        icon_home.setFitHeight(TOOLBAR_H);
        icon_home.setFitWidth(TOOLBAR_H);

        ImageView icon_options = new ImageView(IMG_OPTIONS);
        icon_options.setFitHeight(TOOLBAR_H);
        icon_options.setFitWidth(TOOLBAR_H);

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

        slider_speed = new Slider();
        slider_speed.setMin(0);
        slider_speed.setMax(10);
        slider_speed.setValue(1);
        slider_speed.setShowTickMarks(true);
        slider_speed.setMajorTickUnit(1);
        slider_speed.setMinorTickCount(1);
        slider_speed.setBlockIncrement(1);

        label_speed = new Text("");


        toolBar = new ToolBar();
        toolBar.getItems().addAll(button_menu, button_options, button_play, panelLives, slider_speed, label_speed);

        borderPane.setTop(toolBar);

        CowboyView cowboy_walk_L = new CowboyView();
        CowboyView cowboy_walk_UP = new CowboyView();
        CowboyView cowboy_walk_DW = new CowboyView();
        CowboyView cowboy_walk_R = new CowboyView();

        cowboy_walk_L.setAnimation(CharacterView.Animations.WALK_LEFT);
        cowboy_walk_R.setAnimation(CharacterView.Animations.WALK_RIGHT);
        cowboy_walk_UP.setAnimation(CharacterView.Animations.WALK_UP);
        cowboy_walk_DW.setAnimation(CharacterView.Animations.WALK_DOWN);

        HBox grid_walk = new HBox();
        grid_walk.getChildren().addAll(cowboy_walk_L, cowboy_walk_UP, cowboy_walk_DW, cowboy_walk_R);

        borderPane.setCenter(grid_walk);

        this.getChildren().addAll(borderPane);


        button_menu.setOnAction(
                event -> {
                    this.controller.screensController.setScreen("menu");
                }
        );

    }
}
