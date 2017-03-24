package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.BoundingBox;

/**
 * Created by cactustribe on 20/03/17.
 */
public abstract class Character {

    protected BoundingBox bounds = new BoundingBox(0,0,0,0);

    protected int total_life;
    protected IntegerProperty current_life = new SimpleIntegerProperty(0);
    protected Direction direction = Direction.EAST;
    protected int SPEED = 5;

    public void setBounds(BoundingBox bounds){
        this.bounds = bounds;
    }

    public void setSpeed(int speed){
        this.SPEED = speed;
    }


    public void setDirection(Direction dir){
        this.direction = dir;
    }

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

    public BoundingBox getBounds(){
        return this.bounds;
    }

    public int getSpeed() { return this.SPEED; }

    public Direction getDirection(){
        return this.direction;
    }

    public IntegerProperty current_life(){
        return this.current_life;
    }

}
