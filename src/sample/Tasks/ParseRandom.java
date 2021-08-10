package sample.Tasks;

import javafx.concurrent.Task;
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
        HashMap manga_info = DexHelper.RandomMangaParser(result);
        Scene2Controller.getInstance().SetMangaInfo(manga_info);

        return null;
    }
}