package com.gmail.jerrycrosati.popularmovies;

import com.gmail.jerrycrosati.popularmovies.utilities.NetworkUtils;
import java.net.URL;

public class Movie {
    // Keys used when sending the intent from the MainActivity to the Details activity
    public static final String MOVIE_TITLE_KEY = "movie_title";
    public static final String MOVIE_RATING_KEY = "movie_rating";
    public static final String MOVIE_SYNOPSIS_KEY = "movie_synopsis";
    public static final String MOVIE_POSTER_URL_KEY = "movie_poster_url";

    private URL _posterUrl;
    private String _posterPath;
    private String _name;
    private String _synopsis;
    private double _rating;

    public Movie() {
        _posterUrl = null;
        _posterPath = "";
        _name = "";
        _synopsis = "";
        _rating = 0;
    }

    public Movie(String path, String name, String synopsis, double rating) {
        _posterUrl = NetworkUtils.buildMoviePosterUrl(path);
        _posterPath = path;
        _name = name;
        _synopsis = synopsis;
        _rating = rating;
    }

    public void setPosterUrl(URL posterUrl) {
        _posterUrl = posterUrl;
    }

    public URL getPosterUrl() {
        return _posterUrl;
    }

    public void setPosterPath(String posterPath) {
        _posterPath = posterPath;
    }

    public String getPosterPath() {
        return _posterPath;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public void setSynopsis(String synopsis) {
        _synopsis = synopsis;
    }

    public String getSynopsis() {
        return _synopsis;
    }

    public void setRating(double rating) {
        _rating = rating;
    }

    public double getRating() {
        return _rating;
    }
}
