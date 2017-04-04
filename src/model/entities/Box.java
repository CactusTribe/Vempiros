package model.entities;

import view.entities.BoxView;

/**
 * Created by cactustribe on 25/03/17.
 */
public class Box extends MoveableEntity{

    public Box(){
        this.entityView = new BoxView();
    }

    public boolean canMovedBy(Entity entity){
        if(entity instanceof Player){
            return true;
        }
        return false;
    }

    public void collidedBy(Entity other){
        if(other instanceof Bullet){
            game.getRemovedEntities().add(other);
        }
    }
}
