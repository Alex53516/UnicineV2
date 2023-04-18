package com.example.unicine;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SeleccionAsiento extends AppCompatActivity {

    TextView Titulo;
    TextView Butaca1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_asiento);

        String IdCine = getIntent().getStringExtra("idCines");
        String IdSesion = getIntent().getStringExtra("idSesiones");
        String Sala = getIntent().getStringExtra("nombreSala");

        Log.d(TAG, "El id del cine es "+ IdCine + " El id de la sesion es "+ IdSesion);


        Titulo = (TextView) findViewById(R.id.textViewNomSala);
        Titulo.setText("Asientos de la sala " + Sala);


        int color = ContextCompat.getColor(this, R.color.azul);

        getWindow().setNavigationBarColor(color);
        getWindow().setStatusBarColor(color);


    }
}