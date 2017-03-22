package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.LinkedList;

/**
 * Created by cactustribe on 21/03/17.
 */
public class Game {

    private String usrname;
    private Character player;
    private LinkedList<Character> vampires;
    private IntegerProperty ARENA_WIDTH = new SimpleIntegerProperty(0);
    private IntegerProperty ARENA_HEIGHT = new SimpleIntegerProperty(0);


    public Game(String usrname){
        this.usrname = usrname;
        this.player = new Cowboy(3);
        this.player.setBounds(128,128);
    }

    public void movePlayer(){

        switch (player.getDirection()){

            case NORTH:
                player.setPosition(player.getX(), player.getY() - player.getSpeed());
                break;
            case SOUTH:
                player.setPosition(player.getX(), player.getY()  + player.getSpeed());
                break;
            case EAST:
                player.setPosition(player.getX() + player.getSpeed(), player.getY());
                break;
            case WEST:
                player.setPosition(player.getX() - player.getSpeed(), player.getY());
                break;
        }

    }

    public boolean isPossible(ActionType action){
        boolean isPossible = true;

        switch (action){
            case MOVE:
                if(player.getDirection() == Direction.NORTH){
                    isPossible = (player.getY() - player.getSpeed() >= 0);
                }
                else if(player.getDirection() == Direction.SOUTH){
                    isPossible = (player.getY() + player.getSpeed() + player.getHeight() <= ARENA_HEIGHT.getValue());
                }
                else if(player.getDirection() == Direction.EAST){
                    isPossible = (player.getX() + player.getSpeed() + player.getWidth() <= ARENA_WIDTH.getValue());
                }
                else if(player.getDirection() == Direction.WEST){
                    isPossible = (player.getX() - player.getSpeed() >= 0);
                }

                break;
            case SHOOT:

                break;
        }

        return isPossible;
    }

    public void apply(ActionType action) throws Exception{
        switch (action){
            case MOVE:
                movePlayer();
                break;
            case SHOOT:

                break;
        }
    }

    public Character getPlayer(){
        return this.player;
    }

    public IntegerProperty arena_width(){
        return this.ARENA_WIDTH;
    }

    public IntegerProperty arena_height(){
        return this.ARENA_HEIGHT;
    }
}
