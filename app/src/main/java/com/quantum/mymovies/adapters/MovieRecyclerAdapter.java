package com.quantum.mymovies.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.quantum.mymovies.Models.MovieModel;
import com.quantum.mymovies.MovieDetailsActivity;
import com.quantum.mymovies.R;

import java.util.List;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {

    private List<MovieModel> movieModelList;


    @NonNull
    @Override
    public MovieRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.movie_list_item ,parent,false);
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRecyclerAdapter.ViewHolder holder, int position) {
        String title=movieModelList.get( position ).getTitle();
        String cate=movieModelList.get( position ).getRelease_date();
        float dura=  movieModelList.get( position ).getVote_average();
        String img=movieModelList.get( position ).getPoster_path();
        float rate=movieModelList.get( position ).getVote_average()/2;
        MovieModel movieModel=movieModelList.get( position );

        holder.setData( title,cate,String.valueOf( dura ),rate,img,position );

        holder.imageView.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.imageView.getContext(), MovieDetailsActivity.class );
                intent.putExtra( "movie",movieModel);
                //Toast.makeText( holder.imageView.getContext(), movieModel.getTitle(), Toast.LENGTH_SHORT ).show( );
                holder.imageView.getContext().startActivity( intent );
            }
        } );



    }

    @Override
    public int getItemCount() {
        if(movieModelList!=null){
            return movieModelList.size();
        }else{
            return 0;
        }

    }

    public class ViewHolder extends  RecyclerView.ViewHolder {

        RatingBar ratingBar;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );


            ratingBar=itemView.findViewById( R.id.movie_rating );
            imageView=itemView.findViewById( R.id.movie_img );


        }

        private void setData(String Title,String Category,String Duration,float rating,String Image,int pos){


            ratingBar.setRating( rating );

            Glide.with(imageView.getContext()).load( "https://image.tmdb.org/t/p/w500/"+Image ).into( imageView );



            ratingBar.setNumStars( 5 );
            ratingBar.setClickable( false );







        }


    }

    public void setMovies(List<MovieModel> movies){
        movieModelList=movies;
        notifyDataSetChanged();

    }


}
