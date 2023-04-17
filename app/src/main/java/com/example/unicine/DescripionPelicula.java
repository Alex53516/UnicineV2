package com.example.unicine;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DescripionPelicula extends AppCompatActivity {

    TextView titulo, dur, gen, sinop, edadReco, irPases, noDisponible;
    FrameLayout frm;
    List<String> fechaPeliculas = new ArrayList<>();
    List<String> horaPeliculas = new ArrayList<>();
    List<String> sala = new ArrayList<>();
    View backgroundView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripion_pelicula);

        Drawable degradado = ContextCompat.getDrawable(this, R.drawable.degradado_azul);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setBackgroundDrawable(degradado);


        titulo = (TextView) findViewById(R.id.textViewTituloPeli);
        dur = (TextView) findViewById(R.id.textViewDuracionPeli);
        gen = (TextView) findViewById(R.id.textViewGeneroPeli);
        sinop = (TextView) findViewById(R.id.textViewSinopsisPeli);
        edadReco = (TextView) findViewById(R.id.textViewEdadPeli);
        frm = (FrameLayout) findViewById(R.id.framePases);
        irPases = (TextView) findViewById(R.id.textViewIrPases);
        ImageView imageView = findViewById(R.id.imageViewPeli); // Obtener la ImageView desde el layout de tu actividad
        backgroundView = (View) findViewById(R.id.backgroundView);
        noDisponible = (TextView) findViewById(R.id.textViewSinDisponibilidad);

        frm.setVisibility(View.INVISIBLE);
        noDisponible.setVisibility(View.INVISIBLE);

        irPases.setOnClickListener(this::onClick);
        backgroundView.setOnClickListener(this::onClick);



        // Recupera el título de la película del Intent
        String movieTitle = getIntent().getStringExtra("movieTitle");
        String IdCine = getIntent().getStringExtra("idCine");
        Log.d(TAG, "El id del cine es "+ IdCine);



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference peliculasRef = db.collection("peliculas");
        Query query = peliculasRef.whereEqualTo("Nombre", movieTitle);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                        // Si la consulta devuelve algún resultado, obtener los datos de la primera película encontrada
                        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                        String movieId = documentSnapshot.getId(); // Obtener el identificador de la película
                        String nombre = documentSnapshot.getString("Nombre");
                        String duracion = documentSnapshot.getString("Duracion");
                        String genero = documentSnapshot.getString("Genero");
                        String sinopsis = documentSnapshot.getString("Sinopsis");
                        String edad = documentSnapshot.getString("Edad");
                        String resourceSmall = documentSnapshot.getString("resourceSmall");

                        // Verificar si el id de la película está en el array de ids de películas para el cine específico
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        CollectionReference carteleraRef = db.collection("cartelera");
                        DocumentReference cineRef = carteleraRef.document(IdCine);

                        cineRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    if (documentSnapshot != null && documentSnapshot.exists()) {
                                        List<String> peliculasIds = (List<String>) documentSnapshot.get("pelis");

                                        if (peliculasIds != null && peliculasIds.contains(movieId)) {
                                            // El id de la película está en el array de ids de películas para el cine específico
                                            Log.d("MovieCheck", "La película con ID: " + movieId + " está en el cine con ID: " + IdCine);

                                                // Consultar la colección de sesiones con el id de la película
                                                CollectionReference sesionesRef = db.collection("sesiones");
                                                Query sesionesQuery = sesionesRef.whereEqualTo("IdPelicula", movieId);
                                                sesionesQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            QuerySnapshot querySnapshot = task.getResult();
                                                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                                                // Recorrer todos los documentos que coinciden con la consulta
                                                                for (DocumentSnapshot sesionDocument : querySnapshot.getDocuments()) {
                                                                    String fecha = sesionDocument.getString("Fecha");
                                                                    String hora = sesionDocument.getString("Hora");
                                                                    String idPelicula = sesionDocument.getString("IdPelicula");
                                                                    String idSala = sesionDocument.getString("IdSala");

                                                                    fechaPeliculas.add(fecha);
                                                                    horaPeliculas.add(hora);

                                                                    // Consultar la colección de salas con el idSala
                                                                    DocumentReference salaRef = db.collection("salas").document(idSala);
                                                                    salaRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                            if (task.isSuccessful()) {
                                                                                DocumentSnapshot salaDocument = task.getResult();
                                                                                if (salaDocument != null && salaDocument.exists()) {
                                                                                    String nombreSala = salaDocument.getString("Nombre");

                                                                                    sala.add(nombreSala);

                                                                                    // Mostrar los atributos de la sesión y el nombre de la sala
                                                                                    Log.d("SesionInfo", "Fecha: " + fecha + ", Hora: " + hora + ", Nombre de la sala: " + nombreSala + ", IdPelicula: " + idPelicula);
                                                                                } else {
                                                                                    // No se encontró una sala con ese id
                                                                                    Log.d("SalaCheck", "No se encontró una sala con ID: " + idSala);
                                                                                }
                                                                            } else {
                                                                                // Manejar el caso en que la consulta falla
                                                                                Log.e("SalaCheck", "Error al obtener la sala con ID: " + idSala);
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            } else {
                                                                // No se encontraron sesiones con ese id de película
                                                                Log.d("SesionCheck", "No se encontraron sesiones con la película con ID: " + movieId);
                                                            }
                                                        } else {
                                                            // Manejar el caso en que la consulta falla
                                                            Log.e("SesionCheck", "Error al obtener sesiones para la película con ID: " + movieId);
                                                        }
                                                    }
                                                });


                                        } else {
                                            // El id de la película no está en el array de ids de películas para el cine específico
                                            Log.d("MovieCheck", "La película con ID: " + movieId + " no está en el cine con ID: " + IdCine);
                                        }
                                    } else {
                                        // El documento del cine no existe o es nulo
                                        Log.d("CineCheck", "El cine con ID: " + IdCine + " no existe");
                                    }
                                } else {
                                    // Manejar el caso en que la consulta falla
                                    Log.e("CineCheck", "Error al obtener el cine con ID: " + IdCine);
                                }
                            }
                        });

                        titulo.setText("Titulo: " + nombre);
                        dur.setText("Duración: " + duracion);
                        gen.setText("Géneros: " + genero);
                        sinop.setText("Sinopsis: " + sinopsis);
                        edadReco.setText("Edad recomendada: " + edad);

                        // Obtener el identificador de recurso del drawable
                        int resourceId = getResources().getIdentifier(resourceSmall, "drawable", getPackageName());

                        // Establecer la imagen del drawable en la ImageView
                        imageView.setImageResource(resourceId);

                    } else {
                        // Si no se encuentra ninguna película con ese título, mostrar un mensaje al usuario
                    }
                } else {
                    // Manejar el caso en que la consulta falla
                }
            }
        });

    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.textViewIrPases:

                Bundle bundle = new Bundle();
                bundle.putStringArrayList("fechaPeliculas", (ArrayList<String>) fechaPeliculas);
                bundle.putStringArrayList("horaPeliculas", (ArrayList<String>) horaPeliculas);
                bundle.putStringArrayList("sala", (ArrayList<String>) sala);

                PasesFragment fragmentPases = new PasesFragment();
                fragmentPases.setArguments(bundle);//le paso los argumentos
                FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.add(R.id.framePases, fragmentPases);
                fragmentTransaction1.commit();

                frm.setVisibility(View.VISIBLE);

                if (fechaPeliculas.isEmpty() && horaPeliculas.isEmpty() && sala.isEmpty()) {

                    noDisponible.setVisibility(View.VISIBLE);

                }


                break;

            case R.id.backgroundView:

                frm.setVisibility(View.INVISIBLE);
                noDisponible.setVisibility(View.INVISIBLE);


                break;


        }

    }

}
