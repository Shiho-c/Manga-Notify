package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sample.Helper.DexHelper;
import sample.Helper.Helper;
import sample.Tasks.ParseRandom;

import java.util.ArrayList;
import java.util.Random;

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

    private static Scene2Controller instance;
    public Scene2Controller() {
        instance = this;
    }
    // static method to get instance of view
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
    public void setLabel(String labelText){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                RandomTitle.setText(labelText);
            }
        });
    }

    public void setRandomThumbnail(String cover_url) {
        Image image = new Image(cover_url, 333, 371, true, true, true);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                RandomThumbnail.setImage(image);

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




