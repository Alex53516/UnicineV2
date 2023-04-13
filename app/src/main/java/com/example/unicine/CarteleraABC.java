package com.example.unicine;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CarteleraABC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartelera_abc);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference peliculasRef = db.collection("peliculas");

        String valor = getIntent().getStringExtra("clave");

        List<String> movieTitles = new ArrayList<>();
        List<String> imageMovies = new ArrayList<>();


        if (valor.equals("1")) {

            peliculasRef.whereEqualTo("IdCine", "1")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String movieName = document.getString("Nombre");
                                    String imageName = document.getString("resourceBig");
                                    Log.w(TAG, movieName, task.getException());
                                    movieTitles.add(movieName);
                                    imageMovies.add(imageName);
                                }

                                // Configurar el adaptador de RecyclerView

                                MovieAdapter movieAdapter = new MovieAdapter(movieTitles, imageMovies);
                                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                                recyclerView.setAdapter(movieAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(CarteleraABC.this));
                            } else {
                                Log.w(TAG, "Error obteniendo los documentos.", task.getException());
                            }
                        }
                    });


        } else if (valor.equals("2")) {

            peliculasRef.whereEqualTo("IdCine", "2")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String movieName = document.getString("Nombre");
                                    String imageName = document.getString("resourceBig");
                                    Log.w(TAG, movieName, task.getException());
                                    movieTitles.add(movieName);
                                    imageMovies.add(imageName);
                                }

                                // Configurar el adaptador de RecyclerView

                                MovieAdapter movieAdapter = new MovieAdapter(movieTitles, imageMovies);
                                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                                recyclerView.setAdapter(movieAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(CarteleraABC.this));
                            } else {
                                Log.w(TAG, "Error obteniendo los documentos.", task.getException());
                            }
                        }
                    });

        } else if (valor.equals("3")) {

            peliculasRef.whereEqualTo("IdCine", "3")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String movieName = document.getString("Nombre");
                                    String imageName = document.getString("resourceBig");
                                    Log.w(TAG, movieName, task.getException());
                                    movieTitles.add(movieName);
                                    imageMovies.add(imageName);
                                }

                                // Configurar el adaptador de RecyclerView

                                MovieAdapter movieAdapter = new MovieAdapter(movieTitles, imageMovies);
                                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                                recyclerView.setAdapter(movieAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(CarteleraABC.this));
                            } else {
                                Log.w(TAG, "Error obteniendo los documentos.", task.getException());
                            }
                        }
                    });

        } else if (valor.equals("4")) {

            peliculasRef.whereEqualTo("IdCine", "4")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String movieName = document.getString("Nombre");
                                    String imageName = document.getString("resourceBig");
                                    Log.w(TAG, movieName, task.getException());
                                    movieTitles.add(movieName);
                                    imageMovies.add(imageName);
                                }

                                // Configurar el adaptador de RecyclerView

                                MovieAdapter movieAdapter = new MovieAdapter(movieTitles, imageMovies);
                                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                                recyclerView.setAdapter(movieAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(CarteleraABC.this));
                            } else {
                                Log.w(TAG, "Error obteniendo los documentos.", task.getException());
                            }
                        }
                    });

        } else if (valor.equals("5")) {

        peliculasRef.whereEqualTo("IdCine", "5")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String movieName = document.getString("Nombre");
                                String imageName = document.getString("resourceBig");
                                Log.w(TAG, movieName, task.getException());
                                movieTitles.add(movieName);
                                imageMovies.add(imageName);
                            }

                            // Configurar el adaptador de RecyclerView

                            MovieAdapter movieAdapter = new MovieAdapter(movieTitles, imageMovies);
                            RecyclerView recyclerView = findViewById(R.id.recycler_view);
                            recyclerView.setAdapter(movieAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(CarteleraABC.this));
                        } else {
                            Log.w(TAG, "Error obteniendo los documentos.", task.getException());
                        }
                    }
                });


        } else if (valor.equals("6")) {

            peliculasRef.whereEqualTo("IdCine", "6")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String movieName = document.getString("Nombre");
                                    String imageName = document.getString("resourceBig");
                                    Log.w(TAG, movieName, task.getException());
                                    movieTitles.add(movieName);
                                    imageMovies.add(imageName);
                                }

                                // Configurar el adaptador de RecyclerView

                                MovieAdapter movieAdapter = new MovieAdapter(movieTitles, imageMovies);
                                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                                recyclerView.setAdapter(movieAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(CarteleraABC.this));
                            } else {
                                Log.w(TAG, "Error obteniendo los documentos.", task.getException());
                            }
                        }
                    });

    }



        int color = ContextCompat.getColor(this, R.color.azul);
        getWindow().setNavigationBarColor(color);
        getWindow().setStatusBarColor(color);

    }
}