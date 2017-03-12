package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by cactustribe on 12/03/17.
 */
public class ScreenMenu extends StackPane{

    public ScreenMenu(){

        BorderPane menu_screen = new BorderPane();

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.2f, 0.2f, 0.2f));

        Image first_screen = new Image("file:resources/images/first_screen.jpg");
        ImageView background = new ImageView(first_screen);
        background.setOpacity(0.8);

        Text title = new Text(0, 50, "Pistolero VS Vampiros");
        title.setFont(Font.loadFont("file:resources/fonts/something_strange.ttf", 80));
        title.setFill(Color.CRIMSON);
        title.setStroke(Color.BLACK);
        title.setStrokeWidth(2);
        title.setEffect(ds);

        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(80,12,12,12));
        menu_screen.setTop(title);


        StackPane form = new StackPane();

        FlowPane flow = new FlowPane();
        flow.setVgap(20);
        flow.setMaxWidth(350);
        flow.setMaxHeight(300);

        Text label_pseudo = new Text("Your name : ");
        label_pseudo.setFont(Font.loadFont("file:resources/fonts/something_strange.ttf", 30));
        label_pseudo.setFill(Color.CRIMSON);
        label_pseudo.setStroke(Color.BLACK);
        label_pseudo.setStrokeWidth(1);
        label_pseudo.setEffect(ds);

        TextField field_pseudo = new TextField();
        Button button_play = new Button("PLAY");
        button_play.setMinWidth(315);
        button_play.setMinHeight(50);

        flow.getChildren().addAll(label_pseudo, field_pseudo, button_play);

        form.getChildren().add(flow);
        menu_screen.setCenter(form);

        this.getChildren().addAll(background, menu_screen);

    }


}
