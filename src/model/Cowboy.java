package model;

/**
 * Created by cactustribe on 21/03/17.
 */
public class Cowboy extends Character {

    private int total_bullets = 150;
    private int nb_bullets;

    public Cowboy(int lives){
        this.total_life = lives;
        this.current_life.set(lives);
        this.nb_bullets = total_bullets;
        this.setSpeed(6);
    }

    public int getNbBullets() {
        return this.nb_bullets;
    }

    public void addBullets(int n) {
        this.nb_bullets += n;
    }

    public void removeBullet(){
        if(nb_bullets > 0)
            nb_bullets--;
    }

}
