package sample.Helper;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DexHelper {
    private final OkHttpClient client = new OkHttpClient();
    private Helper Helper = new Helper();

    public void DexLogin(String username, String password) throws IOException {

        String url = "https://api.mangadex.org/auth/login";
        String json = new JSONObject()
                .put("username", username)
                .put("password", password)
                .toString();
        Helper.SendPostRequests(url, json, client);


    }

    public JSONObject DexLatestUpdates() throws IOException {
        HashMap<String, String> params = new HashMap<>();
        String limit = "100";
        params.put("limit", limit);
        params.put("order[publishAt]", "desc");
        String url = Helper.BuildUrl("https://api.mangadex.org/chapter", params);
        JSONObject result = Helper.SendGetRequest(url, client);
        JSONArray results = result.getJSONArray("results");
        JSONObject mangaInfo = new JSONObject();
        ArrayList<String> latestMangaIDs = GetIDFromJSON(results);
        mangaInfo = ViewMangaIDs(latestMangaIDs, mangaInfo);
        return mangaInfo;

    }


    public JSONObject ViewMangaID(String id, JSONObject mangaInfo) throws IOException {
        String url = String.format("https://api.mangadex.org/manga/%s", id);
        HashMap<String, String> params = new HashMap<>();
        params.put("includes[]", "cover_art");
        url = Helper.BuildUrl(url, params);

        JSONObject result = Helper.SendGetRequest(url, client);
        String title = GetTitleFromID(result);
        String cover = GetCoverFromID(result);
        JSONObject info = new JSONObject();
        info.put("id", id);
        info.put("cover", cover);
        mangaInfo.put(title, info);
        return mangaInfo;

    }

    public JSONObject ViewMangaIDs(ArrayList<String> ids, JSONObject mangaInfo) throws IOException {
        ArrayList<String> titles = new ArrayList<>();
        for(String id: ids) {
            mangaInfo = ViewMangaID(id, mangaInfo);
        }
        return mangaInfo;
    }

    public String GetTitleFromID(JSONObject result) {
        HashMap<String, String> mangaDictionary = new HashMap<>();
        JSONObject data = (JSONObject) result.get("data");
        JSONObject attributes = (JSONObject) data.get("attributes");
        JSONObject title = (JSONObject) attributes.get("title");
        return title.get("en").toString();
    }

    public String GetCoverFromID(JSONObject result) {
        HashMap<String, String> mangaDictionary = new HashMap<>();
        JSONArray relationships = result.getJSONArray("relationships");
        String coverFileName = "";
        for (int i = 0; i < relationships.length(); i++) {
            JSONObject type = (JSONObject) relationships.get(i);
            if (type.get("type").equals("cover_art")) {
                JSONObject typeAttributes = (JSONObject) type.get("attributes");
                coverFileName = typeAttributes.get("fileName").toString();
            }
        }
        return coverFileName;
    }

    public ArrayList<String> GetIDFromJSON(JSONArray latestResult) {
        ArrayList<String> latestMangaIDs = new ArrayList<>();
        for (int i = 0; i < latestResult.length(); i++) {
            JSONObject relationships = (JSONObject) latestResult.get(i);
            JSONArray relationshipsArray = relationships.getJSONArray("relationships");

            for (int a = 0; a < relationshipsArray.length(); a++) {
                JSONObject mangaType = (JSONObject) relationshipsArray.get(a);
                if (mangaType.get("type").equals("manga")) {
                    latestMangaIDs.add(mangaType.get("id").toString());
                    break;
                }
            }
        }
        return latestMangaIDs;
    }


}
