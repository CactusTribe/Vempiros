package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Created by cactustribe on 13/03/17.
 */
public class PanelLives extends HBox{

    private int nbLives;
    private int currentLife;

    private static Image HEART = new Image("file:resources/images/heart.png");
    private static Image HEART_OFF = new Image("file:resources/images/heart-off.png");

    public PanelLives(int nbLives){
        this.nbLives = nbLives;
        this.currentLife = nbLives;

        this.repaint();
    }

    public void setCurrentLife(int life){
        if(life <= nbLives && life >= 0){
            this.currentLife = life;
            this.repaint();
        }
    }

    public void repaint(){
        this.getChildren().clear();

        for(int i=0; i<nbLives; i++){
            ImageView heart;

            if(i < currentLife)
                heart = new ImageView(HEART);
            else
                heart = new ImageView(HEART_OFF);

            heart.setFitWidth(35);
            heart.setFitHeight(40);
            this.getChildren().add(heart);
        }
    }
}
