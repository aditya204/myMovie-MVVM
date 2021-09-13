package com.quantum.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.quantum.mymovies.Models.MovieModel;

public class MovieDetailsActivity extends AppCompatActivity {


    ImageView imageView;
    TextView title,details;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_movie_details );

        imageView=findViewById( R.id.movie_details_image );
        title=findViewById( R.id.movie_details_name );
        details=findViewById( R.id.movie_details_detais );
        ratingBar=findViewById( R.id.movie_details_rating );


        getDataFroIntent();


    }

    private void getDataFroIntent() {

        if(getIntent().hasExtra( "movie" )){
            MovieModel movieModel=getIntent().getParcelableExtra( "movie" );

            Glide.with( MovieDetailsActivity.this ).load( "https://image.tmdb.org/t/p/w500/"+movieModel.getPoster_path() ).into( imageView );

            title.setText( movieModel.getTitle() );
            ratingBar.setRating( movieModel.getVote_average()/2 );
            details.setText( movieModel.getMovie_overview() );
        }

    }


}