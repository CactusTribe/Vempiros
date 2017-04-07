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
import model.entities.*;
import view.entities.*;

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

    public boolean debug = false;
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

    public void update(Game game){
        arena.getChildren().clear();

        // AFFICHAGE DES ENTITY (ROCK, BOX, etc)
        LinkedList<Entity> entities = game.getEntities();
        for(Entity entity : entities){

            EntityView entityView = entity.getEntityView();

            if(entity instanceof Player){
                if(!((Player)entity).isAlive()){
                    ((AnimatedView)entityView).setAnimation(AnimatedView.Animations.DEAD, null);
                }
            }

            entityView.setScale(sprite_ratio);
            entityView.update(entity);
            arena.getChildren().add(entityView);

            if(debug) {
                if(entity instanceof Player){
                    showCollisionBox(entity.getBounds(), Color.BLUE);
                }
                else if(entity instanceof CharacterEntity){
                    showCollisionBox(entity.getBounds(), Color.PURPLE);
                }
                else if(entity instanceof MoveableEntity){
                    showCollisionBox(entity.getBounds(), Color.GREENYELLOW);
                }
                else if(entity instanceof StaticEntity){
                    showCollisionBox(entity.getBounds(), Color.RED);
                }
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

    public void displayError(String err){
        System.out.println(err);
    }
}
