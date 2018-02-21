package com.example.chris.starwars;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Chris on 2/11/18.
 */

public class MovieDetailActivity extends AppCompatActivity {

    //private WebView mWebView;
    private TextView titleText;
    private TextView descriptionText;
    private ImageView posterImage;
    private boolean seenIt;
    private boolean wantToSee;
    private boolean doNotLike;
    private Button goBackButton;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        mContext = this;
        titleText = findViewById(R.id.movie_title);
        descriptionText = findViewById(R.id.movie_description);
        posterImage = findViewById(R.id.movie_poster);
        goBackButton = findViewById(R.id.submitButton);
        // instruction url
        //get movie data from main activity

        String title = this.getIntent().getExtras().getString("title");
        String description = this.getIntent().getExtras().getString("description");
        String url = this.getIntent().getExtras().getString("url");

        setTitle(title);

        Picasso.with(mContext).load(this.getIntent().getExtras().getString("poster")).into(posterImage);

        final int position = this.getIntent().getExtras().getInt("position");

        titleText.setText(title);
        //titleText.setTextColor(Color.RED);
        descriptionText.setText(description);
        //descriptionText.setTextColor(Color.BLUE);

        //mWebView = findViewById(R.id.detail_web_view);
        //mWebView.loadUrl(url);

        goBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                // construct intent
                Intent checkboxIntent = new Intent();
                // put the two boolean values into the intent

                checkboxIntent.putExtra("position", position);

                checkboxIntent.putExtra("Already seen", seenIt);
                checkboxIntent.putExtra("Want to see", wantToSee);
                checkboxIntent.putExtra("Does not like", doNotLike);

                // send back to main activity
                setResult(RESULT_OK, checkboxIntent);
                finish();
            }
        });

    }
    public void seenIt(View view){
        seenIt = ((RadioButton) view).isChecked();
    }

    public void wantToSee(View view){
        wantToSee = ((RadioButton) view).isChecked();
    }

    public void doNotLike(View view){
        doNotLike = ((RadioButton) view).isChecked();
    }
}