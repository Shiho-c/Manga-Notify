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

    public ArrayList<String> DexLatestUpdates() throws IOException {
        String limit = "10";
        HashMap<String, String> params = new HashMap<>();
        params.put("limit", limit);
        params.put("order[publishAt]", "desc");
        String url = Helper.BuildUrl("https://api.mangadex.org/chapter", params);
        JSONObject result = Helper.SendGetRequest(url, client);
        JSONArray results = result.getJSONArray("results");
        ArrayList<String> latestMangaIDs = GetIDFromJSON(results);
        ArrayList<String> titles = ViewMangaIDs(latestMangaIDs);
        return titles;

    }


    public String ViewMangaID(String id) throws IOException {
        String url = String.format("https://api.mangadex.org/manga/%s", id);
        JSONObject result = Helper.SendGetRequest(url, client);
        String title = GetTitleFromID(result);
        return title;

    }

    public ArrayList<String> ViewMangaIDs(ArrayList<String> ids) throws IOException {
        ArrayList<String> titles = new ArrayList<>();
        for(String id: ids) {
            String title = ViewMangaID(id);
            titles.add(title);
        }
        return titles;
    }

    public String GetTitleFromID(JSONObject result) {
        HashMap<String, String> mangaDictionary = new HashMap<>();
        JSONObject data = (JSONObject) result.get("data");
        JSONObject attributes = (JSONObject) data.get("attributes");
        JSONObject title = (JSONObject) attributes.get("title");
        return title.get("en").toString();
    }

    public void GetCoverFromID(String id) {

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
