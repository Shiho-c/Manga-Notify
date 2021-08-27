package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sample.Helper.DexHelper;
import sample.Helper.Helper;
import sample.Tasks.CoversLoader;
import sample.Tasks.ParseRandom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Scene2Controller {
    private sample.Helper.DexHelper DexHelper;
    private sample.Helper.Helper Helper;
    private ParseRandom Randomize;
    @FXML
    private VBox HomeVerticalBox;
    @FXML
    private ScrollPane MangaWindow;
    @FXML
    private AnchorPane LatestWindow;
    @FXML
    private ImageView MangaThumbnail;
    @FXML
    private Label MangaTitle;
    @FXML
    private Label MangaDescription;
    @FXML
    private Label MangaGenres;
    @FXML
    private Label MangaStatus;
    @FXML
    private VBox ChaptersBox;

    private static Scene2Controller instance;
    public Scene2Controller() {
        instance = this;
    }
    public static Scene2Controller getInstance() {
        return instance;
    }

    public void RandomManga() {
        Randomize = new ParseRandom();
        Helper.StartThread(Randomize);
    }

    public void HomeManga() {
        Helper.HidePane(MangaWindow);
        Helper.ShowPane(LatestWindow);
    }
    public void SetMangaInfo(HashMap<String, String> mangaInfo, Image image ) throws IOException{
        ArrayList<String> Chapters = DexHelper.GetMangaChapters(mangaInfo.get("id"));

        Helper.HidePane(LatestWindow);
        Helper.ShowPane(MangaWindow);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                SetThumbnail(image);
                SetChapters(Chapters);
                MangaTitle.setText(mangaInfo.get("title"));
                MangaGenres.setText("Genres: " + mangaInfo.get("tags"));
                MangaStatus.setText("Status: " + mangaInfo.get("status"));
                MangaDescription.setText("Plot: " + mangaInfo.get("description"));
            }
        });
    }

    private void SetChapters(ArrayList<String> Chapters) {
        ChaptersBox.getChildren().clear();
        for(String chap: Chapters) {
            Label chapterLabel = new Label();
            chapterLabel.setText(chap);
            ChaptersBox.getChildren().add(chapterLabel);
        }
    }
    private void SetThumbnail(Image image) {
        String url  = image.getUrl();
        System.out.println(url);
        Image image_ = new Image(url, true);
        MangaThumbnail.setImage(image_);
        MangaThumbnail.setPreserveRatio(true);
    }

    @FXML
    private void initialize() throws Exception {
        DexHelper = new DexHelper();
        Helper = new Helper();
        ArrayList<String> MangaIDs = DexHelper.DexLatestUpdateIDs();
        CoversLoader LoadCovers = new CoversLoader(HomeVerticalBox, MangaIDs);
        Helper.StartThread(LoadCovers);
    }
}




