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

            if(!game.getRemovedEntities().contains(this)) {
                game.getRemovedEntities().add(this);

                game.alive_vamp().set(game.alive_vamp().getValue() - 1);
                game.dead_vamp().set(game.dead_vamp().getValue() + 1);
            }
        }

        else if(other instanceof Vampire){
            Vampire vamp = (Vampire)other;

            if(vamp.getGenre() == this.genre){
                if(this.genre == Genre.MALE){

                    if(!game.getRemovedEntities().contains(this)){
                        game.getRemovedEntities().add(this);
                        game.alive_vamp().set(game.alive_vamp().getValue() - 1);
                    }
                }
            }
            else{
                if(this.genre == Genre.FEMALE){
                    if(game.alive_vamp().getValue() < 30){
                        System.out.println("VAMP SPAWNED");
                        game.spawnEntity(game.getAddedEntities(), "model.entities.Vampire", 1,
                                (int)this.getBounds().getWidth() , (int)this.getBounds().getHeight());

                        game.alive_vamp().set(game.alive_vamp().getValue() + 1);
                    }

                }
            }
        }
    }

    public Genre getGenre(){
        return this.genre;
    }
}
