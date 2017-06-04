package com.gmail.jerrycrosati.popularmovies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    // User API Key used for querying the Movie Database
    private static final String MOVIE_DB_API_KEY = "";

    // Query Parameters
    private static final String API_KEY_PARAM = "api_key";

    // The size of the movie poster to retrieve
    private static final String SIZE_PATH = "w500";

    // Base URL strings
    private static final String MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String POSTER_BASE_URL = " http://image.tmdb.org/t/p/";

    /**
     * Create a URL used to retrieve movie information from the Movie Database.
     *
     * @param sortMethod The order to retrieve movies (by popularity or rating)
     * @return A URL to Movie Database
     */
    public static URL buildUrl(String sortMethod) {
        Uri builtUri = Uri.parse(MOVIE_DB_BASE_URL).buildUpon()
                .appendPath(sortMethod)
                .appendQueryParameter(API_KEY_PARAM, MOVIE_DB_API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Create a URL used to get the movie poster from the Movie Database.
     *
     * @param posterPath The path of the movie poster
     * @return A URL to the movie poster image
     */
    public static URL buildMoviePosterUrl(String posterPath) {
        Uri builtUri = Uri.parse(POSTER_BASE_URL)
                .buildUpon()
                .appendPath(SIZE_PATH)
                .appendEncodedPath(posterPath)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from
     * @return The contents of the HTTP response
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
