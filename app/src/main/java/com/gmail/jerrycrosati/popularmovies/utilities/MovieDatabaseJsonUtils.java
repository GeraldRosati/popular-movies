package com.gmail.jerrycrosati.popularmovies.utilities;

import android.net.Network;
import android.util.Log;

import com.gmail.jerrycrosati.popularmovies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class MovieDatabaseJsonUtils {
    /**
     * Parse the JSON data retrieved from the Movie Database to obtain information on each movie.
     *
     * @param movieJsonStr Data retrieved from the Movie Database in JSON format
     * @return A list of movies
     * @throws JSONException
     */
    public static ArrayList<Movie> getMovieDataFromJson(String movieJsonStr) throws JSONException {
        // Movie Information
        final String MDB_RESULTS = "results";

        // Path of the movie poster image
        final String MDB_POSTER_PATH = "poster_path";

        // Movie synopsis
        final String MDB_SYNOPSIS = "overview";

        // Movie release date
        final String MDB_RELEASE_DATE = "release_date";

        // Movie title
        final String MDB_TITLE = "title";

        // Movie Rating
        final String MDB_RATING = "vote_average";

        // Movie Database status code
        final String MDB_STATUS_CODE = "status_code";

        ArrayList<Movie> parsedMovieData;
        JSONObject movieJson = new JSONObject(movieJsonStr);

        // Check for errors
        if (movieJson.has(MDB_STATUS_CODE)) {
            int errorCode = movieJson.getInt(MDB_STATUS_CODE);

            switch (errorCode) {
                // Connection Success
                case HttpURLConnection.HTTP_OK:
                    break;

                // Location invalid
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;

                // Server issue
                default:
                    return null;
            }
        }

        JSONArray movieInfoArray = movieJson.getJSONArray(MDB_RESULTS);
        parsedMovieData = new ArrayList<Movie>();

        for (int i = 0; i < movieInfoArray.length(); i++) {
            String posterPath;
            String title;
            String overview;
            double rating;

            // Retrieve the movie information from the JSON
            JSONObject movie = movieInfoArray.getJSONObject(i);
            posterPath = movie.getString(MDB_POSTER_PATH);
            title = movie.getString(MDB_TITLE);
            overview = movie.getString(MDB_SYNOPSIS);
            rating = movie.getDouble(MDB_RATING);

            // Create the URL for the movie poster
            Movie movieData = new Movie(posterPath, title, overview, rating);
            movieData.setPosterUrl(NetworkUtils.buildMoviePosterUrl(posterPath));
            parsedMovieData.add(movieData);
        }

        return parsedMovieData;
    }
}
