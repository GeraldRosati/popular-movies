package com.gmail.jerrycrosati.popularmovies;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private TextView _movieTitleText;
    private TextView _movieRatingText;
    private TextView _movieSynopsisText;
    private ImageView _moviePosterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        _movieTitleText = (TextView) findViewById(R.id.tv_movie_title);
        _movieRatingText = (TextView) findViewById(R.id.tv_movie_rating);
        _movieSynopsisText = (TextView) findViewById(R.id.tv_movie_synopsis);
        _moviePosterImage = (ImageView) findViewById(R.id.iv_details_movie_poster);

        Intent movieIntent = getIntent();
        Bundle extras = movieIntent.getExtras();

        if (extras != null) {
            // Get the movie information
            double movieRating = extras.getDouble(Movie.MOVIE_RATING_KEY);
            String movieTitle = extras.getString(Movie.MOVIE_TITLE_KEY);
            String movieSynopsis = extras.getString(Movie.MOVIE_SYNOPSIS_KEY);
            String moviePosterUrl = extras.getString(Movie.MOVIE_POSTER_URL_KEY);

            // Display the movie information
            Resources res = getResources();
            String movieRatingString = res.getString(R.string.movie_rating, movieRating);

            _movieRatingText.setText(movieRatingString);
            _movieTitleText.setText(movieTitle);
            _movieSynopsisText.setText(movieSynopsis);
            Picasso.with(this).load(moviePosterUrl).into(_moviePosterImage);
        }
    }
}
