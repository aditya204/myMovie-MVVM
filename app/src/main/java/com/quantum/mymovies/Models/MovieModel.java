package com.quantum.mymovies.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieModel implements Parcelable {

    String title,poster_path,release_date,overview;

    @SerializedName( "id" )
    int movie_id;
    int runtime;
    float vote_average;

    public MovieModel(String title, String poster_path, String release_date, String movie_overview, int movie_id, float vote_average,int runtime) {
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.overview = movie_overview;
        this.movie_id = movie_id;
        this.vote_average = vote_average;
        this.runtime=runtime;
    }

    protected MovieModel(Parcel in) {
        title = in.readString( );
        poster_path = in.readString( );
        release_date = in.readString( );
        overview = in.readString( );
        movie_id = in.readInt( );
        vote_average = in.readFloat( );
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>( ) {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel( in );
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public int getDuration() {
        return runtime;
    }

    public void setDuration(int duration) {
        this.runtime = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getMovie_overview() {
        return overview;
    }

    public void setMovie_overview(String movie_overview) {
        this.overview = movie_overview;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString( title );
        parcel.writeString( poster_path );
        parcel.writeString( release_date );
        parcel.writeString( overview );
        parcel.writeInt( movie_id );
        parcel.writeFloat( vote_average );
    }
}
