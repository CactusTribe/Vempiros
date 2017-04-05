package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.BoundingBox;
import model.entities.*;
import view.entities.AnimatedView;
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

    private int NB_VAMP = 5;

    private IntegerProperty ALIVE_VAMP;
    private IntegerProperty DEAD_VAMP;

    private DoubleProperty ARENA_WIDTH = new SimpleDoubleProperty(820);
    private DoubleProperty ARENA_HEIGHT = new SimpleDoubleProperty(550);


    public Game(){

    }

    public void init(){
        this.removed_entities = new LinkedList<>();
        this.entities = new LinkedList<>();
        this.bulletSchema = new Bullet(Direction.EAST);
        this.bulletSchema.setBounds(new BoundingBox(0,0,30,10));

        ALIVE_VAMP = new SimpleIntegerProperty(NB_VAMP);
        DEAD_VAMP = new SimpleIntegerProperty(0);

        this.spawnEntity("model.entities.Rock", 6, 50, 50);
        this.spawnEntity("model.entities.Box", 5, 50, 50);
        this.spawnEntity("model.entities.Vampire", NB_VAMP, 40, 60);
        this.spawnEntity("model.entities.Player", 1, 40, 80);
    }

    public void spawnEntity(String entity_name, int number, int width, int height){
        Random rand = new Random();

        for(int i=0; i < number; i++){
            int MAX_X = (int)(ARENA_WIDTH.getValue() - width);
            int MAX_Y = (int)(ARENA_HEIGHT.getValue() - height);
            BoundingBox box;

            do{
                int x = rand.nextInt(MAX_X);
                int y = rand.nextInt(MAX_Y);
                box = new BoundingBox(x, y, width, height);
            }while(intersectEntity(box));

            try{
                Entity object =  (Entity) Class.forName(entity_name).newInstance();

                object.setBounds(box);
                object.setGame(this);

                if(object instanceof MoveableEntity){
                    int random_dir = rand.nextInt(Direction.values().length);
                    Direction new_dir = Direction.values()[random_dir];
                    ((MoveableEntity)object).setDirection(new_dir);


                    if(object instanceof CharacterEntity){
                        ((AnimatedView)object.getEntityView()).setAnimation(AnimatedView.Animations.WALK, (
                                (CharacterEntity)object)
                                .getDirection());

                        if(object instanceof Player){
                            this.player = (Player) object;
                        }
                    }
                }
                entities.add(object);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void moveEntities(){
        for(Entity entity : entities){

            if(entity instanceof Bullet){
                Bullet bullet = (Bullet)entity;
                if(!bullet.move(bullet.getSpeed(), bullet.getDirection())){
                    removed_entities.add(entity);
                }
            }

            else if(entity instanceof Vampire){
                Vampire vamp = (Vampire)entity;
                Random rand = new Random();
                int random_dir;

                LinkedList<Direction> possible_dir = new LinkedList<>();
                possible_dir.add(Direction.NORTH);
                possible_dir.add(Direction.SOUTH);
                possible_dir.add(Direction.EAST);
                possible_dir.add(Direction.WEST);

                while(!vamp.move(vamp.getSpeed(), vamp.getDirection())){
                    possible_dir.remove(vamp.getDirection());
                    random_dir = rand.nextInt(possible_dir.size());
                    Direction new_dir = possible_dir.get(random_dir);
                    vamp.setDirection(new_dir);
                    ((AnimatedView)vamp.getEntityView()).setAnimation(AnimatedView.Animations.WALK, vamp.getDirection());
                }
            }
        }

        for(Entity entity : removed_entities){
            entities.remove(entity);
        }

        removed_entities.clear();
    }


    public boolean intersectEntity(BoundingBox box){
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
                    //System.out.println(String.format("%s collide with %s", entity.getClass().getName(), current
                    //        .getClass().getName()));
                    entity.collidedBy(current);
                    current.collidedBy(entity);
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

        if(!player.isAlive()){
            throw new Exception("Vous êtes mort.");
        }

        switch (action){

            case MOVE:
                if(!player.move(player.getSpeed(), player.getDirection())){
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
