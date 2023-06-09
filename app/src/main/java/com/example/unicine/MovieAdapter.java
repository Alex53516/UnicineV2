package com.example.unicine;

import android.content.Intent;
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
    private List<String> imageDrawableNames; // Cambiar a nombres de drawables
    private List<String> cine;


    public MovieAdapter(List<String> movieTitles, List<String> imageDrawableNames, List<String> cineIds) {
        this.movieTitles = movieTitles;
        this.imageDrawableNames = imageDrawableNames;
        this.cine = cineIds;
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
        String drawableName = imageDrawableNames.get(position);
        String idCine = cine.get(position);
        holder.movieTitle.setText(title);

        int resourceId = holder.itemView.getContext().getResources().getIdentifier(drawableName, "drawable", holder.itemView.getContext().getPackageName());

        RoundedCorners roundedCorners = new RoundedCorners(50);
        Glide.with(holder.movieImage.getContext())
                .load(resourceId)
                .transform(new CenterCrop(), roundedCorners)
                .into(holder.movieImage);

        // Agrega un listener de clics
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DescripionPelicula.class);
                intent.putExtra("movieTitle", title);
                intent.putExtra("idCine", idCine);

                holder.itemView.getContext().startActivity(intent);
            }
        });
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
