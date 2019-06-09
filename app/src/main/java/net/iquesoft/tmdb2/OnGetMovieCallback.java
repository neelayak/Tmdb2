package net.iquesoft.tmdb2;


public interface OnGetMovieCallback {

    void onSuccess(Movie movie);

    void onError();
}
