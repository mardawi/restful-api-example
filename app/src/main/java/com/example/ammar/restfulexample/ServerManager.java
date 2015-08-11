package com.example.ammar.restfulexample;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the singleton pattern
 * Created by ammar on 8/10/15.
 */
public class ServerManager {

    public static final String TAG = ServerManager.class.getSimpleName(); // "ServerManager"
    private static ServerManager sInstance;

    public static ServerManager getInstance() {
        if (sInstance == null)
            sInstance = new ServerManager();
        return sInstance;
    }

    private ServerManager() {

    }

    /// now the important

    private final static String ARG_PK = "<pk>";
    private final static String GET_CATEGORIES = "http://api.alarabiya.net/sections/";
    private final static String GET_ARTICLES = "http://api.alarabiya.net/sections/" + ARG_PK;
    OkHttpClient client = new OkHttpClient();

    public List<Category> fetchCategories() {
        String result = makeRequest(GET_CATEGORIES);
        try {

            JSONArray jArray = new JSONArray(result);
            // TODO: loop and return;

            final ArrayList<Category> categories = new ArrayList<>();
            int length = jArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = jArray.getJSONObject(i);
                int pk = jsonObject.getInt("pk");
                JSONObject fields = jsonObject.getJSONObject("fields");
                String title = fields.getString("title");
                categories.add(new Category(title, pk));
            }
            return categories;
        } catch (Exception ex) {
            Log.e(TAG, "message", ex);
            return null;
        }
    }

    public List<Article> fetchArticles(int pk) {
        String result = makeRequest(GET_ARTICLES.replace(ARG_PK, String.valueOf(pk)));
        try {

            JSONArray jArray = new JSONArray(result);
            // TODO: loop and return;

            final ArrayList<Article> articles = new ArrayList<>();
            int length = jArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = jArray.getJSONObject(i);
                JSONObject fields = jsonObject.getJSONObject("fields");
                String title = fields.getString("title");
                String image = fields.getString("image");
                String body = fields.getString("body");
                String url = fields.getString("url");
                String articleSource = fields.getString("source");
                String articleDate = fields.getString("upDate");
                articles.add(new Article(title, image, body, url, articleSource,articleDate));
            }
            return articles;
        } catch (Exception ex) {
            Log.e(TAG, "message", ex);
            return null;
        }
    }

    private String makeRequest(String url) {
        String result;

        try {
            Request.Builder builder = new Request.Builder().url(url);
            Response response = client.newCall(builder.build()).execute();
            result = response.body().string();
            return result;
        } catch (IOException ex) {
            Log.e(TAG, "IOException while calling " + url, ex);
        }
        return null;
    }


}
