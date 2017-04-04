package model.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by cactustribe on 20/03/17.
 */
public abstract class CharacterEntity extends MoveableEntity{

    protected int total_life;
    protected IntegerProperty current_life = new SimpleIntegerProperty(0);

    public void addLife(int n){
        if(current_life.getValue() + n <= total_life)
            current_life.set(current_life.getValue() + n);
        else
            current_life.set(total_life);
    }

    public void removeLife(int n){
        if(n <= current_life.getValue())
            current_life.set(current_life.getValue() - n);
        else
            current_life.set(0);
    }

    public boolean isAlive(){
        return (current_life.getValue() > 0);
    }

    public IntegerProperty current_life(){
        return this.current_life;
    }

}