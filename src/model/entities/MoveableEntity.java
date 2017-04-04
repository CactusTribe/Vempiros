package model.entities;

import javafx.geometry.BoundingBox;
import model.Direction;

import java.util.LinkedList;

/**
 * Created by cactustribe on 03/04/17.
 */
public abstract class MoveableEntity extends Entity{

    protected double speed;
    protected Direction direction;

    public void move(double offset){

        BoundingBox new_box = null;
        switch (this.direction){
            case NORTH:
                new_box = new BoundingBox(bounds.getMinX(), bounds.getMinY() - offset, bounds.getWidth(),
                        bounds.getHeight());
                break;
            case SOUTH:
                new_box = new BoundingBox(bounds.getMinX(), bounds.getMinY() + offset, bounds.getWidth(),
                        bounds.getHeight());
                break;
            case EAST:
                new_box = new BoundingBox(bounds.getMinX() + offset, bounds.getMinY(), bounds.getWidth(),
                        bounds.getHeight());
                break;
            case WEST:
                new_box = new BoundingBox(bounds.getMinX() - offset, bounds.getMinY(), bounds.getWidth(),
                        bounds.getHeight());
                break;
        }

        this.setBounds(new_box);
    }

    public boolean canMovedTo(double offset, Direction direction){
        BoundingBox new_box = game.translateBounds(bounds, direction, offset);
        LinkedList<Entity> collided = game.collidedEntities(new_box);

        if(game.outOfArena(new_box)){
            return false;
        }

        for(Entity entity : collided){
            if(entity != this){
                if(entity instanceof StaticEntity){
                    return false;
                }
                else if(entity instanceof MoveableEntity){
                    if( !((MoveableEntity) entity).canMovedBy(this) ){
                        return false;
                    }
                    else if ( !((MoveableEntity) entity).canMovedTo(offset, direction) ){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public abstract boolean canMovedBy(Entity entity);

    public Direction getDirection(){
        return this.direction;
    }
    public double getSpeed() { return this.speed; }

    public void setDirection(Direction direction){
        this.direction = direction;
    }
    public void setSpeed(double speed) { this.speed = speed; }

}
