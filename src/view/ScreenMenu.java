package view;

import controller.GameController;
import controller.ScreenController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by cactustribe on 12/03/17.
 */
public class ScreenMenu extends Screen{

    public ScreenMenu(){
        this.screenController = new ScreenController();

        VBox vbox = new VBox();
        vbox.setMaxSize(500, 400);

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.2f, 0.2f, 0.2f));

        ImageView background = new ImageView(new Image("images/first_screen.jpg"));
        background.setOpacity(0.8);

        Text title = new Text("Pistolero VS Vampiros");
        //title.setFont(Font.loadFont("file:resources/fonts/something_strange.ttf", 80));
        title.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/something_strange.ttf"), 80));
        title.setFill(Color.CRIMSON);
        title.setStroke(Color.BLACK);
        title.setStrokeWidth(2);
        title.setEffect(ds);


        Text label_pseudo = new Text("Your name : ");
        label_pseudo.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/something_strange.ttf"), 30));
        label_pseudo.setFill(Color.CRIMSON);
        label_pseudo.setStroke(Color.BLACK);
        label_pseudo.setStrokeWidth(1);
        label_pseudo.setEffect(ds);

        TextField field_pseudo = new TextField();

        HBox name_form = new HBox();
        name_form.getChildren().addAll(label_pseudo, field_pseudo);
        name_form.setAlignment(Pos.CENTER);

        Button button_play = new Button("START GAME");
        button_play.setMinSize(350, 50);
        button_play.setId("button-start");

        vbox.getChildren().addAll(title, name_form, button_play);
        vbox.setAlignment(Pos.CENTER);
        VBox.setMargin(name_form, new Insets(100, 0, 0, 0));
        VBox.setMargin(button_play, new Insets(50, 0, 0, 0));

        StackPane.setAlignment(vbox, Pos.CENTER);

        this.getChildren().addAll(background, vbox);

        button_play.setOnAction(
                event -> {

                    ScreenGame screenGame = (ScreenGame)this.screenController.screensController.getScreen("game");
                    GameController controller = new GameController(field_pseudo.getText(), screenGame);

                    this.screenController.screensController.setScreen("game");
                    screenGame.bindController(controller);

                });

        field_pseudo.setOnKeyPressed(
                (event) -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {


                        ScreenGame screenGame = (ScreenGame)this.screenController.screensController.getScreen("game");
                        GameController controller = new GameController(field_pseudo.getText(), screenGame);

                        this.screenController.screensController.setScreen("game");
                        screenGame.bindController(controller);

                    }
                });
    }

}
