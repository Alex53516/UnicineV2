package com.example.unicine;

import static android.content.ContentValues.TAG;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SeleccionAsiento extends AppCompatActivity {

    TextView Titulo, reservar;
    TextView Butaca1, Butaca2, Butaca3, Butaca4, Butaca5, Butaca6, Butaca7, Butaca8, Butaca9, Butaca10, Butaca11, Butaca12, Butaca13, Butaca14,
             Butaca15, Butaca16, Butaca17, Butaca18, Butaca19, Butaca20, Butaca21, Butaca22, Butaca23, Butaca24, Butaca25, Butaca26, Butaca27,
             Butaca28, Butaca29, Butaca30, Butaca31, Butaca32, Butaca33, Butaca34, Butaca35, Butaca36, Butaca37, Butaca38, Butaca39, Butaca40,
             Butaca41, Butaca42, Butaca43, Butaca44, Butaca45;

    int contadorAsientos = 0;

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

        reservar = (TextView) findViewById(R.id.textViewReservarAsientos);

        Butaca1 = findViewById(R.id.textview_verde1); Butaca2 = findViewById(R.id.textview_verde2); Butaca3 = findViewById(R.id.textview_verde3); Butaca4 = findViewById(R.id.textview_verde4); Butaca5 = findViewById(R.id.textview_verde5);
        Butaca6 = findViewById(R.id.textview_verde6); Butaca7 = findViewById(R.id.textview_verde7); Butaca8 = findViewById(R.id.textview_verde8); Butaca9 = findViewById(R.id.textview_verde9); Butaca10 = findViewById(R.id.textview_verde10);
        Butaca11 = findViewById(R.id.textview_verde11); Butaca12 = findViewById(R.id.textview_verde12); Butaca13 = findViewById(R.id.textview_verde13); Butaca14 = findViewById(R.id.textview_verde14); Butaca15 = findViewById(R.id.textview_verde15);
        Butaca16 = findViewById(R.id.textview_verde16); Butaca17 = findViewById(R.id.textview_verde17); Butaca18 = findViewById(R.id.textview_verde18); Butaca19 = findViewById(R.id.textview_verde19); Butaca20 = findViewById(R.id.textview_verde20);
        Butaca21 = findViewById(R.id.textview_verde21); Butaca22 = findViewById(R.id.textview_verde22); Butaca23 = findViewById(R.id.textview_verde23); Butaca24 = findViewById(R.id.textview_verde24); Butaca25 = findViewById(R.id.textview_verde25);
        Butaca26 = findViewById(R.id.textview_verde26); Butaca27 = findViewById(R.id.textview_verde27); Butaca28 = findViewById(R.id.textview_verde28); Butaca29 = findViewById(R.id.textview_verde29); Butaca30 = findViewById(R.id.textview_verde30);
        Butaca31 = findViewById(R.id.textview_verde31); Butaca32 = findViewById(R.id.textview_verde32); Butaca33 = findViewById(R.id.textview_verde33); Butaca34 = findViewById(R.id.textview_verde34); Butaca35 = findViewById(R.id.textview_verde35);
        Butaca36 = findViewById(R.id.textview_verde36); Butaca37 = findViewById(R.id.textview_verde37); Butaca38 = findViewById(R.id.textview_verde38); Butaca39 = findViewById(R.id.textview_verde39); Butaca40 = findViewById(R.id.textview_verde40);
        Butaca41 = findViewById(R.id.textview_verde41); Butaca42 = findViewById(R.id.textview_verde42); Butaca43 = findViewById(R.id.textview_verde43); Butaca44 = findViewById(R.id.textview_verde44); Butaca45 = findViewById(R.id.textview_verde45);

        // Creamos un ArrayList para guardar todas las butacas
        ArrayList<TextView> butacas = new ArrayList<>();
        butacas.add(Butaca1);butacas.add(Butaca2);butacas.add(Butaca3);butacas.add(Butaca4);butacas.add(Butaca5);butacas.add(Butaca6);butacas.add(Butaca7);butacas.add(Butaca8);butacas.add(Butaca9);butacas.add(Butaca10);
        butacas.add(Butaca11);butacas.add(Butaca12);butacas.add(Butaca13);butacas.add(Butaca14);butacas.add(Butaca15);butacas.add(Butaca16);butacas.add(Butaca17);butacas.add(Butaca18);butacas.add(Butaca19);butacas.add(Butaca20);
        butacas.add(Butaca21);butacas.add(Butaca22);butacas.add(Butaca23);butacas.add(Butaca24);butacas.add(Butaca25);butacas.add(Butaca26);butacas.add(Butaca27);butacas.add(Butaca28);butacas.add(Butaca29);butacas.add(Butaca30);
        butacas.add(Butaca31);butacas.add(Butaca32);butacas.add(Butaca33);butacas.add(Butaca34);butacas.add(Butaca35);butacas.add(Butaca36);butacas.add(Butaca37);butacas.add(Butaca38);butacas.add(Butaca39);butacas.add(Butaca40);
        butacas.add(Butaca41);butacas.add(Butaca42);butacas.add(Butaca43);butacas.add(Butaca44);butacas.add(Butaca45);


        // Declara el ArrayList asientos antes del bucle for
        ArrayList<String> numeroButacas = new ArrayList<>();


        // Inicializa FirebaseFirestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Obtiene la referencia al documento de la sesión que deseas leer
        DocumentReference sessionRef = db.collection("sesiones").document(IdSesion);


        // Lee el documento y obtiene el array AsientosCogidos
        sessionRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<String> asientosCogidos = (List<String>) document.get("AsientosReservados");

                        // Verifica si asientosCogidos no es nulo antes de utilizarlo
                        if (asientosCogidos != null) {
                            // Comprueba si alguno de los textos de las butacas coincide con un elemento en asientosCogidos
                            for (TextView butaca : butacas) {
                                String textoButaca = butaca.getText().toString();
                                if (asientosCogidos.contains(textoButaca)) {

                                    butaca.setBackground(getResources().getDrawable(R.drawable.butaca_sahape_roja));
                                    butaca.setClickable(false);

                                }
                            }
                        } else {
                            Log.d("Firestore", "El atributo AsientosCogidos es nulo");
                        }
                    } else {
                        Log.d("Firestore", "No existe el documento con ese ID");
                    }
                } else {
                    Log.d("Firestore", "Error al obtener el documento", task.getException());
                }
            }
        });



        for (TextView butaca : butacas) {
            butaca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Obtiene el color actual del texto
                    int currentTextColor = butaca.getCurrentTextColor();

                    // Define los colores y el background para cada estado
                    int originalTextColor = Color.parseColor("#FFFFFF"); // Reemplaza esto con el color de texto original
                    int selectedTextColor = Color.parseColor("#499B54");
                    Drawable originalBackground = getResources().getDrawable(R.drawable.butaca_shape); // Reemplaza esto con el nombre del background original
                    Drawable selectedBackground = getResources().getDrawable(R.drawable.butaca_shape_invertido);

                    // Verifica el color del texto y realiza acciones diferentes
                    if (currentTextColor == originalTextColor) {
                        butaca.setTextColor(selectedTextColor);
                        butaca.setBackground(selectedBackground);

                        // Agrega el texto del TextView al ArrayList asientos
                        String textoButaca = butaca.getText().toString();
                        numeroButacas.add(textoButaca);
                        contadorAsientos ++;

                    } else {
                        butaca.setTextColor(originalTextColor);
                        butaca.setBackground(originalBackground);

                        // Remueve el texto del TextView del ArrayList asientos
                        String textoButaca = butaca.getText().toString();
                        numeroButacas.remove(textoButaca);

                    }


                }
            });
        }

        reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Inicializa FirebaseFirestore
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // Obtiene la referencia al documento de la sesión que deseas modificar
                DocumentReference sessionRef = db.collection("sesiones").document(IdSesion);

                // Agrega los elementos del ArrayList asientos al array AsientosReservados en Firestore
                sessionRef.update("AsientosReservados", FieldValue.arrayUnion(numeroButacas.toArray()))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("Firestore", "Elementos agregados con éxito a AsientosReservados");

                                if (contadorAsientos > 1){

                                    Toast.makeText(getApplicationContext(), "Asientos reservados correctamente", Toast.LENGTH_SHORT).show();

                                }else{

                                    Toast.makeText(getApplicationContext(), "Asiento reservado correctamente", Toast.LENGTH_SHORT).show();

                                }


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Firestore", "Error al agregar elementos a AsientosReservados", e);
                            }
                        });

            }
        });





        int color = ContextCompat.getColor(this, R.color.azul);

        getWindow().setNavigationBarColor(color);
        getWindow().setStatusBarColor(color);

    }


}