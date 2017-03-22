package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.ActionType;
import model.Character;
import model.Direction;
import model.Game;
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
        gameView.update();


        Timeline movePlayer = new Timeline(new KeyFrame(Duration.seconds(0.01), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(walking){
                    if(game.isPossible(ActionType.MOVE)){
                        try {
                            game.apply(ActionType.MOVE);
                        } catch (Exception e){
                            gameView.displayError(e.toString());
                        }
                    }
                    else{
                        gameView.displayError("Mouvement impossible");
                    }
                    gameView.update();
                }
            }
        }));

        movePlayer.setCycleCount(Timeline.INDEFINITE);
        movePlayer.play();
    }

    public void notifyEvent(KeyEvent ke){
        Character player = game.getPlayer();

        if(ke.getEventType() == KeyEvent.KEY_PRESSED){

            if(last_pressed_key == null || last_pressed_key.getCode() != ke.getCode()){
                switch (ke.getCode()){
                    case Z:
                        current_action = ActionType.MOVE;
                        player.setDirection(Direction.NORTH);
                        gameView.playerView.setAnimation(CharacterView.Animations.WALK_UP);
                        walking = true;
                        break;
                    case S:
                        current_action = ActionType.MOVE;
                        player.setDirection(Direction.SOUTH);
                        gameView.playerView.setAnimation(CharacterView.Animations.WALK_DOWN);
                        walking = true;
                        break;
                    case Q:
                        current_action = ActionType.MOVE;
                        player.setDirection(Direction.WEST);
                        gameView.playerView.setAnimation(CharacterView.Animations.WALK_LEFT);
                        walking = true;
                        break;
                    case D:
                        current_action = ActionType.MOVE;
                        player.setDirection(Direction.EAST);
                        gameView.playerView.setAnimation(CharacterView.Animations.WALK_RIGHT);
                        walking = true;
                        break;
                }

                gameView.playerView.startAnimation();
            }

            last_pressed_key = ke;

            /*
            if(current_action != null){
                if(game.isPossible(current_action)){
                    try {
                        game.apply(current_action);
                    } catch (Exception e){
                        gameView.displayError(e.toString());
                    }
                }
                else{
                    gameView.displayError("Action impossible");
                }
            }
            */


        }
        else if(ke.getEventType() == KeyEvent.KEY_RELEASED){

            if(last_pressed_key == null || last_pressed_key.getCode() == ke.getCode()){
                gameView.playerView.setAnimation(CharacterView.Animations.IDLE);
                walking = false;
            }

            last_pressed_key = null;
            current_action = null;
        }

       // gameView.update();
    }


    public void cheatCode(String str){
        Character player = game.getPlayer();
        List<String> tokens = new ArrayList<String>(Arrays.asList(str.split(" ")));

        try{

            String cmd = tokens.get(0);

            if(cmd.equals("speed")){
                player.setSpeed(Integer.parseInt(tokens.get(1)));
            }
            else if(cmd.equals("life")){
                int arg = Integer.parseInt(tokens.get(1));

                if(arg >= 0) {
                    player.addLife(arg);
                }
                else {
                    player.removeLife(Math.abs(arg));
                }
            }
            else{
                System.out.println("Error: Command doesn't exists.");
            }


        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public Game getGame(){
        return this.game;
    }
}
