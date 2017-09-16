package com.example.android.newsapp.networking;

import android.text.TextUtils;

import com.example.android.newsapp.dataobjects.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by avishai on 9/16/2017.
 */

public class ArticleLoaderApiHelper {

    private static final String KEY_QUERY_URL = "http://content.guardianapis.com/search?api-key=test";
    private static final String KEY_RESPONSE_OBJECT = "response";
    private static final String KEY_RESULTS_ARRAY = "results";
    private static final String KEY_TITLE = "webTitle";
    private static final String KEY_DATE = "webPublicationDate";
    private static final String KEY_SECTION = "sectionName";
    private static final String KEY_URL = "webUrl";

    public static URL createUrl() {
        URL url;
        try {
            url = new URL(KEY_QUERY_URL);
        } catch (MalformedURLException exception) {
            System.out.println("Error with creating URL");
            exception.printStackTrace();
            return null;
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                System.out.println("Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            System.out.println("Problem retrieving the JSON results.");
            e.printStackTrace();
            // no matter what - do this
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                //noinspection ThrowFromFinallyBlock
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<Article> parseJsonToArticles(String jsonResponse) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        try {
            ArrayList<Article> articleList = new ArrayList<>();
            JSONObject jsonResponseObject = new JSONObject(jsonResponse);

            JSONObject baseResponse = jsonResponseObject.getJSONObject(KEY_RESPONSE_OBJECT);
            JSONArray articleJsonArray = baseResponse.getJSONArray(KEY_RESULTS_ARRAY);

            for (int i = 0; i < articleJsonArray.length(); i++) {
                //if parsing one article fails, continue to parse others
                try {
                    String title;
                    String url;
                    String publishedDate = "";
                    String section;

                    //get info specific to the article
                    JSONObject jsonArticle = articleJsonArray.getJSONObject(i);

                    title = jsonArticle.getString(KEY_TITLE);
                    section = jsonArticle.getString(KEY_SECTION);
                    url = jsonArticle.getString(KEY_URL);
                    if (jsonArticle.has(KEY_DATE)) {

                        publishedDate = jsonArticle.getString(KEY_DATE);


                        articleList.add(new Article(title, section, url, publishedDate));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //if all went well - return the articles list
            return articleList;
        } catch (JSONException e) {
            System.out.println("Problem parsing the JSON results");
            e.printStackTrace();
        }
        //return null if something went wrong
        return null;
    }

}
