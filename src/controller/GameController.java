package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.*;
import model.Character;
import view.CharacterView;
import view.ScreenGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cactustribe on 21/03/17.
 */
public class GameController {

    private Game game;
    private ScreenGame gameView;

    private KeyEvent last_pressed_key;
    private ActionType current_action;
    private boolean walking = false;

    public GameController(){

    }

    public void newGame(String usrname, ScreenGame view){
        this.game = new Game(usrname);
        this.gameView = view;
        this.game.arena_width().bind(gameView.arena.widthProperty());
        this.game.arena_height().bind(gameView.arena.heightProperty());

        gameView.menubar.getPanelLives().current_life().bind(game.getPlayer().current_life());
        gameView.update(game);


        Timeline movePlayer = new Timeline(new KeyFrame(Duration.seconds(0.01), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(walking){
                    try {
                        game.apply(ActionType.MOVE);
                    } catch (Exception e){
                        gameView.displayError(e.toString());
                    }
                    gameView.update(game);
                }
            }
        }));

        movePlayer.setCycleCount(Timeline.INDEFINITE);
        movePlayer.play();

        Timeline bulletPropagation = new Timeline(new KeyFrame(Duration.seconds(0.01), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.moveBullets();
                gameView.update(game);
            }
        }));

        bulletPropagation.setCycleCount(Timeline.INDEFINITE);
        bulletPropagation.play();
    }

    public void notifyEvent(KeyEvent ke){
        Character player = game.getPlayer();

        if(ke.getEventType() == KeyEvent.KEY_PRESSED){

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
                            gameView.playerView.setAnimation(CharacterView.Animations.WALK, player.getDirection());
                            gameView.playerView.startAnimation();
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
                    gameView.playerView.setAnimation(CharacterView.Animations.IDLE, player.getDirection());
                    walking = false;
                }
            }

            last_pressed_key = null;
            current_action = null;
            //gameView.playerView.stopAnimation();

        }
    }


    public void cheatCode(String str){
        Character player = game.getPlayer();
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
                ((Cowboy) player).addBullets(arg);
                System.out.println(String.format("Cheat: %d bullets added.", arg));
            }
            else if(cmd.equals("die")) {
                player.removeLife(9999);
                System.out.println(String.format("Cheat: Cowboy is dead."));
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
            else{
                System.out.println("Error: Command doesn't exists.");
            }

            gameView.update(game);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
