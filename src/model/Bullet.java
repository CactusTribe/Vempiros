package model;

import javafx.geometry.BoundingBox;

/**
 * Created by cactustribe on 23/03/17.
 */
public class Bullet {

    private BoundingBox bounds = new BoundingBox(0,0,30,10);
    private Direction direction;
    private int SPEED = 10;

    public Bullet(){
    }

    public void setBounds(BoundingBox bounds){
        this.bounds = bounds;
    }

    public BoundingBox getBounds(){
        return this.bounds;
    }

    public void setDirection(Direction dir){
        this.direction = dir;
    }

    public Direction getDirection(){
        return this.direction;
    }

    public int getSpeed(){
        return this.SPEED;
    }
}
