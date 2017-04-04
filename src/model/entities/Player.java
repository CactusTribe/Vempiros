package model.entities;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.BoundingBox;
import model.Direction;
import view.entities.PlayerView;

import java.util.LinkedList;

/**
 * Created by cactustribe on 21/03/17.
 */
public class Player extends CharacterEntity {

    private DoubleProperty total_bullets;
    private DoubleProperty current_bullets;
    private NumberBinding progress_bullets;

    public Player(int lives, double speed){

        this.entityView = new PlayerView();
        this.speed = speed;
        this.total_life = lives;
        this.current_life.set(lives);
        this.setDirection(Direction.EAST );

        this.total_bullets = new SimpleDoubleProperty(20);
        this.current_bullets = new SimpleDoubleProperty(total_bullets.getValue());
        this.progress_bullets = Bindings.divide(current_bullets, total_bullets);
    }

    public boolean canMovedTo(double offset, Direction direction){
        BoundingBox new_box = game.translateBounds(bounds, direction, offset);
        LinkedList<Entity> collided = game.collidedEntities(new_box);

        if(game.outOfArena(new_box)){
            return false;
        }

        for(Entity entity : collided){
            if(entity != this){
                if(entity instanceof StaticEntity){
                    return false;
                }
                else if(entity instanceof MoveableEntity){
                    if( !((MoveableEntity) entity).canMovedBy(this) ){
                        return false;
                    }
                    else if ( !((MoveableEntity) entity).canMovedTo(offset, direction) ){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean canMovedBy(Entity entity){
        return false;
    }

    public void collidedBy(Entity other){
        if(other instanceof Vampire){
            this.removeLife(1);
        }
    }

    public void shoot(){
        Bullet bullet = new Bullet(this.direction);

        BoundingBox player_box = this.bounds;
        BoundingBox old_box = game.getBulletSchema().getBounds();
        BoundingBox bullet_box = null;

        switch (bullet.getDirection()){

            case NORTH:
                bullet_box = new BoundingBox(player_box.getMinX() + (player_box.getWidth() / 2), player_box.getMinY
                        () - old_box.getWidth(),
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
                bullet_box = new BoundingBox(player_box.getMinX()  - old_box.getWidth() , player_box.getMinY() + (player_box.getHeight() /
                        2), old_box.getWidth(), old_box.getHeight());
                break;
        }

        bullet.setBounds(bullet_box);
        bullet.setSpeed(game.getBulletSchema().getSpeed());
        bullet.setGame(this.game);
        game.getEntities().add(bullet);
        this.removeBullet();
    }

    public void addBullets(int n) {
        if(n <= total_bullets.getValue())
            this.current_bullets.set(current_bullets.getValue() + n);
        else
            this.current_bullets.set(total_bullets.getValue());
    }

    public void removeBullet(){
        if(current_bullets.getValue() > 0)
            current_bullets.set(current_bullets.getValue() - 1);
    }

    public DoubleProperty total_bullets() {
        return total_bullets;
    }

    public DoubleProperty current_bullets() {
        return current_bullets;
    }

    public NumberBinding progress_bullets() {
        return progress_bullets;
    }

}
