package com.rosius.popularmovies;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Ndimofor Ateh Rosius  on 21-May-17.
 */

public class MovieDetailsActivity extends AppCompatActivity {

    private AppBarLayout appbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private ImageView movieThumb;
    private TextView movieYear,movieRating,movieOverview,title;
    String movieTitle,overview,year,poster;
    double rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        movieThumb = (ImageView) findViewById(R.id.movie_thumbnail);
        title = (TextView)findViewById(R.id.movie_title);
        movieYear = (TextView) findViewById(R.id.movie_year);
        movieRating = (TextView) findViewById(R.id.rating);
        movieOverview = (TextView) findViewById(R.id.overview);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //getting intent extra details
        Bundle extra = getIntent().getExtras();
        if(extra != null)
        {
          movieTitle = extra.getString("movie_title");
            overview = extra.getString("movie_overview");
            year = extra.getString("movie_year");
            poster = extra.getString("movie_poster");
            rating = extra.getDouble("movie_rating");

        }

        collapsingToolbarLayout.setTitle(movieTitle);
        title.setText(movieTitle);


        movieRating.setText(String.valueOf(rating));
        movieYear.setText(year);
        movieOverview.setText(overview);
        ColorDrawable cd = new ColorDrawable(ContextCompat.getColor(this, R.color.colorAccent));
        Glide.with(this).load(poster)

                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(cd)
                .into(movieThumb);


    }

}
