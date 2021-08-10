package sample.Tasks;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import okhttp3.OkHttpClient;
import org.json.JSONObject;
import sample.Helper.DexHelper;
import sample.Helper.Helper;
import sample.Main;
import sample.Scene2Controller;

import java.io.IOException;
import java.util.HashMap;

public class ParseRandom extends Task<Void> {
    private final Helper Helper = new Helper();
    private final DexHelper DexHelper = new DexHelper();

    @Override
    protected Void call() throws Exception {
        String url = "https://api.mangadex.org/manga/random";
        JSONObject result = Helper.SendGetRequest(url);
        HashMap manga_info = DexHelper.RandomMangaParser(result);
        Scene2Controller.getInstance().SetRandomMangaInfo(manga_info);

        return null;
    }
}