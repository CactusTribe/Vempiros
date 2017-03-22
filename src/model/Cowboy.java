package model;

/**
 * Created by cactustribe on 21/03/17.
 */
public class Cowboy extends Character{

    public Cowboy(int lives){
        this.total_life = lives;
        this.current_life.set(lives);
    }

}
