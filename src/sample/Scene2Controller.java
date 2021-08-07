package sample;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.Helper.DexHelper;
import sample.Helper.Helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Scene2Controller {
    private sample.Helper.DexHelper DexHelper;
    private sample.Helper.Helper Helper;

    @FXML
    private HBox HomeHorizontalBox;

    @FXML
    private void initialize() throws Exception {
        DexHelper = new DexHelper();
        Helper = new Helper();
        ArrayList<String> MangaIDs = DexHelper.DexLatestUpdateIDs();
        CoversLoader LoadCovers = new CoversLoader(HomeHorizontalBox, MangaIDs);
        Thread th = new Thread(LoadCovers);
        th.setDaemon(true);
        th.start();
    }
}




