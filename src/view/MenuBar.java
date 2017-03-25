package view;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Created by cactustribe on 21/03/17.
 */
public class MenuBar extends ToolBar{

    private Button button_menu;
    private Button button_play;
    private Button button_options;
    private Text label_cowboy_speed;
    private Text label_vamp_speed;
    private Slider slider_cowboy_speed;
    private Slider slider_vamp_speed;
    private Text label_nb_alive;
    private Text label_nb_dead;
    private ProgressIndicator nb_ammo;
    private PanelLives panelLives;

    private int TOOLBAR_H = 40;

    private static Image IMG_PLAY = new Image("images/play.png");
    private static Image IMG_PAUSE = new Image("images/pause.png");
    private static Image IMG_HOME = new Image("images/home.png");
    private static Image IMG_OPTIONS = new Image("images/options.png");
    private static Image IMG_VAMP = new Image("images/vampire.png");
    private static Image IMG_DEAD_VAMP = new Image("images/dead_vampire.png");
    private static Image IMG_AMMO = new Image("images/ammo.png");

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

        ImageView icon_vampire = new ImageView(IMG_VAMP);
        icon_vampire.setFitHeight(TOOLBAR_H);
        icon_vampire.setFitWidth(TOOLBAR_H);

        ImageView icon_dead_vampire = new ImageView(IMG_DEAD_VAMP);
        icon_dead_vampire.setFitHeight(TOOLBAR_H);
        icon_dead_vampire.setFitWidth(TOOLBAR_H);

        ImageView icon_ammo = new ImageView(IMG_AMMO);
        icon_ammo.setFitHeight(TOOLBAR_H);
        icon_ammo.setFitWidth(TOOLBAR_H);

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


        slider_cowboy_speed = new Slider();
        slider_cowboy_speed.setMin(0);
        slider_cowboy_speed.setMax(10);
        slider_cowboy_speed.setValue(1);
        slider_cowboy_speed.setBlockIncrement(1);
        slider_cowboy_speed.setMaxWidth(80);
        label_cowboy_speed = new Text("Player");

        VBox cowboy_speed = new VBox();
        cowboy_speed.setSpacing(5);
        cowboy_speed.setAlignment(Pos.CENTER);
        cowboy_speed.getChildren().addAll(slider_cowboy_speed, label_cowboy_speed);


        slider_vamp_speed = new Slider();
        slider_vamp_speed.setMin(0);
        slider_vamp_speed.setMax(10);
        slider_vamp_speed.setValue(1);
        slider_vamp_speed.setBlockIncrement(1);
        slider_vamp_speed.setMaxWidth(80);
        label_vamp_speed = new Text("Vampires");

        VBox vamp_speed = new VBox();
        vamp_speed.setSpacing(5);
        vamp_speed.setAlignment(Pos.CENTER);
        vamp_speed.getChildren().addAll(slider_vamp_speed, label_vamp_speed);


        label_nb_alive = new Text("99");
        label_nb_alive.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/something_strange.ttf"), 30));
        label_nb_alive.setStroke(Color.BLACK);
        label_nb_alive.setStrokeWidth(2);

        label_nb_dead = new Text("99");
        label_nb_dead.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/something_strange.ttf"), 30));
        label_nb_dead.setStroke(Color.BLACK);
        label_nb_dead.setStrokeWidth(2);

        nb_ammo = new ProgressIndicator(0.6);

        HBox stats = new HBox();
        stats.setSpacing(10);
        stats.setAlignment(Pos.CENTER);
        stats.getChildren().addAll(icon_vampire, label_nb_alive, icon_dead_vampire, label_nb_dead);


        this.getItems().addAll(button_menu, button_options, button_play, new Separator(),
                panelLives, new Separator(), stats, icon_ammo, nb_ammo, new Separator(),
                cowboy_speed,
                vamp_speed);

    }

    public Button getButton_menu(){
        return this.button_menu;
    }

    public Button getButton_play(){
        return this.button_play;
    }

    public PanelLives getPanelLives(){
        return this.panelLives;
    }
}
