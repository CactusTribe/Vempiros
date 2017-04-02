package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.BoundingBox;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by cactustribe on 21/03/17.
 */
public class Game {

    private Cowboy player;
    private LinkedList<Vampire> vampires;
    private LinkedList<Object> objects;
    private LinkedList<Bullet> bullets;


    private Bullet bulletSchema;


    private DoubleProperty ARENA_WIDTH = new SimpleDoubleProperty(820);
    private DoubleProperty ARENA_HEIGHT = new SimpleDoubleProperty(550);


    public Game(){

    }

    public void init(){
        this.player = new Cowboy(3);
        this.bullets = new LinkedList<>();
        this.objects = new LinkedList<>();
        this.vampires = new LinkedList<>();
        this.generateWorld();
        this.bulletSchema = new Bullet();
    }

    public void generateWorld(){
        int NB_BOX = 4;
        int NB_ROCK = 6;
        int NB_VAMP = 1;

        int SIZE_BOX = 70;
        int SIZE_ROCK = 50;

        int SIZE_VAMP_W = 50;
        int SIZE_VAMP_H = 80;

        Random rand = new Random();

        // ROCK
        for(int i=0; i < NB_ROCK; i++){
            int MAX_X = (int)(ARENA_WIDTH.getValue() - SIZE_ROCK);
            int MAX_Y = (int)(ARENA_HEIGHT.getValue() - SIZE_ROCK);
            BoundingBox box = null;

            do{
                int x = rand.nextInt(MAX_X);
                int y = rand.nextInt(MAX_Y);
                box = new BoundingBox(x, y, SIZE_ROCK, SIZE_ROCK);
            }while(intersectObject(box));

            Rock rock = new Rock();
            rock.setBounds(box);
            objects.add(rock);
        }

        // BOX
        for(int i=0; i < NB_BOX; i++){
            int MAX_X = (int)(ARENA_WIDTH.getValue() - SIZE_BOX);
            int MAX_Y = (int)(ARENA_HEIGHT.getValue() - SIZE_BOX);
            BoundingBox box = null;

            do{
                int x = rand.nextInt(MAX_X);
                int y = rand.nextInt(MAX_Y);
                box = new BoundingBox(x, y, SIZE_BOX, SIZE_BOX);
            }while(intersectObject(box));

            Box b = new Box();
            b.setBounds(box);
            objects.add(b);
        }

        // VAMP
        for(int i=0; i < NB_VAMP; i++){
            int MAX_X = (int)(ARENA_WIDTH.getValue() - SIZE_VAMP_W);
            int MAX_Y = (int)(ARENA_HEIGHT.getValue() - SIZE_VAMP_H);
            BoundingBox box = null;

            do{
                int x = rand.nextInt(MAX_X);
                int y = rand.nextInt(MAX_Y);
                box = new BoundingBox(x, y, SIZE_VAMP_W, SIZE_VAMP_H);
            }while(intersectObject(box));

            Vampire v = new Vampire();
            v.setBounds(box);
            vampires.add(v);
        }


        // PLAYER
        int MAX_X = (int)(ARENA_WIDTH.getValue() - 50);
        int MAX_Y = (int)(ARENA_HEIGHT.getValue() - 100);
        BoundingBox box_player = null;
        do{
            int x = rand.nextInt(MAX_X);
            int y = rand.nextInt(MAX_Y);
            box_player = new BoundingBox(x, y, 50, 100);
        }while(intersectObject(box_player));

        this.player.setBounds(box_player);
    }

    public void movePlayer(){
        double speed = player.getSpeed();
        Direction dir = player.getDirection();
        BoundingBox old_box = player.getBounds();
        BoundingBox new_box = translateBounds(old_box, dir, speed);
        player.setBounds(new_box);
    }

