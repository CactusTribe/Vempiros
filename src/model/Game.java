package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.BoundingBox;

import java.util.LinkedList;

/**
 * Created by cactustribe on 21/03/17.
 */
public class Game {

    private String usrname;
    private Cowboy player;
    private LinkedList<Character> vampires;
    private LinkedList<Object> objects;
    private LinkedList<Bullet> bullets;


    private IntegerProperty ARENA_WIDTH = new SimpleIntegerProperty(0);
    private IntegerProperty ARENA_HEIGHT = new SimpleIntegerProperty(0);


    public Game(String usrname){
        this.usrname = usrname;
        this.init();
    }

    public void init(){
        this.player = new Cowboy(3);
        this.player.setBounds(new BoundingBox(0,0,50,100));

        this.bullets = new LinkedList<>();
        this.objects = new LinkedList<>();
        this.generateWorld();
    }

    public void generateWorld(){
        Rock rock = new Rock();
        rock.setBounds(new BoundingBox(230,230,50,50));

        Box box1 = new Box();
        Box box2 = new Box();
        box1.setBounds(new BoundingBox(370, 120, 70,70));
        box2.setBounds(new BoundingBox(300, 300, 70,70));

        objects.add(rock);
        objects.add(box1);
        objects.add(box2);
    }

    public void movePlayer(){
        int speed = player.getSpeed();
        Direction dir = player.getDirection();
        BoundingBox old_box = player.getBounds();
        BoundingBox new_box = translateBounds(old_box, dir, speed);
        player.setBounds(new_box);
    }

    public void moveBullets(){
        for(Bullet bullet : this.bullets){

            int speed = bullet.getSpeed();
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

    public boolean moveObject(Object obj, Direction dir, int value){
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
            else{
                return false;
            }
        }
        else{
            return false;
        }
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
        BoundingBox bullet_box = null;

        switch (bullet.getDirection()){

            case NORTH:
                bullet_box = new BoundingBox(player_box.getMinX() + (player_box.getWidth() / 2),
                        player_box.getMinY(), 10, 30);
                break;
            case SOUTH:
                bullet_box = new BoundingBox(player_box.getMinX() + (player_box.getWidth() / 2),
                        player_box.getMinY() + player_box.getHeight(), 10, 30);
                break;
            case EAST:
                bullet_box = new BoundingBox(player_box.getMinX() + player_box.getWidth(),
                        player_box.getMinY() + (player_box.getHeight() / 2), 30, 10);
                break;
            case WEST:
                bullet_box = new BoundingBox(player_box.getMinX(), player_box.getMinY() + (player_box.getHeight() /
                        2), 30, 10);
                break;
        }

        bullet.setBounds(bullet_box);
        this.bullets.add(bullet);
        player.removeBullet();
    }

    public boolean outOfArena(BoundingBox box){
        BoundingBox arena = new BoundingBox(0, 0, ARENA_WIDTH.getValue(), ARENA_HEIGHT.getValue());
        return !arena.contains(box);
    }

    public boolean intersectObject(BoundingBox box){
        LinkedList<Object> intersected = new LinkedList<>();

        for(Object obj : objects){
            BoundingBox objet_box = obj.getBounds();

            if(box.intersects(objet_box)){
                intersected.add(obj);
            }
        }

        if(intersected.size() > 0){
            for(Object obj : intersected){
                int speed = player.getSpeed();
                Direction dir = player.getDirection();

                if(!moveObject(obj, dir, speed)){
                    return true;
                }
            }
        }
        else{
            return false;
        }

        return false;
    }



    public boolean isPossible(ActionType action){
        boolean isPossible = true;

        if(!player.isAlive())
            return false;

        switch (action){
            case MOVE:

                int speed = player.getSpeed();
                Direction dir = player.getDirection();
                BoundingBox old_box = player.getBounds();
                BoundingBox new_box = translateBounds(old_box, dir, speed);

                isPossible = !outOfArena(new_box) && !intersectObject(new_box);

                break;
            case SHOOT:
                    isPossible = (player.getNbBullets() > 0);
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

    public BoundingBox translateBounds(BoundingBox box, Direction dir, int value){
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

    public Character getPlayer(){
        return this.player;
    }

    public LinkedList<Bullet> getBullets(){
        return this.bullets;
    }

    public LinkedList<Object> getObjects(){
        return this.objects;
    }

    public IntegerProperty arena_width(){
        return this.ARENA_WIDTH;
    }

    public IntegerProperty arena_height(){
        return this.ARENA_HEIGHT;
    }
}
