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
        this.player = new Cowboy(3);
        this.player.setBounds(new BoundingBox(0,0,50,100));

        this.bullets = new LinkedList<>();
        this.objects = new LinkedList<>();
        this.generateWorld();
    }

    public void generateWorld(){
        Rock rock = new Rock();
        rock.setBounds(new BoundingBox(230,230,50,50));

        Box box = new Box();
        box.setBounds(new BoundingBox(370, 120, 70,70));

        objects.add(rock);
        objects.add(box);
    }

    public void movePlayer(){

        BoundingBox old_box = player.getBounds();
        BoundingBox new_box = null;
        int speed = player.getSpeed();

        switch (player.getDirection()){

            case NORTH:
                new_box = new BoundingBox(old_box.getMinX(), old_box.getMinY() - speed, old_box.getWidth(),
                        old_box.getHeight());
                break;
            case SOUTH:
                new_box = new BoundingBox(old_box.getMinX(), old_box.getMinY() + speed, old_box.getWidth(),
                        old_box.getHeight());
                break;
            case EAST:
                new_box = new BoundingBox(old_box.getMinX() + speed, old_box.getMinY(), old_box.getWidth(),
                        old_box.getHeight());
                break;
            case WEST:
                new_box = new BoundingBox(old_box.getMinX() - speed, old_box.getMinY(), old_box.getWidth(),
                        old_box.getHeight());
                break;
        }

        player.setBounds(new_box);

    }

    public void moveBullets(){
        for(Bullet bullet : this.bullets){

            BoundingBox old_box = bullet.getBounds();
            BoundingBox new_box = null;
            int speed = bullet.getSpeed();

            switch (bullet.getDirection()){

                case NORTH:
                    new_box = new BoundingBox(old_box.getMinX(), old_box.getMinY() - speed, old_box.getWidth(),
                            old_box.getHeight());
                    break;
                case SOUTH:
                    new_box = new BoundingBox(old_box.getMinX(), old_box.getMinY() + speed, old_box.getWidth(),
                            old_box.getHeight());
                    break;
                case EAST:
                    new_box = new BoundingBox(old_box.getMinX() + speed, old_box.getMinY(), old_box.getWidth(),
                            old_box.getHeight());
                    break;
                case WEST:
                    new_box = new BoundingBox(old_box.getMinX() - speed, old_box.getMinY(), old_box.getWidth(),
                            old_box.getHeight());
                    break;
            }

            bullet.setBounds(new_box);

            if(bulletCollision(bullet)){
                bullets.remove(bullet);
                break;
            }
        }
    }

    public boolean bulletCollision(Bullet bullet){

        BoundingBox bullet_box = bullet.getBounds();

        if(outOfArena(bullet_box)){
            return true;
        }

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
        boolean intersect = false;

        for(Object obj : objects){
            if(!obj.isMoveable()){
                BoundingBox objet_box = obj.getBounds();
                intersect = box.intersects(objet_box);
            }
        }
        return intersect;
    }

    public boolean isPossible(ActionType action){
        boolean isPossible = true;

        if(!player.isAlive())
            return false;

        switch (action){
            case MOVE:
                BoundingBox old_box = player.getBounds();
                BoundingBox new_box = null;
                int speed = player.getSpeed();

                if(player.getDirection() == Direction.NORTH){
                    new_box = new BoundingBox(old_box.getMinX(), old_box.getMinY() - speed, old_box.getWidth(),
                            old_box.getHeight());
                }
                else if(player.getDirection() == Direction.SOUTH){
                    new_box = new BoundingBox(old_box.getMinX(), old_box.getMinY() + speed, old_box.getWidth(),
                            old_box.getHeight());
                }
                else if(player.getDirection() == Direction.EAST){
                    new_box = new BoundingBox(old_box.getMinX() + speed, old_box.getMinY(), old_box.getWidth(),
                            old_box.getHeight());
                }
                else if(player.getDirection() == Direction.WEST){
                    new_box = new BoundingBox(old_box.getMinX() - speed, old_box.getMinY(), old_box.getWidth(),
                            old_box.getHeight());
                }

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
