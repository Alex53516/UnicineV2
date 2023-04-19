package com.example.unicine;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MetodosDePago extends AppCompatActivity {

    ImageView tarjeta, efectivo;
    TextView cancelar;

    String pasarIdCine;
    String pasarIdSesion;
    String pasarSala;
    String [] pasarNumeroButcasArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodos_de_pago);


        int color = ContextCompat.getColor(this, R.color.azul);

        getWindow().setNavigationBarColor(color);
        getWindow().setStatusBarColor(color);

        tarjeta = (ImageView) findViewById(R.id.imageViewTarjeta);
        efectivo = (ImageView) findViewById(R.id.imageViewEfectivo);


        String IdCine = getIntent().getStringExtra("idCine");
        String IdSesion = getIntent().getStringExtra("idSesione");
        String Sala = getIntent().getStringExtra("nombreSala");
        String[] numeroButacasArray = getIntent().getStringArrayExtra("numeroButacas");

        pasarIdCine = IdCine;
        pasarIdSesion = IdSesion;
        pasarSala = Sala;
        pasarNumeroButcasArray = numeroButacasArray;

        tarjeta.setOnClickListener(this::onClick);
        efectivo.setOnClickListener(this::onClick);


    }


    public void onClick(View v){

            switch (v.getId()) {


                case R.id.imageViewTarjeta:

                    Intent tarjeta = new Intent(this, PagoConTarjeta.class);

                    tarjeta.putExtra("idCine", pasarIdCine);
                    tarjeta.putExtra("idSesione", pasarIdSesion);
                    tarjeta.putExtra("nombreSala", pasarSala);
                    tarjeta.putExtra("numeroButacas", pasarNumeroButcasArray);

                    startActivity(tarjeta);

                    break;

                case R.id.imageViewEfectivo:


                    break;

            }

        }
    }


