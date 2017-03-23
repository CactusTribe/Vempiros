package view;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * Created by cactustribe on 21/03/17.
 */
public class MenuBar extends ToolBar{

    private Button button_menu;
    private Button button_play;
    private Button button_options;
    private Text label_speed;
    private Slider slider_speed;
    private PanelLives panelLives;

    private int TOOLBAR_H = 30;

    private static Image IMG_PLAY = new Image("images/play.png");
    private static Image IMG_PAUSE = new Image("images/pause.png");
    private static Image IMG_HOME = new Image("images/home.png");
    private static Image IMG_OPTIONS = new Image("images/options.png");

    public MenuBar(){

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

        slider_speed = new Slider();
        slider_speed.setMin(0);
        slider_speed.setMax(10);
        slider_speed.setValue(1);
        slider_speed.setShowTickMarks(true);
        slider_speed.setMajorTickUnit(1);
        slider_speed.setMinorTickCount(1);
        slider_speed.setBlockIncrement(1);

        label_speed = new Text("");

        this.getItems().addAll(button_menu, button_options, button_play, panelLives, slider_speed, label_speed);
    }

    public Button getButton_menu(){
        return this.button_menu;
    }

    public PanelLives getPanelLives(){
        return this.panelLives;
    }
}
