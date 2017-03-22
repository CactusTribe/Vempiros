package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by cactustribe on 20/03/17.
 */
public abstract class Character {

    protected int OFFSET_X = 0;
    protected int OFFSET_Y = 0;
    protected int SIZE_W = 0;
    protected int SIZE_H = 0;

    protected int total_life;
    protected IntegerProperty current_life = new SimpleIntegerProperty(0);
    protected Direction direction;
    protected int SPEED = 10;

    public void setBounds(int w, int h){
        this.SIZE_W = w;
        this.SIZE_H = h;
    }

    public void setSpeed(int speed){
        this.SPEED = speed;
    }

    public void setPosition(int x, int y){
        this.OFFSET_X = x;
        this.OFFSET_Y = y;
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

    public int getX() {
        return this.OFFSET_X;
    }

    public int getY() {
        return this.OFFSET_Y;
    }

    public int getWidth() {
        return this.SIZE_W;
    }

    public int getHeight() {
        return this.SIZE_H;
    }

    public int getSpeed() { return this.SPEED; }

    public Direction getDirection(){
        return this.direction;
    }

    public IntegerProperty current_life(){
        return this.current_life;
    }

}
