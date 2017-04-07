package model.entities;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.BoundingBox;
import model.Direction;
import view.entities.PlayerView;

/**
 * Created by cactustribe on 21/03/17.
 */
public class Player extends CharacterEntity {

    private DoubleProperty total_bullets;
    private DoubleProperty current_bullets;
    private NumberBinding progress_bullets;

    private long time_last_hit = 0;
    private double TIME_BEFORE_HIT = 500; //ms

    public Player(){
        this.speed = 10;
        this.total_life = 3;

        this.current_life.set(this.total_life);
        this.setDirection(Direction.EAST);
        this.entityView = new PlayerView();

        this.total_bullets = new SimpleDoubleProperty(20);
        this.current_bullets = new SimpleDoubleProperty(total_bullets.getValue());
        this.progress_bullets = Bindings.divide(current_bullets, total_bullets);
    }


    public boolean canMovedBy(Entity entity){
        return false;
    }

    public void collidedBy(Entity other){
        if(other instanceof Vampire){

            if(time_last_hit == 0 || (System.currentTimeMillis() - time_last_hit >= TIME_BEFORE_HIT) ) {
                this.removeLife(1);
                time_last_hit = System.currentTimeMillis();
            }
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

        if(bullet.move(bullet.getSpeed(), bullet.getDirection())){
            game.getEntities().add(bullet);
        }

        System.out.println(" >> Shoot !");
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
