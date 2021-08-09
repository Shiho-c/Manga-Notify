package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.Helper.DexHelper;
import sample.Helper.Helper;

import java.util.ArrayList;
import java.util.HashMap;

public class CoversLoader extends Task<Void> {
    private VBox HomeVerticalBox;
    private ArrayList<String> MangaIDs;
    private sample.Helper.DexHelper DexHelper;
    private sample.Helper.Helper Helper;

    public CoversLoader(VBox HomeVerticalBox, ArrayList<String> MangaIDs) {
        this.HomeVerticalBox = HomeVerticalBox;
        this.MangaIDs = MangaIDs;
    }

    @Override
    protected Void call() throws Exception {
        DexHelper = new DexHelper();
        Helper = new Helper();
        int counter = 1;
        HBox coverHorizontalBox = new HBox();
        ;
        HBox titleHorizontalBox = new HBox();
        ;
        HashMap<String, String> mangaInfo;
        for (String mangaID : this.MangaIDs) {
            if (counter % 7 == 0) {
                titleHorizontalBox = new HBox(5);
                coverHorizontalBox = new HBox(5);
                addHBoxToVBox(coverHorizontalBox, titleHorizontalBox);

            }
            mangaInfo = DexHelper.ViewMangaID(mangaID);
            String url = String.format("https://uploads.mangadex.org/covers/%s/%s", mangaID, mangaInfo.get("cover"));
            ImageView imageView = new ImageView();
            imageView = Helper.LoadImageFromUrl(imageView, url, 130, 190);
            SetTitlesToHBox(titleHorizontalBox, mangaInfo.get("title"));
            SetCoversToHBox(coverHorizontalBox, imageView);
            counter++;

        }
        return null;

    }

    public void addHBoxToVBox(HBox hbox, HBox title) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                HomeVerticalBox.getChildren().add(title);
                HomeVerticalBox.getChildren().add(hbox);
            }
        });
    }

    public void SetCoversToHBox(HBox hbox, ImageView imageView) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                hbox.getChildren().add(imageView);
            }
        });
    }

    public void SetTitlesToHBox(HBox hbox, String title) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label titleLabel = new Label(title);
                titleLabel.setMinWidth(130);
                hbox.getChildren().add(titleLabel);
            }
        });
    }


}