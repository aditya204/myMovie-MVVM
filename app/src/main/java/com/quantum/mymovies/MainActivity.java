package com.quantum.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.quantum.mymovies.Models.MovieModel;
import com.quantum.mymovies.Response.MovieSearchResponse;
import com.quantum.mymovies.adapters.MovieRecyclerAdapter;
import com.quantum.mymovies.request.MovieApiClient;
import com.quantum.mymovies.request.Servicey;
import com.quantum.mymovies.utils.Credentials;
import com.quantum.mymovies.utils.MovieApi;
import com.quantum.mymovies.viewmodels.MovieListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

public class MainActivity extends AppCompatActivity  {

    private MovieListViewModel movieListViewModel;


    RecyclerView recyclerView;
    MovieRecyclerAdapter movieRecyclerAdapter;
    Toolbar toolbar;
    SearchView searchView;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );


        movieListViewModel= new ViewModelProvider(this).get(MovieListViewModel.class);
        recyclerView=findViewById( R.id.movie_recycler );
        setSupportActionBar(toolbar );
        searchView=findViewById( R.id.seachView );










        setAdapter();
        observerChange();
        observePopularMovies();
        setSearchView();

        movieListViewModel.searchMoviesPop( 1 );


        recyclerView.addOnScrollListener( new RecyclerView.OnScrollListener( ) {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
               if(!recyclerView.canScrollVertically( 1 )){

                   movieListViewModel.searchNextPage();
               }
            }
        } );


    }

    private void observePopularMovies() {
        movieListViewModel.getMoviePop().observe( this, new Observer<List<MovieModel>>( ) {
            @Override
            public void onChanged(List<MovieModel> movieModels) {

                if(movieModels!=null){

                    movieRecyclerAdapter.setMovies( movieModels );
                    movieRecyclerAdapter.notifyDataSetChanged();


                }else {
                    Toast.makeText( MainActivity.this, "nooooo  Changed notified", Toast.LENGTH_SHORT ).show( );

                }

            }
        } );
    }


    private  void observerChange(){
        movieListViewModel.getMovie().observe( this, new Observer<List<MovieModel>>( ) {
            @Override
            public void onChanged(List<MovieModel> movieModels) {

                if(movieModels!=null){

                    movieRecyclerAdapter.setMovies( movieModels );
                    movieRecyclerAdapter.notifyDataSetChanged();

                }else {
                    Toast.makeText( MainActivity.this, "nooooo  Changed notified", Toast.LENGTH_SHORT ).show( );

                }

            }
        } );



    }




    private void setAdapter(){
        movieRecyclerAdapter=new MovieRecyclerAdapter(  );
        recyclerView.setAdapter( movieRecyclerAdapter );
        recyclerView.setLayoutManager( new LinearLayoutManager( this,LinearLayoutManager.HORIZONTAL,false ));




    }

    private void setSearchView(){

        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener( ) {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMoviesApi( query,1 );
                return false;


            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        } );



    }

    @Override
    protected void onStart() {
        super.onStart( );
        movieListViewModel.searchMoviesPop( 1 );

    }

    @Override
    protected void onRestart() {
        super.onRestart( );
        movieListViewModel.searchMoviesPop( 1 );
    }

    @Override
    protected void onResume() {
        super.onResume( );
        movieListViewModel.searchMoviesPop( 1 );
    }

    //    private void GetRetrofitResponse() {
//
//
//        MovieApi movieApi= Servicey.getMovieApi();
//
//        Call<MovieSearchResponse> responseCall= movieApi.searchMovie( Credentials.API_KEY
//        ,"fast",1);
//
//
//        responseCall.enqueue( new Callback<MovieSearchResponse>( ) {
//            @Override
//            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
//                if(response.code()==200){
//                    Toast.makeText( getApplicationContext(), "sucess", Toast.LENGTH_SHORT ).show( );
//                    Log.v("Tag","the respone "+response.body().toString());
//
//                    List<MovieModel> movies=new ArrayList<>( response.body().getMovies() );
//
//
//
//
//                }else{
//                    Toast.makeText( getApplicationContext(), "fail", Toast.LENGTH_SHORT ).show( );
//
//                    try {
//                        Log.v( "Tag","Error"+response.errorBody().string() );
//                    }catch (IOException e){
//                        e.printStackTrace();
//                    }
//
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
//                Toast.makeText( getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT ).show( );
//
//            }
//        } );
//
//    }
}