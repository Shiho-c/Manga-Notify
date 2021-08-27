package sample.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.HttpUrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class DexHelper {
    private final Helper Helper = new Helper();
    public DexHelper() {
    }

    public void DexLogin(String username, String password) throws IOException {

        String url = "https://api.mangadex.org/auth/login";
        String json = new JSONObject()
                .put("username", username)
                .put("password", password)
                .toString();
        Helper.SendPostRequests(url, json);

    }

    public ArrayList<String> DexLatestUpdateIDs() throws IOException {
        HashMap<String, String> params = new HashMap<>();
        String limit = "100";
        params.put("limit", limit);
        params.put("order[publishAt]", "desc");
        String url = Helper.BuildUrl("https://api.mangadex.org/chapter", params);
        JSONObject result = Helper.SendGetRequest(url);
        JSONArray results = result.getJSONArray("results");
        return GetLatestMangaIDs(results);

    }



    public HashMap<String, String> ViewMangaID(String id) throws IOException {
        HashMap<String, String> mangaInfo;
        String url = String.format("https://api.mangadex.org/manga/%s", id);
        HashMap<String, String> params = new HashMap<>();
        params.put("includes[]", "cover_art");
        url = Helper.BuildUrl(url, params);

        JSONObject result = Helper.SendGetRequest(url);
        mangaInfo = GetMangaInfo(result, url);
        mangaInfo.put("id", id);
        return mangaInfo;

    }

    public HashMap<String, String> GetMangaInfo(JSONObject result, String url) {
        HashMap<String, String> mangaInfo = new HashMap<>();
        JSONObject data = (JSONObject) result.get("data");

        JSONObject descriptionJson =  data.getJSONObject("attributes").getJSONObject("description");
        JSONArray tagsJson =  data.getJSONObject("attributes").getJSONArray("tags");
        String mangaTitle;
        String status =  data.getJSONObject("attributes").getString("status");
        String mangaDescription = descriptionJson.get("en").toString();
        String coverUrl = GetCoverFromID(result);

        String tagIDs = GetTagsFromJson(tagsJson);
        if(data.getJSONObject("attributes").getJSONObject("title").has("en")) {
            mangaTitle = data.getJSONObject("attributes").getJSONObject("title").getString("en");
        }
        else {
            mangaTitle = data.getJSONObject("attributes").getJSONObject("title").getString("jp");
        }

        mangaInfo.put("cover", coverUrl);
        mangaInfo.put("title", mangaTitle);
        mangaInfo.put("description", mangaDescription);
        mangaInfo.put("tags", tagIDs);
        mangaInfo.put("status", status);
        return mangaInfo;

    }
    public String GetTagsFromJson(JSONArray tags) {
        StringBuilder tagIDs = new StringBuilder();
        for(int i = 0; i < tags.length(); i ++){
            JSONObject res = (JSONObject) tags.get(i);
            String name = res.getJSONObject("attributes").getJSONObject("name").getString("en");
            tagIDs.append(name).append("/");
        }
        return tagIDs.toString();
    }
    
    public ArrayList<String> GetMangaChapters(String id) throws IOException{
        ArrayList<String> chapters = new ArrayList<>();
        String url = String.format("https://api.mangadex.org/manga/%s/feed", id);
        HttpUrl.Builder url_builder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        url_builder.addQueryParameter("translatedLanguage[]", "en");
        url_builder.addQueryParameter("order[chapter]", "asc");
        url_builder.addQueryParameter("limit", "500");
        JSONObject results = Helper.SendGetRequest(url_builder.build().toString());
        JSONArray resultsArray = results.getJSONArray("results");
        for(int i = 0; i < resultsArray.length(); i ++) {
            JSONObject result = (JSONObject) resultsArray.get(i);
            String chapter = "Chapter " +  result.getJSONObject("data").getJSONObject("attributes").getString("chapter") + ": " +  result.getJSONObject("data").getJSONObject("attributes").getString("title");
            chapters.add(chapter);
        }

        return chapters;
    }

    public String GetCoverFromID(JSONObject result) {
        JSONArray relationships = result.getJSONArray("relationships");
        String coverFileName = "";
        for (int i = 0; i < relationships.length(); i++) {
            JSONObject type = (JSONObject) relationships.get(i);
            if (type.get("type").equals("cover_art")) {
                JSONObject typeAttributes = (JSONObject) type.get("attributes");
                coverFileName = typeAttributes.get("fileName").toString();
                break;
            }
        }
        return coverFileName;
    }

    public ArrayList<String> GetLatestMangaIDs(JSONArray latestResult) {
        ArrayList<String> latestMangaIDs = new ArrayList<>();
        for (int i = 0; i < latestResult.length(); i++) {
            JSONObject relationships = (JSONObject) latestResult.get(i);
            JSONArray relationshipsArray = relationships.getJSONArray("relationships");

            for (int a = 0; a < relationshipsArray.length(); a++) {
                JSONObject mangaType = (JSONObject) relationshipsArray.get(a);
                if (mangaType.get("type").equals("manga") && !(latestMangaIDs.contains(mangaType.get("id")))) {
                    latestMangaIDs.add(mangaType.get("id").toString());
                    break;
                }
            }
        }
        return latestMangaIDs;
    }

    public String GetID(JSONObject result) {
        JSONObject data = (JSONObject) result.get("data");
        if(data.get("type").equals("manga")) {
            return data.get("id").toString();
        }
        return "";
    }

    public HashMap<String, String> RandomMangaParser(JSONObject result) throws IOException {
        String mangaID = GetID(result);
        HashMap<String, String> mangaInfo;
        mangaInfo = ViewMangaID(mangaID);
        String cover_url = String.format("https://uploads.mangadex.org/covers/%s/%s", mangaID, mangaInfo.get("cover"));
        mangaInfo.replace("cover", cover_url);
        return mangaInfo;
    }


}
