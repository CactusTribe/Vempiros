package view;

import controller.GameController;
import controller.ScreenController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Insets;
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
    public Pane arena;
    public CharacterView playerView;
    public LinkedList<ObjectView> objects;
    public LinkedList<BulletView> bullets;

    public boolean debug = true;
    private boolean paused = true;
    private double sprite_ratio = 1.0;
    private double lastArenaW = 0;
    private double lastArenaH = 0;


    public ScreenGame(){

        borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundImage(new Image("images/sand1.jpg"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));


        menubar = new MenuBar();

        arena = new Pane();
        arena.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        cheat_console = new TextField();
        cheat_console.setFocusTraversable(false);

        borderPane.setTop(menubar);
        borderPane.setCenter(arena);
        borderPane.setBottom(cheat_console);
        this.getChildren().addAll(borderPane);

        playerView = new CowboyView();
        playerView.setFocusTraversable(true);

        screenController = new ScreenController();

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

                    playerView.requestFocus();
                }
        );

        arena.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                System.out.println("Key Pressed: " + ke.getCode());
                gameController.notifyEvent(ke);

                arena.requestFocus();
            }
        });

        arena.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                System.out.println("Key Released: " + ke.getCode());
                gameController.notifyEvent(ke);

                arena.requestFocus();
            }
        });

        cheat_console.setOnKeyPressed(
                (event) -> {
                    if (event.getCode().equals(KeyCode.ENTER))
                        {
                            gameController.cheatCode(cheat_console.getText());
                            cheat_console.clear();
                            arena.requestFocus();
                        }

        });

/*
        final ChangeListener<Number> resizeListener = new ChangeListener<Number>() {
            Timer timer = null;
            TimerTask task = null;
            final long delayTime = 100;

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, final Number newValue) {
                if (task != null) {
                    task.cancel();
                }
                if( timer != null){
                    timer.cancel();
                }

                timer = new Timer();
                task = new TimerTask(){
                    @Override
                    public void run() {
                        double ratio = 1.0;

                        if(lastArenaW > 0 && lastArenaH > 0){
                            if(arena.getWidth() > arena.getHeight()){
                                ratio = arena.getHeight() / lastArenaH;

                            }
                            else if (arena.getWidth() < arena.getHeight()){
                                ratio = arena.getWidth() / lastArenaW;
                            }

                            gameController.resizeGame(ratio);
                            sprite_ratio = ratio;
                        }

                        System.out.println("resize to " + arena.getWidth() + " " + arena.getHeight());
                        lastArenaW = arena.getWidth();
                        lastArenaH = arena.getHeight();

                        timer.cancel();
                    }
                };
                timer.schedule(task, delayTime);
            }
        };
        */


        final ChangeListener<Number> resizeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, final Number newValue) {

                if(lastArenaW > 0 && lastArenaH > 0){
                    double ratio = arena.getWidth() / lastArenaW;
                    //gameController.resizeGame(ratio);
                }

                System.out.println("resize to " + arena.getWidth() + " " + arena.getHeight());
                lastArenaW = arena.getWidth();
                lastArenaH = arena.getHeight();
            }
        };


        //borderPane.widthProperty().addListener(resizeListener);

    }

    public void bindController(GameController controller){
        this.gameController = controller;
        System.out.println("New game " + this.getWidth() + " " + this.getHeight());
        //this.gameController.newGame();



        arena.maxWidthProperty().bind(arena.heightProperty().multiply(1.5));
    }

    public void init(Game game){
        playerView = new CowboyView();
        playerView.setFocusTraversable(true);
        objects = new LinkedList<>();
        bullets = new LinkedList<>();

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
    }

    public void update(Game game){
        arena.getChildren().clear();

        double bounding_ratio = game.getBounding_ratio();

        // AFFICHAGE DES OBJETS (ROCK, BOX, etc)
        LinkedList<Object> objects_liste = game.getObjects();
        for(int i=0; i < objects.size(); i++){
            Object obj = objects_liste.get(i);
            ObjectView objview = objects.get(i);

            //System.out.println(String.format("%f : %d", sprite_ratio, objview.width()));
            objview.setSize((int)(objview.width() * sprite_ratio), (int)(objview.height() * sprite_ratio));

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

        // AFFICHAGE DU JOUEUR
        Character player = game.getPlayer();
        if(!player.isAlive()){
            playerView.setAnimation(CharacterView.Animations.DEAD, null);
        }

        //System.out.println(String.format("%f : %d", sprite_ratio, playerView.width()));
        playerView.setSize((int)(playerView.width() * sprite_ratio), (int)(playerView.height() * sprite_ratio));
        BoundingBox player_box = player.getBounds();
        playerView.setLayoutX(player_box.getMinX()-(playerView.width()/2)+(player_box.getWidth()/ 2));
        playerView.setLayoutY(player_box.getMinY()-(playerView.height()/2)+(player_box.getHeight()/ 2)-10);

        if(debug){
            showCollisionBox(player.getBounds(), Color.BLUE);
        }
        // ----------------------------------------------------------------------------


        // AFFICHAGE DES BALLES
        LinkedList<Bullet> listeBullets = game.getBullets();


        for(Bullet bullet : listeBullets){

            BulletView bview = new BulletView(bullet.getDirection());
            BoundingBox bullet_box = bullet.getBounds();

            //System.out.println(String.format("%f : %d", bullet_ratio, bview.width()));
            //bview.setSize((int)(bview.width() * bullet_ratio), (int)(bview.height() * bullet_ratio));
            if(bullet.getDirection() == Direction.NORTH || bullet.getDirection() == Direction.SOUTH){
                bview.setSize((int)(bullet_box.getHeight()), (int)(bullet_box.getWidth()));
            }
            else {
                bview.setSize((int) (bullet_box.getWidth()), (int) (bullet_box.getHeight()));
            }

            bview.setLayoutX(bullet_box.getMinX()-(bview.width()/2)+(bullet_box.getWidth() / 2));
            bview.setLayoutY(bullet_box.getMinY()-(bview.height()/2)+(bullet_box.getHeight() / 2));
            arena.getChildren().add(bview);

            if(debug) {
                showCollisionBox(bullet_box, Color.BLUE);
            }
        }

        arena.getChildren().add(playerView);
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
