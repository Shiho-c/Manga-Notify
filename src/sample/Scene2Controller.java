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
import javafx.concurrent.Task;

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

        //JSONObject MangaInfo = DexHelper.DexLatestUpdates();

        Shit task = new Shit(HomeVerticalBox);
        new Thread(task).start();
        /*
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

            ImageView imageView = Helper.LoadImageFromUrl(url);

            hbox.getChildren().add(imageView);
            counter ++;
            */
    }
}




