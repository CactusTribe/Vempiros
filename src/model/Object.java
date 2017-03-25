package model;

import javafx.geometry.BoundingBox;

/**
 * Created by cactustribe on 24/03/17.
 */
public abstract class Object {

    protected BoundingBox bounds = new BoundingBox(0,0,0,0);
    protected boolean moveable;


    public boolean isMoveable(){
        return this.moveable;
    }

    public void setMoveable(boolean bool){
        this.moveable = bool;
    }

    public void setBounds(BoundingBox bounds){
        this.bounds = bounds;
    }

    public BoundingBox getBounds(){
        return this.bounds;
    }


}
