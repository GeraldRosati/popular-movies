package com.gmail.jerrycrosati.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private ArrayList<Movie> _movieData;
    private final MovieAdapterOnClickHandler _clickHandler;

    interface MovieAdapterOnClickHandler {
        void onClick(Movie movieInfo);
    }

    /**
     * Create a MovieAdapter
     *
     * @param clickHandler The on-click handler for the adapter. Called when an item is clicked.
     */
    MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        _clickHandler = clickHandler;
    }

    class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView _posterImageView;

        MovieAdapterViewHolder(View view) {
            super(view);
            _posterImageView = (ImageView) view.findViewById(R.id.iv_movie_poster);
            view.setOnClickListener(this);
        }

        /**
         * Called when a child view is clicked.
         *
         * @param v The view that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = _movieData.get(adapterPosition);
            _clickHandler.onClick(movie);
        }
    }

    /**
     * Called when the ViewHolder is created.
     *
     * @param viewGroup The ViewGroup that contains the ViewHolders
     * @param viewType The type of view to display
     * @return The new ViewHolder containing the view
     */
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_poster_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    /**
     * Displays the data at the specified position.
     *
     * @param movieAdapterViewHolder The ViewHolder to be updated
     * @param position The index of the data
     */
    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        Movie movie = _movieData.get(position);

        // Load the movie poster from the URL
        Picasso.with(movieAdapterViewHolder._posterImageView.getContext())
                .load(movie.getPosterUrl().toString())
                .into(movieAdapterViewHolder._posterImageView);
    }

    /**
     * Returns the number of items to display
     *
     * @return The number of movie posters available
     */
    @Override
    public int getItemCount() {
        if (_movieData == null) {
            return 0;
        }

        return _movieData.size();
    }

    /**
     * Used to set the movie data for the MovieAdapter if the adapter has already been created
     *
     * @param movieData The updated movie data to be displayed
     */
    void setMovieData(ArrayList<Movie> movieData) {
        _movieData = movieData;
        notifyDataSetChanged();
    }
}
