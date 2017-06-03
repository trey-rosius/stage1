package com.rosius.popularmovies.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rosius.popularmovies.R;
import com.rosius.popularmovies.models.PopularMoviesItem;
import com.rosius.popularmovies.viewholders.PopularMoviesViewHolder;

import java.util.List;

/**
 * Created by Owner on 21-May-17.
 */

public class PopularMoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnImageClickListener onImageClickListener;

    public void setOnImageClickListener(OnImageClickListener onImageClickListener)
    {
        this.onImageClickListener = onImageClickListener;
    }
    private Context context;
    private List<PopularMoviesItem>pItem;
    public PopularMoviesAdapter(Context context,List<PopularMoviesItem>pItem)
    {
        this.context = context;
        this.pItem = pItem;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.activity_popular_movie_item, parent, false);
       viewHolder = new PopularMoviesViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PopularMoviesItem  current = pItem.get(position);
        PopularMoviesViewHolder viewHolder = (PopularMoviesViewHolder)holder;
        ColorDrawable cd = new ColorDrawable(ContextCompat.getColor(context, R.color.colorAccent));
        Glide.with(context).load(current.getMoviePosterThumbnail())

                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(cd)
                .into(viewHolder.movieThumb);


        viewHolder.movieThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onImageClickListener != null)
                {
                    onImageClickListener.onImageThumbClicked(
                            view,
                            current.getId(),
                            current.getMovieTitle(),
                            current.getMoviePosterThumbnail(),
                            current.getMovieOverview(),
                            current.getMovieVoteAverage(),
                            current.getMovieReleaseDate()
                    );
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return pItem.size();
    }

    public interface OnImageClickListener
    {
        void onImageThumbClicked(View v,int movieId,String movieTitle,String movieThumb,String movieOverview,double movieVoteAverage,String movieReleaseDate);
    }
}
