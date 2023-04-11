package com.example.unicine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<String> movieTitles;
    private List<Integer> imageResourceIds;

    public MovieAdapter(List<String> movieTitles, List<Integer> imageResourceIds) {
        this.movieTitles = movieTitles;
        this.imageResourceIds = imageResourceIds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = movieTitles.get(position);
        int imageResourceId = imageResourceIds.get(position);
        holder.movieTitle.setText(title);
        RoundedCorners roundedCorners = new RoundedCorners(50);
        Glide.with(holder.movieImage.getContext())
                .load(imageResourceId)
                .transform(new CenterCrop(), roundedCorners)
                .into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return movieTitles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView movieTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movie_image);
            movieTitle = itemView.findViewById(R.id.movie_title);
        }
    }
}
