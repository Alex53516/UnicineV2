package com.example.unicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ListaCines extends AppCompatActivity {

    TextView c1, c2, c3, c4, c5, c6;
    TextView d1, d2, d3, d4, d5, d6;
    String cineValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cines2);

        c1 = (TextView) findViewById(R.id.textViewC1);
        c2 = (TextView) findViewById(R.id.textViewC2);
        c3 = (TextView) findViewById(R.id.textViewC3);
        c4 = (TextView) findViewById(R.id.textViewC4);
        c5 = (TextView) findViewById(R.id.textViewC5);
        c6 = (TextView) findViewById(R.id.textViewC6);


        d1 = (TextView) findViewById(R.id.textViewD1);
        d2 = (TextView) findViewById(R.id.textViewD2);
        d3 = (TextView) findViewById(R.id.textViewD3);
        d4 = (TextView) findViewById(R.id.textViewD4);
        d5 = (TextView) findViewById(R.id.textViewD5);
        d6 = (TextView) findViewById(R.id.textViewD6);


        c1.setOnClickListener(this::onClick);
        d1.setOnClickListener(this::onClick);
        c2.setOnClickListener(this::onClick);
        d2.setOnClickListener(this::onClick);
        c3.setOnClickListener(this::onClick);
        d3.setOnClickListener(this::onClick);
        c4.setOnClickListener(this::onClick);
        d4.setOnClickListener(this::onClick);
        c5.setOnClickListener(this::onClick);
        d5.setOnClickListener(this::onClick);
        c6.setOnClickListener(this::onClick);
        d6.setOnClickListener(this::onClick);

        int color = ContextCompat.getColor(this, R.color.azul);

        getWindow().setNavigationBarColor(color);
        getWindow().setStatusBarColor(color);


        FirebaseApp.initializeApp(this);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference cineRef = db.collection("cines").document("1");

        cineRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String nombre = document.getString("Nombre");
                        String direccion = document.getString("Direccion");
                        c1.setText(nombre);
                        d1.setText(direccion);
                    }

                }
            }
        });

        cineRef = db.collection("cines").document("2");
        cineRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String nombre = document.getString("Nombre");
                        String direccion = document.getString("Direccion");
                        c2.setText(nombre);
                        d2.setText(direccion);

                    }

                }
            }
        });

        cineRef = db.collection("cines").document("3");
        cineRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String nombre = document.getString("Nombre");
                        String direccion = document.getString("Direccion");
                        c3.setText(nombre);
                        d3.setText(direccion);

                    }

                }
            }
        });

        cineRef = db.collection("cines").document("4");
        cineRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String nombre = document.getString("Nombre");
                        String direccion = document.getString("Direccion");
                        c4.setText(nombre);
                        d4.setText(direccion);

                    }

                }
            }
        });

        cineRef = db.collection("cines").document("5");
        cineRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String nombre = document.getString("Nombre");
                        String direccion = document.getString("Direccion");
                        c5.setText(nombre);
                        d5.setText(direccion);

                    }

                }
            }
        });

        cineRef = db.collection("cines").document("6");
        cineRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String nombre = document.getString("Nombre");
                        String direccion = document.getString("Direccion");
                        c6.setText(nombre);
                        d6.setText(direccion);

                    }

                }
            }
        });

    }


    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.textViewC1:

            case R.id.textViewD1:

                Intent carteleraABC1 = new Intent(this, CarteleraABC.class);
                cineValor = "1";
                carteleraABC1.putExtra("clave", cineValor);
                startActivity(carteleraABC1);

                break;

            case R.id.textViewC2:

            case R.id.textViewD2:

                Intent carteleraLys = new Intent(this, CarteleraABC.class);
                cineValor = "2";
                carteleraLys.putExtra("clave", cineValor);
                startActivity(carteleraLys);

                break;


            case R.id.textViewC3:

            case R.id.textViewD3:

                Intent carteleraYelmo = new Intent(this, CarteleraABC.class);
                cineValor = "3";
                carteleraYelmo.putExtra("clave", cineValor);
                startActivity(carteleraYelmo);

                break;


        }


    }

}