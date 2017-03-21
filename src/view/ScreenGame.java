package view;

import controller.GameController;
import controller.ScreenController;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import model.Character;

/**
 * Created by cactustribe on 12/03/17.
 */
public class ScreenGame extends Screen{

    private BorderPane borderPane;
    private MenuBar menubar;
    private Pane arena;
    private GameController gameController;
    public CharacterView playerView;



    public ScreenGame(String usrname){

        borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundImage(new Image("file:resources/images/sand1.jpg"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

        playerView = new CowboyView();
        menubar = new MenuBar();
        arena = new Pane();
        borderPane.setTop(menubar);
        borderPane.setCenter(arena);
        this.getChildren().addAll(borderPane);

        this.screenController = new ScreenController();
        this.gameController = new GameController();
        this.gameController.newGame(usrname, this);

        System.out.println("New player : " + usrname);

        arena.getChildren().addAll(playerView);

        menubar.getButton_menu().setOnAction(
                event -> {
                    this.screenController.screensController.setScreen("menu");
                }
        );


        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                System.out.println("Key Pressed: " + ke.getCode());
                gameController.notifyEvent(ke);
            }
        });

        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                System.out.println("Key Released: " + ke.getCode());
                gameController.notifyEvent(ke);
            }
        });

    }

    public void update(){
        Character player = gameController.getGame().getPlayer();
        playerView.setLayoutX(player.getX());
        playerView.setLayoutY(player.getY());


        this.requestFocus();

    }
}
