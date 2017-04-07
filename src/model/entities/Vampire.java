package model.entities;

import view.entities.VampireFView;
import view.entities.VampireMView;

import java.util.Random;

import static model.entities.Vampire.Genre.FEMALE;

/**
 * Created by cactustribe on 02/04/17.
 */
public class Vampire extends CharacterEntity {

    public enum Genre{
        FEMALE, MALE;
    }

    private Genre genre;

    public Vampire(){
        Random rand = new Random();
        this.setInitialSpeed(rand.nextInt(10) + 1);

        genre = Genre.values()[rand.nextInt(Genre.values().length)];
        switch (genre){
            case FEMALE:
                this.entityView = new VampireFView();
                break;
            case MALE:
                this.entityView = new VampireMView();
                break;
        }

    }

    public boolean canMovedBy(Entity entity){
        return false;
    }

    public void collidedBy(Entity other){

        if(other instanceof Bullet){
            System.out.println("VAMP TOUCHED");
            game.getRemovedEntities().add(other);
            game.getRemovedEntities().add(this);

            game.alive_vamp().set(game.alive_vamp().getValue() - 1);
            game.dead_vamp().set(game.dead_vamp().getValue() + 1);
        }
    }
}
