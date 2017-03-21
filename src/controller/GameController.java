package controller;

import javafx.event.EventType;
import javafx.scene.input.KeyEvent;
import model.Character;
import model.Direction;
import model.Game;
import view.CharacterView;
import view.ScreenGame;

/**
 * Created by cactustribe on 21/03/17.
 */
public class GameController {

    private Game game;
    private ScreenGame gameView;
    private KeyEvent last_pressed_key;
    private static int MOVE_SPEED = 10;

    public GameController(){

    }

    public void newGame(String usrname, ScreenGame view){
        this.game = new Game(usrname);
        this.gameView = view;

        gameView.update();
    }

    public void notifyEvent(KeyEvent ke){
        Character player = game.getPlayer();

        if(ke.getEventType() == KeyEvent.KEY_PRESSED){
            if(last_pressed_key == null || ke.getCode() != last_pressed_key.getCode()){
                switch (ke.getCode()){
                    case UP:
                        gameView.playerView.setAnimation(CharacterView.Animations.WALK_UP);
                        break;
                    case DOWN:
                        gameView.playerView.setAnimation(CharacterView.Animations.WALK_DOWN);
                        break;
                    case LEFT:
                        gameView.playerView.setAnimation(CharacterView.Animations.WALK_LEFT);
                        break;
                    case RIGHT:
                        gameView.playerView.setAnimation(CharacterView.Animations.WALK_RIGHT);
                        break;
                }

                gameView.playerView.startAnimation();
            }

            switch (ke.getCode()){
                case UP:
                    player.setDirection(Direction.NORTH);
                    player.setPosition(player.getX(), player.getY() - MOVE_SPEED);
                    break;
                case DOWN:
                    player.setDirection(Direction.SOUTH);
                    player.setPosition(player.getX(), player.getY()  + MOVE_SPEED);
                    break;
                case LEFT:
                    player.setDirection(Direction.WEST);
                    player.setPosition(player.getX() - MOVE_SPEED, player.getY());
                    break;
                case RIGHT:
                    player.setDirection(Direction.EAST);
                    player.setPosition(player.getX() + MOVE_SPEED, player.getY());
                    break;
            }

            last_pressed_key = ke;
        }
        else if(ke.getEventType() == KeyEvent.KEY_RELEASED){
            if(ke.getCode() == last_pressed_key.getCode()){
                gameView.playerView.setAnimation(CharacterView.Animations.IDLE);
                last_pressed_key = null;
            }
        }



        gameView.update();
    }

    public Game getGame(){
        return this.game;
    }
}
