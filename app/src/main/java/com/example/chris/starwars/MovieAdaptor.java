package com.example.chris.starwars;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Chris on 2/12/18.
 */

public class MovieAdaptor extends BaseAdapter {

    private Context mContext;
    private ArrayList<Movie> mMovieList;
    private LayoutInflater mInflater;

    public MovieAdaptor(Context mContext, ArrayList<Movie> mMovieList) {

        this.mContext = mContext;
        this.mMovieList = mMovieList;
        mInflater = (LayoutInflater)mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mMovieList.size();
    }

    @Override
    public Object getItem(int position){
        return mMovieList.get(position);
    }

    // returns the row id associated with the specific position in the list
    @Override
    public long getItemId(int position){

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //check if the view already exists
        //if yes, don't need to find and findViewById again
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_movie,
                    parent, false);
            holder = new ViewHolder();
            holder.titleTextView = convertView.findViewById(R.id.movie_list_title);
            holder.descriptionTextView = convertView.findViewById(R.id.movie_list_serving);
            holder.characterTextView = convertView.findViewById(R.id.movie_list_characters);
            holder.hasSeenTextView = convertView.findViewById(R.id.has_seen);
            holder.thumbnailImageView = convertView.findViewById(R.id.movie_list_thumbnail);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        TextView titleTextView = holder.titleTextView;
        TextView descriptionTextView = holder.descriptionTextView;
        TextView characterTextView = holder.characterTextView;
        TextView hasSeenTextView = holder.hasSeenTextView;
        ImageView thumbnailImageView = holder.thumbnailImageView;

        Movie movie = (Movie) getItem(position);

        titleTextView.setText(movie.title);
        titleTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        titleTextView.setTextSize(20);

        descriptionTextView.setText("Description: " + movie.description);
        descriptionTextView.setTextSize(9);
        descriptionTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));

        characterTextView.setText("Main Characters: " + movie.main_characters.get(0)
                + ", " + movie.main_characters.get(1)
                + ", " + movie.main_characters.get(2));
        characterTextView.setTextSize(14);
        characterTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));

        hasSeenTextView.setText(movie.hasSeen);
        hasSeenTextView.setTextSize(10);

        Picasso.with(mContext).load(movie.poster).into(thumbnailImageView);
        return convertView;
    }

    private static class ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;
        public TextView characterTextView;
        public TextView hasSeenTextView;
        public ImageView thumbnailImageView;
    }
}