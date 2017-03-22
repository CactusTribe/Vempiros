package view;

import controller.GameController;
import controller.ScreenController;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import model.Character;

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


    public ScreenGame(String usrname){

        borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundImage(new Image("file:resources/images/sand1.jpg"),
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
        arena.getChildren().addAll(playerView);

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
                //System.out.println("Key Pressed: " + ke.getCode());
                gameController.notifyEvent(ke);

                playerView.requestFocus();
            }
        });

        playerView.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                //System.out.println("Key Released: " + ke.getCode());
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

    public void update(){
        Character player = gameController.getGame().getPlayer();
        playerView.setLayoutX(player.getX());
        playerView.setLayoutY(player.getY());

        playerView.requestFocus();
    }

    public void displayError(String err){
        System.out.println(err);
    }
}
