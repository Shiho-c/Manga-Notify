package sample.Tasks;

import javafx.concurrent.Task;
import javafx.scene.image.Image;
import org.json.JSONObject;
import sample.Helper.DexHelper;
import sample.Helper.Helper;
import sample.Scene2Controller;

import java.util.HashMap;

public class ParseRandom extends Task<Void> {
    private final Helper Helper = new Helper();
    private final DexHelper DexHelper = new DexHelper();

    @Override
    protected Void call() throws Exception {
        String url = "https://api.mangadex.org/manga/random";
        JSONObject result = Helper.SendGetRequest(url);

        HashMap mangaInfo = DexHelper.RandomMangaParser(result);
        Image image = new Image(mangaInfo.get("cover").toString(), true);

        Scene2Controller.getInstance().SetMangaInfo(mangaInfo, image);

        return null;
    }
}