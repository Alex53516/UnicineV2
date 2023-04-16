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
import com.google.firebase.firestore.DocumentSnapshot;
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
        List<String> cine = new ArrayList<>();



        if (valor.equals("1")) {

            db.collection("cartelera").document("1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Comprueba si el atributo idCine es igual a 1
                            String idCine = document.getString("IdCine");
                            assert idCine != null;
                            if (idCine.equals("1")) {
                                // Obtiene el array de ids de películas
                                List<String> peliculasIds = (List<String>) document.get("pelis");

                                assert peliculasIds != null;
                                for (String peliculaId : peliculasIds) {
                                    // Consulta para obtener la información de cada película
                                    db.collection("peliculas").document(peliculaId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> peliculaTask) {
                                            if (peliculaTask.isSuccessful()) {
                                                DocumentSnapshot peliculaDocument = peliculaTask.getResult();
                                                if (peliculaDocument.exists()) {
                                                    String movieName = peliculaDocument.getString("Nombre");
                                                    String imageName = peliculaDocument.getString("resourceBig");
                                                    String id = document.getString("IdCine");


                                                    movieTitles.add(movieName);
                                                    imageMovies.add(imageName);
                                                    cine.add(id);

                                                    MovieAdapter movieAdapter = new MovieAdapter(movieTitles, imageMovies, cine);
                                                    RecyclerView recyclerView = findViewById(R.id.recycler_view);
                                                    recyclerView.setAdapter(movieAdapter);
                                                    recyclerView.setLayoutManager(new LinearLayoutManager(CarteleraABC.this));
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    } else {
                        Log.w(TAG, "Error obteniendo los documentos.", task.getException());
                    }
                }
            });



        } else if (valor.equals("2")) {

            db.collection("cartelera").document("2").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String idCine = document.getString("IdCine");
                            if (idCine.equals("2")) {
                                // Obtiene el array de ids de películas
                                List<String> peliculasIds = (List<String>) document.get("pelis");

                                assert peliculasIds != null;
                                for (String peliculaId : peliculasIds) {
                                    // Consulta para obtener la información de cada película
                                    db.collection("peliculas").document(peliculaId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> peliculaTask) {
                                            if (peliculaTask.isSuccessful()) {
                                                DocumentSnapshot peliculaDocument = peliculaTask.getResult();
                                                if (peliculaDocument.exists()) {
                                                    String movieName = peliculaDocument.getString("Nombre");
                                                    String imageName = peliculaDocument.getString("resourceBig");
                                                    String id = document.getString("IdCine");


                                                    movieTitles.add(movieName);
                                                    imageMovies.add(imageName);
                                                    cine.add(id);


                                                    MovieAdapter movieAdapter = new MovieAdapter(movieTitles, imageMovies, cine);
                                                    RecyclerView recyclerView = findViewById(R.id.recycler_view);
                                                    recyclerView.setAdapter(movieAdapter);
                                                    recyclerView.setLayoutManager(new LinearLayoutManager(CarteleraABC.this));
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    } else {
                        Log.w(TAG, "Error obteniendo los documentos.", task.getException());
                    }
                }
            });


        } else if (valor.equals("3")) {

            db.collection("cartelera").document("3").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String idCine = document.getString("IdCine");
                            if (idCine.equals("3")) {
                                // Obtiene el array de ids de películas
                                List<String> peliculasIds = (List<String>) document.get("pelis");

                                assert peliculasIds != null;
                                for (String peliculaId : peliculasIds) {
                                    // Consulta para obtener la información de cada película
                                    db.collection("peliculas").document(peliculaId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> peliculaTask) {
                                            if (peliculaTask.isSuccessful()) {
                                                DocumentSnapshot peliculaDocument = peliculaTask.getResult();
                                                if (peliculaDocument.exists()) {
                                                    String movieName = peliculaDocument.getString("Nombre");
                                                    String imageName = peliculaDocument.getString("resourceBig");
                                                    String id = document.getString("IdCine");


                                                    movieTitles.add(movieName);
                                                    imageMovies.add(imageName);
                                                    cine.add(id);


                                                    MovieAdapter movieAdapter = new MovieAdapter(movieTitles, imageMovies, cine);
                                                    RecyclerView recyclerView = findViewById(R.id.recycler_view);
                                                    recyclerView.setAdapter(movieAdapter);
                                                    recyclerView.setLayoutManager(new LinearLayoutManager(CarteleraABC.this));
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    } else {
                        Log.w(TAG, "Error obteniendo los documentos.", task.getException());
                    }
                }
            });


        } else if (valor.equals("4")) {

            db.collection("cartelera").document("4").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String idCine = document.getString("IdCine");
                            if (idCine.equals("4")) {
                                // Obtiene el array de ids de películas
                                List<String> peliculasIds = (List<String>) document.get("pelis");

                                assert peliculasIds != null;
                                for (String peliculaId : peliculasIds) {
                                    // Consulta para obtener la información de cada película
                                    db.collection("peliculas").document(peliculaId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> peliculaTask) {
                                            if (peliculaTask.isSuccessful()) {
                                                DocumentSnapshot peliculaDocument = peliculaTask.getResult();
                                                if (peliculaDocument.exists()) {
                                                    String movieName = peliculaDocument.getString("Nombre");
                                                    String imageName = peliculaDocument.getString("resourceBig");
                                                    String id = document.getString("IdCine");


                                                    movieTitles.add(movieName);
                                                    imageMovies.add(imageName);
                                                    cine.add(id);


                                                    MovieAdapter movieAdapter = new MovieAdapter(movieTitles, imageMovies, cine);
                                                    RecyclerView recyclerView = findViewById(R.id.recycler_view);
                                                    recyclerView.setAdapter(movieAdapter);
                                                    recyclerView.setLayoutManager(new LinearLayoutManager(CarteleraABC.this));
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    } else {
                        Log.w(TAG, "Error obteniendo los documentos.", task.getException());
                    }
                }
            });


        } else if (valor.equals("5")) {

            db.collection("cartelera").document("5").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String idCine = document.getString("IdCine");
                            if (idCine.equals("5")) {
                                // Obtiene el array de ids de películas
                                List<String> peliculasIds = (List<String>) document.get("pelis");

                                assert peliculasIds != null;
                                for (String peliculaId : peliculasIds) {
                                    // Consulta para obtener la información de cada película
                                    db.collection("peliculas").document(peliculaId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> peliculaTask) {
                                            if (peliculaTask.isSuccessful()) {
                                                DocumentSnapshot peliculaDocument = peliculaTask.getResult();
                                                if (peliculaDocument.exists()) {
                                                    String movieName = peliculaDocument.getString("Nombre");
                                                    String imageName = peliculaDocument.getString("resourceBig");
                                                    String id = document.getString("IdCine");


                                                    movieTitles.add(movieName);
                                                    imageMovies.add(imageName);
                                                    cine.add(id);


                                                    MovieAdapter movieAdapter = new MovieAdapter(movieTitles, imageMovies, cine);
                                                    RecyclerView recyclerView = findViewById(R.id.recycler_view);
                                                    recyclerView.setAdapter(movieAdapter);
                                                    recyclerView.setLayoutManager(new LinearLayoutManager(CarteleraABC.this));
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    } else {
                        Log.w(TAG, "Error obteniendo los documentos.", task.getException());
                    }
                }
            });



        } else if (valor.equals("6")) {

            db.collection("cartelera").document("6").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String idCine = document.getString("IdCine");
                            if (idCine.equals("6")) {
                                // Obtiene el array de ids de películas
                                List<String> peliculasIds = (List<String>) document.get("pelis");

                                assert peliculasIds != null;
                                for (String peliculaId : peliculasIds) {
                                    // Consulta para obtener la información de cada película
                                    db.collection("peliculas").document(peliculaId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> peliculaTask) {
                                            if (peliculaTask.isSuccessful()) {
                                                DocumentSnapshot peliculaDocument = peliculaTask.getResult();
                                                if (peliculaDocument.exists()) {
                                                    String movieName = peliculaDocument.getString("Nombre");
                                                    String imageName = peliculaDocument.getString("resourceBig");
                                                    String id = document.getString("IdCine");


                                                    movieTitles.add(movieName);
                                                    imageMovies.add(imageName);
                                                    cine.add(id);


                                                    MovieAdapter movieAdapter = new MovieAdapter(movieTitles, imageMovies, cine);
                                                    RecyclerView recyclerView = findViewById(R.id.recycler_view);
                                                    recyclerView.setAdapter(movieAdapter);
                                                    recyclerView.setLayoutManager(new LinearLayoutManager(CarteleraABC.this));
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
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