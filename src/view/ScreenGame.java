package view;

import controller.GameController;
import controller.ScreenController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.*;
import model.Character;
import model.Object;

import java.util.LinkedList;

/**
 * Created by cactustribe on 12/03/17.
 */
public class ScreenGame extends Screen{

    private BorderPane borderPane;
    private TextField cheat_console;
    private GameController gameController;

    public MenuBar menubar;
    public Pane wrapperPane;
    public Pane arena;

    public CharacterView playerView;
    private LinkedList<VampireView> vampires;
    private LinkedList<ObjectView> objects;

    public boolean debug = true;
    private boolean paused = true;
    private double sprite_ratio = 1.0;
    private double lastArenaW = 0;
    private double lastArenaH = 0;


    public ScreenGame(){
        screenController = new ScreenController();
        this.setWidth(860);
        this.setHeight(640);

        borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundImage(new Image("images/sand1.jpg"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));


        menubar = new MenuBar();
        wrapperPane = new Pane();
        arena = new Pane();
        arena.setBorder(new Border(new BorderStroke(Color.SIENNA, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(5.0))));

        wrapperPane.getChildren().add(arena);

        cheat_console = new TextField();
        cheat_console.setFocusTraversable(false);

        borderPane.setTop(menubar);
        borderPane.setCenter(wrapperPane);
        borderPane.setBottom(cheat_console);
        this.getChildren().addAll(borderPane);

        playerView = new CowboyView();
        playerView.setFocusTraversable(true);

        menubar.getButton_menu().setOnAction(
                event -> {
                    gameController.pauseGame();
                    this.screenController.screensController.setScreen("menu");
                }
        );

        menubar.getButton_play().setOnMouseClicked(
                event -> {
                    if(paused){
                        paused = false;
                        menubar.setPaused(false);
                        gameController.startGame();
                    }
                    else{
                        paused = true;
                        menubar.setPaused(true);
                        gameController.pauseGame();
                    }
                    wrapperPane.requestFocus();
                }
        );


        wrapperPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                //System.out.println("Key Pressed: " + ke.getCode());

                if(ke.getCode() == KeyCode.R){
                   gameController.newGame();
                }
                else{
                    gameController.notifyEvent(ke);
                }

                wrapperPane.requestFocus();
            }
        });

        wrapperPane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                //System.out.println("Key Released: " + ke.getCode());
                gameController.notifyEvent(ke);

                wrapperPane.requestFocus();
            }
        });

        cheat_console.setOnKeyPressed(
                (event) -> {
                    if (event.getCode().equals(KeyCode.ENTER))
                    {
                        gameController.cheatCode(cheat_console.getText());
                        cheat_console.clear();
                        wrapperPane.requestFocus();
                    }

                });

        final ChangeListener<Number> resizeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, final Number newValue) {

                if(lastArenaW > 0 && lastArenaH > 0){
                    double ratio = arena.getWidth() / lastArenaW;
                    sprite_ratio = ratio;
                    gameController.resizeGame(ratio);
                }

                lastArenaW = arena.getWidth();
                lastArenaH = arena.getHeight();
            }
        };

        arena.prefWidthProperty().bind(wrapperPane.widthProperty());
        arena.prefHeightProperty().bind(wrapperPane.heightProperty());

        arena.layoutXProperty().bind(wrapperPane.widthProperty().subtract(arena.widthProperty()).divide(2));
        arena.layoutYProperty().bind(wrapperPane.heightProperty().subtract(arena.heightProperty()).divide(2));

        arena.maxWidthProperty().bind(arena.prefHeightProperty().multiply(1.5));
        arena.maxHeightProperty().bind(arena.prefWidthProperty().divide(1.5));

        arena.widthProperty().addListener(resizeListener);
        arena.heightProperty().addListener(resizeListener);
    }


    public void bindController(GameController controller){
        this.gameController = controller;
        this.gameController.newGame();
    }

    public void init(Game game){
        playerView = new CowboyView();
        playerView.setFocusTraversable(true);
        objects = new LinkedList<>();
        vampires = new LinkedList<>();

        LinkedList<Object> objects_liste = game.getObjects();
        for(Object obj : objects_liste) {

            ObjectView objview = null;
            if (obj instanceof Rock) {
                objview = new RockView();
            } else if (obj instanceof Box) {
                objview = new BoxView();
            }
            objects.add(objview);
        }

        LinkedList<Vampire> vampires_liste = game.getVampires();
        for(Vampire vamp : vampires_liste) {
            vampires.add(new VampireView());
        }
    }

    public void update(Game game){
        arena.getChildren().clear();

        // AFFICHAGE DES OBJETS (ROCK, BOX, etc)
        LinkedList<Object> objects_liste = game.getObjects();
        for(int i=0; i < objects.size(); i++){
            Object obj = objects_liste.get(i);
            ObjectView objview = objects.get(i);

            objview.setSize(objview.width() * sprite_ratio, objview.height() * sprite_ratio);

            BoundingBox obj_box = obj.getBounds();
            objview.setLayoutX(obj_box.getMinX()-(objview.width()/2)+(obj_box.getWidth() / 2));
            objview.setLayoutY(obj_box.getMinY()-(objview.height()/2)+(obj_box.getHeight() / 2));
            arena.getChildren().add(objview);

            if(debug) {
                if(obj.isMoveable()){
                    showCollisionBox(obj.getBounds(), Color.GREENYELLOW);
                }
                else{
                    showCollisionBox(obj.getBounds(), Color.RED);
                }
            }
        }
        // ----------------------------------------------------------------------------

        // AFFICHAGE DES VAMPIRES
        LinkedList<Vampire> vampires_liste = game.getVampires();
        for(int i=0; i < vampires.size(); i++){
            Vampire vamp = vampires_liste.get(i);
            VampireView vampview = vampires.get(i);

            vampview.setAnimation(CharacterView.Animations.WALK, vamp.getDirection());
            vampview.setSize(vampview.width() * sprite_ratio, vampview.height() * sprite_ratio);

            BoundingBox obj_box = vamp.getBounds();
            vampview.setLayoutX(obj_box.getMinX()-(vampview.width()/2)+(obj_box.getWidth() / 2));
            vampview.setLayoutY(obj_box.getMinY()-(vampview.height()/2)+(obj_box.getHeight() / 2));
            arena.getChildren().add(vampview);

            if(debug) {
                showCollisionBox(vamp.getBounds(), Color.PURPLE);
            }
        }
        // ----------------------------------------------------------------------------

        // AFFICHAGE DU JOUEUR
        Character player = game.getPlayer();

        playerView.setSize(playerView.width() * sprite_ratio, playerView.height() * sprite_ratio);
        BoundingBox player_box = player.getBounds();
        playerView.setLayoutX(player_box.getMinX()-(playerView.width()/2)+(player_box.getWidth()/ 2));
        playerView.setLayoutY(player_box.getMinY()-(playerView.height()/2)+(player_box.getHeight()/ 2)-10);
        arena.getChildren().add(playerView);

        if(!player.isAlive()){
            playerView.setAnimation(CharacterView.Animations.DEAD, null);
        }

        if(debug){
            showCollisionBox(player.getBounds(), Color.BLUE);
        }
        // ----------------------------------------------------------------------------


        // AFFICHAGE DES BALLES
        LinkedList<Bullet> listeBullets = game.getBullets();

        for(Bullet bullet : listeBullets){

            BulletView bview = new BulletView(bullet.getDirection());
            BoundingBox bullet_box = bullet.getBounds();

            //System.out.println(String.format("%f : %f", bullet_ratio, bview.width()));
            //bview.setSize((int)(bview.width() * bullet_ratio), (int)(bview.height() * bullet_ratio));
            if(bullet.getDirection() == Direction.NORTH || bullet.getDirection() == Direction.SOUTH){
                bview.setSize(bullet_box.getHeight(), bullet_box.getWidth());
            }
            else {
                bview.setSize(bullet_box.getWidth(), bullet_box.getHeight());
            }

            bview.setLayoutX(bullet_box.getMinX()-(bview.width()/2)+(bullet_box.getWidth() / 2));
            bview.setLayoutY(bullet_box.getMinY()-(bview.height()/2)+(bullet_box.getHeight() / 2));
            arena.getChildren().add(bview);

            if(debug) {
                showCollisionBox(bullet_box, Color.BLUE);
            }
        }

        sprite_ratio = 1.0;

    }

    public void showCollisionBox(BoundingBox box, Color color){
        Pane pane = new Pane();
        pane.setLayoutX(box.getMinX());
        pane.setLayoutY(box.getMinY());
        pane.setPrefWidth(box.getWidth());
        pane.setPrefHeight(box.getHeight());
        pane.setBorder(new Border(new BorderStroke(color,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        arena.getChildren().add(pane);
    }

    public GameController getGameController(){
        return this.gameController;
    }

    public void displayError(String err){
        System.out.println(err);
    }
}
