package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.BoundingBox;
import model.entities.*;
import sun.awt.image.ImageWatched;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by cactustribe on 21/03/17.
 */
public class Game {

    private Player player;
    private LinkedList<Entity> entities;
    private LinkedList<Entity> removed_entities;

    private Bullet bulletSchema;

    private int NB_VAMP;
    private int NB_ROCK;
    private int NB_BOX;

    private IntegerProperty ALIVE_VAMP;
    private IntegerProperty DEAD_VAMP;

    private DoubleProperty ARENA_WIDTH = new SimpleDoubleProperty(820);
    private DoubleProperty ARENA_HEIGHT = new SimpleDoubleProperty(550);


    public Game(){

    }

    public void init(){
        this.player = new Player(3, 10);
        this.removed_entities = new LinkedList<>();
        this.entities = new LinkedList<>();
        this.bulletSchema = new Bullet(Direction.EAST);
        this.bulletSchema.setBounds(new BoundingBox(0,0,30,10));

        this.NB_VAMP = 4;
        this.NB_ROCK = 6;
        this.NB_BOX = 4;

        ALIVE_VAMP = new SimpleIntegerProperty(NB_VAMP);
        DEAD_VAMP = new SimpleIntegerProperty(0);

        this.generateWorld();
    }

    public void generateWorld(){

        int SIZE_BOX = 70;
        int SIZE_ROCK = 50;

        int SIZE_VAMP_W = 50;
        int SIZE_VAMP_H = 80;

        Random rand = new Random();

        // ROCK
        for(int i=0; i < NB_ROCK; i++){
            int MAX_X = (int)(ARENA_WIDTH.getValue() - SIZE_ROCK);
            int MAX_Y = (int)(ARENA_HEIGHT.getValue() - SIZE_ROCK);
            BoundingBox box;

            do{
                int x = rand.nextInt(MAX_X);
                int y = rand.nextInt(MAX_Y);
                box = new BoundingBox(x, y, SIZE_ROCK, SIZE_ROCK);
            }while(intersectObject(box));

            Rock rock = new Rock();
            rock.setBounds(box);
            rock.setGame(this);
            entities.add(rock);
        }

        // BOX
        for(int i=0; i < NB_BOX; i++){
            int MAX_X = (int)(ARENA_WIDTH.getValue() - SIZE_BOX);
            int MAX_Y = (int)(ARENA_HEIGHT.getValue() - SIZE_BOX);
            BoundingBox box;

            do{
                int x = rand.nextInt(MAX_X);
                int y = rand.nextInt(MAX_Y);
                box = new BoundingBox(x, y, SIZE_BOX, SIZE_BOX);
            }while(intersectObject(box));

            Box b = new Box();
            b.setBounds(box);
            b.setGame(this);
            entities.add(b);
        }

        // VAMP
        for(int i=0; i < NB_VAMP; i++){
            int MAX_X = (int)(ARENA_WIDTH.getValue() - SIZE_VAMP_W);
            int MAX_Y = (int)(ARENA_HEIGHT.getValue() - SIZE_VAMP_H);
            BoundingBox box;

            do{
                int x = rand.nextInt(MAX_X);
                int y = rand.nextInt(MAX_Y);
                box = new BoundingBox(x, y, SIZE_VAMP_W, SIZE_VAMP_H);
            }while(intersectObject(box));

            Vampire v = new Vampire();
            v.setBounds(box);
            v.setGame(this);
            entities.add(v);
        }


        // PLAYER
        int MAX_X = (int)(ARENA_WIDTH.getValue() - 50);
        int MAX_Y = (int)(ARENA_HEIGHT.getValue() - 100);
        BoundingBox box_player;

        do{
            int x = rand.nextInt(MAX_X);
            int y = rand.nextInt(MAX_Y);
            box_player = new BoundingBox(x, y, 50, 100);
        }while(intersectObject(box_player) || intersectVampires(box_player));

        this.player.setBounds(box_player);
        this.player.setGame(this);
    }

    public void moveEntities(){
        for(Entity entity : entities){

            if(entity instanceof Bullet){
                Bullet bullet = (Bullet)entity;
                BoundingBox new_box = translateBounds(bullet.getBounds(), bullet.getDirection(), bullet.getSpeed());

                if(outOfArena(new_box)){
                    this.removed_entities.add(entity);
                }
                else{
                    bullet.move(bullet.getSpeed());
                    objectCollision(entity);
                }

            }
        }

        for(Entity entity : removed_entities){
            entities.remove(entity);
        }
    }

    /*
    public void moveVampires(){
        for(Entity entity : this.entities){

            if(entity instanceof Vampire){
                Vampire vampire = (Vampire)entity;

                Random rand = new Random();
                boolean intersect_object = false;

                LinkedList<Direction> possible_dir = new LinkedList<>();
                possible_dir.add(Direction.NORTH);
                possible_dir.add(Direction.SOUTH);
                possible_dir.add(Direction.EAST);
                possible_dir.add(Direction.WEST);

                double speed = vampire.getSpeed();
                Direction dir = vampire.getDirection();
                BoundingBox old_box = vampire.getBounds();
                BoundingBox new_box = translateBounds(old_box, dir, speed);

                if(outOfArena(new_box) || intersectObject(new_box)){
                    intersect_object = true;
                    possible_dir.remove(dir);

                }else if(new_box.intersects(player.getBounds())){
                    intersect_object = true;
                    possible_dir.remove(dir);
                    player.removeLife(1);
                }

                while(intersect_object){

                    intersect_object = false;
                    int random_dir = rand.nextInt(possible_dir.size());
                    dir = possible_dir.get(random_dir);
                    new_box = translateBounds(old_box, dir, speed);

                    if(outOfArena(new_box) || intersectObject(new_box)){
                        intersect_object = true;
                        possible_dir.remove(dir);
                    }
                }

                vampire.setBounds(new_box);
                vampire.setDirection(dir);
            }
        }
    }
*/


