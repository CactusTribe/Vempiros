package model;

/**
 * Created by cactustribe on 23/03/17.
 */
public class Bullet {

    private int OFFSET_X = 0;
    private int OFFSET_Y = 0;
    private int SIZE_W = 0;
    private int SIZE_H = 0;
    private Direction direction;
    private int SPEED = 10;

    public Bullet(){
        this.setBounds(30,10);
    }

    public void setBounds(int w, int h){
        this.SIZE_W = w;
        this.SIZE_H = h;
    }

    public void setPosition(int x, int y){
        this.OFFSET_X = x;
        this.OFFSET_Y = y;
    }

    public void setDirection(Direction dir){
        this.direction = dir;
    }

    public int getX(){
        return this.OFFSET_X;
    }

    public int getY(){
        return this.OFFSET_Y;
    }

    public Direction getDirection(){
        return this.direction;
    }

    public int getWidth(){
        return SIZE_W;
    }

    public int getHeight(){
        return SIZE_H;
    }

    public int getSpeed(){
        return this.SPEED;
    }
}
