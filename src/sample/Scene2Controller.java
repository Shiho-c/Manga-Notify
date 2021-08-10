package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sample.Helper.DexHelper;
import sample.Helper.Helper;
import sample.Tasks.CoversLoader;
import sample.Tasks.ParseRandom;

import java.util.ArrayList;
import java.util.HashMap;

public class Scene2Controller {
    private sample.Helper.DexHelper DexHelper;
    private sample.Helper.Helper Helper;
    private ParseRandom Randomize;
    @FXML
    private VBox HomeVerticalBox;
    @FXML
    private AnchorPane RandomWindow;
    @FXML
    private AnchorPane LatestWindow;
    @FXML
    private ImageView RandomThumbnail;
    @FXML
    private Label RandomTitle;
    @FXML
    private Label RandomDescription;

    private static Scene2Controller instance;
    public Scene2Controller() {
        instance = this;
    }
    public static Scene2Controller getInstance() {
        return instance;
    }

    public void RandomManga() {
        Helper.HidePane(LatestWindow);
        Helper.ShowPane(RandomWindow);
        Helper.StartThread(Randomize);
    }

    public void HomeManga() {
        Helper.HidePane(RandomWindow);
        Helper.ShowPane(LatestWindow);
    }
    public void SetRandomMangaInfo(HashMap<String, String> mangaInfo ){
        Image image = new Image(mangaInfo.get("cover"), 333, 371, true, true, true);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                RandomTitle.setText(mangaInfo.get("title"));
                RandomDescription.setText("Plot: " + mangaInfo.get("description"));
                System.out.println("Plot: " + mangaInfo.get("description"));
                RandomThumbnail.setImage(image);
                RandomThumbnail.setFitHeight(371);
                RandomThumbnail.setFitHeight(333);
            }
        });
    }

    @FXML
    private void initialize() throws Exception {
        DexHelper = new DexHelper();
        Helper = new Helper();
        Randomize = new ParseRandom();
        ArrayList<String> MangaIDs = DexHelper.DexLatestUpdateIDs();
        CoversLoader LoadCovers = new CoversLoader(HomeVerticalBox, MangaIDs);
        Helper.StartThread(LoadCovers);
    }
}




