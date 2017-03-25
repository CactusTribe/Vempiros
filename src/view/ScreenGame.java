package view;

import controller.GameController;
import controller.ScreenController;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
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

    private String usrname;
    private BorderPane borderPane;
    private TextField cheat_console;
    private GameController gameController;

    public MenuBar menubar;
    public Pane arena;
    public CharacterView playerView;
    public boolean debug = true;


    public ScreenGame(String usrname){
        this.usrname = usrname;

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

        menubar.getButton_menu().setOnAction(
                event -> {
                    this.screenController.screensController.setScreen("menu");
                }
        );

        menubar.getButton_play().setOnAction(
                event -> {
                    gameController.newGame(this.usrname, this);
                    playerView.requestFocus();
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

            BoundingBox obj_box = obj.getBounds();
            objview.setLayoutX(obj_box.getMinX()-(objview.width()/2)+(obj_box.getWidth() / 2));
            objview.setLayoutY(obj_box.getMinY()-(objview.height()/2)+(obj_box.getHeight() / 2));
            arena.getChildren().add(objview);

            if(debug) {
                if(obj.isMoveable()){
                    showCollisionBox(obj.getBounds(), Color.GREENYELLOW);
                }
                else{
                    showCollisionBox(obj.getBounds(), Color.RED);
                }
            }

        }

        // AFFICHAGE DU JOUEUR
        Character player = game.getPlayer();
        if(!player.isAlive()){
            playerView.setAnimation(CharacterView.Animations.DEAD, null);
        }
        BoundingBox player_box = player.getBounds();
        playerView.setLayoutX(player_box.getMinX()-(playerView.getWidth()/2)+(player_box.getWidth()/ 2));
        playerView.setLayoutY(player_box.getMinY()-(playerView.getHeight()/2)+(player_box.getHeight()/ 2)-10);

        if(debug){
            showCollisionBox(player.getBounds(), Color.BLUE);
        }


        // AFFICHAGE DES BALLES
        LinkedList<Bullet> listeBullets = game.getBullets();
        for(Bullet bullet : listeBullets){
            BulletView bview = new BulletView(bullet.getDirection());
            bview.setLayoutX(bullet.getBounds().getMinX()-(bview.width()/2)+(bullet.getBounds().getWidth() / 2));
            bview.setLayoutY(bullet.getBounds().getMinY()-(bview.height()/2)+(bullet.getBounds().getHeight() / 2));
            arena.getChildren().add(bview);

            if(debug) {
                showCollisionBox(bullet.getBounds(), Color.BLUE);
            }
        }

        arena.getChildren().add(playerView);
        //playerView.requestFocus();
    }

    public void showCollisionBox(BoundingBox box, Color color){
        Pane pane = new Pane();
        pane.setLayoutX(box.getMinX());
        pane.setLayoutY(box.getMinY());
        pane.setPrefWidth(box.getWidth());
        pane.setPrefHeight(box.getHeight());
        pane.setBorder(new Border(new BorderStroke(color,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        arena.getChildren().add(pane);
    }

    public GameController getGameController(){
        return this.gameController;
    }

    public void displayError(String err){
        System.out.println(err);
    }
}