    public boolean intersectVampires(BoundingBox box){
        for(Entity entity : entities){
            BoundingBox vamp_box = entity.getBounds();
            if(box.intersects(vamp_box)){ return true; }
        }
        return false;
    }

    public boolean intersectObject(BoundingBox box){
        for(Entity entity : entities){
            BoundingBox objet_box = entity.getBounds();
            if(box.intersects(objet_box)){ return true; }
        }
        return false;
    }


    public void objectCollision(Entity current){
        for(Entity entity : entities){
            if(entity != current){

                if(current.collidesWith(entity)){
                    if(entity instanceof MoveableEntity && current instanceof MoveableEntity){
                        if(((MoveableEntity) entity).canMovedBy(current)){
                            ((MoveableEntity)entity).setDirection(((MoveableEntity)current).getDirection());
                            ((MoveableEntity)entity).move(((MoveableEntity)current).getSpeed());
                        }
                    }
                    entity.collidedBy(current);
                }
            }
        }
    }

    public LinkedList<Entity> collidedEntities(BoundingBox box){
        LinkedList<Entity> collided = new LinkedList<>();

        for(Entity entity : entities){
            if(box.intersects(entity.getBounds())){
                collided.add(entity);
            }
        }
        return collided;
    }


    public boolean outOfArena(BoundingBox box){
        BoundingBox arena = new BoundingBox(0, 0, ARENA_WIDTH.getValue(), ARENA_HEIGHT.getValue());
        return !arena.contains(box);
    }


    public void apply(ActionType action) throws Exception{

        if(!player.isAlive())
            throw new Exception("Vous êtes mort.");

        switch (action){

            case MOVE:

                if(player.canMovedTo(player.getSpeed(), player.getDirection())){
                    player.move(player.getSpeed());
                    objectCollision(player);
                }
                else{
                    throw new Exception("Mouvement impossible.");
                }

                break;

            case SHOOT:
                if(player.current_bullets().getValue() > 0) {
                    player.shoot();
                }
                else {
                    throw new Exception("Pas assez de munitions.");
                }
                break;
        }
    }

    public BoundingBox translateBounds(BoundingBox box, Direction dir, double value){
        BoundingBox new_box = null;
        switch (dir){
            case NORTH:
                new_box = new BoundingBox(box.getMinX(), box.getMinY() - value, box.getWidth(),
                        box.getHeight());
                break;
            case SOUTH:
                new_box = new BoundingBox(box.getMinX(), box.getMinY() + value, box.getWidth(),
                        box.getHeight());
                break;
            case EAST:
                new_box = new BoundingBox(box.getMinX() + value, box.getMinY(), box.getWidth(),
                        box.getHeight());
                break;
            case WEST:
                new_box = new BoundingBox(box.getMinX() - value, box.getMinY(), box.getWidth(),
                        box.getHeight());
                break;
        }
        return new_box;
    }

    public void resizeBoundingBox(double ratio){
        double new_X, new_Y, new_W, new_H;

        if(player != null){
            BoundingBox old_box = player.getBounds();

            new_X = old_box.getMinX() * ratio;
            new_Y = old_box.getMinY() * ratio;
            new_W = old_box.getWidth() * ratio;
            new_H = old_box.getHeight() * ratio;

            BoundingBox new_box = new BoundingBox(new_X, new_Y, new_W, new_H);
            player.setBounds(new_box);
            player.setSpeed(player.getSpeed() * ratio);
        }

        if(entities != null){
            for(Entity entity : this.entities){
                BoundingBox old_box = entity.getBounds();

                new_X = old_box.getMinX() * ratio;
                new_Y = old_box.getMinY() * ratio;
                new_W = old_box.getWidth() * ratio;
                new_H = old_box.getHeight() * ratio;

                BoundingBox new_box = new BoundingBox(new_X, new_Y, new_W, new_H);
                entity.setBounds(new_box);

                if(entity instanceof MoveableEntity){
                    ((MoveableEntity)entity).setSpeed(((MoveableEntity)entity).getSpeed() * ratio);
                }
            }
        }

        // MODIFICATION DU SCHEMA DE LA BALLE
        this.bulletSchema.setBounds(new BoundingBox(0,0,
                bulletSchema.getBounds().getWidth() * ratio, bulletSchema.getBounds().getHeight() * ratio));
        this.bulletSchema.setSpeed(bulletSchema.getSpeed() * ratio);
    }

    public Player getPlayer(){
        return this.player;
    }

    public LinkedList<Entity> getRemovedEntities(){
        return this.removed_entities;
    }

    public LinkedList<Entity> getEntities(){
        return this.entities;
    }

    public DoubleProperty arena_width(){
        return this.ARENA_WIDTH;
    }

    public DoubleProperty arena_height(){
        return this.ARENA_HEIGHT;
    }

    public IntegerProperty alive_vamp(){
        return this.ALIVE_VAMP;
    }

    public IntegerProperty dead_vamp(){
        return this.DEAD_VAMP;
    }

    public Bullet getBulletSchema(){
        return this.bulletSchema;
    }

}
