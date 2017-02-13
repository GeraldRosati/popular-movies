package com.gmail.jerrycrosati.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.jerrycrosati.popularmovies.utilities.MovieDatabaseJsonUtils;
import com.gmail.jerrycrosati.popularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {
    // Movie Poster views
    RecyclerView _posterRecyclerView;
    MovieAdapter _movieAdapter;

    // Displays an error message when data could not be retrieved from the Movie Database
    TextView _errorMessageDisplay;

    // Displayed when getting a HTTP response
    ProgressBar _loadingIndicator;

    // The order to load the movie posters
    String _sortOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _posterRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_posters);
        _errorMessageDisplay = (TextView) findViewById(R.id.tv_error_message);
        _loadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        GridLayoutManager layoutManager =
                new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        _posterRecyclerView.setLayoutManager(layoutManager);
        _posterRecyclerView.setHasFixedSize(true);

        _movieAdapter = new MovieAdapter(this);
        _posterRecyclerView.setAdapter(_movieAdapter);

        // Initially, load the movie posters based on popularity
        loadMovieData("popular");
    }

    /**
     * Load movie data from the Movie Database.
     *
     * @param sortOrder The order to load the movie posters. Either by popularity or rating.
     */
    private void loadMovieData(String sortOrder) {
        Log.i("loadMovieData", "inLoadMovieData");
        _sortOrder = sortOrder;
        new MovieDatabaseQueryTask().execute(sortOrder);
    }

    /**
     * Called when a movie poster is clicked.
     *
     * @param movie The movie poster that was clicked.
     */
    @Override
    public void onClick(Movie movie) {
        Toast.makeText(this, "Navigate to Movie activity!", Toast.LENGTH_LONG).show();
    }

    /**
     * Hide the error message and show the movie poster list.
     */
    private void showMoviePosterView() {
        _errorMessageDisplay.setVisibility(View.INVISIBLE);
        _posterRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * Show the error message and hide the movie poster list.
     */
    private void showErrorMessage() {
        _errorMessageDisplay.setVisibility(View.VISIBLE);
        _posterRecyclerView.setVisibility(View.INVISIBLE);
    }

    public class MovieDatabaseQueryTask extends AsyncTask<String, Void, ArrayList<Movie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            _loadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            // If there's no sort order for the movie list, don't look anything up.
            if (params.length == 0) {
                return null;
            }

            String sortType = params[0];
            URL movieUrl = NetworkUtils.buildUrl(sortType);

            try {
                // Query the Movie Database to get a list of movies in JSON format.
                String jsonMovieDatabaseResponse = NetworkUtils.getResponseFromHttpUrl(movieUrl);

                // Create Movie objects from the JSON data
                ArrayList<Movie> jsonMovieData =
                        MovieDatabaseJsonUtils.getMovieDataFromJson(jsonMovieDatabaseResponse);

                return jsonMovieData;

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movieData) {
            _loadingIndicator.setVisibility(View.INVISIBLE);

            // Show the movie posters if data was successfully retrieved,
            // otherwise, show the error message
            if (movieData != null) {
                showMoviePosterView();
                _movieAdapter.setMovieData(movieData);
            } else {
                showErrorMessage();
            }
        }
    }

    /**
     * Called when the options menu is created
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Called when an item in the menu is selected.
     *
     * @param item The selected item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int clickedItemId = item.getItemId();

        switch (clickedItemId) {
            case R.id.menu_item_refresh:
                loadMovieData(_sortOrder);
                break;
            case R.id.menu_item_popularity:
                loadMovieData("popular");
                break;
            case R.id.menu_item_rating:
                loadMovieData("rating");
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
