package model.entities;

import javafx.geometry.BoundingBox;
import model.Direction;
import view.entities.AnimatedView;

import java.util.LinkedList;

/**
 * Created by cactustribe on 03/04/17.
 */
public abstract class MoveableEntity extends Entity{

    protected double speed;
    protected Direction direction;

    public boolean move(double offset, Direction dir){

        boolean move_done = true;
        Direction old_dir = this.getDirection();
        BoundingBox old_box = this.getBounds();
        BoundingBox new_box = game.translateBounds(bounds, dir, offset);
        LinkedList<Entity> collided = game.collidedEntities(new_box);

        if(game.outOfArena(new_box)){
            return false;
        }

        for(Entity entity : collided){
            if(entity != this){
                if(entity instanceof StaticEntity){
                    move_done = false;
                    break;
                }
                else if(entity instanceof MoveableEntity){

                    //((MoveableEntity) entity).setDirection(this.getDirection());

                    if( !((MoveableEntity) entity).canMovedBy(this) ){
                        move_done = false;
                        break;
                    }

                    else if ( !((MoveableEntity) entity).move(offset, dir) ){
                        move_done = false;
                        break;
                    }
                }
            }
        }

        this.setBounds(new_box);
        game.objectCollision(this);

        if(!move_done){
            this.setDirection(old_dir);
            this.setBounds(old_box);
        }

        return move_done;
    }

    public abstract boolean canMovedBy(Entity entity);

    public Direction getDirection(){
        return this.direction;
    }
    public double getSpeed() { return this.speed; }

    public void setDirection(Direction direction){
        this.direction = direction;

        if(this.entityView instanceof AnimatedView){
            ((AnimatedView)this.entityView).setAnimation(AnimatedView.Animations.WALK, this.direction);
        }
    }
    public void setSpeed(double speed) { this.speed = speed; }

}
