package model;

/**
 * Created by cactustribe on 21/03/17.
 */
public class Game {

    private String usrname;
    private Character player;

    public Game(String usrname){
        this.usrname = usrname;
        this.player = new Cowboy(3);
    }

    public Character getPlayer(){
        return this.player;
    }
}
