package model.entities;

import view.entities.VampireView;

/**
 * Created by cactustribe on 02/04/17.
 */
public class Vampire extends CharacterEntity {

    public Vampire(){
        this.entityView = new VampireView();
        this.setSpeed(2);
    }

    public boolean canMovedBy(Entity entity){
        return false;
    }

    public void collidedBy(Entity other){

        if(other instanceof Bullet){
            game.getRemovedEntities().add(other);
            game.getRemovedEntities().add(this);

            game.alive_vamp().set(game.alive_vamp().getValue() - 1);
            game.dead_vamp().set(game.dead_vamp().getValue() + 1);
        }
    }
}
