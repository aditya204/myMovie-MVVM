package com.quantum.mymovies.request;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.quantum.mymovies.AppExecutors;
import com.quantum.mymovies.MainActivity;
import com.quantum.mymovies.Models.MovieModel;
import com.quantum.mymovies.Response.MovieSearchResponse;
import com.quantum.mymovies.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieApiClient {

    private MutableLiveData<List<MovieModel>> mMovies;
    private static MovieApiClient insttance;

    private RetriveMoviesRunnable retriveMoviesRunnable;

    private  MutableLiveData<List<MovieModel>> mMoviesPopular;
    private RetriveMoviesRunnablePop retriveMoviesRunnablePop;




    public static MovieApiClient getInstance(){

        if(insttance==null){
            insttance=new MovieApiClient();
        }
        return insttance;
    }

    private MovieApiClient(){
        mMovies=new MutableLiveData<>(  );
        mMoviesPopular=new MutableLiveData<>(  );
    }



    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }
    public LiveData<List<MovieModel>> getMoviesPop(){
        return mMoviesPopular;
    }

    public void searchMoviesApi(String query, int pageNumber){

        if(retriveMoviesRunnable!=null){
            retriveMoviesRunnable=null;
        }


        retriveMoviesRunnable=new RetriveMoviesRunnable( query,pageNumber );

        final Future<?> myHandler= AppExecutors.getInstance().networkIO().submit(retriveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule( new Runnable( ) {
            @Override
            public void run() {
                //canceling the retrofit call

                myHandler.cancel( true );

            }
        } ,3000, TimeUnit.MICROSECONDS);




    }

    public void searchMoviesPop( int pageNumber){

        if(retriveMoviesRunnablePop!=null){
            retriveMoviesRunnablePop=null;
        }


        retriveMoviesRunnablePop=new RetriveMoviesRunnablePop( pageNumber );

        final Future<?> myHandler2= AppExecutors.getInstance().networkIO().submit(retriveMoviesRunnablePop);

        AppExecutors.getInstance().networkIO().schedule( new Runnable( ) {
            @Override
            public void run() {
                //canceling the retrofit call

                myHandler2.cancel( true );

            }
        } ,1000, TimeUnit.MICROSECONDS);




    }




    private  class RetriveMoviesRunnable implements Runnable{

        private String query;
        private  int pageNumber;
        boolean cancleRequest;

        public RetriveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancleRequest=false;
        }

        @Override
        public void run() {

            Call<MovieSearchResponse> response=getMovies( query,pageNumber );

            response.enqueue( new Callback<MovieSearchResponse>( ) {
                @Override
                public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                    if(response.code()==200){

                        List<MovieModel> list=new ArrayList<>( ((MovieSearchResponse)response.body()).getMovies() );


                        if(pageNumber==1){
                           // MainActivity.button.setText( "Success" );
                            Log.v("Success","Success ho gaya");
                            mMovies.postValue( list );
                            for(MovieModel movie: list){
                                Log.v("Tag","the Title  "+movie.getTitle());

                            }
                        }else{

                            List<MovieModel> currentList=mMovies.getValue();
                            currentList.addAll( list );
                            mMovies.postValue( currentList );

                        }
                    }else{
                        ResponseBody error=response.errorBody();
                        Log.v("Tag",error.toString());
                    }
                }

                @Override
                public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

                }
            } );


        }

        private Call<MovieSearchResponse> getMovies(String query,int pageNumber){
            return Servicey.getMovieApi().searchMovie( Credentials.API_KEY,
                    query,pageNumber);
        }


        private void cancelRequest(){
            Log.v( "Tag", "Cancelling Search request");
            cancleRequest=true;
        }
    }

    private  class RetriveMoviesRunnablePop implements Runnable{


        private  int pageNumber;
        boolean cancleRequest;

        public RetriveMoviesRunnablePop( int pageNumber) {

            this.pageNumber = pageNumber;
            cancleRequest=false;
        }

        @Override
        public void run() {

            Call<MovieSearchResponse> response=getPop( pageNumber );

            response.enqueue( new Callback<MovieSearchResponse>( ) {
                @Override
                public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                    if(response.code()==200){

                        List<MovieModel> list=new ArrayList<>( ((MovieSearchResponse)response.body()).getMovies() );


                        if(pageNumber==1){

                            Log.v("Success","Success ho gaya");
                            mMoviesPopular.postValue( list );

                        }else{

                            List<MovieModel> currentList=mMoviesPopular.getValue();
                            currentList.addAll( list );
                            mMoviesPopular.postValue( currentList );

                        }
                    }else{
                        ResponseBody error=response.errorBody();
                        Log.v("Tag",error.toString());
                    }
                }

                @Override
                public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                    Log.v("Tag",t.getMessage());
                }
            } );


        }

        private Call<MovieSearchResponse> getPop(int pageNumber){
            return Servicey.getMovieApi( ).getPopular( Credentials.API_KEY,
                    pageNumber);
        }


        private void cancelRequest(){
            Log.v( "Tag", "Cancelling Search request");
            cancleRequest=true;
        }
    }

}
