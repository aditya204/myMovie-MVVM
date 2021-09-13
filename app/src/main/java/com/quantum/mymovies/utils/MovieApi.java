package com.quantum.mymovies.utils;

import com.quantum.mymovies.Response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query( "api_key" ) String key,
            @Query( "query" ) String query,
            @Query( "page" ) int page

    );


    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopular(
            @Query( "api_key" ) String key,
            @Query( "page" ) int page

    );

}
