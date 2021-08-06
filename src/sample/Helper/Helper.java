package sample.Helper;

import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Helper {

    public String BuildUrl(String targetUrl, HashMap<String, String> params) {
        HttpUrl.Builder url_builder = Objects.requireNonNull(HttpUrl.parse(targetUrl)).newBuilder();
        for(String key: params.keySet()) {
            url_builder.addQueryParameter(key, params.get(key));
        }
        String url = url_builder.build().toString();
        return url;
    }

    public JSONObject SendGetRequest(String url, OkHttpClient client) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        JSONObject results;
        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            results = new JSONObject(Objects.requireNonNull(response.body()).string());

        }
        return results;
    }

    public void SendPostRequests(String url, String json, OkHttpClient client) throws IOException {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(
                json,
                JSON
        );

        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "OkHttp Bot")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            System.out.println(Objects.requireNonNull(response.body()).string());
        }

    }

    public ImageView LoadImageFromUrl(String url) {
        ImageView imageView = new ImageView(new Image(url));
        ///imageView.setCache(true);
        //imageView.setCacheHint(CacheHint.SPEED);
        imageView.setFitHeight(300);
        imageView.setFitWidth(150);
        //imageView.setSmooth(true);
        //imageView.setPreserveRatio(true);

        return imageView;
    }


}
