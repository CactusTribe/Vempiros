package model;

/**
 * Created by cactustribe on 24/03/17.
 */
public abstract class Object {

    protected int OFFSET_X = 0;
    protected int OFFSET_Y = 0;
    protected int SIZE_W = 0;
    protected int SIZE_H = 0;
    protected boolean moveable;


    public boolean isMoveable(){
        return this.moveable;
    }

    public void setMoveable(boolean bool){
        this.moveable = bool;
    }

    public void setBounds(int w, int h){
        this.SIZE_W = w;
        this.SIZE_H = h;
    }

    public void setPosition(int x, int y){
        this.OFFSET_X = x;
        this.OFFSET_Y = y;
    }

    public int getX() {
        return this.OFFSET_X;
    }

    public int getY() {
        return this.OFFSET_Y;
    }

    public int getWidth() {
        return this.SIZE_W;
    }

    public int getHeight() {
        return this.SIZE_H;
    }

}
