package com.quantum.mymovies.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.quantum.mymovies.Models.MovieModel;
import com.quantum.mymovies.request.MovieApiClient;

import java.util.List;

import retrofit2.http.Query;

public class MovieRepository {


    private  static  MovieRepository instance;
    private static MovieApiClient movieApiClient;

    private  String mQuery;
    private  int mpageNumber;

    public  static MovieRepository getInstance() {

        if(instance==null){
            instance=new MovieRepository();
        }
        return instance;
    }

    private MovieRepository(){
        movieApiClient=MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies();
    }
    public LiveData<List<MovieModel>> getPop(){
        return movieApiClient.getMoviesPop();
    }

    public void searchMoviesApi(String query,int pageNumber){

        mQuery= query;
        mpageNumber=pageNumber;
        movieApiClient.searchMoviesApi(query,pageNumber);



    }

    public void searchMoviesPop(int pageNumber){


        mpageNumber=pageNumber;
        movieApiClient.searchMoviesPop(pageNumber);



    }

    public  void searchNextPage(){
        searchMoviesApi( mQuery,mpageNumber+1 );
    }

}
