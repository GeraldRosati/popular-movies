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
    private TextView _movieReleaseDateText;
    private ImageView _moviePosterImage;

    private double _movieRating;
    private String _movieTitle;
    private String _movieSynopsis;
    private String _movieReleaseDate;
    private String _moviePosterUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        _movieTitleText = (TextView) findViewById(R.id.tv_movie_title);
        _movieRatingText = (TextView) findViewById(R.id.tv_movie_rating);
        _movieSynopsisText = (TextView) findViewById(R.id.tv_movie_synopsis);
        _movieReleaseDateText = (TextView) findViewById(R.id.tv_movie_release_date);
        _moviePosterImage = (ImageView) findViewById(R.id.iv_details_movie_poster);

        Intent movieIntent = getIntent();
        Bundle extras = movieIntent.getExtras();

        if (extras != null) {
            getMovieData(extras);
            displayMovieInformation();
        }
    }

    /**
     * Get the movie data.
     *
     * @param extras The bundle containing the movie information.
     */
    private void getMovieData(Bundle extras)
    {
        // Get the movie information
        _movieRating = extras.getDouble(Movie.MOVIE_RATING_KEY);
        _movieTitle = extras.getString(Movie.MOVIE_TITLE_KEY);
        _movieSynopsis = extras.getString(Movie.MOVIE_SYNOPSIS_KEY);
        _movieReleaseDate = extras.getString(Movie.MOVIE_RELEASE_DATE_KEY);
        _moviePosterUrl = extras.getString(Movie.MOVIE_POSTER_URL_KEY);
    }

    /**
     * Display the movie information.
     */
    private void displayMovieInformation()
    {
        Resources res = getResources();
        String movieRatingString = res.getString(R.string.movie_rating, _movieRating);

        _movieRatingText.setText(movieRatingString);
        _movieTitleText.setText(_movieTitle);
        _movieSynopsisText.setText(_movieSynopsis);
        _movieReleaseDateText.setText(_movieReleaseDate);
        Picasso.with(this).load(_moviePosterUrl).into(_moviePosterImage);
    }
}
