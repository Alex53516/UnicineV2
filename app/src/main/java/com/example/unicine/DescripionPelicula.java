package com.example.unicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

        int color = ContextCompat.getColor(this, R.color.azul);

        getWindow().setNavigationBarColor(color);
        getWindow().setStatusBarColor(color);


        titulo = (TextView) findViewById(R.id.textViewTituloPeli);
        dur = (TextView) findViewById(R.id.textViewDuracionPeli);
        gen = (TextView) findViewById(R.id.textViewGeneroPeli);
        sinop = (TextView) findViewById(R.id.textViewSinopsisPeli);
        edadReco = (TextView) findViewById(R.id.textViewEdadPeli);

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


                        titulo.setText("Titulo: " + nombre);
                        dur.setText("Duración: " + duracion);
                        gen.setText("Género: " + genero);
                        sinop.setText(sinopsis);
                        edadReco.setText("Edad recomendada: " + edad);

                        HashMap<String, Integer> imagenes = new HashMap<>();
                        imagenes.put("El gato con botas", R.drawable.elgatoconbotasvertical);
                        imagenes.put("Cars", R.drawable.cars);
                        imagenes.put("El rey león", R.drawable.elreyleon);
                        imagenes.put("Toy story", R.drawable.toystory);


                        if (imagenes.containsKey(nombre)) {
                            int idImagen = imagenes.get(nombre);
                            ImageView imageView = findViewById(R.id.imageViewPeli); // Obtener la ImageView desde el layout de tu actividad
                            imageView.setImageResource(idImagen); // Mostrar la imagen correspondiente en la ImageView
                        }

                        // Agregar más títulos y rutas de imágenes según necesites






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