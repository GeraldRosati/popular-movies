package com.gmail.jerrycrosati.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gmail.jerrycrosati.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView _jsonDisplay;
    TextView _errorMessageDisplay;
    ProgressBar _loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _jsonDisplay = (TextView) findViewById(R.id.tv_movie_json);
        _errorMessageDisplay = (TextView) findViewById(R.id.tv_error_message);
        _loadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        makeMovieDatabaseQuery();
    }

    private void makeMovieDatabaseQuery() {
        URL movieDatabaseUrl = NetworkUtils.buildUrl("popular");
        new MovieDatabaseQueryTask().execute(movieDatabaseUrl);
    }

    private void showJsonDataView() {
        _errorMessageDisplay.setVisibility(View.INVISIBLE);
        _jsonDisplay.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        _errorMessageDisplay.setVisibility(View.VISIBLE);
        _jsonDisplay.setVisibility(View.INVISIBLE);
    }

    public class MovieDatabaseQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            _loadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String movieDatabaseResults = null;
            try {
                movieDatabaseResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieDatabaseResults;
        }

        @Override
        protected void onPostExecute(String movieDatabaseResults) {
            _loadingIndicator.setVisibility(View.INVISIBLE);

            if ((movieDatabaseResults != null) && (movieDatabaseResults.equals("") == false)) {
                _jsonDisplay.setText(movieDatabaseResults);
            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int clickedItemId = item.getItemId();
        if (clickedItemId == R.id.refresh) {
            makeMovieDatabaseQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
