package net.iquesoft.tmdb2;

import java.util.List;

public interface OnGetMoviesCallback {

    void onSuccess(int i,List<Response.Movie> movies);

    void onError();
}
