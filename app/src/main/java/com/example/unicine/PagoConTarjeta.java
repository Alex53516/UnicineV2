package com.example.unicine;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PagoConTarjeta extends AppCompatActivity {

    double precioEntrada = 6.80;
    TextView operacion, resultadoOperacion, fecha, pagar;
    EditText nom, codigo, numerTar;
    String nombreCine;
    String fechas;
    String horas;
    String pelicula;
    String userId;
    String idPelicula;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_con_tarjeta);

        String idCine = getIntent().getStringExtra("idCine");
        String idSesione = getIntent().getStringExtra("idSesione");
        String nombreSala = getIntent().getStringExtra("nombreSala");
        String[] numeroButacas = getIntent().getStringArrayExtra("numeroButacas");
        ArrayList<String> numeroButacasCoger = new ArrayList<>(Arrays.asList(numeroButacas));



        int numeroBu = numeroButacas.length;

        int color = ContextCompat.getColor(this, R.color.azul);

        getWindow().setNavigationBarColor(color);
        getWindow().setStatusBarColor(color);

        double result = (numeroBu * precioEntrada);
        String resultString = String.valueOf(result);

        operacion = (TextView) findViewById(R.id.textViewOperacion);
        resultadoOperacion = (TextView) findViewById(R.id.textViewOperacionResul);
        fecha = (TextView) findViewById(R.id.textViewFechaTarjeta);
        pagar = (TextView) findViewById(R.id.textViewRealizarPago);
        nom = (EditText) findViewById(R.id.my_edittextNombreTarjeta);
        codigo = (EditText) findViewById(R.id.my_edittextCodigoTarjeta);
        numerTar = (EditText) findViewById(R.id.my_editText_numeroTarjeta);

        Log.d("Datos", "idCine = " + idCine + "idSesion = " + idSesione + "nombreSala = " + nombreSala + "NumeroAsientos = " + numeroButacas.length);

        operacion.setText(precioEntrada + " X " + numeroBu + " = ");
        resultadoOperacion.setText(resultString + "€");

        pagar.setText("\n" + "Pagar " + resultString + "€");

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(PagoConTarjeta.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        fecha.setText(selectedDate);
                    }
                }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });


        pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean avanzar = true;

                String numerTarString = numerTar.getText().toString();


                if (numerTarString.length() == 16){

                    avanzar = true;

                }else{

                    Toast.makeText(getApplicationContext(), "El numero de la tarjeta no es válido", Toast.LENGTH_SHORT).show();
                    avanzar = false;

                }

                if (codigo.getText().length() == 3){

                    avanzar = true;

                }else{

                    Toast.makeText(getApplicationContext(), "El codigo de seguridad de la tarjeta no es válido", Toast.LENGTH_SHORT).show();

                    avanzar = false;

                }

                if (nom.getText().length() < 1 ){

                    Toast.makeText(getApplicationContext(), "No puedes dejar el nombre vacio", Toast.LENGTH_SHORT).show();

                    avanzar = false;

                }

                if (fecha.getText().equals("")){

                    Toast.makeText(getApplicationContext(), "No puedes dejar la fecha vacia", Toast.LENGTH_SHORT).show();

                    avanzar = false;

                }


                if (avanzar == true){


                    // Inicializa FirebaseFirestore
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    // Obtiene la referencia al documento de la sesión que deseas modificar
                    DocumentReference sessionRef = db.collection("sesiones").document(idSesione);

                    // Agrega los elementos del ArrayList asientos al array AsientosReservados en Firestore
                    sessionRef.update("AsientosReservados", FieldValue.arrayUnion(numeroButacasCoger.toArray()))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("Firestore", "Elementos agregados con éxito a AsientosReservados");

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("Firestore", "Error al agregar elementos a AsientosReservados", e);
                                }
                            });


                    // Inicializa FirebaseFirestore

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser user = auth.getCurrentUser();
                    String userName = user.getDisplayName();

                    db.collection("cines").document(idCine)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                             nombreCine = document.getString("Nombre");

                                            db.collection("sesiones").document(idSesione)
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                DocumentSnapshot document = task.getResult();
                                                                if (document.exists()) {
                                                                    fechas = document.getString("Fecha");
                                                                    horas = document.getString("Hora");
                                                                    idPelicula = document.getString("IdPelicula");

                                                                    db.collection("peliculas").document(idPelicula)
                                                                            .get()
                                                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        DocumentSnapshot document = task.getResult();
                                                                                        if (document.exists()) {
                                                                                            pelicula = document.getString("Nombre");


                                                                                            db.collection("users")
                                                                                                    .whereEqualTo("name", userName)
                                                                                                    .get()
                                                                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                        @Override
                                                                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                            if (task.isSuccessful()) {
                                                                                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                                                    userId = document.getId();
                                                                                                                    Log.d("Firestore", "ID del usuario encontrado: " + userId);

                                                                                                                    // Crea un nuevo objeto Map que contendrá los atributos y sus valores
                                                                                                                    Map<String, Object> ticket = new HashMap<>();

                                                                                                                    // Agrega los pares clave-valor al objeto Map
                                                                                                                    ticket.put("Cine", nombreCine);
                                                                                                                    ticket.put("Sala", nombreSala);
                                                                                                                    ticket.put("Pelicula", pelicula);
                                                                                                                    ticket.put("Fecha", fechas);
                                                                                                                    ticket.put("Hora", horas);
                                                                                                                    ticket.put("Usuario", userId);
                                                                                                                    ticket.put("Asientos", numeroButacasCoger);
                                                                                                                    ticket.put("IdSesion", idSesione);

                                                                                                                    // Agrega un nuevo documento a la colección "ticket" con los atributos y valores especificados
                                                                                                                    db.collection("ticket")
                                                                                                                            .add(ticket)
                                                                                                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                                                                                @Override
                                                                                                                                public void onSuccess(DocumentReference documentReference) {
                                                                                                                                    Log.d(TAG, "Documento añadido con ID: " + documentReference.getId());

                                                                                                                                    // Actualiza el documento del usuario en la colección "users" añadiendo el ID del ticket a la lista "idtickets"
                                                                                                                                    db.collection("users").document(userId)
                                                                                                                                            .update("tickets", FieldValue.arrayUnion(documentReference.getId()))
                                                                                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                                                @Override
                                                                                                                                                public void onSuccess(Void aVoid) {
                                                                                                                                                    Log.d(TAG, "idtickets actualizado con éxito");
                                                                                                                                                    Toast.makeText(getApplicationContext(), "Asientos reservados correctamente", Toast.LENGTH_SHORT).show();

                                                                                                                                                }
                                                                                                                                            })
                                                                                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                                                                                @Override
                                                                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                                                                    Log.w(TAG, "Error al actualizar idtickets", e);
                                                                                                                                                }
                                                                                                                                            });

                                                                                                                                    Intent pagado = new Intent(getApplicationContext(), PantallaPrincipal.class);
                                                                                                                                    startActivity(pagado);

                                                                                                                                    // Aquí puedes realizar acciones adicionales después de agregar el documento correctamente
                                                                                                                                    // Por ejemplo, mostrar un mensaje de éxito o redirigir a otra actividad
                                                                                                                                }
                                                                                                                            })
                                                                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                                                                @Override
                                                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                                                    Log.w(TAG, "Error al añadir el documento", e);

                                                                                                                                    // Aquí puedes realizar acciones adicionales en caso de que ocurra un error al agregar el documento
                                                                                                                                    // Por ejemplo, mostrar un mensaje de error
                                                                                                                                }
                                                                                                                            });
                                                                                                                }
                                                                                                            } else {
                                                                                                                Log.w("Firestore", "Error al obtener documentos", task.getException());
                                                                                                            }
                                                                                                        }
                                                                                                    });

                                                                                            // hacer algo con el nombre del cine
                                                                                        } else {
                                                                                            // el documento no existe
                                                                                        }
                                                                                    } else {
                                                                                        // error al obtener el documento
                                                                                    }
                                                                                }
                                                                            });

                                                                    // hacer algo con el nombre del cine
                                                                } else {
                                                                    // el documento no existe
                                                                }
                                                            } else {
                                                                // error al obtener el documento
                                                            }
                                                        }
                                                    });

                                            // hacer algo con el nombre del cine
                                        } else {
                                            // el documento no existe
                                        }
                                    } else {
                                        // error al obtener el documento
                                    }
                                }
                            });


                }

            }

        });
    }


}