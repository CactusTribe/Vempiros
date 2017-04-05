package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.*;
import model.entities.CharacterEntity;
import model.entities.Player;
import view.ScreenGame;
import view.entities.AnimatedView;
import view.entities.PlayerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cactustribe on 21/03/17.
 */
public class GameController {

    private String usrname = "";
    private Game game;
    private ScreenGame gameView;
    private Timeline gameLoop;

    private KeyEvent last_pressed_key;
    private ActionType current_action;

    private boolean walking = false;
    private boolean paused = true;
    private long startTimeReloadBullets = 0;
    private long TIME_BEFORE_ADD_BULLET = 1000; // ms


    public GameController(String usrname, ScreenGame view){
        this.usrname = usrname;
        this.gameView = view;
        this.game = new Game();
    }

    public void newGame(){
        game.init();
        gameView.update(game);

        game.arena_width().bind(gameView.arena.widthProperty());
        game.arena_height().bind(gameView.arena.heightProperty());

        gameView.menubar.getPanelLives().current_life().bind(game.getPlayer().current_life());
        gameView.menubar.getAmmoBar().progressProperty().bind((game.getPlayer()).progress_bullets());

        gameView.menubar.getAliveVamp().textProperty().bind(Bindings.convert(game.alive_vamp()));
        gameView.menubar.getDeadVamp().textProperty().bind(Bindings.convert(game.dead_vamp()));


    }

    public void startGame(){

        if(gameLoop != null){
            gameLoop.stop();
        }
        gameLoop = new Timeline();
        gameLoop.setCycleCount( Timeline.INDEFINITE );

        Player player = game.getPlayer();

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
                        startTimeReloadBullets = 0;
                    }
                }
                //---------------------------------------------

                // DEPLACEMENTS
                if(walking){
                    try {
                        game.apply(ActionType.MOVE);
                    } catch (Exception e){
                        gameView.displayError(e.toString());
                        //ze.printStackTrace();
                    }
                }
                //---------------------------------------------

                game.moveEntities();
                gameView.update(game);
            }
        });

        gameLoop.getKeyFrames().add( kf );
        gameLoop.play();
        this.paused = false;
    }

    public void pauseGame(){
        if(gameLoop != null){
            gameLoop.stop();
            this.paused = true;
        }
    }

    public void resizeGame(double ratio){
        game.resizeBoundingBox(ratio);
        gameView.update(game);
    }


    public void notifyEvent(KeyEvent ke){
        CharacterEntity player = game.getPlayer();

        if(player.isAlive()){

            if(!paused && ke.getEventType() == KeyEvent.KEY_PRESSED){
                if(last_pressed_key == null || last_pressed_key.getCode() != ke.getCode()){

                    // Choix de l'action en fonction de la touche
                    switch (ke.getCode()){
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
                            try {
                                game.apply(current_action);
                            } catch (Exception e){
                                gameView.displayError(e.toString());
                                //e.printStackTrace();
                            }
                            break;
                        default:
                            current_action = null;
                            break;
                    }

                    // Gestion de l'annimation de l'action en cours
                    if(current_action != null){
                        switch (current_action){
                            case MOVE:
                                ((PlayerView)player.getEntityView()).setAnimation(AnimatedView.Animations.WALK, player
                                        .getDirection());
                                ((PlayerView)player.getEntityView()).startAnimation();
                                walking = true;
                                break;
                            case SHOOT:
                                break;
                        }
                        last_pressed_key = ke;
                    }
                    else{
                        walking = false;
                        last_pressed_key = null;
                    }

                }

            }
            else if(ke.getEventType() == KeyEvent.KEY_RELEASED){
                if(ke.getCode() != KeyCode.SPACE){
                    if(last_pressed_key == null || last_pressed_key.getCode() == ke.getCode() || last_pressed_key.getCode
                            () == KeyCode.SPACE) {
                        ((PlayerView)player.getEntityView()).setAnimation(AnimatedView.Animations.IDLE, player.getDirection());
                        walking = false;
                    }
                }
                last_pressed_key = null;
                current_action = null;
            }
        }
        else{
            walking = false;
        }

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
                if(gameView.debug){
                    gameView.debug = false;
                    System.out.println(String.format("Cheat: Debug disabled."));
                }
                else{
                    gameView.debug = true;
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
            else{
                System.out.println("Error: Command doesn't exists.");
            }

            gameView.update(game);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
