package com.gmail.jerrycrosati.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    private TextView _movieTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        _movieTitleText = (TextView) findViewById(R.id.tv_movie_title);

        Intent movieIntent = getIntent();
        if (movieIntent.hasExtra(Intent.EXTRA_TEXT)) {
            String movieTitle = movieIntent.getStringExtra(Intent.EXTRA_TEXT);
            _movieTitleText.setText(movieTitle);
        }
    }
}
