package sample;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONObject;
import sample.Helper.DexHelper;
import sample.Helper.Helper;

import java.io.IOException;
import java.util.HashMap;

public class CoversLoader implements Runnable {
    private VBox HomeVerticalBox;
    public CoversLoader(VBox HomeVerticalBox) {
        this.HomeVerticalBox = HomeVerticalBox;

    }
    @Override
    public void run() {
        try {
            LoadCover();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject LoadCover() throws IOException {
        DexHelper DexHelper = new DexHelper();
        Helper Helper = new Helper();
        JSONObject MangaInfo = DexHelper.DexLatestUpdates();
        int counter = 0;
        HBox hbox = new HBox();

        for (int i = 0; i < MangaInfo.names().length(); i++) {
            System.out.println(i + " out of " + MangaInfo.names().length());
            if (counter >= 5) {
                counter = 0;
                hbox = new HBox();
            }
            String title = MangaInfo.names().getString(i);
            JSONObject titleJson = (JSONObject) MangaInfo.get(title);
            HashMap<String, String> Manga = getMangaInfo(titleJson);
            String url = String.format("https://uploads.mangadex.org/covers/%s/%s", Manga.get("id"), Manga.get("cover"));

            ImageView imageView = Helper.LoadImageFromUrl(url);
            HBox hboxCopy = hbox;
            addImageToBox(hboxCopy, imageView);
            if(counter == 0) {
                addHBoxToVBox(hboxCopy);
            }
            counter++;
        }
        JSONObject obje = new JSONObject();
        return obje;
    }

    public HashMap<String, String> getMangaInfo(JSONObject manga) {
        HashMap<String, String> mangaInfo = new HashMap<>();

        String id = manga.get("id").toString();
        String cover = manga.get("cover").toString();
        mangaInfo.put("id", id);
        mangaInfo.put("cover", cover);
        return mangaInfo;
    }
    public void addImageToBox(HBox hbox, ImageView imageView) {
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