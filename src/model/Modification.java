package model;

import model.entities.Entity;

/**
 * Created by cactustribe on 12/04/17.
 */
public class Modification {

    public enum ModificationType{
        KILL, REMOVE, SPAWN;
    }

    private ModificationType type;
    private Entity entity;

    public Modification(ModificationType type, Entity entity){
        this.type = type;
        this.entity = entity;
    }

    public ModificationType getType(){
        return this.type;
    }

    public Entity getEntity(){
        return this.entity;
    }
}
