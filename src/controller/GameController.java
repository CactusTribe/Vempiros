package controller;

import common.Sounds;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import model.*;
import model.entities.CharacterEntity;
import model.entities.Entity;
import model.entities.Player;
import model.entities.Vampire;
import view.ScreenGame;
import view.entities.AnimatedView;
import view.graphical.Splash;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cactustribe on 21/03/17.
 */
public class GameController {

    private String usrname = "";
    private Game game;
    private Player player;
    private ScreenGame gameView;
    private Timeline gameLoop;

    private KeyEvent last_pressed_key;
    private ActionType current_action;

    private BooleanProperty paused = new SimpleBooleanProperty(true);

    private long startTimeReloadBullets = 0;
    private long TIME_BEFORE_ADD_BULLET = 1000; // ms

    public boolean debug = false;


    public GameController(String usrname, ScreenGame view){
        this.usrname = usrname;
        this.gameView = view;
        this.game = new Game();
    }

    public void newGame(){
        game.init();
        player = game.getPlayer();

        gameView.update(game);

        game.arena_width().bind(gameView.arena.widthProperty());
        game.arena_height().bind(gameView.arena.heightProperty());

        gameView.menubar.getPanelLives().current_life().bind(game.getPlayer().current_life());
        gameView.menubar.getAmmoBar().progressProperty().bind((game.getPlayer()).progress_bullets());

        gameView.menubar.getAliveVamp().textProperty().bind(Bindings.convert(game.alive_vamp()));
        gameView.menubar.getDeadVamp().textProperty().bind(Bindings.convert(game.dead_vamp()));

        gameView.menubar.getSliderPlayerSpeed().valueProperty().set(1.0);
        gameView.menubar.getSliderVampSpeed().valueProperty().set(1.0);

        gameView.menubar.paused_property().bind(paused);

        game.getPlayer().alive().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    System.out.println("GAME OVER");
                    gameView.setSplash(Splash.GAME_OVER);
                    Sounds.play(Sounds.SoundType.GAME_OVER);
                    pauseGame();
                    ((AnimatedView)game.getPlayer().getEntityView()).setAnimation(AnimatedView.Animations.DEAD, null);
                }
                else{
                    gameView.setSplash(Splash.NONE);
                }
            }
        });

        game.alive_vamp().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() == 0){
                    System.out.println("YOU WIN");
                    gameView.setSplash(Splash.WIN);
                    Sounds.play(Sounds.SoundType.GAME_WIN);
                    pauseGame();
                }
            }
        });

        gameView.setSplash(Splash.NONE);
        gameView.update(game);
        //startGame();
    }

    public void startGame(){

        if(gameLoop != null){
            gameLoop.stop();
        }
        gameLoop = new Timeline();
        gameLoop.setCycleCount( Timeline.INDEFINITE );


        KeyFrame kf = new KeyFrame( Duration.seconds(0.017), new EventHandler<ActionEvent>() {
            public void handle(ActionEvent ae)
            {

                // GAME EVENTS
                if(player.current_bullets().getValue() == 0){
                    if(startTimeReloadBullets == 0){
                        startTimeReloadBullets = System.currentTimeMillis();
                    }
                    else if(System.currentTimeMillis() - startTimeReloadBullets >= TIME_BEFORE_ADD_BULLET){
                        player.addBullets(9999);
                        Sounds.play(Sounds.SoundType.GUN_RELOAD);
                        startTimeReloadBullets = 0;
                    }
                }
                //---------------------------------------------

                // DEPLACEMENTS
                if(player.isWalking()){
                    try {
                        game.apply(ActionType.MOVE);
                    } catch (Exception e){
                        gameView.displayError(e.toString());
                    }
                }
                //---------------------------------------------

                game.moveEntities();
                game.applyModifications();
                gameView.update(game);
            }
        });

        gameLoop.getKeyFrames().add( kf );
        gameLoop.play();
        this.paused.set(false);
    }

    public void pauseGame(){
        player.setWalking(false);

        if(gameLoop != null){
            gameLoop.stop();
            this.paused.set(true);
        }
    }

    public void resizeGame(double ratio){
        game.resizeBoundingBox(ratio);
        gameView.update(game);
    }

    public void setPlayerSpeed(double ratio){
        Player player = game.getPlayer();
        player.setSpeed(player.getInitialSpeed() * ratio);
    }

    public void setVampSpeed(double ratio){
        for(Entity entity : game.getEntities()){
            if(entity instanceof Vampire) {
                Vampire vamp = (Vampire) entity;
                vamp.setSpeed(vamp.getInitialSpeed() * ratio);
            }
        }
    }


    public void notifyEvent(Event event){

        if(player.isAlive() && !paused.getValue()){
            if(event instanceof KeyEvent){
                this.notifyKeyEvent((KeyEvent) event);
            }
            else if(event instanceof MouseEvent){
                this.notifyMouseEvent((MouseEvent) event);
            }
        }
    }

    public void notifyKeyEvent(KeyEvent event){

        if(event.getEventType() == KeyEvent.KEY_PRESSED){

            if(last_pressed_key == null || last_pressed_key.getCode() != event.getCode()){

                // Choix de l'action en fonction de la touche
                switch (event.getCode()){
                    case Z:
                        current_action = ActionType.MOVE;
                        player.setDirection(Direction.NORTH);
                        break;
                    case S:
                        current_action = ActionType.MOVE;
                        player.setDirection(Direction.SOUTH);
                        break;
                    case Q:
                        current_action = ActionType.MOVE;
                        player.setDirection(Direction.WEST);
                        break;
                    case D:
                        current_action = ActionType.MOVE;
                        player.setDirection(Direction.EAST);
                        break;
                    case SPACE:
                        current_action = ActionType.SHOOT;
                        break;
                    default:
                        current_action = null;
                        break;
                }

                if(current_action != null){
                    switch (current_action){
                        case MOVE:
                            player.setWalking(true);
                            break;
                        case SHOOT:
                            try {
                                game.apply(current_action);
                            } catch (Exception e){
                                gameView.displayError(e.toString());
                            }
                            break;
                    }
                    last_pressed_key = event;
                }
                else{
                    player.setWalking(false);
                    last_pressed_key = null;
                }

            }

        }
        else if(event.getEventType() == KeyEvent.KEY_RELEASED){
            if(event.getCode() != KeyCode.SPACE){
                if(last_pressed_key == null || last_pressed_key.getCode() == event.getCode() || last_pressed_key.getCode
                        () == KeyCode.SPACE) {
                    player.setWalking(false);
                }
            }
            last_pressed_key = null;
            current_action = null;
        }
    }

    public void notifyMouseEvent(MouseEvent event){

        if(event.getEventType() == MouseEvent.MOUSE_MOVED){

            if(!player.isWalking()) {

                BoundingBox player_box = player.getBounds();
                double mouseX = event.getX();
                double mouseY = event.getY();
                double minX = player_box.getMinX();
                double minY = player_box.getMinY();
                double maxX = player_box.getMaxX();
                double maxY = player_box.getMaxY();

                double centerX = player_box.getMinX() + (player_box.getWidth() / 2);
                double centerY = player_box.getMinY() + (player_box.getHeight() / 2);

                // NORTH
                if (mouseY < centerY && (mouseX >= minX && mouseX <= maxX)) {
                    player.setDirection(Direction.NORTH);
                }
                // SOUTH
                else if (mouseY >= centerY && (mouseX >= minX && mouseX <= maxX)) {
                    player.setDirection(Direction.SOUTH);
                }
                // WEST
                else if (mouseX < centerX && (mouseY >= minY && mouseY <= maxY)) {
                    player.setDirection(Direction.WEST);
                }
                // EAST
                else if (mouseX >= centerX && (mouseY >= minY && mouseY <= maxY)) {
                    player.setDirection(Direction.EAST);
                }
                // NORTH WEST
                else if (mouseY < minY && mouseX < minX) {
                    player.setDirection(Direction.NORTH_WEST);
                }
                // NORTH EAST
                else if (mouseY < minY && mouseX > maxX) {
                    player.setDirection(Direction.NORTH_EAST);
                }
                // SOUTH WEST
                else if (mouseY >= minY && mouseX < minX) {
                    player.setDirection(Direction.SOUTH_WEST);
                }
                // SOUTH EAST
                else if (mouseY >= minY && mouseX > maxX) {
                    player.setDirection(Direction.SOUTH_EAST);
                }
            }
        }

        else if(event.getEventType() == MouseEvent.MOUSE_PRESSED){

            if(event.getButton() == MouseButton.PRIMARY){
                try {
                    game.apply(ActionType.SHOOT);
                } catch (Exception e){
                    gameView.displayError(e.toString());
                }
            }
        }
        //System.out.println(player.getDirection());
    }


    public void cheatCode(String str){
        CharacterEntity player = game.getPlayer();
        List<String> tokens = new ArrayList<String>(Arrays.asList(str.split(" ")));

        try{
            String cmd = tokens.get(0);

            if(cmd.equals("speed")){
                int arg = Integer.parseInt(tokens.get(1));

                player.setSpeed(arg);
                System.out.println(String.format("Cheat: speed x%d.", arg));
            }
            else if(cmd.equals("life")){
                int arg = Integer.parseInt(tokens.get(1));

                if(arg >= 0) {
                    player.addLife(arg);
                    System.out.println(String.format("Cheat: %d life added.", arg));
                }
                else {
                    player.removeLife(Math.abs(arg));
                    System.out.println(String.format("Cheat: %d life removed.", arg));
                }
            }
            else if(cmd.equals("ammo")) {
                int arg = Integer.parseInt(tokens.get(1));
                ((Player) player).addBullets(arg);
                System.out.println(String.format("Cheat: %d bullets added.", arg));
            }
            else if(cmd.equals("die")) {
                player.removeLife(9999);
                System.out.println(String.format("Cheat: Player is dead."));
            }
            else if(cmd.equals("debug")) {
                if(debug){
                    debug = false;
                    System.out.println(String.format("Cheat: Debug disabled."));
                }
                else{
                    debug = true;
                    System.out.println(String.format("Cheat: Debug enabled."));
                }
            }
            else if(cmd.equals("reload") || cmd.equals("rl")) {
                newGame();
                System.out.println(String.format("Cheat: Game reloaded."));
            }
            else if(cmd.equals("god")) {
                if(!player.getImmortel()) {
                    player.setImmortel(true);
                    System.out.println(String.format("Cheat: God mod ON."));
                }
                else {
                    player.setImmortel(false);
                    System.out.println(String.format("Cheat: God mod OFF."));
                }
            }
            else if(cmd.equals("killall")) {
                game.killAll();
                System.out.println(String.format("Cheat: All vampires killed."));
            }
            else{
                System.out.println("Error: Command doesn't exists.");
            }

            gameView.update(game);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
