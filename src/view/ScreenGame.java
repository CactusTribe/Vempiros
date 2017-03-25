package view;

import controller.GameController;
import controller.ScreenController;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.*;
import model.Character;
import model.Object;

import java.util.LinkedList;

/**
 * Created by cactustribe on 12/03/17.
 */
public class ScreenGame extends Screen{

    private BorderPane borderPane;
    private TextField cheat_console;
    private GameController gameController;

    public MenuBar menubar;
    public Pane arena;
    public CharacterView playerView;
    public boolean debug = true;


    public ScreenGame(String usrname){

        borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundImage(new Image("images/sand1.jpg"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        this.getChildren().addAll(borderPane);

        menubar = new MenuBar();

        arena = new Pane();
        cheat_console = new TextField();
        cheat_console.setFocusTraversable(false);

        borderPane.setTop(menubar);
        borderPane.setCenter(arena);
        borderPane.setBottom(cheat_console);

        playerView = new CowboyView();
        playerView.setFocusTraversable(true);

        screenController = new ScreenController();
        gameController = new GameController();
        gameController.newGame(usrname, this);


        menubar.getButton_menu().setOnAction(
                event -> {
                    this.screenController.screensController.setScreen("menu");
                }
        );

        playerView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                System.out.println("Key Pressed: " + ke.getCode());
                gameController.notifyEvent(ke);

                playerView.requestFocus();
            }
        });

        playerView.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                System.out.println("Key Released: " + ke.getCode());
                gameController.notifyEvent(ke);

                playerView.requestFocus();
            }
        });

        cheat_console.setOnKeyPressed(
                (event) -> {
                    if (event.getCode().equals(KeyCode.ENTER))
                        {
                            gameController.cheatCode(cheat_console.getText());
                            cheat_console.clear();
                            playerView.requestFocus();
                        }

        });

    }

    public void update(Game game){
        arena.getChildren().clear();

        // AFFICHAGE DES OBJETS (ROCK, BOX, etc)
        LinkedList<Object> objects = game.getObjects();
        for(Object obj : objects){

            ObjectView objview = null;

            if(obj instanceof Rock) {
                objview = new RockView();
            }
            else if(obj instanceof Box){
                objview = new BoxView();
            }


            objview.setLayoutX(obj.getBounds().getMinX()-(objview.width()/2)+(obj.getBounds().getWidth() / 2));
            objview.setLayoutY(obj.getBounds().getMinY()-(objview.height()/2)+(obj.getBounds().getHeight() / 2));
            arena.getChildren().add(objview);

            if(debug) {
                Pane obj_box = new Pane();
                obj_box.setLayoutX(obj.getBounds().getMinX());
                obj_box.setLayoutY(obj.getBounds().getMinY());
                obj_box.setPrefWidth(obj.getBounds().getWidth());
                obj_box.setPrefHeight(obj.getBounds().getHeight());

                if(obj.isMoveable()){
                    obj_box.setBorder(new Border(new BorderStroke(Color.GREENYELLOW,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                }
                else{
                    obj_box.setBorder(new Border(new BorderStroke(Color.BLUE,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                }

                objview.setBorder(new Border(new BorderStroke(Color.PURPLE,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                arena.getChildren().add(obj_box);
            }

        }

        // AFFICHAGE DU JOUEUR
        Character player = game.getPlayer();
        if(!player.isAlive()){
            playerView.setAnimation(CharacterView.Animations.DEAD, null);
        }
        playerView.setLayoutX(player.getBounds().getMinX()-(playerView.getWidth()/2)+(player.getBounds().getWidth() / 2));
        playerView.setLayoutY(player.getBounds().getMinY()-(playerView.getHeight()/2)+(player.getBounds().getHeight() / 2));

        if(debug){
            Pane player_box = new Pane();
            player_box.setLayoutX(player.getBounds().getMinX());
            player_box.setLayoutY(player.getBounds().getMinY());
            player_box.setPrefWidth(player.getBounds().getWidth());
            player_box.setPrefHeight(player.getBounds().getHeight());
            player_box.setBorder(new Border(new BorderStroke(Color.BLUE,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

            arena.getChildren().add(player_box);

            playerView.setBorder(new Border(new BorderStroke(Color.PURPLE,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
        else{
            playerView.setBorder(null);
        }


        // AFFICHAGE DES BALLES
        LinkedList<Bullet> listeBullets = game.getBullets();
        for(Bullet bullet : listeBullets){
            BulletView bview = new BulletView(bullet.getDirection());
            bview.setLayoutX(bullet.getBounds().getMinX()-(bview.width()/2)+(bullet.getBounds().getWidth() / 2));
            bview.setLayoutY(bullet.getBounds().getMinY()-(bview.height()/2)+(bullet.getBounds().getHeight() / 2));
            arena.getChildren().add(bview);

            if(debug) {
                Pane bullet_box = new Pane();
                bullet_box.setLayoutX(bullet.getBounds().getMinX());
                bullet_box.setLayoutY(bullet.getBounds().getMinY());
                bullet_box.setPrefWidth(bullet.getBounds().getWidth());
                bullet_box.setPrefHeight(bullet.getBounds().getHeight());
                bullet_box.setBorder(new Border(new BorderStroke(Color.BLUE,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                arena.getChildren().add(bullet_box);


                bview.setBorder(new Border(new BorderStroke(Color.PURPLE,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

            }
        }


        arena.getChildren().add(playerView);
        //playerView.requestFocus();
    }

    public void displayError(String err){
        System.out.println(err);
    }
}
