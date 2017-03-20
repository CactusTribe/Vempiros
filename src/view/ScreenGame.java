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

    private static final Image SPRITE_COWBOY = new Image("file:resources/images/cowboy.png");

    private static final int COLUMNS  =   14;
    private static final int COUNT    =  140;
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  0;
    private static final int WIDTH    = 128;
    private static final int HEIGHT   = 128;

    public ScreenGame(){
        this.controller = new ScreenController();

        borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundImage(new Image("file:resources/images/dirt.jpg"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));


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

        final ImageViewAnimation cowboy = new ImageViewAnimation(SPRITE_COWBOY, 9, 9, 0, 0, 128, 128, 5);
        cowboy.start();
        //final ImageView imageView = new ImageView(SPRITE_COWBOY);
        //imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));
        /*
        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(10000),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
*/
        borderPane.setCenter(cowboy);

        this.getChildren().addAll(borderPane);


        button_menu.setOnAction(
                event -> {
                    this.controller.screensController.setScreen("menu");
                }
        );

    }
}
