package sample;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.HashMap;
import sample.Helper.DexHelper;
import sample.Helper.Helper;

public class CoversLoader extends Task<Void> {
    private HBox HomeHorizontalBox;
    private ArrayList<String> MangaIDs;
    private DexHelper DexHelper;
    private Helper Helper;

    public CoversLoader(HBox HomeVerticalBox, ArrayList<String> MangaIDs) {
        this.HomeHorizontalBox = HomeVerticalBox;
        this.MangaIDs = MangaIDs;
    }

    @Override protected Void call () throws Exception {
        DexHelper = new DexHelper();
        Helper = new Helper();
        int counter = 0;
        VBox vbox = new VBox();
        System.out.println(this.MangaIDs.size());
        for(String mangaID: this.MangaIDs) {
            counter++;
            HashMap<String, String> mangaInfo = new HashMap<>();
            mangaInfo = DexHelper.ViewMangaID(mangaID, mangaInfo);
            String url = String.format("https://uploads.mangadex.org/covers/%s/%s", mangaID, mangaInfo.get("cover"));
            ImageView imageView = new ImageView();
            imageView = Helper.LoadImageFromUrl(imageView, url, 130, 190);

            Label label = new Label(mangaInfo.get("title"));
            vbox.getChildren().add(label);


            SetCoversToHBox(vbox, imageView);
            //hbox.getChildren().add(imageView);

            //if(counter == 0) { ; }
            if (counter % 6 == 0 ) { addHBoxToVBox(vbox); vbox = new VBox(); }


        }
        return null;

    }

    public void addHBoxToVBox(VBox vbox) {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {

                HomeHorizontalBox.getChildren().add(vbox);
            }
        });
    }

    public void SetCoversToHBox(VBox vbox, ImageView imageView) {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                vbox.getChildren().add(imageView);
            }
        });
    }


}