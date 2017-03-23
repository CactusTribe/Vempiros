package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import view.BulletView;

import java.util.LinkedList;

/**
 * Created by cactustribe on 21/03/17.
 */
public class Game {

    private String usrname;
    private Cowboy player;
    private LinkedList<Character> vampires;
    private LinkedList<Bullet> bullets;


    private IntegerProperty ARENA_WIDTH = new SimpleIntegerProperty(0);
    private IntegerProperty ARENA_HEIGHT = new SimpleIntegerProperty(0);


    public Game(String usrname){
        this.usrname = usrname;
        this.player = new Cowboy(3);
        this.player.setBounds(128,128);
        this.bullets = new LinkedList<>();
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

    public void moveBullets(){
        for(Bullet bullet : this.bullets){
            switch (bullet.getDirection()){

                case NORTH:
                    bullet.setPosition(bullet.getX(), bullet.getY() - bullet.getSpeed());
                    break;
                case SOUTH:
                    bullet.setPosition(bullet.getX(), bullet.getY()  + bullet.getSpeed());
                    break;
                case EAST:
                    bullet.setPosition(bullet.getX() + bullet.getSpeed(), bullet.getY());
                    break;
                case WEST:
                    bullet.setPosition(bullet.getX() - bullet.getSpeed(), bullet.getY());
                    break;
            }
        }
    }

    public void shoot(){
        Bullet bullet = new Bullet();
        bullet.setDirection(player.getDirection());

        switch (bullet.getDirection()){

            case NORTH:
                bullet.setPosition(player.getX() + (player.getWidth() / 2), player.getY());
                break;
            case SOUTH:
                bullet.setPosition(player.getX() + (player.getWidth() / 2), player.getY() + player.getHeight());
                break;
            case EAST:
                bullet.setPosition(player.getX() + player.getWidth(), player.getY() + (player.getHeight() / 2));
                break;
            case WEST:
                bullet.setPosition(player.getX(), player.getY() + (player.getHeight() / 2));
                break;
        }

        this.bullets.add(bullet);
        player.removeBullet();
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
                    isPossible = (player.getNbBullets() > 0);
                break;
        }

        return isPossible;
    }

    public void apply(ActionType action) throws Exception{

        switch (action){
            case MOVE:
                if(isPossible(action))
                    movePlayer();
                else throw new Exception("Mouvement impossible.");
                break;
            case SHOOT:
                if(isPossible(action))
                    shoot();
                else throw new Exception("Pas assez de munitions.");
                break;
        }
    }

    public Character getPlayer(){
        return this.player;
    }

    public LinkedList<Bullet> getBullets(){
        return this.bullets;
    }

    public IntegerProperty arena_width(){
        return this.ARENA_WIDTH;
    }

    public IntegerProperty arena_height(){
        return this.ARENA_HEIGHT;
    }
}
