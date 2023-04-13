package com.example.unicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;

public class DescripionPelicula extends AppCompatActivity {

    TextView titulo, dur, gen, sinop, edadReco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripion_pelicula);

        Drawable degradado = getResources().getDrawable(R.drawable.degradado_azul);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setBackgroundDrawable(degradado);


        titulo = (TextView) findViewById(R.id.textViewTituloPeli);
        dur = (TextView) findViewById(R.id.textViewDuracionPeli);
        gen = (TextView) findViewById(R.id.textViewGeneroPeli);
        sinop = (TextView) findViewById(R.id.textViewSinopsisPeli);
        edadReco = (TextView) findViewById(R.id.textViewEdadPeli);
        ImageView imageView = findViewById(R.id.imageViewPeli); // Obtener la ImageView desde el layout de tu actividad


        // Recupera el título de la película del Intent
        String movieTitle = getIntent().getStringExtra("movieTitle");

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
                        String nombre = documentSnapshot.getString("Nombre");
                        String duracion = documentSnapshot.getString("Duracion");
                        String genero = documentSnapshot.getString("Genero");
                        String sinopsis = documentSnapshot.getString("Sinopsis");
                        String edad = documentSnapshot.getString("Edad");
                        String resourceSmall = documentSnapshot.getString("resourceSmall"); // Obtener el valor del atributo "resourceSmall"

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
}
