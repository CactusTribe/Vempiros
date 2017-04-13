package model.entities;

import common.Sounds;
import javafx.scene.media.AudioClip;
import model.Modification;
import view.entities.VampireFView;
import view.entities.VampireMView;

import java.nio.file.Paths;
import java.util.Random;

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

            game.getModifications().add(new Modification(Modification.ModificationType.REMOVE, other));

            if(!game.entityModified(this)){
                game.getModifications().add(new Modification(Modification.ModificationType.KILL, this));
            }
        }

        else if(other instanceof Vampire){
            Vampire vamp = (Vampire)other;

            if(vamp.getGenre() == this.genre){
                if(this.genre == Genre.MALE){

                    if(!game.entityModified(this)){
                        game.getModifications().add(new Modification(Modification.ModificationType.REMOVE, this));
                    }
                }
            }
            else{
                if(this.genre == Genre.FEMALE){
                    System.out.println("VAMP SPAWNED");
                    game.getModifications().add(new Modification(Modification.ModificationType.SPAWN, new Vampire()));
                }
            }
        }
    }

    public Genre getGenre(){
        return this.genre;
    }
}
