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
    String IdSesionCerra;
    ArrayList<String> numeroButacasCerra;

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
        cancelar = (TextView) findViewById(R.id.textViewCancelarPago);


        String IdCine = getIntent().getStringExtra("idCine");
        String IdSesion = getIntent().getStringExtra("idSesione");
        String Sala = getIntent().getStringExtra("nombreSala");
        String[] numeroButacasArray = getIntent().getStringArrayExtra("numeroButacas");
        ArrayList<String> numeroButacas = new ArrayList<>(Arrays.asList(numeroButacasArray));

        pasarIdCine = IdCine;
        pasarIdSesion = IdSesion;
        pasarSala = Sala;
        pasarNumeroButcasArray = numeroButacasArray;

        tarjeta.setOnClickListener(this::onClick);
        efectivo.setOnClickListener(this::onClick);


        IdSesionCerra = IdSesion;
        numeroButacasCerra = numeroButacas;

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicializa FirebaseFirestore
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // Obtiene la referencia al documento de la sesión que deseas modificar
                DocumentReference sessionRef = db.collection("sesiones").document(IdSesion);

                // Elimina los elementos del array asientos del atributo NumeroAsientos en Firestore
                sessionRef.update("AsientosReservados", FieldValue.arrayRemove(numeroButacas.toArray()))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("Firestore", "Elementos eliminados con éxito de NumeroAsientos");

                                Intent cancelarTransaccion = new Intent(getApplicationContext(), PantallaPrincipal.class);
                                startActivity(cancelarTransaccion);

                                Toast.makeText(getApplicationContext(), "La transacción se ha cancelado", Toast.LENGTH_SHORT).show();


                                // Aquí puedes agregar acciones adicionales después de eliminar los asientos correctamente
                                // Por ejemplo, mostrar un mensaje o redirigir a otra actividad
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Firestore", "Error al eliminar elementos de NumeroAsientos", e);

                                // Aquí puedes agregar acciones adicionales en caso de que ocurra un error al eliminar los asientos
                                // Por ejemplo, mostrar un mensaje de error
                            }
                        });
            }
        });

    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            // Aquí puedes agregar el código que deseas ejecutar cuando la aplicación se cierra
            // Por ejemplo, guardar los cambios, detener una tarea en segundo plano, etc.

            // Inicializa FirebaseFirestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();


            // Obtiene la referencia al documento de la sesión que deseas modificar
            DocumentReference sessionRef = db.collection("sesiones").document(IdSesionCerra);

            // Elimina los elementos del array asientos del atributo NumeroAsientos en Firestore
            sessionRef.update("AsientosReservados", FieldValue.arrayRemove(numeroButacasCerra.toArray()))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("Firestore", "Elementos eliminados con éxito de NumeroAsientos");

                            // Aquí puedes agregar acciones adicionales después de eliminar los asientos correctamente
                            // Por ejemplo, mostrar un mensaje o redirigir a otra actividad
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("Firestore", "Error al eliminar elementos de NumeroAsientos", e);

                            // Aquí puedes agregar acciones adicionales en caso de que ocurra un error al eliminar los asientos
                            // Por ejemplo, mostrar un mensaje de error
                        }
                    });

        }
    }


    public void onBackPressed() {
        // No llames a super.onBackPressed() para evitar que regrese a la pantalla anterior
    }


    public void onClick(View v) {

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

