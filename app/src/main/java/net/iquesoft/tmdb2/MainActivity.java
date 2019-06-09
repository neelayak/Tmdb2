package net.iquesoft.tmdb2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MoviesRepository moviesRepository;
    private RecyclerView moviesList;
    private MoviesAdapter adapter;
    private boolean isFetchingMovies;
    private int currentPage = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        moviesList = findViewById(R.id.movies_list);
        moviesList.setLayoutManager(new LinearLayoutManager(this));
        moviesRepository = MoviesRepository.getInstance();
        setupOnScrollListener();


    }
    OnMoviesClickCallback callback = new OnMoviesClickCallback() {
        @Override
        public void onClick(Response.Movie movie) {
            long l=movie.getId();
            int i
                    =(int)l;
            Intent intent = new Intent(MainActivity.this, MovieActivity.class);
            intent.putExtra(MovieActivity.MOVIE_ID, i);
            startActivity(intent);
        }
    };
    private void getMovies(int page) {
        isFetchingMovies = true;
        moviesRepository.getMovies(page,  new OnGetMoviesCallback() {
            @Override
            public void onSuccess(int page, List<Response.Movie> movies) {
                if (adapter == null) {
                    adapter = new MoviesAdapter(movies,  callback);

                    moviesList.setAdapter(adapter);
                    Log.e("ID",""+movies.get(1).getId());
                } else {
                    if (page == 1) {
                        adapter.clearMovies();
                    }
                    adapter.appendMovies(movies);
                }
                currentPage = page;
                Log.e("CurrentPage2",""+currentPage);
                isFetchingMovies = false;


            }



            @Override
            public void onError() {

            }
        });
    }
    private void setupOnScrollListener() {
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        moviesList.setLayoutManager(manager);
        moviesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int totalItemCount = manager.getItemCount();
                int visibleItemCount = manager.getChildCount();
                int firstVisibleItem = manager.findFirstVisibleItemPosition();

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    if (!isFetchingMovies) {

                        //currentPage=currentPage+1;
                        getMovies(currentPage + 1);
                        Log.e("CurrentPage",""+currentPage);
                    }
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMovies(currentPage);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
