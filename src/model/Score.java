package model;

/**
 * Created by cactustribe on 18/04/17.
 */
public class Score implements Comparable<Score>{

    private String name;
    private int value;

    public Score(String name, int value){
        this.name = name;
        this.value = value;
    }

    public String getName(){
        return this.name;
    }

    public Integer getValue(){
        return this.value;
    }

    public int compareTo(Score score)
    {
        return getValue().compareTo(score.getValue());
    }
}