    public void moveBullets(){
        for(Bullet bullet : this.bullets){

            double speed = bullet.getSpeed();
            Direction dir = bullet.getDirection();
            BoundingBox old_box = bullet.getBounds();
            BoundingBox new_box = translateBounds(old_box, dir, speed);

            bullet.setBounds(new_box);

            if(bulletCollision(bullet)){
                bullets.remove(bullet);
                break;
            }
        }
    }

    public void moveVampires(){
        for(Vampire vampire : this.vampires){

            Random rand = new Random();
            boolean intersect_object = false;

            double speed = vampire.getSpeed();
            Direction dir = vampire.getDirection();
            BoundingBox old_box = vampire.getBounds();
            BoundingBox new_box = translateBounds(old_box, dir, speed);


            if(!outOfArena(new_box)){
                for(Object cur_obj : objects){
                    if(new_box.intersects(cur_obj.getBounds())){
                        intersect_object = true;
                        break;
                    }
                }
            }else{
                intersect_object = true;
            }

            while(intersect_object){

                intersect_object = false;
                int random_dir = rand.nextInt(4);
                dir = Direction.values()[random_dir];
                new_box = translateBounds(old_box, dir, speed);

                if(!outOfArena(new_box)){
                    for(Object cur_obj : objects){
                        if(new_box.intersects(cur_obj.getBounds())){
                            intersect_object = true;
                            break;
                        }
                    }
                }else{
                    intersect_object = true;
                }
            }

            vampire.setBounds(new_box);
            vampire.setDirection(dir);
        }
    }

    public boolean moveObject(Object obj, Direction dir, double value){
        if(obj.isMoveable()){
            BoundingBox old_box = obj.getBounds();
            BoundingBox new_box = translateBounds(old_box, dir, value);

            if(!outOfArena(new_box)){
                for(Object cur_obj : objects){
                    if(cur_obj != obj && new_box.intersects(cur_obj.getBounds())){
                        return false;
                    }
                }
                obj.setBounds(new_box);
                return true;
            }
            else { return false; }
        }
        else { return false; }
    }

    public boolean bulletCollision(Bullet bullet){
        BoundingBox bullet_box = bullet.getBounds();

        if(outOfArena(bullet_box)) { return true; }

        for(Object obj : objects){
            BoundingBox objet_box = obj.getBounds();
            if(bullet_box.intersects(objet_box)){
                return true;
            }
        }
        return false;
    }

    public void shoot(){
        Bullet bullet = new Bullet();
        bullet.setDirection(player.getDirection());

        BoundingBox player_box = player.getBounds();
        BoundingBox old_box = bulletSchema.getBounds();
        BoundingBox bullet_box = null;

        switch (bullet.getDirection()){

            case NORTH:
                bullet_box = new BoundingBox(player_box.getMinX() + (player_box.getWidth() / 2), player_box.getMinY(),
                        old_box.getHeight(), old_box.getWidth());
                break;
            case SOUTH:
                bullet_box = new BoundingBox(player_box.getMinX() + (player_box.getWidth() / 2),
                        player_box.getMinY() + player_box.getHeight(),
                        old_box.getHeight(), old_box.getWidth());
                break;
            case EAST:
                bullet_box = new BoundingBox(player_box.getMinX() + player_box.getWidth(),
                        player_box.getMinY() + (player_box.getHeight() / 2),
                        old_box.getWidth(), old_box.getHeight());
                break;
            case WEST:
                bullet_box = new BoundingBox(player_box.getMinX(), player_box.getMinY() + (player_box.getHeight() /
                        2), old_box.getWidth(), old_box.getHeight());
                break;
        }

        bullet.setBounds(bullet_box);
        bullet.setSpeed(bulletSchema.getSpeed());
        this.bullets.add(bullet);
        player.removeBullet();
    }

    public boolean outOfArena(BoundingBox box){
        BoundingBox arena = new BoundingBox(0, 0, ARENA_WIDTH.getValue(), ARENA_HEIGHT.getValue());
        return !arena.contains(box);
    }

