package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.Helper.DexHelper;

import java.io.IOException;
import java.util.Objects;
import java.util.ArrayList;
public class Scene2Controller {
    private sample.Helper.DexHelper DexHelper;
    @FXML
    private VBox HomeVerticalBox;

    @FXML
    private void initialize() throws IOException {
        DexHelper = new DexHelper();
        ArrayList<String> titles = DexHelper.DexLatestUpdates();
        System.out.println("Shit" + titles);
        for(String t:titles) {
            Label b = new Label(t);
            System.out.println(t);
            HomeVerticalBox.getChildren().add(b);
        }
        /*
        try {
            for(int i = 0; i < 10; i ++) {
                HBox hbox = new HBox();
                for (int a = 0; a < 10; a++) {
                    ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Resources/shit.jpg"))));
                    imageView.setCache(true);
                    imageView.setFitHeight(300);
                    imageView.setFitWidth(150);
                    hbox.getChildren().add(imageView);
                }
                HomeVerticalBox.getChildren().add(hbox);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/


    }

}
