package com.example.unicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.TextView;

public class DescripionPelicula extends AppCompatActivity {

    TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripion_pelicula);

        int color = ContextCompat.getColor(this, R.color.azul);

        getWindow().setNavigationBarColor(color);
        getWindow().setStatusBarColor(color);


        titulo = (TextView) findViewById(R.id.textViewTituloPeli);

        // Recupera el título de la película del Intent
        String movieTitle = getIntent().getStringExtra("movieTitle");
        titulo.setText(movieTitle);

    }
}