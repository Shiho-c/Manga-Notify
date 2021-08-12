package sample.Helper;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import okhttp3.*;
import org.json.JSONObject;
import sample.Scene2Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Helper {
    public final OkHttpClient client = new OkHttpClient();
    public String BuildUrl(String targetUrl, HashMap<String, String> params) {
        HttpUrl.Builder url_builder = Objects.requireNonNull(HttpUrl.parse(targetUrl)).newBuilder();
        for (String key : params.keySet()) {
            url_builder.addQueryParameter(key, params.get(key));
        }
        return url_builder.build().toString();
    }
    public void StartThread(Object obj) {
        Thread th = new Thread((Runnable) obj);
        th.setDaemon(true);
        th.start();



    }
    public JSONObject SendGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        JSONObject results;
        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            results = new JSONObject(Objects.requireNonNull(response.body()).string());

        }
        return results;
    }

    public void SendPostRequests(String url, String json) throws IOException {
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
        }

    }

    public void SetMangaButtonAction(Label manga, HashMap<String, String> mangaInfo, Image image) {
        manga.setOnMouseClicked(arg0 -> Scene2Controller.getInstance().SetMangaInfo(mangaInfo, image));
    }

    public ImageView LoadImageFromUrl(String url, int width, int height) {
        Image image = new Image(url, width, height, true, true, true);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);


        return imageView;
    }

    public void ShowPane(AnchorPane pane) {
        pane.setVisible(true);
    }

    public void HidePane(AnchorPane pane) {
        pane.setVisible(false);
    }

}
