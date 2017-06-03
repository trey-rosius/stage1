package com.rosius.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rosius.popularmovies.adapters.PopularMoviesAdapter;
import com.rosius.popularmovies.app.AppController;
import com.rosius.popularmovies.config.Config;
import com.rosius.popularmovies.models.PopularMoviesItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,PopularMoviesAdapter.OnImageClickListener,LoaderManager.LoaderCallbacks {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private PopularMoviesAdapter popularMoviesAdapter;
    private Spinner sort;
    public boolean topRated = false;
    public boolean popular = false;
    private List<PopularMoviesItem>dataItem;
    public static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        sort = (Spinner)findViewById(R.id.sort_movies);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        sort.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        sort.setAdapter(adapter);
        /*
        Setup the recyclerview Grid
         */

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setupRecyclerview();

    }

    public void setupRecyclerview()
    {
        dataItem = new ArrayList<>();
        popularMoviesAdapter = new PopularMoviesAdapter(this,dataItem);
       popularMoviesAdapter.setOnImageClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(popularMoviesAdapter);

        progressBar.setVisibility(View.VISIBLE);
        //fetch most popular movies
        PopularMoviesTask popularMoviesTask = new PopularMoviesTask();
        popularMoviesTask.getPopularMovies();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(i ==0)
        {
            if(popular)
            {

            }
            else
            {
                dataItem.clear();
                progressBar.setVisibility(View.VISIBLE);
                PopularMoviesTask popularMoviesTask = new PopularMoviesTask();
                popularMoviesTask.getPopularMovies();
            }
        }
        else
        {
            if(topRated)
            {

            }
            else
            {
                dataItem.clear();
                progressBar.setVisibility(View.VISIBLE);
                TopRatedMoviesTask topRatedMoviesTask = new TopRatedMoviesTask();
                topRatedMoviesTask.getTopRatedMovies();
            }
        }
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onImageThumbClicked(View v, int movieId, String movieTitle, String movieThumb, String movieOverview, double movieVoteAverage, String movieReleaseDate) {
        PopularMoviesItem popularMoviesItem = new PopularMoviesItem(movieId,
                movieTitle,
                movieThumb,
                movieOverview,
                movieVoteAverage,
                movieReleaseDate);

        Log.d(TAG,movieTitle+movieOverview);

        Intent intent = new Intent(this,MovieDetailsActivity.class);
        intent.putExtra("movie_title",movieTitle);
        intent.putExtra("movie_overview",movieOverview);
        intent.putExtra("movie_year",movieReleaseDate);
        intent.putExtra("movie_poster",movieThumb);
        intent.putExtra("movie_rating",movieVoteAverage);
        startActivity(intent);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {


    }



    @Override
    public void onLoaderReset(Loader loader) {

    }

    public class TopRatedMoviesTask extends Loader
    {
        /**
         * Stores away the application context associated with context.
         * Since Loaders can be used across multiple activities it's dangerous to
         * store the context directly; always use {@link #getContext()} to retrieve
         * the Loader's Context, don't use the constructor argument directly.
         * The Context returned by {@link #getContext} is safe to use across
         * Activity instances.
         *
         * @param context used to retrieve the application context.
         */
        public TopRatedMoviesTask(Context context) {
            super(context);
        }

        @Override
        protected void onForceLoad() {
            super.onForceLoad();
        }

        @Override
        public void deliverResult(Object data) {
            super.deliverResult(data);
        }

        //  public TopRatedMoviesTask()
     //   {


      //  }

        public void getTopRatedMovies()
        {
            topRated = true;
            popular = false;
                    String POPULAR_URL = Config.BASE_URL + Config.TOP_RATED_MOVIES+Config.API_KEY;
            Log.d(TAG,POPULAR_URL);
            // Making json object request
            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    POPULAR_URL, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                  //  Log.d(TAG, response.toString());
                    Log.d("Inside", "check point");
                    try {

                        int pageNumber = response.getInt("page");
                        long totalRes = response.getLong("total_results");
                        int totalPages = response.getInt("total_pages");

                        JSONArray feedArray = response.getJSONArray("results");


                        for (int i = 0; i < feedArray.length(); i++) {
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            PopularMoviesItem pItem = new PopularMoviesItem();

                            pItem.setId(feedObj.getInt("id"));
                            pItem.setMovieTitle(feedObj.getString("original_title"));
                            pItem.setMovieOverview(feedObj.getString("overview"));
                            String thumb = Config.BASE_URL_SIZE+feedObj.getString("poster_path");
                            pItem.setMoviePosterThumbnail(thumb);
                            pItem.setMovieReleaseDate(feedObj.getString("release_date"));
                            pItem.setMovieVoteAverage(feedObj.getDouble("vote_average"));


                            dataItem.add(pItem);

                            }

                        progressBar.setVisibility(View.GONE);
                        popularMoviesAdapter.notifyDataSetChanged();




                    } catch (JSONException e) {
                        e.printStackTrace();

                    }





                }

            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Log.d("Ive Reached here", "check point");


                }
            }) {

                /**
                 * Passing some request headers
                 * */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    // headers.put("Content-Type", "application/json");
                    headers.put("api_key", Config.API_KEY);
                    return headers;
                }


            };

            // Setting timeout to volley request because fetching tori takes alot of time
            int socketTimeout = 60000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjReq.setRetryPolicy(policy);


            // Adding request to request queue
            AppController.getInstance().get().addToRequestQueue(jsonObjReq);


        }
    }

    public class PopularMoviesTask
    {
        public PopularMoviesTask()
        {

        }

        public void getPopularMovies()
        {
            topRated = false;
            popular = true;
            String POPULAR_URL = Config.BASE_URL + Config.POPULAR_MOVIES +Config.API_KEY;
            Log.d(TAG,POPULAR_URL);
            // Making json object request
            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    POPULAR_URL, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    //  Log.d(TAG, response.toString());
                    Log.d("Inside", "check point");
                    try {

                        int pageNumber = response.getInt("page");
                        long totalRes = response.getLong("total_results");
                        int totalPages = response.getInt("total_pages");

                        JSONArray feedArray = response.getJSONArray("results");


                        for (int i = 0; i < feedArray.length(); i++) {
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            PopularMoviesItem pItem = new PopularMoviesItem();

                            pItem.setId(feedObj.getInt("id"));
                            pItem.setMovieTitle(feedObj.getString("original_title"));
                            pItem.setMovieOverview(feedObj.getString("overview"));
                            String thumb = Config.BASE_URL_SIZE+feedObj.getString("poster_path");
                            pItem.setMoviePosterThumbnail(thumb);
                            pItem.setMovieReleaseDate(feedObj.getString("release_date"));
                            pItem.setMovieVoteAverage(feedObj.getDouble("vote_average"));


                            dataItem.add(pItem);

                        }

                        progressBar.setVisibility(View.GONE);
                        popularMoviesAdapter.notifyDataSetChanged();




                    } catch (JSONException e) {
                        e.printStackTrace();

                    }





                }

            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Log.d("Ive Reached here", "check point");


                }
            }) {

                /**
                 * Passing some request headers
                 * */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    // headers.put("Content-Type", "application/json");
                    headers.put("api_key", Config.API_KEY);
                    return headers;
                }


            };

            // Setting timeout to volley request because fetching tori takes alot of time
            int socketTimeout = 60000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjReq.setRetryPolicy(policy);


            // Adding request to request queue
            AppController.getInstance().get().addToRequestQueue(jsonObjReq);


        }
    }

}

