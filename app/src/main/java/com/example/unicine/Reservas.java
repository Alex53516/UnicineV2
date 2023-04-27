package com.example.unicine;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
    List<String> ticketsID;
    TextView sinTiquet;


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
        List<String> peliculaTicket = new ArrayList<>();
        List<String> idSesiones = new ArrayList<>();
        List<String> idTickets = new ArrayList<>();
        List<String> precioTickets= new ArrayList<>();



        int color = ContextCompat.getColor(this, R.color.azul);

        sinTiquet = (TextView) findViewById(R.id.textViewSinTiquet);

        sinTiquet.setVisibility(View.INVISIBLE);


        getWindow().setNavigationBarColor(color);
        getWindow().setStatusBarColor(color);



        // Obtiene la instancia actual de FirebaseAuth
        FirebaseAuth auth = FirebaseAuth.getInstance();
        // Obtiene el usuario conectado
        FirebaseUser user = auth.getCurrentUser();

        String userDisplayName = user.getDisplayName();
        userNameTicket.add(userDisplayName);


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // ...

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

                                ticketsID = (List<String>) document.get("tickets");

                                if (ticketsID.size() == 0){

                                    sinTiquet.setVisibility(View.VISIBLE);
                                }

                                assert ticketsID != null;

                                for (String ticketsIDS : ticketsID) {
                                    db.collection("ticket")
                                            .document(ticketsIDS)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot document = task.getResult();
                                                        if (document.exists()) {

                                                            // Aquí puedes obtener los atributos del documento
                                                            String cine = document.getString("Cine");
                                                            String sala = document.getString("Sala");
                                                            List<String> asientos = (List<String>) document.get("Asientos");
                                                            String asientosString = TextUtils.join(", ", asientos);
                                                            String pelicula = document.getString("Pelicula");
                                                            String fecha = document.getString("Fecha");
                                                            String hora = document.getString("Hora");
                                                            String idSesion = document.getString("IdSesion");
                                                            String idTicket = document.getId();
                                                            String precio = document.getString("Precio");

                                                            Log.d("Firestore", "Asientos: " + asientosString);

                                                            asientosTicket.add(asientosString);
                                                            nombreCineTicket.add(cine);
                                                            nombreSalaTicket.add(sala);
                                                            peliculaTicket.add(pelicula);
                                                            fechatTicket.add(fecha);
                                                            horaTicket.add(hora);
                                                            idTickets.add(idTicket);
                                                            idSesiones.add(idSesion);
                                                            precioTickets.add(precio);


                                                            TicketAdapter ticketAdapter = new TicketAdapter(nombreCineTicket, nombreSalaTicket, fechatTicket, horaTicket, asientosTicket, userNameTicket, peliculaTicket, idTickets, idSesiones, precioTickets);
                                                            RecyclerView recyclerView = findViewById(R.id.recycler_view3);
                                                            recyclerView.setAdapter(ticketAdapter);
                                                            recyclerView.setLayoutManager(new LinearLayoutManager(Reservas.this));



                                                        }
                                                    }
                                                }
                                            });
                                }

                            }
                        }
                    }
                });
    }
}
