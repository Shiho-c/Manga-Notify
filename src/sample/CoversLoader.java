package sample;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;
import sample.Helper.DexHelper;
import sample.Helper.Helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CoversLoader implements Runnable {
    private VBox HomeVerticalBox;
    private Helper Helper;
    public CoversLoader(VBox HomeVerticalBox) {
        this.HomeVerticalBox = HomeVerticalBox;
        this.Helper = new Helper();

    }
    @Override
    public void run() {
        try {
            LoadCover();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LoadCover() throws IOException {
        DexHelper DexHelper = new DexHelper();
        Helper Helper = new Helper();
        ArrayList<String> MangaIDs = DexHelper.DexLatestUpdateIDs();
        int counter = 0;
        HBox hbox = new HBox();

        for (String mangaID : MangaIDs) {
            HashMap<String, String> mangaInfo = new HashMap<>();
            mangaInfo = DexHelper.ViewMangaID(mangaID, mangaInfo);

            String url = String.format("https://uploads.mangadex.org/covers/%s/%s", mangaID, mangaInfo.get("cover"));

            ImageView imageView = Helper.LoadImageFromUrl(url);
            HBox hboxCopy = hbox;
            addImageToBox(hboxCopy, imageView, counter);
            if (counter >= 5) {
                counter = 0;
                hbox = new HBox();
            }
            if(counter == 0) {addHBoxToVBox(hbox);}

            counter++;
        }
    }

    public void addImageToBox(HBox hbox, ImageView imageView, int counter) {
            Platform.runLater(new Runnable(){
            @Override
            public void run() {
                hbox.getChildren().add(imageView);
            }
        });
    }

    public void addHBoxToVBox(HBox hbox) {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                HomeVerticalBox.getChildren().add(hbox);
            }
        });
    }


}