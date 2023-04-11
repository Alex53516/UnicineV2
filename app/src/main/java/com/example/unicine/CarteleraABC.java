package com.example.unicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;
import java.util.List;

public class CarteleraABC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartelera_abc);

        List<String> movieTitles = new ArrayList<>();
        movieTitles.add("El gato con botas");
        movieTitles.add("Cars");
        // Añadir más títulos...

        List<Integer> imageResourceIds = new ArrayList<>();
        imageResourceIds.add(R.drawable.elgatoconbotas);
        imageResourceIds.add(R.drawable.cars);
        // Añadir más recursos de imágenes...

        MovieAdapter movieAdapter = new MovieAdapter(movieTitles, imageResourceIds);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }
}