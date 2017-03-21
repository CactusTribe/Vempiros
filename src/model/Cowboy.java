package model;

import view.CowboyView;

/**
 * Created by cactustribe on 21/03/17.
 */
public class Cowboy extends Character{

    public Cowboy(int lives){
        this.OFFSET_X = 10;
        this.OFFSET_Y = 10;
        this.total_life = lives;
    }

}
