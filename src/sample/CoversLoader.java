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
    private VBox HomeVerticalBox;
    private ArrayList<String> MangaIDs;
    private DexHelper DexHelper;
    private Helper Helper;

    public CoversLoader(VBox HomeVerticalBox, ArrayList<String> MangaIDs) {
        this.HomeVerticalBox = HomeVerticalBox;
        this.MangaIDs = MangaIDs;
    }

    @Override protected Void call () throws Exception {
        DexHelper = new DexHelper();
        Helper = new Helper();
        int counter = 1;
        HBox coverHorizontalBox = new HBox();
        HBox titleHorizontalBox = new HBox();
        addHBoxToVBox(coverHorizontalBox, titleHorizontalBox);
        for(String mangaID: this.MangaIDs) {
            HashMap<String, String> mangaInfo = new HashMap<>();
            mangaInfo = DexHelper.ViewMangaID(mangaID, mangaInfo);
            String url = String.format("https://uploads.mangadex.org/covers/%s/%s", mangaID, mangaInfo.get("cover"));
            ImageView imageView = new ImageView();
            imageView = Helper.LoadImageFromUrl(imageView, url, 130, 190);
            SetTitlesToHBox(titleHorizontalBox, mangaInfo.get("title"));
            SetCoversToHBox(coverHorizontalBox, imageView);
            if (counter % 7 == 0 ) {
                if(!(HomeVerticalBox.getChildren().contains(coverHorizontalBox))) {
                    addHBoxToVBox(coverHorizontalBox, titleHorizontalBox);
                }
                titleHorizontalBox = new HBox();
                coverHorizontalBox = new HBox();
            }
            counter++;

        }
        return null;

    }

    public void addHBoxToVBox(HBox hbox, HBox title) {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                HomeVerticalBox.getChildren().add(title);
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

    public void SetTitlesToHBox(HBox hbox, String title) {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                Label titleLabel = new Label(title);
                hbox.getChildren().add(titleLabel);
            }
        });
    }


}