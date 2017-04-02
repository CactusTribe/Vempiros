package model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by cactustribe on 21/03/17.
 */
public class Cowboy extends Character {

    private DoubleProperty total_bullets;
    private DoubleProperty current_bullets;
    private NumberBinding progress_bullets;

    public Cowboy(int lives){
        this.total_life = lives;
        this.current_life.set(lives);

        this.total_bullets = new SimpleDoubleProperty(20);
        this.current_bullets = new SimpleDoubleProperty(total_bullets.getValue());
        this.progress_bullets = Bindings.divide(current_bullets, total_bullets);

        this.setSpeed(10);
    }

    public void addBullets(int n) {
        if(n <= total_bullets.getValue())
            this.current_bullets.set(current_bullets.getValue() + n);
        else
            this.current_bullets.set(total_bullets.getValue());
    }

    public void removeBullet(){
        if(current_bullets.getValue() > 0)
            current_bullets.set(current_bullets.getValue() - 1);
    }

    public DoubleProperty total_bullets() {
        return total_bullets;
    }

    public DoubleProperty current_bullets() {
        return current_bullets;
    }

    public NumberBinding progress_bullets() {
        return progress_bullets;
    }

}
