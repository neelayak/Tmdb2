package net.iquesoft.tmdb2;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";
    private List<Response.Movie> movies;

    private OnMoviesClickCallback callback;

    public MoviesAdapter(List<Response.Movie> movies, OnMoviesClickCallback callback) {
        this.callback = callback;
        this.movies = movies;
    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
    public void appendMovies(List<Response.Movie> moviesToAppend) {
        movies.addAll(moviesToAppend);
        notifyDataSetChanged();
    }

    public void clearMovies() {
        movies.clear();
        notifyDataSetChanged();
    }
    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView releaseDate;
        TextView title;
        TextView rating;
        TextView overview;
        ImageView poster;
        Response.Movie movie;
        public MovieViewHolder(View itemView) {
            super(itemView);
            releaseDate = itemView.findViewById(R.id.textView4);
            title = itemView.findViewById(R.id.textView);
            overview = itemView.findViewById(R.id.textView2);
            rating = itemView.findViewById(R.id.textView3);
            poster = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("ID",""+movie.getId());
                    callback.onClick(movie);
                }
            });
        }

        public void bind(Response.Movie movie) {
            this.movie = movie;
            releaseDate.setText(movie.getRelease_date());
            title.setText(movie.getTitle());
            rating.setText(String.valueOf(movie.getVote_average()));
            overview.setText(movie.getOverview());
            Glide.with(itemView)
                    .load(IMAGE_BASE_URL + movie.getPoster_path())
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(poster);
        }
    }
}
