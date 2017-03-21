package model;

/**
 * Created by cactustribe on 20/03/17.
 */
public abstract class Character {

    protected int OFFSET_X;
    protected int OFFSET_Y;
    protected int total_life;
    protected int current_life;
    protected Direction direction;

    public void setPosition(int x, int y){
        this.OFFSET_X = x;
        this.OFFSET_Y = y;
    }

    public void setDirection(Direction dir){
        this.direction = dir;
    }

    public void addLife(int n){
        if(current_life + n <= total_life)
            current_life += n;
        else
            current_life = total_life;
    }

    public void removeLife(int n){
        if(n <= current_life)
            current_life -= n;
        else
            current_life = 0;
    }

    public boolean isAlive(){
        return (current_life > 0);
    }

    public int getX(){
        return this.OFFSET_X;
    }

    public int getY(){
        return this.OFFSET_Y;
    }

}
