package com.quantum.mymovies.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.quantum.mymovies.Models.MovieModel;

public class MovieResponse {

    @SerializedName( "results" )
    @Expose
    private MovieModel movie;
    public  MovieModel getmovie(){
        return  movie;
    }
}
