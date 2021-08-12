package sample.Tasks;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.Helper.DexHelper;
import sample.Helper.Helper;

import java.util.ArrayList;
import java.util.HashMap;

public class CoversLoader extends Task<Void> {
    private final VBox HomeVerticalBox;
    private final ArrayList<String> MangaIDs;
    private final DexHelper DexHelper = new DexHelper();
    private final Helper Helper = new Helper();
    public CoversLoader(VBox HomeVerticalBox, ArrayList<String> MangaIDs) {
        this.HomeVerticalBox = HomeVerticalBox;
        this.MangaIDs = MangaIDs;
    }

    @Override
    protected Void call() throws Exception {

        int counter = 1;
        HBox coverHorizontalBox = new HBox();

        HBox titleHorizontalBox = new HBox();

        HashMap<String, String> mangaInfo;
        ImageView imageView;

        for (String mangaID : this.MangaIDs) {
            if (counter % 7 == 0) {
                titleHorizontalBox = new HBox(15);
                coverHorizontalBox = new HBox(15);
                addHBoxToVBox(coverHorizontalBox, titleHorizontalBox);

            }
            mangaInfo = DexHelper.ViewMangaID(mangaID);
            String coverUrl = String.format("https://uploads.mangadex.org/covers/%s/%s", mangaID, mangaInfo.get("cover"));
            System.out.println("replacing cover");
            mangaInfo.replace("cover", coverUrl);
            System.out.println("creating image");
            Image image = new Image(coverUrl, 130, 280, false, true, true);
            System.out.println("setting image view");
            imageView = new ImageView(image);
            System.out.println("setting imageview size");
            //imageView.setFitWidth(130);
            //imageView.setFitHeight(280);
            //imageView.setPreserveRatio(true);
            System.out.println("adding titles");
            SetTitlesToHBox(titleHorizontalBox, mangaInfo, image);
            System.out.println("adding covers");
            SetCoversToHBox(coverHorizontalBox, imageView);
            System.out.println("next pls");
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


    public void SetTitlesToHBox(HBox hbox, HashMap mangaInfo, Image image) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label titleLabel = new Label((String) mangaInfo.get("title"));
                Helper.SetMangaButtonAction(titleLabel, mangaInfo, image);
                titleLabel.setAlignment(Pos.CENTER);
                titleLabel.setPrefWidth(130);
                hbox.getChildren().add(titleLabel);
            }
        });
    }


}