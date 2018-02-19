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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        //data to display
        final ArrayList<Movie> movieList = Movie.getMoviesFromFile
                ("movies.json", this);
        //create adaptor
        MovieAdaptor adaptor = new MovieAdaptor(this, movieList);

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
                //detailIntent.putExtra("main_characters", selectedMovie.main_characters.toString());
                detailIntent.putExtra("url", selectedMovie.url);

                startActivity(detailIntent);
            }
        });
    }

    public void launchActivity() {
        // 1. intent with from and to
        Intent intent = new Intent(mContext, MovieDetailActivity.class);
        // 2. add data to the intent
        //intent.putExtra("username", username.getText().toString());
        // 3. start activity with the intent
        //startActivity(intent);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode ==RESULT_OK) {
                boolean hasSeen = data.getBooleanExtra("seenIt", false);
                boolean wantToSee = data.getBooleanExtra("wantToSee", false);
                boolean doNotLike = data.getBooleanExtra("doNotLike", false);

                //check to see which box has been selected
                //then display different string in the text view

                if(hasSeen & !wantToSee & !doNotLike) {
                    resultTextView.setText("Has seen");
                }
                else if(!hasSeen & wantToSee & !doNotLike) {
                    resultTextView.setText("Wants to see");
                }
                else if(!hasSeen & !wantToSee & doNotLike) {
                    resultTextView.setText("Does not like");
                }
                else {
                    resultTextView.setText("Has seen?");
                }
            }
        }
    }

}
