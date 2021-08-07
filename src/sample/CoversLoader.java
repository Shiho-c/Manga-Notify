package sample;


import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.HashMap;
import java.util.HashSet;
import sample.Helper.DexHelper;
import sample.Helper.Helper;

public class CoversLoader extends Task<Void> {
    private VBox HomeVerticalBox;
    private HashSet<String> MangaIDs;
    private DexHelper DexHelper;
    private Helper Helper;

    public CoversLoader(VBox HomeVerticalBox, HashSet<String> MangaIDs) {
        this.HomeVerticalBox = HomeVerticalBox;
        this.MangaIDs = MangaIDs;
    }

    @Override protected Void call () throws Exception {
        DexHelper = new DexHelper();
        Helper = new Helper();
        int counter = 0;
        HBox hbox = new HBox();
        addHBoxToVBox(hbox);
        System.out.println(this.MangaIDs.size());
        for(String mangaID: this.MangaIDs) {
            HashMap<String, String> mangaInfo = new HashMap<>();
            mangaInfo = DexHelper.ViewMangaID(mangaID, mangaInfo);
            String url = String.format("https://uploads.mangadex.org/covers/%s/%s", mangaID, mangaInfo.get("cover"));
            //ImageView imageView = new ImageView();
            //imageView = Helper.LoadImageFromUrl(imageView, url);
            Image image = new Image(url, 130, 190, true, true, true);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(190);
            imageView.setFitWidth(130);
            imageView.preserveRatioProperty();

            SetCoversToHBox(hbox, imageView);
            //hbox.getChildren().add(imageView);
            if (counter >= 6) { counter = 0; addHBoxToVBox(hbox); hbox = new HBox(); }
            //if(counter == 0) { ; }

            counter++;

        }
        return null;

    }

    public void addHBoxToVBox(HBox hbox) {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                HomeVerticalBox.getChildren().add(hbox);
            }
        });
    }

    public void SetCoversToHBox(HBox hbox, ImageView imageView) {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                hbox.getChildren().add(imageView);
            }
        });
    }


}