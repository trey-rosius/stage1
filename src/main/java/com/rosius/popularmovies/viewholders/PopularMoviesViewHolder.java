package com.rosius.popularmovies.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.rosius.popularmovies.R;

/**
 * Created by Ndimofor Ateh Rosius on 21-May-17.
 */

public class PopularMoviesViewHolder extends RecyclerView.ViewHolder {

    public ImageView movieThumb;
    public PopularMoviesViewHolder(View itemView) {
        super(itemView);
        movieThumb = (ImageView)itemView.findViewById(R.id.movie);
    }
}
