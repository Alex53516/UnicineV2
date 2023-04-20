package com.example.unicine;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Reservas extends AppCompatActivity {

    String userId;
    int completedQueries = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        List<String> userNameTicket = new ArrayList<>();
        List<String> nombreCineTicket = new ArrayList<>();
        List<String> nombreSalaTicket = new ArrayList<>();
        List<String> fechatTicket = new ArrayList<>();
        List<String> horaTicket = new ArrayList<>();
        List<String> asientosTicket = new ArrayList<>();
        List<String> moovieName = new ArrayList<>();





        // Obtiene la instancia actual de FirebaseAuth
        FirebaseAuth auth = FirebaseAuth.getInstance();
        // Obtiene el usuario conectado
        FirebaseUser user = auth.getCurrentUser();

        String userDisplayName = user.getDisplayName();
        userNameTicket.add(userDisplayName);

        completedQueries++;
        updateAdapterIfReady(nombreCineTicket, nombreSalaTicket, fechatTicket, horaTicket, asientosTicket, userNameTicket, moovieName);




        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .whereEqualTo("name", userDisplayName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                userId = document.getId();
                                Log.d("Firestore", "ID del usuario encontrado: " + userId);


                                db.collection("ticket")
                                        .whereEqualTo("idUsuario", userId)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        // Aquí puedes obtener los atributos del documento
                                                        String idCine = document.getString("idCine");
                                                        String idSesion = document.getString("idSesion");
                                                        List<String> asientos = (List<String>) document.get("asientos");
                                                        String asientosString = TextUtils.join(", ", asientos);

                                                        Log.d("Firestore", "Asientos: " + asientosString);


                                                        asientosTicket.add(asientosString);
                                                        completedQueries++;
                                                        updateAdapterIfReady(nombreCineTicket, nombreSalaTicket, fechatTicket, horaTicket, asientosTicket, userNameTicket, moovieName);



                                                        // Obtiene los datos del cine
                                                        db.collection("cines")
                                                                .document(idCine)
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                        if (task.isSuccessful()) {
                                                                            DocumentSnapshot cineDocument = task.getResult();
                                                                            if (cineDocument.exists()) {
                                                                                // Aquí puedes obtener los atributos del cine
                                                                                String nombreCine = cineDocument.getString("Nombre");
                                                                                completedQueries++;
                                                                                Log.d("Firestore", "Nombre del cine: " + nombreCine);

                                                                                updateAdapterIfReady(nombreCineTicket, nombreSalaTicket, fechatTicket, horaTicket, asientosTicket, userNameTicket, moovieName);


                                                                                nombreCineTicket.add(nombreCine);

                                                                                // Aquí puedes utilizar los datos obtenidos para realizar otras acciones
                                                                            } else {
                                                                                Log.w("Firestore", "No se encontró el documento de cine");
                                                                            }
                                                                        } else {
                                                                            Log.w("Firestore", "Error al obtener el documento de cine", task.getException());
                                                                        }
                                                                    }
                                                                });

                                                        // Obtiene los datos de la sesión
                                                        db.collection("sesiones")
                                                                .document(idSesion)
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                        if (task.isSuccessful()) {
                                                                            DocumentSnapshot sesionDocument = task.getResult();
                                                                            if (sesionDocument.exists()) {
                                                                                // Aquí puedes obtener los atributos de la sesión
                                                                                String fecha = sesionDocument.getString("Fecha");
                                                                                String hora = sesionDocument.getString("Hora");
                                                                                String idPelicula = sesionDocument.getString("IdPelicula");

                                                                                Log.d("Firestore", "Fecha: " + fecha);
                                                                                Log.d("Firestore", "Hora: " + hora);


                                                                                fechatTicket.add(fecha);
                                                                                updateAdapterIfReady(nombreCineTicket, nombreSalaTicket, fechatTicket, horaTicket, asientosTicket, userNameTicket, moovieName);
                                                                                completedQueries++;

                                                                                horaTicket.add(hora);
                                                                                updateAdapterIfReady(nombreCineTicket, nombreSalaTicket, fechatTicket, horaTicket, asientosTicket, userNameTicket, moovieName);
                                                                                completedQueries++;

                                                                                String idSala = sesionDocument.getString("IdSala");

                                                                                // Obtiene los datos de la película
                                                                                db.collection("peliculas")
                                                                                        .document(idPelicula)
                                                                                        .get()
                                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    DocumentSnapshot peliculaDocument = task.getResult();
                                                                                                    if (peliculaDocument.exists()) {
                                                                                                        // Aquí puedes obtener los atributos de la película
                                                                                                        String nombrePelicula = peliculaDocument.getString("Nombre");

                                                                                                        moovieName.add(nombrePelicula);

                                                                                                        updateAdapterIfReady(nombreCineTicket, nombreSalaTicket, fechatTicket, horaTicket, asientosTicket, userNameTicket, moovieName);
                                                                                                        completedQueries++;


                                                                                                        // Aquí puedes utilizar los datos obtenidos para realizar otras acciones
                                                                                                    } else {
                                                                                                        Log.w("Firestore", "No se encontró el documento de película");
                                                                                                    }
                                                                                                } else {
                                                                                                    Log.w("Firestore", "Error al obtener el documento de película", task.getException());
                                                                                                }
                                                                                            }
                                                                                        });

                                                                                // Obtiene los datos de la sala
                                                                                db.collection("salas")
                                                                                        .document(idSala)
                                                                                        .get()
                                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    DocumentSnapshot salaDocument = task.getResult();
                                                                                                    if (salaDocument.exists()) {
                                                                                                        // Aquí puedes obtener los atributos de la sala
                                                                                                        String nombreSala = salaDocument.getString("Nombre");

                                                                                                        Log.d("Firestore", "Nombre de la sala: " + nombreSala);


                                                                                                        nombreSalaTicket.add(nombreSala);
                                                                                                        completedQueries++;

                                                                                                        updateAdapterIfReady(nombreCineTicket, nombreSalaTicket, fechatTicket, horaTicket, asientosTicket, userNameTicket, moovieName);


                                                                                                        // Aquí puedes utilizar los datos obtenidos para realizar otras acciones
                                                                                                    } else {
                                                                                                        Log.w("Firestore", "No se encontró el documento de sala");
                                                                                                    }
                                                                                                } else {
                                                                                                    Log.w("Firestore", "Error al obtener el documento de sala", task.getException());
                                                                                                }
                                                                                            }
                                                                                        });

                                                                                // Aquí puedes utilizar los datos obtenidos para realizar otras acciones
                                                                            }
                                                                            else {
                                                                                Log.w("Firestore", "No se encontró el documento de sesión");
                                                                            }
                                                                        } else {
                                                                            Log.w("Firestore", "Error al obtener el documento de sesión", task.getException());
                                                                        }
                                                                    }
                                                                });

                                                        // Aquí puedes utilizar los datos obtenidos para realizar otras acciones
                                                    }

                                                } else {
                                                    Log.w("Firestore", "Error al obtener documentos", task.getException());
                                                }
                                            }
                                        });


                            }
                        } else {
                            Log.w("Firestore", "Error al obtener documentos", task.getException());
                        }
                    }

                });


    }


    private void updateAdapterIfReady(List<String> userNameTicket, List<String> nombreCineTicket, List<String> nombreSalaTicket, List<String> fechatTicket, List<String> horaTicket, List<String> asientosTicket, List<String> moovieName) {
        int totalQueries = 7; // Cambia esto al número total de consultas que estás realizando

        if (completedQueries == totalQueries) {
            TicketAdapter ticketAdapter = new TicketAdapter(nombreCineTicket, nombreSalaTicket, fechatTicket, horaTicket, asientosTicket, userNameTicket, moovieName);
            RecyclerView recyclerView = findViewById(R.id.recycler_view3);
            recyclerView.setAdapter(ticketAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(Reservas.this));
        }
    }




}