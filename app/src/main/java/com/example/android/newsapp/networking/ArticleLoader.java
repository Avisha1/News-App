package com.example.android.newsapp.networking;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import com.example.android.newsapp.dataobjects.Article;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by avishai on 9/16/2017.
 */

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    /** Tag for log messages */
    private static final String LOG_TAG = ArticleLoader.class.getName();

    public ArticleLoader(Context context) {
        super(context);
    }

    @Override
    public List<Article> loadInBackground() {
        ArrayList<Article> answer = null;

        URL url = ArticleLoaderApiHelper.createUrl();

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = ArticleLoaderApiHelper.makeHttpRequest(url);
        } catch (IOException e) {
            System.out.println("Oops. here was a problem with the HTTP request.");
            e.printStackTrace();
        }
        if (jsonResponse != null) {
            answer = ArticleLoaderApiHelper.parseJsonToArticles(jsonResponse);
        } else {
            System.out.println("response is null :/ ");
        }
        return answer;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "TEST: onStartLoading");
        forceLoad();
    }
}