    public boolean intersectObject(BoundingBox box){
        for(Object obj : objects){
            BoundingBox objet_box = obj.getBounds();

            if(box.intersects(objet_box)){
                return true;
            }
        }
        return false;
    }

    public boolean objectCollision(BoundingBox box){
        LinkedList<Object> intersected = new LinkedList<>();

        for(Object obj : objects){
            BoundingBox objet_box = obj.getBounds();

            if(box.intersects(objet_box)){
                intersected.add(obj);
            }
        }

        if(intersected.size() > 0){
            for(Object obj : intersected){
                double speed = player.getSpeed();
                Direction dir = player.getDirection();

                if(!moveObject(obj, dir, speed)){
                    return true;
                }
            }
        }
        else{ return false; }
        return false;
    }


    public boolean isPossible(ActionType action){
        boolean isPossible = true;

        if(!player.isAlive())
            return false;

        switch (action){
            case MOVE:

                double speed = player.getSpeed();
                Direction dir = player.getDirection();
                BoundingBox old_box = player.getBounds();
                BoundingBox new_box = translateBounds(old_box, dir, speed);

                isPossible = !outOfArena(new_box) && !objectCollision(new_box);

                break;
            case SHOOT:
                    isPossible = (player.current_bullets().getValue() > 0);
                break;
        }
        return isPossible;
    }

    public void apply(ActionType action) throws Exception{

        switch (action){
            case MOVE:
                if(isPossible(action))
                    movePlayer();
                else throw new Exception("Mouvement impossible.");
                break;
            case SHOOT:
                if(isPossible(action))
                    shoot();
                else throw new Exception("Pas assez de munitions.");
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

        if(objects != null){
            for(Object obj : this.objects){
                BoundingBox old_box = obj.getBounds();

                new_X = old_box.getMinX() * ratio;
                new_Y = old_box.getMinY() * ratio;
                new_W = old_box.getWidth() * ratio;
                new_H = old_box.getHeight() * ratio;

                BoundingBox new_box = new BoundingBox(new_X, new_Y, new_W, new_H);
                obj.setBounds(new_box);
            }
        }

        if(vampires != null){
            for(Vampire vamp : this.vampires){
                BoundingBox old_box = vamp.getBounds();

                new_X = old_box.getMinX() * ratio;
                new_Y = old_box.getMinY() * ratio;
                new_W = old_box.getWidth() * ratio;
                new_H = old_box.getHeight() * ratio;

                BoundingBox new_box = new BoundingBox(new_X, new_Y, new_W, new_H);
                vamp.setBounds(new_box);
                vamp.setSpeed(vamp.getSpeed() * ratio);
            }
        }

        if(bullets != null){
            for(Bullet bullet : this.bullets){
                BoundingBox old_box = bullet.getBounds();

                new_X = old_box.getMinX() * ratio;
                new_Y = old_box.getMinY() * ratio;
                new_W = old_box.getWidth() * ratio;
                new_H = old_box.getHeight() * ratio;

                BoundingBox new_box = new BoundingBox(new_X, new_Y, new_W, new_H);
                bullet.setBounds(new_box);
                bullet.setSpeed(bullet.getSpeed() * ratio);
            }
        }


        // MODIFICATION DU SCHEMA DE LA BALLE
        this.bulletSchema.setBounds(new BoundingBox(0,0,
                bulletSchema.getBounds().getWidth() * ratio, bulletSchema.getBounds().getHeight() * ratio));
        this.bulletSchema.setSpeed(bulletSchema.getSpeed() * ratio);
    }

    public Character getPlayer(){
        return this.player;
    }

    public LinkedList<Bullet> getBullets(){
        return this.bullets;
    }

    public LinkedList<Object> getObjects(){
        return this.objects;
    }

    public LinkedList<Vampire> getVampires(){
        return this.vampires;
    }

    public DoubleProperty arena_width(){
        return this.ARENA_WIDTH;
    }

    public DoubleProperty arena_height(){
        return this.ARENA_HEIGHT;
    }

}
