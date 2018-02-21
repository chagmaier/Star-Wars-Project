package com.example.chris.starwars;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private Context mContext;
    public TextView resultTextView;
    private ArrayList<Movie> movieList;
    private MovieAdaptor adaptor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        //data to display
        movieList = Movie.getMoviesFromFile("movies.json", this);
        //create adaptor
        adaptor = new MovieAdaptor(this, movieList);

        resultTextView = findViewById(R.id.has_seen);

        mListView = findViewById(R.id.movie_list_view);
        mListView.setAdapter(adaptor);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Movie selectedMovie = movieList.get(position);

                Intent detailIntent = new Intent(mContext, MovieDetailActivity.class);
                detailIntent.putExtra("title", selectedMovie.title);
                detailIntent.putExtra("description", selectedMovie.description);
                detailIntent.putExtra("poster", selectedMovie.poster);
                detailIntent.putExtra("main_characters", selectedMovie.main_characters.toString());
                detailIntent.putExtra("url", selectedMovie.url);
                detailIntent.putExtra("position", position);

                launchActivity(detailIntent);
                //startActivityForResult(detailIntent, 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) { // SECOND ACTIVITY IS SENDING DATA

                final int position = data.getIntExtra("position", -1);
                //int position = data.getExtras().getInt("has_seen_position");

                boolean seenIt = data.getBooleanExtra("button1", false);
                boolean wantToSee = data.getBooleanExtra("button2", false);
                boolean doNotLike = data.getBooleanExtra("button3", false);

                // check to see which box has been selected
                // then display different strings in the text view

                if (seenIt){
                    movieList.get(position).hasSeen="Has seen";
                }

                else if (wantToSee){
                    movieList.get(position).hasSeen="Wants to see";
                }

                else if(doNotLike){
                    movieList.get(position).hasSeen="Does not like";
                }
                adaptor.notifyDataSetChanged();
            }
        }
    }

    public void launchActivity(Intent intent) {
        startActivityForResult(intent, 1);
    }
}