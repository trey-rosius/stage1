package com.rosius.popularmovies.models;

import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;

/**
 * Created by Ndimofor ateh Rosius on 19-May-17.
 */

public class PopularMoviesItem implements Parcelable {
    private int id;
    private String movieTitle;


    public PopularMoviesItem() {

    }
    public PopularMoviesItem(Parcel in)
    {
        this.id = in.readInt();
        this.movieTitle = in.readString();
        this.moviePosterThumbnail = in.readString();
        this.movieOverview  = in.readString();
        this.movieVoteAverage = in.readDouble();
        this.movieReleaseDate = in.readString();
    }

    public PopularMoviesItem(int id, String movieTitle, String moviePosterThumbnail, String movieOverview, double movieVoteAverage, String movieReleaseDate) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.moviePosterThumbnail = moviePosterThumbnail;
        this.movieOverview = movieOverview;
        this.movieVoteAverage = movieVoteAverage;
        this.movieReleaseDate = movieReleaseDate;
    }

    private String moviePosterThumbnail;
    private String movieOverview;
    private double movieVoteAverage;
    private String movieReleaseDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMoviePosterThumbnail() {
        return moviePosterThumbnail;
    }

    public void setMoviePosterThumbnail(String moviePosterThumbnail) {
        this.moviePosterThumbnail = moviePosterThumbnail;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public double getMovieVoteAverage() {
        return movieVoteAverage;
    }

    public void setMovieVoteAverage(double movieVoteAverage) {
        this.movieVoteAverage = movieVoteAverage;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        try
        {
            parcel.writeInt(this.id);
            parcel.writeString(this.movieTitle);
            parcel.writeString(this.moviePosterThumbnail);
             parcel.writeString(this.movieOverview);
            parcel.writeDouble(this.movieVoteAverage);
            parcel.writeString(this.movieReleaseDate);
        }
        catch(ParcelFormatException e)
        {
            e.printStackTrace();
        }

    }
    public static final Creator CREATOR = new Creator() {
        public PopularMoviesItem createFromParcel(Parcel in) {
            return new PopularMoviesItem(in);
        }

        public PopularMoviesItem[] newArray(int size) {
            return new PopularMoviesItem [size];
        }
    };
}
