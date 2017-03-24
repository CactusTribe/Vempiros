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
import sun.awt.image.ImageWatched;

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
            ObjectView objview;

            if(obj instanceof Rock){
                objview = new RockView();
                objview.setSize(obj.getWidth(), obj.getWidth());
                objview.setLayoutX(obj.getX());
                objview.setLayoutY(obj.getY());

                if(debug) {
                    objview.setBorder(new Border(new BorderStroke(Color.PURPLE,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                }
                arena.getChildren().add(objview);
            }
        }

        // AFFICHAGE DU JOUEUR
        Character player = game.getPlayer();
        if(!player.isAlive()){
            playerView.setAnimation(CharacterView.Animations.DEAD, null);
        }
        playerView.setLayoutX(player.getBounds().getMinX());
        playerView.setLayoutY(player.getBounds().getMinY());

        if(debug){
            playerView.setBorder(new Border(new BorderStroke(Color.BLUE,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
        else{
            playerView.setBorder(null);
        }


        // AFFICHAGE DES BALLES
        LinkedList<Bullet> listeBullets = game.getBullets();
        for(Bullet bullet : listeBullets){
            BulletView bview = new BulletView(bullet.getDirection());
            bview.setLayoutX(bullet.getX());
            bview.setLayoutY(bullet.getY());
            arena.getChildren().add(bview);
        }


        arena.getChildren().add(playerView);
        //playerView.requestFocus();
    }

    public void displayError(String err){
        System.out.println(err);
    }
}
