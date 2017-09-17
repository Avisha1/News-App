package com.example.android.newsapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.newsapp.R;
import com.example.android.newsapp.dataobjects.Article;

import java.util.ArrayList;

/**
 * Created by avishai on 9/16/2017.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {

    public ArticleAdapter(@NonNull Context context, ArrayList<Article> ArticleList) {
        super(context, 0, ArticleList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Article article = getItem(position);

        TextView titleView = (TextView)listItemView.findViewById(R.id.title);
        titleView.setText(article.getTitle());

        TextView sectionView = (TextView)listItemView.findViewById(R.id.section);
        sectionView.setText(article.getSection());

        TextView dateView = (TextView)listItemView.findViewById(R.id.published_date);
        String date = article.getDatePublished();
        if(!date.isEmpty()){

            dateView.setText(date);
        }
        else{
            dateView.setVisibility(View.GONE);
        }
        return listItemView;
    }
}
