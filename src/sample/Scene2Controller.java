package sample;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import sample.Helper.DexHelper;
import sample.Helper.Helper;

import java.io.IOException;

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

        CoversLoader task = new CoversLoader(HomeVerticalBox);
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




