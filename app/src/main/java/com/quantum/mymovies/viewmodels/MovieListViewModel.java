package com.quantum.mymovies.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.quantum.mymovies.AppExecutors;
import com.quantum.mymovies.Models.MovieModel;
import com.quantum.mymovies.repository.MovieRepository;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MovieListViewModel extends ViewModel {

   private  MovieRepository movieRepository;

    public MovieListViewModel() {
        movieRepository=MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovie(){
        return movieRepository.getMovies();
    }
    public LiveData<List<MovieModel>> getMoviePop(){
        return movieRepository.getPop();
    }


    public void searchMoviesApi(String query,int pageNumber){
        movieRepository.searchMoviesApi(query,pageNumber);
    }

    public void searchMoviesPop(int pageNumber){
        movieRepository.searchMoviesPop(pageNumber);
    }

    public void searchNextPage(){
        movieRepository.searchNextPage();

    }
}
