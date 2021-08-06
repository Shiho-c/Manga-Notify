package sample;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONObject;
import sample.Helper.DexHelper;
import sample.Helper.Helper;

import java.io.IOException;

public class Shit implements Runnable {
    private VBox HomeVerticalBox;
    public Shit(VBox HomeVerticalBox) {
        this.HomeVerticalBox = HomeVerticalBox;

    }
    @Override
    public void run() {
        try {
            task1();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject task1() throws IOException {
        DexHelper DexHelper = new DexHelper();
        Helper Helper = new Helper();
        JSONObject MangaInfo = DexHelper.DexLatestUpdates();
        int counter = 0;
        HBox hbox = new HBox();

        for (int i = 0; i < MangaInfo.names().length(); i++) {
            System.out.println(i + " out of " + MangaInfo.names().length());
            if (counter >= 5) {
                counter = 0;
                HBox finalHbox = hbox;
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        HomeVerticalBox.getChildren().add(finalHbox);
                    }
                });

                hbox = new HBox();
            }
            String title = MangaInfo.names().getString(i);
            JSONObject titleJson = (JSONObject) MangaInfo.get(title);
            String id = titleJson.get("id").toString();
            String cover = titleJson.get("cover").toString();
            String url = String.format("https://uploads.mangadex.org/covers/%s/%s", id, cover);

            ImageView imageView = Helper.LoadImageFromUrl(url);

            hbox.getChildren().add(imageView);
            counter++;
        }
        JSONObject obje = new JSONObject();
        return obje;
    }
}