package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sample.Helper.DexHelper;
import sample.Helper.Helper;

import java.util.ArrayList;

public class Scene2Controller {
    private sample.Helper.DexHelper DexHelper;
    private sample.Helper.Helper Helper;

    @FXML
    private VBox HomeVerticalBox;
    @FXML
    private Button HomeButton;

    @FXML
    private AnchorPane RandomWindow;
    @FXML
    private AnchorPane LatestWindow;

    public void RandomManga() {
        System.out.println("ra");
        Helper.HidePane(LatestWindow);
        Helper.ShowPane(RandomWindow);
    }

    public void HomeManga() {
        System.out.println("al");
        Helper.HidePane(RandomWindow);
        Helper.ShowPane(LatestWindow);
    }


    @FXML
    private void initialize() throws Exception {
        DexHelper = new DexHelper();
        Helper = new Helper();
        ArrayList<String> MangaIDs = DexHelper.DexLatestUpdateIDs();
        CoversLoader LoadCovers = new CoversLoader(HomeVerticalBox, MangaIDs);
        Thread th = new Thread(LoadCovers);
        th.setDaemon(true);
        th.start();
    }
}




