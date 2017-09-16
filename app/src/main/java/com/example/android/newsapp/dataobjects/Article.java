package com.example.android.newsapp.dataobjects;

/**
 * Created by avishai on 9/16/2017.
 */

public class Article {

    private String mTitle;
    private String mSection;
    private String mUrl;
    private String mDatePublished;

    public Article(String name, String section, String url, String datePublished){
        this.mTitle = name;
        this.mSection = section;
        this.mUrl = url;
        this.mDatePublished = datePublished;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSection() {
        return mSection;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getDatePublished() {
        return mDatePublished;
    }
}
