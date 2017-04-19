package model.entities;

import model.Modification;
import view.entities.RockView;

public class Rock extends StaticEntity{

    public Rock(){
        this.entityView = new RockView();
    }

    public void collidedBy(Entity other){
        if(other instanceof Bullet){
            game.getModifications().add(new Modification(Modification.ModificationType.REMOVE, other));
        }
    }
}
