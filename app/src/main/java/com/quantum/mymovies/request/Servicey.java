package com.quantum.mymovies.request;

import androidx.constraintlayout.utils.widget.MockView;

import com.quantum.mymovies.utils.Credentials;
import com.quantum.mymovies.utils.MovieApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Servicey  {

    static  Servicey servicey;

    private  static Retrofit.Builder retrofitBuilder= new Retrofit.Builder()
            .baseUrl( Credentials.BASE_URL )
            .addConverterFactory( GsonConverterFactory.create() );

    private  static Retrofit retrofit=retrofitBuilder.build();

    private static MovieApi movieApi= retrofit.create( MovieApi.class );

    public static synchronized Servicey getInstance(){
        if(servicey==null){
            servicey=new Servicey();
        }
        return servicey;
    }

    public static MovieApi getMovieApi(){
        return movieApi;
    }

}
