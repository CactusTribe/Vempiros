package view;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by cactustribe on 13/04/17.
 */
public class ConfigDialog extends Dialog<HashMap<String, KeyCode>>{

    private GridPane grid;
    private LinkedHashMap<String, TextField> fields_liste;
    private LinkedHashMap<String, KeyCode> keycode_liste;

    public ConfigDialog(){
        super();
        this.setTitle("Configuration");
        this.setHeaderText("Choose your bindings.");
        this.setGraphic(new ImageView("images/options.png"));
        this.fields_liste = new LinkedHashMap<>();
        this.keycode_liste = new LinkedHashMap<>();

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        this.grid = new GridPane();
        this.grid.setHgap(10);
        this.grid.setVgap(10);
        this.grid.setPadding(new Insets(20, 150, 10, 10));
        this.getDialogPane().setContent(grid);

        this.readBindingsFile();
        this.buildForm();

        this.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {

                for(HashMap.Entry<String, TextField> entry : fields_liste.entrySet()){
                    TextField field  = entry.getValue();

                    if(!field.getText().equals("") && !field.getText().equals(field.getPromptText())){
                        this.keycode_liste.put(entry.getKey(), KeyCode.getKeyCode(field.getText()));
                    }
                }

                return this.keycode_liste;
            }
            return null;
        });

        Optional<HashMap<String, KeyCode>> result = this.showAndWait();
        result.ifPresent(list_config -> {

            String text = "";

            for(HashMap.Entry<String, KeyCode> entry : list_config.entrySet()){
                text += String.format("%s %s\n", entry.getKey(), entry.getValue().getName());
            }

            try{
                Files.write(Paths.get("resources/bindings"), text.getBytes());
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public void readBindingsFile(){

        try (Stream<String> stream = Files.lines(Paths.get("resources/bindings"))) {
            List<String> list = stream.sequential().collect(Collectors.toList());

            for(String line : list){
                String[] args = line.split(" ");
                if( KeyCode.getKeyCode(args[1]) != null)
                    keycode_liste.put(args[0],  KeyCode.getKeyCode(args[1]));
            }

            System.out.println(String.format(" -> Read %d binds", keycode_liste.size()));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void buildForm(){

        int count = 0;
        for(HashMap.Entry<String, KeyCode> entry : keycode_liste.entrySet()){
            TextField field = new TextField();
            field.setPromptText(entry.getValue().getName());
            fields_liste.put(entry.getKey(), field);
            grid.add(new Label(entry.getKey()), 0, count);
            grid.add(field, 1, count);


            field.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    field.clear();
                    field.setText(event.getCode().getName());
                    Platform.runLater(() -> grid.requestFocus());
                }
            });
            count++;
        }
    }

}
