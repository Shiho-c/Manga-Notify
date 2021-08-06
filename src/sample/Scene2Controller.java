package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONObject;
import sample.Helper.DexHelper;
import sample.Helper.Helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.ArrayList;
public class Scene2Controller {
    private sample.Helper.DexHelper DexHelper;
    private sample.Helper.Helper Helper;

    @FXML
    private VBox HomeVerticalBox;

    @FXML
    private void initialize() throws IOException {
        DexHelper = new DexHelper();
        Helper = new Helper();

        JSONObject MangaInfo = DexHelper.DexLatestUpdates();
        int counter = 0;
        HBox hbox = new HBox();
        for (int i = 0; i < MangaInfo.names().length(); i++) {
            System.out.println(i + " out of " + MangaInfo.names().length());
            if(counter >= 5) {
                counter = 0;
                HomeVerticalBox.getChildren().add(hbox);
                hbox = new HBox();
            }
            String title = MangaInfo.names().getString(i);
            JSONObject titleJson = (JSONObject) MangaInfo.get(title);
            String id = titleJson.get("id").toString();
            String cover = titleJson.get("cover").toString();
            String url = String.format("https://uploads.mangadex.org/covers/%s/%s", id, cover);

            ImageView imageView = new ImageView(new Image(url));
            imageView.setCache(true);
            imageView.setFitHeight(300);
            imageView.setFitWidth(150);
            hbox.getChildren().add(imageView);
            counter ++;
        }

        /*
        System.out.println("Shit" + titles);
        for(String t:titles) {
            Label b = new Label(t);
            System.out.println(t);
            HomeVerticalBox.getChildren().add(b);
        }*/
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
